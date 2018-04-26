package io.renren.modules.us.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import io.renren.modules.us.dao.TSCategoryDao;
import io.renren.modules.us.entity.TSCategoryEntity;
import io.renren.modules.us.param.UsChildCategoryParam;
import io.renren.modules.us.param.UsPageParam;
import io.renren.modules.us.service.TSCategoryService;
import org.springframework.stereotype.Service;

import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

/**
 * @author Sys
 */
@Service("tSCategoryService")
public class TSCategoryServiceImpl extends ServiceImpl<TSCategoryDao, TSCategoryEntity> implements TSCategoryService {

    @Override
    public R list(UsPageParam pageParam) {
        //设置分页参数
        Map<String, Object> map = new HashMap<>(2);
        map.put("limit", pageParam.getPageSize().toString());
        map.put("page", pageParam.getPageNo().toString());
        //设置查询条件
        EntityWrapper<TSCategoryEntity> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("id", "icon_id", "code", "name");
        wrapper.where("code REGEXP {0}", "^[A]{1}[0-9]{1,}$");
        //查询数据
        Page<Map<String, Object>> page = this.selectMapsPage(new Query<TSCategoryEntity>(map).getPage(), wrapper);
        return R.ok(new PageUtils(page));
    }

    @Override
    public R childList(UsChildCategoryParam childCategoryParam) {
        //设置分页参数
        Map<String, Object> map = new HashMap<>(2);
        map.put("limit", childCategoryParam.getPageSize().toString());
        map.put("page", childCategoryParam.getPageNo().toString());
        //设置查询条件
        EntityWrapper<TSCategoryEntity> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("id", "icon_id", "code", "name", "parent_code");
        wrapper.where("parent_code like {0}", childCategoryParam.getCode() + "%");
        //查询数据
        Page<Map<String, Object>> page = this.selectMapsPage(new Query<TSCategoryEntity>(map).getPage(), wrapper);
        //处理查询数据
        List<Map<String, Object>> list = new ArrayList<>();
        for (Map<String, Object> m1 : page.getRecords()) {
            //如果是二级分类
            if (m1.get("parent_code").toString().matches("^[A]{1}[0-9]{1,}$")) {
                List<Map<String, Object>> tempList = new ArrayList<>();
                String code = m1.get("code").toString();
                for (Map<String, Object> m2 : page.getRecords()) {
                    if (m2.get("parent_code").toString().equals(code)) {
                        tempList.add(m2);
                    }
                }
                m1.put("list", tempList);
                list.add(m1);
            }
        }
        page.setRecords(list);
        return R.ok(new PageUtils(page));
    }

}
