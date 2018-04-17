package io.renren.modules.us.controller;

import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.us.param.UsUserCooperationParam;
import io.renren.modules.us.service.UsUserCooperationService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sys
 * @date 2018-04-12 16:21:17
 */
@RestController
@RequestMapping("/api/cooperation")
@Api("第三方接口")
public class UsUserCooperationController {
    @Autowired
    private UsUserCooperationService cooperationService;

    /**
     * 第三方登录接口
     */
    @PostMapping("signin")
    @ApiOperation("第三方登录接口")
    public R signIn(@RequestBody UsUserCooperationParam cooperationParam) {
        ValidatorUtils.validateEntity(cooperationParam);
        return cooperationService.signIn(cooperationParam);
    }

}
