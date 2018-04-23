package io.renren.modules.us.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import io.renren.modules.us.dao.UsUserDao;
import io.renren.modules.us.entity.*;
import io.renren.modules.us.param.*;
import io.renren.modules.us.service.TSDepartService;
import io.renren.modules.us.service.TSTypeService;
import io.renren.modules.us.service.UsUserPlantParamService;
import io.renren.modules.us.service.UsUserService;
import io.renren.modules.us.util.UsIdUtil;
import io.renren.modules.us.util.UsSessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;
import java.util.Map;
import java.util.UUID;



@Service("usUserService")
public class UsUserServiceImpl extends ServiceImpl<UsUserDao, UsUserEntity> implements UsUserService {
    @Autowired
    private TSDepartService tSDepartService;
    @Autowired
    private TSTypeService tSTypeService;
    @Autowired
    private UsUserPlantParamService usUserPlantParamService;

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
            userPlant.setId(UUID.randomUUID().toString().substring(0,10).replace("-",""));
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
        user_.setUJobid(user.getUJobid());
        user_.setUDepartid(user.getUDepartid());
        user_.setRemark(user.getRemark());

        user_.setRealname(user.getRealname());
        user_.setId(user.getId());
        user_.setEmail(user.getEmail());
        user_.setNickname(user.getNickname());
        user_.setMobilePhone(user.getMobilePhone());
        user_.setCitizenNo(user.getCitizenNo());

        user_.setAddress(user.getAddress());
        user_.setPortrait(user.getPortrait());

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

        user.setUDepartid(form.getuDepartid());
        //工作单位
        if (null != form.getuDepartid()  &&  !"".equals(form.getuDepartid())){
            TSDepartEntity tSDepart =  tSDepartService.selectById(form.getuDepartid());
            user.setPersonDepartname(tSDepart.getDepartname());
        }
        user.setUJobid(form.getuJobid());
        //职业
        if (null != form.getuJobid()  &&  !"".equals(form.getuJobid())){
            TSTypeEntity ts = tSTypeService.queryByCode(form.getuJobid(),"job_list");
            user.setPersonJob(ts.getTypename());
        }
        user.setAppid(form.getAppid());
        this.updateById(user);
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

        user.setUJobid(form.getuJobid());
        user.setUDepartid(form.getuDepartid());


        //工作单位
        if (null != form.getuDepartid()  &&  !"".equals(form.getuDepartid())){
            TSDepartEntity tSDepart =  tSDepartService.selectById(form.getuDepartid());
            user.setPersonDepartname(tSDepart.getDepartname());
        }
        //职业
        if (null != form.getuJobid()  &&  !"".equals(form.getuJobid())){
            TSTypeEntity ts = tSTypeService.queryByCode(form.getuJobid(),"job_list");
            user.setPersonJob(ts.getTypename());
        }

        user.setStatus(1);//已实名认证
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
        user.setStatus(0);//初始未认证
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
}
