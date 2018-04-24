package io.renren.modules.us.controller;

import io.renren.common.utils.R;
import io.renren.modules.us.param.UsSmsParam;
import io.renren.modules.us.service.UsSmsService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
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
@RequestMapping("/api/sms")
@Api("短信接口")
public class UsSmsController {

    private UsSmsService usSmsService;

    /**
     * 短信接口
     */
    @PostMapping("get")
    @ApiOperation("获取验证码接口")
    public R getCode(@RequestBody UsSmsParam smsParam) {
        return usSmsService.getCode(smsParam);
    }

    @Autowired
    public void setUsSmsService(UsSmsService usSmsService) {
        this.usSmsService = usSmsService;
    }
}
