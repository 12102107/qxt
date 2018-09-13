package io.renren.modules.us.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.us.entity.UsHistoryEntity;

import java.util.List;
import java.util.Map;

public interface UsHistoryService extends IService<UsHistoryEntity> {

    PageUtils list(Map<String, Object> param);

    void delete(String userId/*,String findMethod*/);

    List<Map> isExistOrgin(String userId, String origin, String destination/*,String findMethod*/);

    void updateDate(String id, String dateString/*,String findMethod*/);
}
