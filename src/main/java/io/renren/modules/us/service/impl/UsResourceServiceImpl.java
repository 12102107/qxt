package io.renren.modules.us.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import io.renren.modules.us.dao.UsResourceDao;
import io.renren.modules.us.entity.UsResourceEntity;
import io.renren.modules.us.param.UsResourceParam;
import io.renren.modules.us.service.UsResourceService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.Map;


/**
 * @author Li
 */
@Service("usResourceService")
public class UsResourceServiceImpl extends ServiceImpl<UsResourceDao, UsResourceEntity> implements UsResourceService {

    @Override
    public R list(UsResourceParam resourceParam) {
        //设置分页参数
        Map<String, Object> map = new HashMap<>(2);
        map.put("limit", resourceParam.getPageSize().toString());
        map.put("page", resourceParam.getPageNo().toString());
        //设置查询条件
        EntityWrapper<UsResourceEntity> wrapper = new EntityWrapper<>();
        wrapper.where("find_in_set({0},category_id)", resourceParam.getCategory());
        //查询数据
        Page<Map<String, Object>> page = this.selectMapsPage(new Query<UsResourceEntity>(map).getPage(), wrapper);
        return R.ok(new PageUtils(page));
    }

}
