package io.renren.modules.us.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.renren.modules.us.entity.UsNoticeEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;
import org.springframework.stereotype.Repository;

/**
 * 
 * 
 * @author sys
 * @email 
 * @date 2018-04-23 13:47:28
 */
@Mapper
@Repository
public interface UsNoticeDao extends BaseMapper<UsNoticeEntity> {

    UsNoticeEntity findByNoticeType(@Param("noticeType") String noticeType, @Param("appId") String appId);
}
