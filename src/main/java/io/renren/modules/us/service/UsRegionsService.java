package io.renren.modules.us.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.us.entity.UsRegionsEntity;

import java.util.Map;

/**
 * 
 *
 * @author sys
 * @email 
 * @date 2018-04-19 14:43:50
 */
public interface UsRegionsService extends IService<UsRegionsEntity> {

    PageUtils queryPage(Map<String, Object> params);

}

