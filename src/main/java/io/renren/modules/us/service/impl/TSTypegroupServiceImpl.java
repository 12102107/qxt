package io.renren.modules.us.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.us.dao.TSTypegroupDao;
import io.renren.modules.us.entity.TSTypegroupEntity;
import io.renren.modules.us.service.TSTypegroupService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("tSTypegroupService")
public class TSTypegroupServiceImpl extends ServiceImpl<TSTypegroupDao, TSTypegroupEntity> implements TSTypegroupService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<TSTypegroupEntity> page = this.selectPage(
                new Query<TSTypegroupEntity>(params).getPage(),
                new EntityWrapper<TSTypegroupEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 通过typegroupcode查询typegroupid
     * @param typegroupcode
     * @return
     */
    public String queryTypeGroupIdByCode(String typegroupcode) {

        EntityWrapper<TSTypegroupEntity> wrapper = new EntityWrapper<>();
        wrapper.setEntity(new TSTypegroupEntity());
        wrapper.where("typegroupcode={0}", typegroupcode);
        List<TSTypegroupEntity> list = this.selectList(wrapper);

        if (list.isEmpty()) {
            return null;
        } else if (list.size() == 1) {
            return list.get(0).getId();
        }
        return null;
    }

}
