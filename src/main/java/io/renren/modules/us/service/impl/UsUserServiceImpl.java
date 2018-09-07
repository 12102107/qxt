package io.renren.modules.us.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.eidlink.sdk.EidlinkService;
import com.eidlink.sdk.pojo.request.MOMTRealNameParam;
import com.eidlink.sdk.pojo.request.base.MOMTRealNameParameters;
import com.eidlink.sdk.pojo.request.base.RealName;
import com.eidlink.sdk.pojo.result.CommonResult;
import io.renren.common.exception.RRException;
import io.renren.common.utils.*;
import io.renren.modules.us.dao.UsUserDao;
import io.renren.modules.us.entity.TSTypeEntity;
import io.renren.modules.us.entity.UsUserEntity;
import io.renren.modules.us.entity.UsUserPlantParamEntity;
import io.renren.modules.us.param.*;
import io.renren.modules.us.service.TSTypeService;
import io.renren.modules.us.service.UsElectronicCardNumberService;
import io.renren.modules.us.service.UsUserPlantParamService;
import io.renren.modules.us.service.UsUserService;
import io.renren.modules.us.util.Base64Util;
import io.renren.modules.us.util.UsIdUtil;
import io.renren.modules.us.util.UsSessionUtil;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.context.annotation.Scope;
import org.springframework.stereotype.Service;
import org.springframework.transaction.annotation.Transactional;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.*;


@Service("usUserService")
public class UsUserServiceImpl extends ServiceImpl<UsUserDao, UsUserEntity> implements UsUserService {

    public static final int INITIALIZE_USER_STATUS = 0;//注册后初始状态
    public static final int REAL_USER_STATUS = 1;//实名状态

    private Logger logger = LoggerFactory.getLogger(getClass());

    @Value("${us.eid.content}")
    private String content;

    @Value("${us.eid.displayed}")
    private String displayed;

    @Value("${us.eid.returnUrl}")
    private String url;

    @Value("${us.img.uploadImg}")
    private String UPLOADImg;

    @Value("${us.img.dirTemp}")
    private String DIRTEMP;

    private RedisUtils redisUtil;

    private UsElectronicCardNumberService usElectronicCardNumber;

    private TSTypeService tSTypeService;

    private UsUserPlantParamService usUserPlantParamService;

