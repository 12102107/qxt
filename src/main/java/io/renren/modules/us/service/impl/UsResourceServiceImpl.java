package io.renren.modules.us.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.us.dao.UsResourceDao;
import io.renren.modules.us.entity.UsResourceEntity;
import io.renren.modules.us.service.UsResourceService;


@Service("usResourceService")
public class UsResourceServiceImpl extends ServiceImpl<UsResourceDao, UsResourceEntity> implements UsResourceService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<UsResourceEntity> page = this.selectPage(
                new Query<UsResourceEntity>(params).getPage(),
                new EntityWrapper<UsResourceEntity>()
        );

        return new PageUtils(page);
    }

}
