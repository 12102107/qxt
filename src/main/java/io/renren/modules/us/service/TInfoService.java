package io.renren.modules.us.service;

import com.baomidou.mybatisplus.service.IService;
import io.renren.common.utils.PageUtils;
import io.renren.modules.us.entity.TInfoEntity;

import java.util.Map;

/**
 * 
 *
 * @author sys
 * @email 
 * @date 2018-04-18 16:06:54
 */
public interface TInfoService extends IService<TInfoEntity> {

    PageUtils queryPage(Map<String, Object> params);

    TInfoEntity queryByCode(String code);
}

