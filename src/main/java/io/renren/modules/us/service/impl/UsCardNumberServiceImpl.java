package io.renren.modules.us.service.impl;

import org.springframework.stereotype.Service;
import java.util.Map;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;

import io.renren.modules.us.dao.UsCardNumberDao;
import io.renren.modules.us.entity.UsCardNumberEntity;
import io.renren.modules.us.service.UsCardNumberService;


@Service("usCardNumberService")
public class UsCardNumberServiceImpl extends ServiceImpl<UsCardNumberDao, UsCardNumberEntity> implements UsCardNumberService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<UsCardNumberEntity> page = this.selectPage(
                new Query<UsCardNumberEntity>(params).getPage(),
                new EntityWrapper<UsCardNumberEntity>()
        );

        return new PageUtils(page);
    }

}
