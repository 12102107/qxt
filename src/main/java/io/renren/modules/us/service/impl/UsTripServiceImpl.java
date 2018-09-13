package io.renren.modules.us.service.impl;
import java.util.List;
import java.util.Map;

import org.aspectj.weaver.ast.And;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Service;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.impl.ServiceImpl;
import io.renren.common.utils.PageUtils;
import io.renren.common.utils.Query;
import io.renren.modules.us.dao.UsTripDao;
import io.renren.modules.us.entity.TSTypeEntity;
import io.renren.modules.us.entity.UsTripLocationEntity;
import io.renren.modules.us.param.UsTripParam;
import io.renren.modules.us.service.UsTripService;

@Service("usTripService")
public class UsTripServiceImpl extends ServiceImpl<UsTripDao, UsTripLocationEntity> implements UsTripService {


	 @Override
	    public PageUtils list(Map<String, Object> param) {
	        EntityWrapper<UsTripLocationEntity> w1 = new EntityWrapper<>();
	        Page<UsTripLocationEntity> page = this.selectPage(
	                new Query<UsTripLocationEntity>(param).getPage(),
	                w1.setSqlSelect("id", "city", "name","type","amap_longitude", "amap_latitude")
	                .where("user_id={0}", param.get("userId"))
	                .and("type={0}", param.get("type"))
	                .and("is_deleted={0}", "0")
	                .orderBy("create_date", false));  // 时间的倒叙排列

	        return new PageUtils(page);

	    }

	@Override
	public UsTripLocationEntity selectByType(UsTripParam usTriprParam,String userId) {
			EntityWrapper<UsTripLocationEntity> wrapper = new EntityWrapper<>();
	        wrapper.setEntity(new UsTripLocationEntity());
	        wrapper.where("type={0}", usTriprParam.getType())
	        .and("name={0}", usTriprParam.getName())
	        .and("user_id={0}", userId);
	        List<UsTripLocationEntity> list = this.selectList(wrapper);

	        if (list.isEmpty()) {
	            return null;
	        } else if (list.size() == 1) {
	            return list.get(0);
	        }
	        return null;
		}

	@Override
	public Page<Map> historyList(Map<String, Object> params) {
		
		 Page<Map> page = new Page<>(Integer.parseInt(params.get("page").toString()),Integer.parseInt(params.get("limit").toString()));

	        return page.setRecords(this.baseMapper.selectUserListPage(page, params));
	}

	@Override
	public PageUtils collectList(Map<String, Object> params) {
		 EntityWrapper<UsTripLocationEntity> w1 = new EntityWrapper<>();
	        Page<UsTripLocationEntity> page = this.selectPage(
	                new Query<UsTripLocationEntity>(params).getPage(),
	                w1.setSqlSelect("id", "city", "name","type","amap_longitude", "amap_latitude")
	                .where("user_id={0}", params.get("userId"))
	                .and("type={0}", params.get("type"))
	                .and("is_deleted={0}", "0")
	                .orderBy("create_date", false));  // 时间的倒叙排列

	        return new PageUtils(page);
	}

	@Override
	public void deleteHistory(String type,String userId) {
		this.baseMapper.deleteHistory(type, userId);
		return;
		
	}
	}