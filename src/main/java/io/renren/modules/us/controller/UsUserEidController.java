package io.renren.modules.us.controller;

import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.us.param.UsEidLoginParam;
import io.renren.modules.us.param.UsSessionParam;
import io.renren.modules.us.param.UsUserAuthParam;
import io.renren.modules.us.service.UsUserEidService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.context.annotation.Scope;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RestController;

import java.io.UnsupportedEncodingException;

@RestController
@Api("EID接口")
public class UsUserEidController {

    private UsUserEidService eidService;

    @Scope("prototype")
    @PostMapping("/api/user/eidLogin")
    @ApiOperation("EID登录")
    public R eidLogin(@RequestBody UsEidLoginParam param) throws InterruptedException, UnsupportedEncodingException {
        ValidatorUtils.validateEntity(param);
        return eidService.eidLogin(param);
    }

    @Scope("prototype")
    @PostMapping("/api/user/eidAuth")
    @ApiOperation("EID认证")
    public R eidAuth(@RequestBody UsSessionParam param) throws InterruptedException, UnsupportedEncodingException {
        ValidatorUtils.validateEntity(param);
        return eidService.eidAuth(param);
    }

//    @Scope("prototype")
//    @PostMapping("/api/user/auth")
//    @ApiOperation("第三方EID认证")
//    public R auth(@RequestBody UsUserAuthParam param) throws InterruptedException, UnsupportedEncodingException {
//        ValidatorUtils.validateEntity(param);
//        return eidService.auth(param);
//    }

    @Autowired
    public void setEidService(UsUserEidService eidService) {
        this.eidService = eidService;
    }
}
