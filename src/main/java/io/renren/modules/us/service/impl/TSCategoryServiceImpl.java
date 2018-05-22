package io.renren.modules.us.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.common.utils.R;
import io.renren.modules.us.dao.TSCategoryDao;
import io.renren.modules.us.entity.TSCategoryEntity;
import io.renren.modules.us.entity.UsResourceEntity;
import io.renren.modules.us.param.UsChildCategoryParam;
import io.renren.modules.us.param.UsPageParam;
import io.renren.modules.us.service.TSCategoryService;
import io.renren.modules.us.service.UsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
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

    @Value("${us.icon.path}")
    private String path;

    private UsResourceService resourceService;

    @Override
    public R list(UsPageParam pageParam) {
        //设置分页参数
        Map<String, Object> map = new HashMap<>(2);
        map.put("limit", pageParam.getPageSize().toString());
        map.put("page", pageParam.getPageNo().toString());
        //设置查询条件
        EntityWrapper<TSCategoryEntity> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("id", "code", "name", "icon_id", "order_num");
        wrapper.where("code REGEXP {0}", "^[A]{1}[0-9]{1,}$");
        wrapper.orderBy("order_num", true);
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
        //查询分类数据
        EntityWrapper<TSCategoryEntity> w1 = new EntityWrapper<>();
        w1.setSqlSelect("id", "code", "name", "icon_id", "order_num");
        w1.where("parent_code = {0}", childCategoryParam.getCode());
        w1.orderBy("order_num", true);
        Page<Map<String, Object>> page = this.selectMapsPage(new Query<TSCategoryEntity>(map).getPage(), w1);
        //查询资源数据
        EntityWrapper<UsResourceEntity> w2 = new EntityWrapper<>();
        int i = 0;
        for (Map<String, Object> m1 : page.getRecords()) {
            if (i == 0) {
                w2.where("find_in_set({0},category_id)", m1.get("code").toString());
                w2.and("status = {0}", "1");
            } else {
                w2.or("find_in_set({0},category_id)", m1.get("code").toString());
                w2.and("status = {0}", "1");
            }
            i++;
        }
        w2.orderBy("order_num", true);
        List<UsResourceEntity> resourceList = resourceService.selectList(w2);
        //处理查询数据
        for (Map<String, Object> m2 : page.getRecords()) {
            String code = m2.get("code").toString();
            List<UsResourceEntity> list = new ArrayList<>();
            for (UsResourceEntity resource : resourceList) {
                if (resource.getCategoryId().contains(code)) {
                    //补全icon路径
                    if (resource.getIcon() != null && !resource.getIcon().isEmpty()) {
                        resource.setIcon(path + resource.getIcon());
                    }
                    list.add(resource);
                }
            }
            m2.put("list", list);
        }
        return R.ok(new PageUtils(page));
    }

    @Autowired
    public void setResourceService(UsResourceService resourceService) {
        this.resourceService = resourceService;
    }
}
