package io.renren.modules.us.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.renren.modules.us.entity.UsApiEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * @author sys
 */
@Mapper
@Repository
public interface UsApiDao extends BaseMapper<UsApiEntity> {
    UsApiEntity getWithAppId(@Param("appId") String appId, @Param("url") String url);

    UsApiEntity getWithoutAppId(@Param("url") String url);
}