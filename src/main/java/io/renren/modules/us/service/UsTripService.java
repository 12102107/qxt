package io.renren.modules.us.service;

import java.util.Map;

import com.baomidou.mybatisplus.plugins.Page;
import com.baomidou.mybatisplus.service.IService;

import io.renren.common.utils.PageUtils;
import io.renren.modules.us.entity.UsTripLocationEntity;
import io.renren.modules.us.param.UsTripParam;

/**
 * @author sys
 * @email
 * @date 2018-04-12 16:26:30
 */
public interface UsTripService extends IService<UsTripLocationEntity> {

	PageUtils list(Map<String, Object> params);

	UsTripLocationEntity selectByType(UsTripParam usTriprParam, String userId);

	Page<Map> historyList(Map<String, Object> params);

	PageUtils collectList(Map<String, Object> params);

	void deleteHistory(String type, String userId);




}

