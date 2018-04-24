package io.renren.modules.us.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.Constant;
import io.renren.common.utils.R;
import io.renren.modules.us.dao.UsUserCooperationDao;
import io.renren.modules.us.entity.UsUserCooperationEntity;
import io.renren.modules.us.entity.UsUserEntity;
import io.renren.modules.us.param.UsUserCooperationSignInParam;
import io.renren.modules.us.param.UsUserCooperationSignUpParam;
import io.renren.modules.us.service.UsSmsService;
import io.renren.modules.us.service.UsUserCooperationService;
import io.renren.modules.us.service.UsUserService;
import io.renren.modules.us.util.UsIdUtil;
import io.renren.modules.us.util.UsSessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Li
 */
@Service("usUserCooperationService")
public class UsUserCooperationServiceImpl extends ServiceImpl<UsUserCooperationDao, UsUserCooperationEntity> implements UsUserCooperationService {

    private UsUserService userService;

    private UsSmsService smsService;

    private List<UsUserCooperationEntity> getUserCooperation(String appid, String type, String openid) {
        EntityWrapper<UsUserCooperationEntity> wrapper = new EntityWrapper<>();
        wrapper.setEntity(new UsUserCooperationEntity());
        wrapper.where("appid={0}", appid)
                .and("type={0}", type)
                .and("openid={0}", openid);
        return this.selectList(wrapper);
    }

    private R signUpWithoutPassword(UsUserCooperationSignUpParam signUpParam) {
        //验证手机号码关联的用户是否存在
        EntityWrapper<UsUserEntity> wrapper = new EntityWrapper<>();
        wrapper.where("mobile_phone={0}", signUpParam.getMobile())
                .and("appid={0}", signUpParam.getAppid())
                .last("limit 1");
        wrapper.setEntity(new UsUserEntity());
        UsUserEntity userEntity = userService.selectOne(wrapper);
        if (userEntity == null || userEntity.getId() == null) {
            return R.error(Constant.Result.NO_REG_MOBILE.getValue(), Constant.Message.NO_REG_MOBILE.getValue());
        }
        //更新session,保存数据
        String session = UsSessionUtil.generateSession();
        userEntity.setSession(session);
        userService.updateById(userEntity);
        //注册第三方帐号
        UsUserCooperationEntity cooperationEntity = new UsUserCooperationEntity();
        cooperationEntity.setId(UsIdUtil.generateId());
        cooperationEntity.setUserId(userEntity.getId());
        cooperationEntity.setOpenid(signUpParam.getOpenid());
        cooperationEntity.setAccessToken(signUpParam.getAccessToken());
        cooperationEntity.setType(signUpParam.getType());
        cooperationEntity.setAppid(signUpParam.getAppid());
        cooperationEntity.setCreateDate(new Date());
        this.insert(cooperationEntity);
        Map<String, Object> map = new HashMap<>(1);
        map.put("session", session);
        return R.ok(map);
    }

    private R signUpWithPassword(UsUserCooperationSignUpParam signUpParam) {
        //验证手机号码关联的用户是否存在
        EntityWrapper<UsUserEntity> wrapper = new EntityWrapper<>();
        wrapper.where("mobile_phone={0}", signUpParam.getMobile())
                .and("appid={0}", signUpParam.getAppid())
                .last("limit 1");
        wrapper.setEntity(new UsUserEntity());
        UsUserEntity userEntity = userService.selectOne(wrapper);
        if (userEntity != null) {
            return R.error(Constant.Result.REG_MOBILE.getValue(), Constant.Message.REG_MOBILE.getValue());
        }
        //注册用户帐号
        userEntity = new UsUserEntity();
        String userid = UsIdUtil.generateId();
        userEntity.setId(userid);
        userEntity.setPassword(signUpParam.getPassword());
        userEntity.setMobilePhone(signUpParam.getMobile());
        userEntity.setCreateDate(new Date());
        //更新session,保存数据
        String session = UsSessionUtil.generateSession();
        userEntity.setSession(session);
        userService.insert(userEntity);
        //注册第三方帐号
        UsUserCooperationEntity cooperationEntity = new UsUserCooperationEntity();
        cooperationEntity.setId(userid);
        cooperationEntity.setUserId(userEntity.getId());
        cooperationEntity.setOpenid(signUpParam.getOpenid());
        cooperationEntity.setAccessToken(signUpParam.getAccessToken());
        cooperationEntity.setType(signUpParam.getType());
        cooperationEntity.setAppid(signUpParam.getAppid());
        cooperationEntity.setCreateDate(new Date());
        this.insert(cooperationEntity);
        Map<String, Object> map = new HashMap<>(1);
        map.put("session", session);
        return R.ok(map);
    }

    @Override
    public R signIn(UsUserCooperationSignInParam signInParam) {
        List<UsUserCooperationEntity> list = getUserCooperation(signInParam.getAppid(), signInParam.getType(), signInParam.getOpenid());
        if (list.isEmpty()) {
            return R.error(Constant.Result.COOPERATION_NOT_EXIST.getValue(), Constant.Message.COOPERATION_NOT_EXIST.getValue());
        } else if (list.size() == 1) {
            //更新token,updateDate,保存数据
            UsUserCooperationEntity entity = list.get(0);
            entity.setAccessToken(signInParam.getAccessToken());
            entity.setUpdateDate(new Date());
            this.updateById(entity);
            //更新session,保存数据
            String session = UsSessionUtil.generateSession();
            UsUserEntity userEntity = userService.selectById(entity.getUserId());
            userEntity.setSession(session);
            userService.updateById(userEntity);
            //更新session缓存
            return R.ok();
        } else {
            return R.error();
        }
    }

    @Override
    public R signUp(UsUserCooperationSignUpParam signUpParam) {
        //验证第三方帐号是否注册
        List<UsUserCooperationEntity> list = getUserCooperation(signUpParam.getAppid(), signUpParam.getType(), signUpParam.getOpenid());
        if (!list.isEmpty()) {
            return R.error(Constant.Result.COOPERATION_EXIST.getValue(), Constant.Message.COOPERATION_EXIST.getValue());
        }
        //验证短信验证码是否正确
        int result = smsService.checkCode(signUpParam.getAppid(), signUpParam.getMobile(), signUpParam.getCode());
        if (result == Constant.Result.SMS_CODE_EXPIRE.getValue()) {
            return R.error(Constant.Result.SMS_CODE_EXPIRE.getValue(), Constant.Message.SMS_CODE_EXPIRE.getValue());
        }
        if (result != Constant.Result.SMS_CODE_CORRECT.getValue()) {
            return R.error(Constant.Result.SMS_CODE_ERROR.getValue(), Constant.Message.SMS_CODE_ERROR.getValue());
        }
        //根据是否密码参数是否为空,执行不同的注册逻辑
        if (signUpParam.getPassword() == null || signUpParam.getPassword().isEmpty()) {
            return signUpWithoutPassword(signUpParam);
        } else {
            return signUpWithPassword(signUpParam);
        }
    }

    @Autowired
    public void setUserService(UsUserService userService) {
        this.userService = userService;
    }

    @Autowired
    public void setSmsService(UsSmsService smsService) {
        this.smsService = smsService;
    }
}
