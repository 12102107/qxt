package io.renren.modules.us.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.eidlink.sdk.EidlinkService;
import com.eidlink.sdk.pojo.request.MOMTRealNameParam;
import com.eidlink.sdk.pojo.request.base.MOMTRealNameParameters;
import com.eidlink.sdk.pojo.request.base.RealName;
import com.eidlink.sdk.pojo.result.CommonResult;
import io.renren.common.exception.RRException;
import io.renren.common.utils.Constant;
import io.renren.common.utils.R;
import io.renren.common.utils.RedisUtils;
import io.renren.modules.us.dao.UsUserDao;
import io.renren.modules.us.entity.UsUserEntity;
import io.renren.modules.us.param.UsEidLoginParam;
import io.renren.modules.us.param.UsSessionParam;
import io.renren.modules.us.param.UsUserAuthParam;
import io.renren.modules.us.service.UsUserEidService;
import io.renren.modules.us.service.UsUserService;
import io.renren.modules.us.util.UsCardNumberUtil;
import io.renren.modules.us.util.UsCryptoUtil;
import io.renren.modules.us.util.UsSessionUtil;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;

import java.io.UnsupportedEncodingException;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.UUID;

@Service("usUserEidService")
public class UsUserEidServiceImpl extends ServiceImpl<UsUserDao, UsUserEntity> implements UsUserEidService {

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${us.eid.content}")
    private String content;

    @Value("${us.eid.displayed}")
    private String displayed;

    @Value("${us.eid.returnUrl}")
    private String url;

    private UsUserService userService;

    private RedisUtils redisUtil;

    private UsSessionUtil sessionUtil;

    private UsCardNumberUtil cardNumberUtil;

    private boolean verifyEid(UsUserEntity user) throws InterruptedException, UnsupportedEncodingException {
        //验证参数
        if (user == null || user.getMobilePhone() == null || user.getRealname() == null || user.getCitizenNo() == null || "".equals(user.getMobilePhone())
                || "".equals(user.getRealname()) || "".equals(user.getCitizenNo())) {
            logger.error("EID认证所需要的信息不完整");
            throw new RRException("EID认证所需要的信息不完整");
        }
        //发送EID验证请求
        String mobile = user.getMobilePhone();
        String realName = user.getRealname();
        String citizenNo = user.getCitizenNo();
        String datetime = new SimpleDateFormat("yyyyMMddHHmmss").format(new Date());
        //业务id
        String seqno = UUID.randomUUID().toString().replaceAll("-", "");
        String dataToSign = UsCryptoUtil.base64Encode(datetime + ":" + seqno + ":" + content);
        //签名
        String dataToBeDisplayed = UsCryptoUtil.base64Encode(displayed);
        //异步接收路径
        String returnUrl = url + "?eid=" + seqno;
        RealName realNameObj = new RealName(realName, citizenNo);
        MOMTRealNameParameters pkiParam = new MOMTRealNameParameters(mobile, dataToSign, dataToBeDisplayed);
        MOMTRealNameParam reqParam = new MOMTRealNameParam(pkiParam, realNameObj);
        reqParam.setReturnUrl(returnUrl);
        //获取EID请求返回值
        CommonResult result = EidlinkService.doPost(reqParam);
        if (result == null || result.getResult() == null) {
            throw new RRException("EID请求返回值为空");
        }
        //EID请求成功
        if (result.getResult().equals("00")) {
            //循环获取Redis中回调结果
            for (int i = 0; i < 20; i++) {
                if (redisUtil.hasKey(seqno)) {
                    String value = redisUtil.get(seqno);
                    JSONObject json = JSONObject.parseObject(value);
                    //返回验证结果
                    return json.get("result").equals("00") && json.get("result_detail").equals("0000000");
                } else {
                    Thread.sleep(3000);
                }
            }
            logger.error("获取EID回调数据超时");
            throw new RRException("获取EID回调数据超时");
        } else {
            //EID请求失败
            logger.error(result.getResultDetail());
            throw new RRException("EID请求失败");
        }
    }

    @Override
    public R eidLogin(UsEidLoginParam param) throws InterruptedException, UnsupportedEncodingException {
        //查询用户信息
        EntityWrapper<UsUserEntity> wrapper = new EntityWrapper<>();
        wrapper.setEntity(new UsUserEntity());
        wrapper.where("mobile_phone={0}", param.getMobile());
        List<UsUserEntity> list = this.selectList(wrapper);
        if (list.isEmpty()) {
            return R.error("用户不存在");
        }
        if (list.size() != 1) {
            return R.error("手机号码重复");
        }
        UsUserEntity user = list.get(0);
        boolean b = this.verifyEid(user);
        if (b) {
            //修改user相关属性状态
            user.setUpdateDate(new Date());
            String session = UsSessionUtil.generateSession();
            user.setSession(session);
            user.setEidLevel(Constant.EidLevel.EID_LEVEL_3.getValue());
            user.setClientId(param.getClient_id());
            this.updateById(user);
            //清理失效的Session,保存新的Session
            sessionUtil.deleteSession(user.getId());
            sessionUtil.saveSession(user.getId(), session);
            return R.ok(userService.unifyUserDataReturned(user.getId(),cardNumberUtil.getIdCard()));
        } else {
            return R.error("未通过EID认证");
        }
    }

    @Override
    public R eidAuth(UsSessionParam param) throws InterruptedException, UnsupportedEncodingException {
        EntityWrapper<UsUserEntity> wrapper = new EntityWrapper<>();
        wrapper.setEntity(new UsUserEntity());
        wrapper.where("session={0}", param.getSession());
        List<UsUserEntity> list = this.selectList(wrapper);
        if (list.isEmpty()) {
            return R.error("用户不存在");
        }
        if (list.size() != 1) {
            return R.error("手机号码重复");
        }
        UsUserEntity user = list.get(0);
        boolean b = this.verifyEid(user);
        if (b) {
            //修改user相关属性状态
            user.setUpdateDate(new Date());
            user.setEidLevel(Constant.EidLevel.EID_LEVEL_3.getValue());
            this.updateById(user);
            return R.ok(userService.unifyUserDataReturned(user.getId(),cardNumberUtil.getIdCard()));
        } else {
            return R.error("未通过EID认证");
        }
    }

    @Override
    public boolean updateEidLevel(String id, Integer eidLevel) {
        UsUserEntity user = new UsUserEntity();
        user.setId(id);
        user.setEidLevel(eidLevel);
        return this.updateById(user);
    }

    @Scope("prototype")
    @Override
    public R auth(UsUserAuthParam param) throws InterruptedException, UnsupportedEncodingException {
        UsUserEntity user = new UsUserEntity();
        user.setCitizenNo(param.getCitizenNo());
        user.setRealname(param.getName());
        user.setMobilePhone(param.getMobile());
        boolean b = this.verifyEid(user);
        if (b) {
            return R.ok();
        } else {
            return R.error("实名认证失败");
        }
    }

    @Autowired
    public void setRedisUtil(RedisUtils redisUtil) {
        this.redisUtil = redisUtil;
    }

    @Autowired
    public void setSessionUtil(UsSessionUtil sessionUtil) {
        this.sessionUtil = sessionUtil;
    }

    @Autowired
    public void setUserService(UsUserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setCardNumberUtil(UsCardNumberUtil cardNumberUtil) {
        this.cardNumberUtil = cardNumberUtil;
    }
}
