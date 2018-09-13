package io.renren.modules.us.dao;

import com.baomidou.mybatisplus.mapper.BaseMapper;
import io.renren.modules.us.entity.UsHistoryEntity;
import io.renren.modules.us.entity.UsPushEntity;
import org.apache.ibatis.annotations.Mapper;
import org.apache.ibatis.annotations.Param;

import java.util.List;
import java.util.Map;

@Mapper
public interface UsHistoryDao extends BaseMapper<UsHistoryEntity> {

    /**
     * 批量更新状态
     */
    int deleteHistory(@Param("userId")String userId/*,@Param("findMethod")String findMethod*/);

    List<Map> isExistOrgin(@Param("userId")String userId, @Param("origin")String origin, @Param("destination")String destination/*,@Param("findMethod")String findMethod*/);

    void updateDate(@Param("id")String id, @Param("dateString")String dateString/*,@Param("findMethod")String findMethod*/);
}
