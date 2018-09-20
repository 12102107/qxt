package io.renren.modules.us.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.us.dao.UsCardMenuDao;
import io.renren.modules.us.entity.UsCardMenuEntity;
import io.renren.modules.us.service.UsCardMenuService;


@Service("usCardMenuService")
public class UsCardMenuServiceImpl extends ServiceImpl<UsCardMenuDao, UsCardMenuEntity> implements UsCardMenuService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<UsCardMenuEntity> page = this.selectPage(
                new Query<UsCardMenuEntity>(params).getPage(),
                new EntityWrapper<UsCardMenuEntity>()
        );

        return new PageUtils(page);
    }

}
