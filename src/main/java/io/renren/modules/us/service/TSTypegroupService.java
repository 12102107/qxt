package io.renren.modules.us.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.us.entity.TSTypegroupEntity;

import java.util.Map;

/**
 * 
 *
 * @author sys
 * @email 
 * @date 2018-04-20 09:55:04
 */
public interface TSTypegroupService extends IService<TSTypegroupEntity> {

    PageUtils queryPage(Map<String, Object> params);

    String queryTypeGroupIdByCode(String typegroupcode);
}

