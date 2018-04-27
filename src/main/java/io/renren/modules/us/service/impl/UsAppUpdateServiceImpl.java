package io.renren.modules.us.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.R;
import io.renren.modules.us.dao.UsAppUpdateDao;
import io.renren.modules.us.entity.UsAppUpdateEntity;
import io.renren.modules.us.param.UsBaseParam;
import io.renren.modules.us.service.UsAppUpdateService;
import org.springframework.stereotype.Service;

import java.util.HashMap;
import java.util.List;
import java.util.Map;


/**
 * @author Li
 */
@Service("usAppUpdateService")
public class UsAppUpdateServiceImpl extends ServiceImpl<UsAppUpdateDao, UsAppUpdateEntity> implements UsAppUpdateService {

    @Override
    public R list(UsBaseParam baseParam) {
        EntityWrapper<UsAppUpdateEntity> w1 = new EntityWrapper<>();
        w1.setSqlSelect("type", "max(creat_date) as date");
        w1.where("appid = {0}", baseParam.getAppid());
        w1.groupBy("type");
        List<Map<String, Object>> list = this.selectMaps(w1);
        if (list == null || list.isEmpty()) {
            return R.ok();
        }
        EntityWrapper<UsAppUpdateEntity> w2 = new EntityWrapper<>();
        int i = 0;
        for (Map<String, Object> map : list) {
            if (i == 0) {
                w2.where("type = {0}", map.get("type").toString());
                w2.and("creat_date = {0}", map.get("date").toString());
                w2.and("appid = {0}", baseParam.getAppid());
            } else {
                w2.or("type = {0}", map.get("type").toString());
                w2.and("creat_date = {0}", map.get("date").toString());
                w2.and("appid = {0}", baseParam.getAppid());
            }
            i++;
        }
        List<UsAppUpdateEntity> l2 = this.selectList(w2);
        Map<String, Object> result = new HashMap<>();
        for (UsAppUpdateEntity updateEntity : l2) {
            result.put(updateEntity.getType(), updateEntity);
        }
        return R.ok(result);
    }

}
