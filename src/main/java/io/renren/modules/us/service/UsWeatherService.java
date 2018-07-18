package io.renren.modules.us.service;

import io.renren.common.utils.R;
import io.renren.modules.us.param.UsSmsParam;
import io.renren.modules.us.param.UsWeatherParam;

public interface UsWeatherService {

	R getWeather(UsWeatherParam weatherParam);
}
