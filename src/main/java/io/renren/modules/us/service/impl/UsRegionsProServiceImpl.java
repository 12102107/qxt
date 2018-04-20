package io.renren.modules.us.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.us.dao.UsRegionsProDao;
import io.renren.modules.us.entity.UsRegionsEntity;
import io.renren.modules.us.entity.UsRegionsProEntity;
import io.renren.modules.us.service.UsRegionsProService;
import io.renren.modules.us.service.UsRegionsService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.*;


@Service("usRegionsProService")
public class UsRegionsProServiceImpl extends ServiceImpl<UsRegionsProDao, UsRegionsProEntity> implements UsRegionsProService {
    @Autowired
    private UsRegionsService usRegionsService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<UsRegionsProEntity> page = this.selectPage(
                new Query<UsRegionsProEntity>(params).getPage(),
                new EntityWrapper<UsRegionsProEntity>()
        );

        return new PageUtils(page);
    }

    //一二级属地查询列表
    public List<LinkedHashMap<Object, Object>>  queryList() {
        EntityWrapper<UsRegionsProEntity> wrapper = new EntityWrapper<>();
        wrapper.setEntity(new UsRegionsProEntity());
        List<UsRegionsProEntity> list = this.selectList(wrapper);

        List<LinkedHashMap<Object, Object>> parentMap = new ArrayList<>();

        for (UsRegionsProEntity ur:list) {
            LinkedHashMap<Object, Object> parentMap_ =new LinkedHashMap<>();

            EntityWrapper<UsRegionsEntity> wrapper0 = new EntityWrapper<>();
            wrapper0.setEntity(new UsRegionsEntity());
            wrapper0.where("p_region_id={0}", ur.getRegionId());
            List<UsRegionsEntity> list0 = usRegionsService.selectList(wrapper0);

            List listcity = new ArrayList();

            for (UsRegionsEntity urcity:list0) {
                LinkedHashMap map0 = new LinkedHashMap();
                map0.put("value",urcity.getRegionId());
                map0.put("text",urcity.getLocalName());
                listcity.add(map0);
            }
            parentMap_.put("value",ur.getRegionId());
            parentMap_.put("text",ur.getLocalName());
            parentMap_.put("children",listcity);
            parentMap.add(parentMap_);

        }

        return parentMap;
    }

}
