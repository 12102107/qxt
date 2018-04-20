package io.renren.modules.us.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.us.dao.TSDepartDao;
import io.renren.modules.us.entity.TSDepartEntity;
import io.renren.modules.us.service.TSDepartService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("tSDepartService")
public class TSDepartServiceImpl extends ServiceImpl<TSDepartDao, TSDepartEntity> implements TSDepartService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<TSDepartEntity> page = this.selectPage(
                new Query<TSDepartEntity>(params).getPage(),
                new EntityWrapper<TSDepartEntity>()
        );

        return new PageUtils(page);
    }

    /**
     *
     * @param pid
     * @return
     */
    public List<TSDepartEntity> queryDepartListByPid(String pid) {

        EntityWrapper<TSDepartEntity> wrapper = new EntityWrapper<>();
        wrapper.setEntity(new TSDepartEntity());
        wrapper.where("parentdepartid={0}", pid);
        wrapper.orderBy("org_code");
        List<TSDepartEntity> list = this.selectList(wrapper);
        return list;
    }

    public List<TSDepartEntity> queryDepartListByOrgcode(String orgcode) {

        EntityWrapper<TSDepartEntity> wrapper = new EntityWrapper<>();
        wrapper.setEntity(new TSDepartEntity());
        wrapper.where("org_code={0}", orgcode);
        wrapper.orderBy("org_code");
        List<TSDepartEntity> list = this.selectList(wrapper);
        return list;
    }
}
