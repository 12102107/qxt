package io.renren.modules.us.controller;

import io.renren.common.utils.R;
import io.renren.modules.us.param.UsWeatherParam;
import io.renren.modules.us.service.UsWeatherService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import java.io.IOException;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author sys
 * @date 2018-04-18 15:54:19
 */
@RestController
@RequestMapping("/api/weather")
@Api("天气接口")
public class UsWeatherController {

    private UsWeatherService usWeatherService;
    
    /**
     *天气接口
     * @throws IOException 
     */
    @PostMapping("get")
    @ApiOperation("获取天气接口")
    public R getWeather(@RequestBody UsWeatherParam weatherParam) {
        return usWeatherService.getWeather(weatherParam);
    }

    @Autowired
    public void setUsWeatherParam(UsWeatherService usWeatherService) {
        this.usWeatherService = usWeatherService;
    }
}
