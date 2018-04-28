package io.renren.modules.us.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.*;
import io.renren.modules.us.dao.UsUserDao;
import io.renren.modules.us.entity.TSDepartEntity;
import io.renren.modules.us.entity.TSTypeEntity;
import io.renren.modules.us.entity.UsUserEntity;
import io.renren.modules.us.entity.UsUserPlantParamEntity;
import io.renren.modules.us.param.*;
import io.renren.modules.us.service.TSDepartService;
import io.renren.modules.us.service.TSTypeService;
import io.renren.modules.us.service.UsUserPlantParamService;
import io.renren.modules.us.service.UsUserService;
import io.renren.modules.us.util.UsIdUtil;
import io.renren.modules.us.util.UsSessionUtil;
import org.apache.commons.fileupload.disk.DiskFileItemFactory;
import org.apache.commons.fileupload.servlet.ServletFileUpload;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import sun.misc.BASE64Decoder;

import javax.servlet.http.HttpServletRequest;
import java.io.File;
import java.io.FileOutputStream;
import java.io.OutputStream;
import java.text.SimpleDateFormat;
import java.util.Date;
import java.util.List;
import java.util.Map;



@Service("usUserService")
public class UsUserServiceImpl extends ServiceImpl<UsUserDao, UsUserEntity> implements UsUserService {
    @Autowired
    private TSDepartService tSDepartService;
    @Autowired
    private TSTypeService tSTypeService;
    @Autowired
    private UsUserPlantParamService usUserPlantParamService;
    public static final int INITIALIZE_USER_STATUS = 0;//注册后初始状态
    public static final int REAL_USER_STATUS = 1;//实名状态
    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<UsUserEntity> page = this.selectPage(
                new Query<UsUserEntity>(params).getPage(),
                new EntityWrapper<UsUserEntity>()
        );

        return new PageUtils(page);
    }

    @Override
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
           UsUserHPram user_ = this.usHiddenProperty(entity);
            return R.ok(user_);
        } else {
            return R.error();
        }
    }

    /**
     * 调用隐藏返回不需要的属性
     * @param user
     * @return
     */
    public UsUserHPram usHiddenProperty (UsUserEntity user) {

        UsUserHPram user_ = new UsUserHPram();
        user_.setSession(user.getSession());

        user_.setStatus(user.getStatus());
        user_.setPersonDepartname(user.getPersonDepartname());
        user_.setPersonJob(user.getPersonJob());
        user_.setuJobid(user.getuJobid());
        user_.setuDepartid(user.getuDepartid());
        user_.setRemark(user.getRemark());

        user_.setRealname(user.getRealname());
        user_.setId(user.getId());
        user_.setEmail(user.getEmail());
        user_.setNickname(user.getNickname());
        user_.setMobilePhone(user.getMobilePhone());
        user_.setCitizenNo(user.getCitizenNo());

        user_.setAddress(user.getAddress());
        user_.setPortrait(user.getPortrait());
        user_.setSex(user.getSex());
        return user_;
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
            TSDepartEntity tSDepart =  tSDepartService.selectById(user.getuDepartid());
            if (tSDepart!=null){
                user.setPersonDepartname(tSDepart.getDepartname());
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
        return user;
    }

    //注册
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

    public String uploadPortrait(UsUserEntity user, UsUserPortraiParam form){

        //String customerId ="";//加多层文件夹,暂时用不到
        String ret_fileName = "";//返回给前端已修改的图片名称
        String base64Img = form.getPortraitData();
        // 临时文件路径
        //String realPath = ClassUtils.getDefaultClassLoader().getResource("").getPath();获取绝对路径 例/D:renren-fast/..

        String realPath = "C:";
        String uploadImg = "\\hmPhotos\\upload";
        String dirTemp = "\\hmPhotos\\upload";
        String tempPath =  "C:/"+ dirTemp;

        File file_normer = new File(realPath + uploadImg);
        if (!file_normer.exists()) {
            file_normer.mkdirs();
        }
        // 用于设置图片过大，存入临时文件
        DiskFileItemFactory factory = new DiskFileItemFactory();
        factory.setSizeThreshold(20 * 1024 * 1024); // 设定使用内存超过5M时，将产生临时文件并存储于临时目录中。
        factory.setRepository(new File(tempPath)); // 设定存储临时文件的目录。

        ServletFileUpload upload = new ServletFileUpload(factory);
        upload.setHeaderEncoding("UTF-8");
        base64Img = base64Img.replaceAll("data:image/jpeg;base64,", "");
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
            ret_fileName = new String((new SimpleDateFormat("yyyyMMddhhmmssSSS").format(new Date())+".jpg").getBytes("gb2312"), "ISO8859-1" ) ;
            File file = new File(realPath + uploadImg+"/" + ret_fileName);
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
        return portrait;
    }
}
