package io.renren.modules.us.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import com.eidlink.sdk.EidlinkService;
import com.eidlink.sdk.pojo.request.MOMTRealNameParam;
import com.eidlink.sdk.pojo.request.base.MOMTRealNameParameters;
import com.eidlink.sdk.pojo.request.base.RealName;
import com.eidlink.sdk.pojo.result.CommonResult;
import io.renren.common.utils.*;
import io.renren.modules.us.dao.UsUserDao;
import io.renren.modules.us.entity.TSTypeEntity;
import io.renren.modules.us.entity.UsUserEntity;
import io.renren.modules.us.entity.UsUserPlantParamEntity;
import io.renren.modules.us.param.*;
import io.renren.modules.us.service.*;
import io.renren.modules.us.util.Base64Util;
import io.renren.modules.us.util.UsIdUtil;
import io.renren.modules.us.util.UsSessionUtil;
import net.sf.json.JSONObject;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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
	private RedisUtils redisUtil;
    @Autowired
    private TSDepartService tSDepartService;
    @Autowired
    private UsElectronicCardNumberService usElectronicCardNumber;
    @Autowired
    private TSTypeService tSTypeService;
    @Autowired
    private UsUserPlantParamService usUserPlantParamService;
    public static final int INITIALIZE_USER_STATUS = 0;//注册后初始状态
    public static final int REAL_USER_STATUS = 1;//实名状态

    private   static final String   UPLOADImg  = "\\apache-tomcat-8.5.24\\webapps\\hmPhotos\\upload";
    private   static final String  DIRTEMP = "\\apache-tomcat-8.5.24\\webapps\\hmPhotos\\upload";

    @Value("${us.eid.content}")
    private String content;

    @Value("${us.eid.displayed}")
    private String displayed;

    @Value("${us.eid.returnUrl}")
    private String url;


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
    public R signIn(UsLoginParam form){
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
            this.updateById(entity);

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

            //返回user隐藏部分字段
            Map<String, Object> user_ = this.usHidden(entity.getId());
            // 实名认证成功后返回电子卡号
            String cardnumber=usElectronicCardNumber.electronicCardNumber(entity.getId());
            user_.put("cardnumber",cardnumber);
            return R.ok(user_);
        } else {
            return R.error();
        }
    }

    /**
     * 返回user对象部分属性
     * @param id
     * @return
     */
    public Map<String, Object> usHidden (String id) {

        EntityWrapper<UsUserEntity> w1 = new EntityWrapper<>();
        w1.setSqlSelect("session", "status","u_jobid as uJobid",
                "u_departid as uDepartid","remark","realname","id","email","nickname","mobile_phone as mobilePhone","citizen_no as citizenNo","address","portrait","sex");
        w1.where("id = {0}", id);
        List<Map<String, Object>> list = this.selectMaps(w1);
        if (list == null || list.isEmpty() || list.size() == 0) {
            return null;
        }
        Map<String, Object> map = list.get(0);
        //工作单位
        String personDepartname_ = null;
        if (null != map.get("uDepartid")  &&  !"".equals(map.get("uDepartid"))){
            TSTypeEntity ts_ = tSTypeService.queryByCode(map.get("uDepartid").toString(),"dep_list");
            if(ts_!=null){
                personDepartname_ = ts_.getTypename();
            }
        }
        map.put("personDepartname",personDepartname_);

        //职业
        String personJob_ = null;
        if (null != map.get("uJobid")  &&  !"".equals(map.get("uJobid"))){
            TSTypeEntity ts = tSTypeService.queryByCode(map.get("uJobid").toString(),"job_list");
            if(ts!=null){
                personJob_ = ts.getTypename();
            }
        }
        map.put("personJob",personJob_);
        //存取图片路径
        if(null != map.get("portrait") && !"".equals(map.get("portrait"))){
            HttpServletRequest request = HttpContextUtils.getHttpServletRequest();
            String path = request.getScheme() + "://" + request.getServerName() + ":" + request.getServerPort() + "/hmPhotos" + "/";
            map.put("portrait",path + map.get("portrait").toString());
        }

        return map;
    }

    /**
     * 检查用户是否存在
     * */
    @Override
    public UsUserEntity checkUserExits(String userId, String oldPassword){
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
     * @param
     * @param form
     * @return
     */
    @Override
    public UsUserEntity updatePersonalInfo(UsUserEntity user, UsUserParam form){
        user.setUpdateDate(new Date());
        user.setNickname(form.getNickname());
        user.setRealname(form.getRealname());
        user.setCitizenNo(form.getCitizenNo());
        user.setSex(form.getSex());
        user.setEmail(form.getEmail());
        user.setAddress(form.getAddress());
        user.setRemark(form.getRemark());

        user.setuDepartid(form.getuDepartid());
        user.setuJobid(form.getuJobid());

        user = this.queryName(user);

        user.setAppid(form.getAppid());
        this.updateById(user);
        return user;
    }

    /**
     * 根据工作单位id获取名称等
     * @param
     * @return
     */
    public UsUserEntity queryName(UsUserEntity user) {
        //工作单位
        if (null != user.getuDepartid()  &&  !"".equals(user.getuDepartid())){
            TSTypeEntity ts_ = tSTypeService.queryByCode(user.getuDepartid(),"dep_list");
            if(ts_!=null){
                user.setPersonDepartname(ts_.getTypename());
            }

        }
        //职业
        if (null != user.getuJobid()  &&  !"".equals(user.getuJobid())){
            TSTypeEntity ts = tSTypeService.queryByCode(user.getuJobid(),"job_list");
            if(ts!=null){
                user.setPersonJob(ts.getTypename());
            }
        }
        return user;
    }



    /**
     * 实名认证
     * @param user
     * @param form
     * @return
     */
    @Override
    @Transactional
    public UsUserEntity realnameCert(UsUserEntity user, UsUserRealCertParam form){

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
        user.setAppid(form.getAppid());

        this.updateById(user);
        // 实名认证成功后返回电子卡号
        String cardnumber=usElectronicCardNumber.electronicCardNumber(user.getId());
        user.setCardNumber(cardnumber);

        return user;
    }

    //注册
    @Transactional
    public UsUserEntity reg(UsRegisterParam form){
        //保存用户信息
        UsUserEntity user = new UsUserEntity();
        user.setMobilePhone(form.getMobilePhone());
        user.setPassword(form.getPassword());
        user.setId(UsIdUtil.generateId());
        user.setCreateDate(new Date());
        user.setStatus(INITIALIZE_USER_STATUS);//初始未认证
        String session = UsSessionUtil.generateSession();//生成session
        user.setSession(session);
        user.setAppid(form.getAppid());
        this.insert(user);

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

    public R uploadPortrait(UsUserEntity user, UsUserPortraiParam form){

        //String customerId ="";//加多层文件夹,暂时用不到
        String ret_fileName = "";//返回给前端已修改的图片名称
        String base64Img = form.getPortraitData();
        String type = "";
        if (base64Img.length()>22){
            if(base64Img.substring(11,14).equals("png") || base64Img.substring(11,15).equals("jpeg") ){
                if(base64Img.substring(11,14).equals("png")){
                    type = "png";
                }else{
                    type = "jpeg";
                }
                // 临时文件路径
                //String realPath = ClassUtils.getDefaultClassLoader().getResource("").getPath();获取绝对路径 例/D:renren-fast/..

                String realPath = "C:";
                String tempPath =  "C:/"+ DIRTEMP;

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
                if (type.equals("png")){
                    base64Img = base64Img.replaceAll("data:image/png;base64,", "");

                }else{
                    base64Img = base64Img.replaceAll("data:image/jpeg;base64,", "");
                }

                if (base64Img == null || "".equals(base64Img) || "data:image/jpeg;base64".equals(base64Img) || "data:image/png;base64".equals(base64Img)){
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

                    ret_fileName = new String((new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date())+"."+type).getBytes("gb2312"), "ISO8859-1" ) ;

                    File file = new File(realPath + UPLOADImg+"/" + ret_fileName);
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
                String image_url =  "upload/" + ret_fileName;

                user.setPortrait(image_url);
                user.setUpdateDate(new Date());
                this.updateById(user);

                String portrait = path + image_url;

                Map<String, Object> map = new HashMap<>();
                map.put("portrait", portrait);//头像路径
                return  R.ok(map);

            }else{
                return R.error("上传的图片格式不支持，仅支持png或jpeg格式");
            }
        }else {
            return R.error("上传的图片数据为空");
        }
    }

    /*
     * eid注册验证
     */
    @Override
    public R eidLogin(UsSmsParam form) {
        // TODO Auto-generated method stub
        //查询用户信息
        EntityWrapper<UsUserEntity> wrapper = new EntityWrapper<>();
        wrapper.setEntity(new UsUserEntity());
        wrapper.where("mobile_phone={0}", form.getMobile());
        List<UsUserEntity> list = this.selectList(wrapper);

        if(list.isEmpty()){
            return R.error("用户不存在");
        }else{
System.out.println("list======="+list.size());
        	UsUserEntity us = list.get(0);
           // for(UsUserEntity us:list){
        	if(!redisUtil.hasKey("phone"+us.getMobilePhone())){
        		redisUtil.setTimes("phone"+us.getMobilePhone(), us.getMobilePhone());
        		String mobile = us.getMobilePhone();//手机号;
                String name = us.getRealname();//姓名;
                String idnum = us.getCitizenNo();//"14070019770130819X";
                SimpleDateFormat df = new SimpleDateFormat("yyyyMMddHHmmss");//设置日期格式
                String datetime = df.format(new Date());// new Date()为获取当前系统时间
                String seqno = UUID.randomUUID().toString().replaceAll("-", "");//业务id
                String dataToSign = Base64Util.encodeData(datetime+":"+seqno+":"+content);//"MjAxNTA0MDExMTAzMzE6MTAwMDAwMDE6aGVsbG9UZXN0";//20150401110331:10000001:helloTest
                String display = Base64Util.encodeData(displayed);//签名
                String returnUrl = url+"?eid="+seqno;//异步接收路径

                RealName realName = new RealName(name, idnum);
                MOMTRealNameParameters pkiParam = new MOMTRealNameParameters(mobile, dataToSign, display);
                MOMTRealNameParam reqParam = new MOMTRealNameParam(pkiParam, realName);
                reqParam.setReturnUrl(returnUrl);
                String msg = "";
                CommonResult result = EidlinkService.doPost(reqParam);
                if(result.getResult().equals("00")){//eid调取成功
                    for(int i=0;i<6;i++){//循环获取redis中数据，根据业务id,如果没有数据10s一次，一共循环一分钟
                        if(redisUtil.hasKey(seqno)){
                            String value = redisUtil.get(seqno);
                            JSONObject json = JSONObject.fromObject(value);
                            if(json.get("result").equals("00")){
System.out.println("us======="+us);
                                us.setUpdateDate(new Date());
                                String session = UsSessionUtil.generateSession();
                                us.setSession(session);
                                us.setAppid(form.getAppid());
                                this.updateById(us);

                                msg = "验证成功";
                           
                                break;
                            }else{
                                msg = "验证失败";
                                break;
                            }
                        }else{
                            try {
                                Thread.sleep(10000);
                            } catch (InterruptedException e) {
                                // TODO Auto-generated catch block
                                e.printStackTrace();
                            }
                        }
                    }
System.out.println("msg======="+msg);   
                    if(msg.equals("")){
                    	msg = "验证失败";
                    }
                    redisUtil.delete("phone"+us.getMobilePhone());
                    return R.ok(msg);
                }else{
                	 redisUtil.delete("phone"+us.getMobilePhone());
                    return R.error("验证失败");
                }
        	}else{
        		return R.error("重复提交");
        	}
        	
                
            }
        //}

    }

    @Override
    public R queryMobile(String  id) {
        EntityWrapper<UsUserEntity> wrapper = new EntityWrapper<>();
        wrapper.setEntity(new UsUserEntity());
        wrapper.where("id={0}", id);
        List<UsUserEntity> list = this.selectList(wrapper);

        if (list.isEmpty()) {
            return R.error("用户不存在");
        } else{
            if (list.get(0).getStatus().equals(2)) {
                return R.ok("用户通过认证");
            }else {
                return R.ok("用户通过不认证");
            }

        }
    }

    @Autowired
    public void setRedisUtil(RedisUtils redisUtil) {
        this.redisUtil = redisUtil;
    }

}
