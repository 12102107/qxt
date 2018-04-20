package io.renren.modules.us.service.impl;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.us.dao.TInfoDao;
import io.renren.modules.us.entity.TInfoEntity;
import io.renren.modules.us.service.TInfoService;
import org.springframework.stereotype.Service;

import java.util.List;
import java.util.Map;


@Service("tInfoService")
public class TInfoServiceImpl extends ServiceImpl<TInfoDao, TInfoEntity> implements TInfoService {

    @Override
    public PageUtils queryPage(Map<String, Object> params) {
        Page<TInfoEntity> page = this.selectPage(
                new Query<TInfoEntity>(params).getPage(),
                new EntityWrapper<TInfoEntity>()
        );

        return new PageUtils(page);
    }

    public TInfoEntity queryByCode(String code){

        EntityWrapper<TInfoEntity> wrapper = new EntityWrapper<>();
        wrapper.setEntity(new TInfoEntity());
        wrapper.where("code={0}", code);
        List<TInfoEntity> list = this.selectList(wrapper);

        if (list.isEmpty()) {
            return null;
        } else if (list.size() == 1) {
            return list.get(0);
        }
        return null;
    }

}
