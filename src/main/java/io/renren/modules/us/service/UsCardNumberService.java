package io.renren.modules.us.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.us.entity.UsCardNumberEntity;

import java.util.Map;

/**
 * 
 *
 * @author sys
 * @email 
 * @date 2018-08-29 13:09:39
 */
public interface UsCardNumberService extends IService<UsCardNumberEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

