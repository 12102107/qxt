package io.renren.modules.us.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.us.entity.UsResourceEntity;

import java.util.Map;

/**
 * 
 *
 * @author sys
 * @email 
 * @date 2018-04-12 16:21:17
 */
public interface UsResourceService extends IService<UsResourceEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

