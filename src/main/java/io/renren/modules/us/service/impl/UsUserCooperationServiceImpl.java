package io.renren.modules.us.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.Constant;
import io.renren.common.utils.R;
import io.renren.modules.us.dao.UsUserCooperationDao;
import io.renren.modules.us.entity.UsUserCooperationEntity;
import io.renren.modules.us.entity.UsUserEntity;
import io.renren.modules.us.param.UsUserCooperationParam;
import io.renren.modules.us.service.UsUserCooperationService;
import io.renren.modules.us.service.UsUserService;
import io.renren.modules.us.util.UsCryptoUtil;
import io.renren.modules.us.util.UsIdUtil;
import io.renren.modules.us.util.UsSessionUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.Date;
import java.util.List;


/**
 * @author Li
 */
@Service("usUserCooperationService")
public class UsUserCooperationServiceImpl extends ServiceImpl<UsUserCooperationDao, UsUserCooperationEntity> implements UsUserCooperationService {

    @Autowired
    private UsUserService userService;

    @Override
    public R signIn(UsUserCooperationParam cooperationParam){
        EntityWrapper<UsUserCooperationEntity> wrapper = new EntityWrapper<>();
        wrapper.setEntity(new UsUserCooperationEntity());
        wrapper.where("appid={0}", cooperationParam.getAppid())
                .and("type={0}", cooperationParam.getType())
                .and("openid={0}", cooperationParam.getOpenid());
        List<UsUserCooperationEntity> list = this.selectList(wrapper);
        if (list.isEmpty()) {
            return R.error(Constant.Result.NO_MOBILE.getValue(), Constant.Message.NO_MOBILE.getValue());
        } else if (list.size() == 1) {
            //保存数据,更新Session,更新缓存
            UsUserCooperationEntity entity = list.get(0);
            entity.setAccessToken(cooperationParam.getAccessToken());
            entity.setUpdateDate(new Date());
            this.updateById(entity);
            String session = UsSessionUtil.generateSession();
            UsUserEntity userEntity = userService.selectById(entity.getUserId());
            userEntity.setSession(session);
            userService.updateById(userEntity);
            return R.ok();
        } else {
            return R.error();
        }
    }

}
