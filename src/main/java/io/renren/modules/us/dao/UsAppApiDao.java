package io.renren.modules.us.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.renren.modules.us.entity.UsAppApiEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author sys
 * @date 2018-05-03 10:30:53
 */
@Mapper
@Repository
public interface UsAppApiDao extends BaseMapper<UsAppApiEntity> {

    Integer countId(@Param("appid") String appid, @Param("url") String url);

}