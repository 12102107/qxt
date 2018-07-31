package io.renren.modules.us.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.us.dao.TSTypeDao;
import io.renren.modules.us.entity.TSTypeEntity;
import io.renren.modules.us.entity.UsUserEntity;
import io.renren.modules.us.service.TSTypeService;
import io.renren.modules.us.service.TSTypegroupService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("tSTypeService")
public class TSTypeServiceImpl extends ServiceImpl<TSTypeDao, TSTypeEntity> implements TSTypeService {
    @Autowired
    private TSTypegroupService tsTypegroupService;

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<TSTypeEntity> page = this.selectPage(
                new Query<TSTypeEntity>(params).getPage(),
                new EntityWrapper<TSTypeEntity>()
        );

        return new PageUtils(page);
    }

    /**
     * 查询具体一条type值
     * @param typecode
     * @return
     */
    public TSTypeEntity queryByCode(String typecode, String typegroupcode){

        String typegroupid = tsTypegroupService.queryTypeGroupIdByCode(typegroupcode);

        EntityWrapper<TSTypeEntity> wrapper = new EntityWrapper<>();
        wrapper.setEntity(new TSTypeEntity());
        wrapper.where("typegroupid={0}", typegroupid);
        wrapper.and("typecode={0}",typecode);
        List<TSTypeEntity> list = this.selectList(wrapper);

        if (list.isEmpty()) {
            return null;
        } else if (list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

    /**
     * 查询某个数据字典列表
     * @param typegroupcode
     * @return
     */
    public List<TSTypeEntity> queryList(String typegroupcode){

        String typegroupid = tsTypegroupService.queryTypeGroupIdByCode(typegroupcode);

        EntityWrapper<TSTypeEntity> wrapper = new EntityWrapper<>();
        wrapper.setEntity(new TSTypeEntity());
        wrapper.where("typegroupid={0}", typegroupid);
        wrapper.orderBy("typecode");
        List<TSTypeEntity> list = this.selectList(wrapper);
        return list;
    }
    
}
