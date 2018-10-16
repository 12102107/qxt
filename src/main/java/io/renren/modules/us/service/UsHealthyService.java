package io.renren.modules.us.service;

import java.util.Map;

import com.baomidou.mybatisplus.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.us.entity.UsHealthyEntity;

/**
 * 
 *
 * @author sys
 * @email 
 * @date 2018-04-23 13:47:28
 */
public interface UsHealthyService extends IService<UsHealthyEntity> {

    PageUtils queryPage(Map<String, Object> params);

}

