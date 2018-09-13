package io.renren.modules.us.dao;

import java.util.List;
import java.util.Map;

import org.apache.ibatis.annotations.Mapper;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import com.baomidou.mybatisplus.plugins.pagination.Pagination;

import io.renren.modules.us.entity.UsTripLocationEntity;

/**
 * 
 * 
 * @author sys
 * @email 
 * @date 2018-04-12 16:26:30
 */
@Mapper
public interface UsTripDao extends BaseMapper<UsTripLocationEntity> {
	
	public List<Map> selectUserListPage(Pagination page ,Map map);

	public void deleteHistory(String type, String userId);

}
