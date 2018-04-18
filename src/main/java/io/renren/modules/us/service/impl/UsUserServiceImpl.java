package io.renren.modules.us.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.Constant;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import io.renren.modules.us.dao.UsUserDao;
import io.renren.modules.us.entity.TSDepartEntity;
import io.renren.modules.us.entity.UsUserEntity;
import io.renren.modules.us.entity.UsUserPlantParamEntity;
import io.renren.modules.us.param.UsLoginParam;
import io.renren.modules.us.param.UsUserParam;
import io.renren.modules.us.service.UsUserPlantParamService;
import io.renren.modules.us.service.UsUserService;
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
            usUserPlantParamService.insert(userPlant);

            return R.ok(entity);
        } else {
            return R.error();
        }
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
     * @param userId
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
        user.setUJobid(form.getuJobid());
        user.setPersonJob(form.getPersonJob());
        user.setUDepartid(form.getuDepartid());
        user.setPersonDepartname(form.getPersonDepartname());
        this.updateById(user);
        return user;
    }
}