    private UsSessionUtil sessionUtil;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<UsUserEntity> page = this.selectPage(
                new Query<UsUserEntity>(params).getPage(),
                new EntityWrapper<UsUserEntity>()
        );
        return new PageUtils(page);
    }

    @Override
    @Transactional
    public R signIn(UsLoginParam form) {
        EntityWrapper<UsUserEntity> wrapper = new EntityWrapper<>();
        wrapper.setEntity(new UsUserEntity());
        wrapper.where("mobile_phone={0}", form.getMobilePhone())
                .and("password={0}", form.getPassword());
        List<UsUserEntity> list = this.selectList(wrapper);
        if (list.isEmpty()) {
            return R.error(Constant.Result.ERROR_MOBILE.getValue(), Constant.Message.ERROR_MOBILE.getValue());
        } else if (list.size() == 1) {
            //保存数据,更新Session,更新缓存
            UsUserEntity entity = list.get(0);
            entity.setUpdateDate(new Date());
            String session = UsSessionUtil.generateSession();
            entity.setSession(session);
            entity.setAppid(form.getAppid());
            entity.setClientId(form.getClient_id());
            this.updateById(entity);
            //清理失效的Session,保存新的Session
            sessionUtil.deleteSession(entity.getId());
            sessionUtil.saveSession(entity.getId(), session);

            //保存设备信息
            UsUserPlantParamEntity userPlant = new UsUserPlantParamEntity();
            userPlant.setUserId(entity.getId());
            userPlant.setId(UsIdUtil.generateId());
            userPlant.setUnitType(form.getUnitType());
            userPlant.setEquipmentManufacturer(form.getEquipmentManufacturer());
            userPlant.setScreenResolution(form.getScreenResolution());
            userPlant.setDpi(form.getDpi());
            userPlant.setSystemName(form.getSystemName());
            userPlant.setSystemVersion(form.getSystemVersion());
            userPlant.setNetworkType(form.getNetworkType());
            userPlant.setCreateDate(new Date());
            userPlant.setAppid(form.getAppid());
            usUserPlantParamService.insert(userPlant);

            entity.setCardNumber(usElectronicCardNumber.getElectronicCardNumber(entity.getId()));
            entity.setPassword("");

            return R.ok(entity);
        } else {
            return R.error();
        }
    }

    /**
     * 返回user对象部分属性
     *
     * @param id
     * @return
     */
    public Map<String, Object> usHidden(String id) {
        EntityWrapper<UsUserEntity> w1 = new EntityWrapper<>();
        w1.setSqlSelect("session", "status", "u_jobid as uJobid",
                "u_departid as uDepartid", "remark", "realname", "id", "email", "nickname", "mobile_phone as mobilePhone", "citizen_no as citizenNo", "address", "portrait", "sex");
        w1.where("id = {0}", id);
        List<Map<String, Object>> list = this.selectMaps(w1);
        if (list == null || list.isEmpty() || list.size() == 0) {
            return null;
        }
        Map<String, Object> map = list.get(0);
        //工作单位
        String personDepartname_ = null;
        if (null != map.get("uDepartid") && !"".equals(map.get("uDepartid"))) {
            TSTypeEntity ts_ = tSTypeService.queryByCode(map.get("uDepartid").toString(), "dep_list");
            if (ts_ != null) {
                personDepartname_ = ts_.getTypename();
            }
        }
        map.put("personDepartname", personDepartname_);

        //职业
        String personJob_ = null;
        if (null != map.get("uJobid") && !"".equals(map.get("uJobid"))) {
            TSTypeEntity ts = tSTypeService.queryByCode(map.get("uJobid").toString(), "job_list");
            if (ts != null) {
                personJob_ = ts.getTypename();
            }
        }
        map.put("personJob", personJob_);
        //存取图片路径
        if (null != map.get("portrait") && !"".equals(map.get("portrait"))) {
            HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
            String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/hmPhotos" + "/";
            map.put("portrait", path + map.get("portrait").toString());
        }

        return map;
    }

    /**
     * 检查用户是否存在
     */
    @Override
    public UsUserEntity checkUserExits(String userId, String oldPassword) {
        EntityWrapper<UsUserEntity> wrapper = new EntityWrapper<>();
        wrapper.setEntity(new UsUserEntity());
        wrapper.where("id={0}", userId)
                .and("password={0}", oldPassword);
        List<UsUserEntity> list = this.selectList(wrapper);
        if (list.isEmpty()) {
            return null;
        } else if (list.size() == 1) {
            return list.get(0);
        }

        return null;
    }

    /**
     * 修改个人信息
     *
     * @param
     * @param form
     * @return
     */
    @Override
    public UsUserEntity updatePersonalInfo(UsUserEntity user, UsUserParam form) {
        user.setUpdateDate(new Date());
        if (form.getNickname() != null && !"".equals(form.getNickname())) {
            user.setNickname(form.getNickname());
        }
        if (form.getRealname() != null && !"".equals(form.getRealname())) {
            user.setRealname(form.getRealname());
        }
        if (form.getCitizenNo() != null && !"".equals(form.getCitizenNo())) {
            user.setCitizenNo(form.getCitizenNo());
        }
        if (form.getSex() != null && !"".equals(form.getSex())) {
            user.setSex(form.getSex());
        }
        if (form.getEmail() != null && !"".equals(form.getEmail())) {
            user.setEmail(form.getEmail());
        }
        if (form.getAddress() != null && !"".equals(form.getAddress())) {
            user.setAddress(form.getAddress());
        }
        if (form.getRemark() != null && !"".equals(form.getRemark())) {
            user.setRemark(form.getRemark());
        }
        if (form.getuDepartid() != null && !"".equals(form.getuDepartid())) {
            user.setuDepartid(form.getuDepartid());
        }
        if (form.getuJobid() != null && !"".equals(form.getuJobid())) {
            user.setuJobid(form.getuJobid());
        }
        user = this.queryName(user);
        user.setAppid(form.getAppid());
        this.updateById(user);
        return user;
    }

    /**
     * 根据工作单位id获取名称等
     *
     * @param
     * @return
     */
    public UsUserEntity queryName(UsUserEntity user) {
        //工作单位
        if (null != user.getuDepartid() && !"".equals(user.getuDepartid())) {
            TSTypeEntity ts_ = tSTypeService.queryByCode(user.getuDepartid(), "dep_list");
            if (ts_ != null) {
                user.setPersonDepartname(ts_.getTypename());
            }

        }
        //职业
        if (null != user.getuJobid() && !"".equals(user.getuJobid())) {
            TSTypeEntity ts = tSTypeService.queryByCode(user.getuJobid(), "job_list");
            if (ts != null) {
                user.setPersonJob(ts.getTypename());
            }
        }
        return user;
    }


    /**
     * 实名认证
     *
     * @param user
     * @param form
     * @return
     */
    @Override
    @Transactional
    public UsUserEntity realnameCert(UsUserEntity user, UsUserRealCertParam form) {

        user.setUpdateDate(new Date());

        user.setRealname(form.getRealname());
        user.setNickname(form.getNickname());
        user.setCitizenNo(form.getCitizenNo());
        user.setSex(form.getSex());
        user.setEmail(form.getEmail());
        user.setAddress(form.getAddress());

        user.setuJobid(form.getuJobid());
        user.setuDepartid(form.getuDepartid());
        user = this.queryName(user);

        user.setStatus(REAL_USER_STATUS);//已实名认证
        user.setEidLevel(Constant.EidLevel.EID_LEVEL_2.getValue());//实名认证后EID等级改为2
        user.setAppid(form.getAppid());

        this.updateById(user);
        // 实名认证成功后返回电子卡号
        String cardnumber = usElectronicCardNumber.electronicCardNumber(user.getId());
        user.setCardNumber(cardnumber);
        user.setEidLevel(Constant.EidLevel.EID_LEVEL_2.getValue());//返回eid状态

        return user;
    }

    //注册
    @Transactional
    public UsUserEntity reg(UsRegisterParam form) {
        //保存用户信息
        UsUserEntity user = new UsUserEntity();
        user.setLoginStatus("0");
        user.setEidLevel(Constant.EidLevel.EID_LEVEL_1.getValue());
        user.setMobilePhone(form.getMobilePhone());
        user.setPassword(form.getPassword());
        String userId = UsIdUtil.generateId();
        user.setId(userId);
        user.setCreateDate(new Date());
        user.setStatus(INITIALIZE_USER_STATUS);//初始未认证
        String session = UsSessionUtil.generateSession();//生成session
        user.setSession(session);
        user.setAppid(form.getAppid());
        user.setClientId(form.getClient_id());
        this.insert(user);
        //保存Session到缓存
        sessionUtil.saveSession(userId, session);

        //保存设备信息
        UsUserPlantParamEntity userPlant = new UsUserPlantParamEntity();
        userPlant.setUserId(user.getId());
        userPlant.setId(UsIdUtil.generateId());
        userPlant.setUnitType(form.getUnitType());
        userPlant.setEquipmentManufacturer(form.getEquipmentManufacturer());
        userPlant.setScreenResolution(form.getScreenResolution());
        userPlant.setDpi(form.getDpi());
        userPlant.setSystemName(form.getSystemName());
        userPlant.setSystemVersion(form.getSystemVersion());
        userPlant.setNetworkType(form.getNetworkType());
        userPlant.setCreateDate(new Date());
        userPlant.setAppid(form.getAppid());
        usUserPlantParamService.insert(userPlant);

        return user;
    }

    public R uploadPortrait(UsUserEntity user, UsUserPortraiParam form) {

        //String customerId ="";//加多层文件夹,暂时用不到
        String ret_fileName = "";//返回给前端已修改的图片名称
        String base64Img = form.getPortraitData();
        String type = "";
        if (base64Img.length() > 22) {
            if (base64Img.substring(11, 14).equals("png") || base64Img.substring(11, 15).equals("jpeg")) {
                if (base64Img.substring(11, 14).equals("png")) {
                    type = "png";
                } else {
                    type = "jpeg";
                }
                // 临时文件路径
                //String realPath = ClassUtils.getDefaultClassLoader().getResource("").getPath();获取绝对路径 例/D:renren-fast/..

                String realPath = "C:";
                String tempPath = "C:/" + DIRTEMP;

                File file_normer = new File(realPath + UPLOADImg);
                if (!file_normer.exists()) {
                    file_normer.mkdirs();
                }
                // 用于设置图片过大，存入临时文件
                DiskFileItemFactory factory = new DiskFileItemFactory();
                factory.setSizeThreshold(20 * 1024 * 1024); // 设定使用内存超过5M时，将产生临时文件并存储于临时目录中。
                factory.setRepository(new File(tempPath)); // 设定存储临时文件的目录。

                ServletFileUpload upload = new ServletFileUpload(factory);
                upload.setHeaderEncoding("UTF-8");
                if (type.equals("png")) {
                    base64Img = base64Img.replaceAll("data:image/png;base64,", "");

                } else {
                    base64Img = base64Img.replaceAll("data:image/jpeg;base64,", "");
                }

                if (base64Img == null || "".equals(base64Img) || "data:image/jpeg;base64".equals(base64Img) || "data:image/png;base64".equals(base64Img)) {
                    return R.error("上传的图片数据为空");
                }
                BASE64Decoder decoder = new BASE64Decoder();
                try {
                    // Base64解码
                    byte[] b = decoder.decodeBuffer(base64Img);
                    for (int i = 0; i < b.length; ++i) {
                        if (b[i] < 0) {// 调整异常数据
                            b[i] += 256;
                        }
                    }
                    // 生成jpeg图片

                    ret_fileName = new String((new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date()) + "." + type).getBytes("gb2312"), "ISO8859-1");

                    File file = new File(realPath + UPLOADImg + "/" + ret_fileName);
                    OutputStream out = new FileOutputStream(file);
                    out.write(b);
                    out.flush();
                    out.close();
                } catch (Exception e) {
                    e.printStackTrace();
                }
                //--------上传头像成功-----------
                //存取图片路径
                //处理查询数据
                HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
                String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/hmPhotos" + "/";
                String image_url = "upload/" + ret_fileName;

                //user.setPortrait(image_url);
                user.setUpdateDate(new Date());
                String portrait = path + image_url;
                user.setPortrait(portrait);
                this.updateById(user);

                Map<String, Object> map = new HashMap<>();
                map.put("portrait", portrait);//头像路径
                return R.ok(map);

            } else {
                return R.error("上传的图片格式不支持，仅支持png或jpeg格式");
            }
        } else {
            return R.error("上传的图片数据为空");
        }
    }

    private boolean verifyEid(UsUserEntity user) throws InterruptedException {
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
        String dataToSign = Base64Util.encodeData(datetime + ":" + seqno + ":" + content);
        //签名
        String dataToBeDisplayed = Base64Util.encodeData(displayed);
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
    public R eidLogin(UsEidLoginParam param) throws InterruptedException {
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
            //给前端返回登录状态,如果是EID登录值为1
            user.setLoginStatus("1");
            user.setCardNumber(this.getCardNumber(user.getId()));
            user.setPassword("");
            return R.ok(user);
        } else {
            return R.error("未通过EID认证");
        }
    }

    @Override
    public R eidAuth(UsSessionParam param) throws InterruptedException {
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
            user.setCardNumber(this.getCardNumber(user.getId()));
            user.setPassword("");
            return R.ok(user);
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

    @Override
    public String getCardNumber(String userId) {
        return usElectronicCardNumber.getElectronicCardNumber(userId);
    }

    @Scope("prototype")
    @Override
    public R auth(UsUserAuthParam param) throws InterruptedException {
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
    public void setUsElectronicCardNumber(UsElectronicCardNumberService usElectronicCardNumber) {
        this.usElectronicCardNumber = usElectronicCardNumber;
    }

    @Autowired
    public void settSTypeService(TSTypeService tSTypeService) {
        this.tSTypeService = tSTypeService;
    }

    @Autowired
    public void setUsUserPlantParamService(UsUserPlantParamService usUserPlantParamService) {
        this.usUserPlantParamService = usUserPlantParamService;
    }

    @Autowired
    public void setSessionUtil(UsSessionUtil sessionUtil) {
        this.sessionUtil = sessionUtil;
    }

}
