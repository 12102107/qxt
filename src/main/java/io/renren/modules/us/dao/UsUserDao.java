package io.renren.modules.us.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.renren.modules.us.entity.UsUserEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.Map;

/**
 * @author sys
 * @email
 * @date 2018-04-12 16:26:30
 */
@Mapper
public interface UsUserDao extends BaseMapper<UsUserEntity> {

    Map<String, Object> unifyUserDataReturned(@Param("userId") String userId, @Param("cardId") String cardId);

}