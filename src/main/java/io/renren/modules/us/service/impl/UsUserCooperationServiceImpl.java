package io.renren.modules.us.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.us.dao.UsUserCooperationDao;
import io.renren.modules.us.entity.UsUserCooperationEntity;
import io.renren.modules.us.service.UsUserCooperationService;


@Service("usUserCooperationService")
public class UsUserCooperationServiceImpl extends ServiceImpl<UsUserCooperationDao, UsUserCooperationEntity> implements UsUserCooperationService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<UsUserCooperationEntity> page = this.selectPage(
                new Query<UsUserCooperationEntity>(params).getPage(),
                new EntityWrapper<UsUserCooperationEntity>()
        );

        return new PageUtils(page);
    }

}
