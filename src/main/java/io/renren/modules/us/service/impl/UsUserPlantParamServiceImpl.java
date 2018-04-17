package io.renren.modules.us.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.us.dao.UsUserPlantParamDao;
import io.renren.modules.us.entity.UsUserPlantParamEntity;
import io.renren.modules.us.service.UsUserPlantParamService;


@Service("usUserPlantParamService")
public class UsUserPlantParamServiceImpl extends ServiceImpl<UsUserPlantParamDao, UsUserPlantParamEntity> implements UsUserPlantParamService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<UsUserPlantParamEntity> page = this.selectPage(
                new Query<UsUserPlantParamEntity>(params).getPage(),
                new EntityWrapper<UsUserPlantParamEntity>()
        );

        return new PageUtils(page);
    }

}
