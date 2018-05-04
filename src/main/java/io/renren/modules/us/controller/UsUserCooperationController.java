package io.renren.modules.us.controller;

import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.us.param.UsUserCooperationBindParam;
import io.renren.modules.us.param.UsUserCooperationInfoParam;
import io.renren.modules.us.param.UsUserCooperationSignInParam;
import io.renren.modules.us.param.UsUserCooperationSignUpParam;
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

    private UsUserCooperationService cooperationService;

    /**
     * 第三方登录接口
     */
    @PostMapping("signin")
    @ApiOperation("第三方登录接口")
    public R signIn(@RequestBody UsUserCooperationSignInParam signInParam) {
        ValidatorUtils.validateEntity(signInParam);
        return cooperationService.signIn(signInParam);
    }

    /**
     * 第三方信息接口
     */
    @PostMapping("info")
    @ApiOperation("第三方信息接口")
    public R info(@RequestBody UsUserCooperationInfoParam infoParam) {
        ValidatorUtils.validateEntity(infoParam);
        return cooperationService.info(infoParam);
    }

    /**
     * 第三方注册接口
     */
    @PostMapping("signup")
    @ApiOperation("第三方注册接口")
    public R signUp(@RequestBody UsUserCooperationSignUpParam signUpParam) {
        ValidatorUtils.validateEntity(signUpParam);
        return cooperationService.signUp(signUpParam);
    }

    /**
     * 第三方绑定接口
     */
    @PostMapping("bind")
    @ApiOperation("第三方绑定接口")
    public R bing(@RequestBody UsUserCooperationBindParam bindParam) {
        ValidatorUtils.validateEntity(bindParam);
        return cooperationService.bind(bindParam);
    }

    @Autowired
    public void setCooperationService(UsUserCooperationService cooperationService) {
        this.cooperationService = cooperationService;
    }
}
