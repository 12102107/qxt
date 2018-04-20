package io.renren.modules.us.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.us.dao.UsRegionsDao;
import io.renren.modules.us.entity.UsRegionsEntity;
import io.renren.modules.us.service.UsRegionsService;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("usRegionsService")
public class UsRegionsServiceImpl extends ServiceImpl<UsRegionsDao, UsRegionsEntity> implements UsRegionsService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<UsRegionsEntity> page = this.selectPage(
                new Query<UsRegionsEntity>(params).getPage(),
                new EntityWrapper<UsRegionsEntity>()
        );

        return new PageUtils(page);
    }


}
