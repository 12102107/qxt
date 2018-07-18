package io.renren.modules.us.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;

import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.us.dao.UsAdvertisingDao;
import io.renren.modules.us.entity.UsAdvertisingEntity;
import io.renren.modules.us.entity.UsResourceEntity;
import io.renren.modules.us.service.UsAdvertisingService;

import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.util.Map;


@Service("usAdvertisingService")
public class UsAdvertisingImpl extends ServiceImpl<UsAdvertisingDao, UsAdvertisingEntity> implements UsAdvertisingService {
    @Value("${us.icon.path}")
    private String path;

    public PageUtils queryPage(Map<String, Object> params) {
        //查询已发布，按照时间倒序排列（部分字段）
        EntityWrapper<UsAdvertisingEntity> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("id","title","icon","content","create_date").eq("status",1)
        .orderBy("create_date", false) ;  // 时间的倒叙排列
        Page<Map<String, Object>> page = this.selectMapsPage(new Query<UsResourceEntity>(params).getPage(), wrapper);
        //补全icon路径
        for (Map<String, Object> m : page.getRecords()) {
            Object obj = m.get("icon");
            if (obj != null && !obj.equals("")) {
                m.put("icon", path + obj.toString());
            }
        }
        return new PageUtils(page);
    }

}
