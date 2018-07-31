package io.renren.modules.us.service;

import com.baomidou.mybatisplus.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.us.entity.UsAdvertisingEntity;
import io.renren.modules.us.entity.UsNoticeEntity;

import java.util.Map;

/**
 * 
 *
 * @author sys
 * @email 
 * @date 2018-04-23 13:47:28
 */
public interface UsAdvertisingService extends IService<UsAdvertisingEntity> {

    PageUtils queryPage(Map<String, Object> params);
}

