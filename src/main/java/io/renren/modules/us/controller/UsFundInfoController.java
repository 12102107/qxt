package io.renren.modules.us.controller;

import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.us.param.UsFundInfoParam;
import io.renren.modules.us.service.UsFundInfoService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

/**
 * @author sys
 * @date 2018-04-28 14:01:06
 */
@RestController
@RequestMapping("/api/fund")
@Api("公积金接口")
public class UsFundInfoController {

    private UsFundInfoService usFundInfoService;

    /**
     * 个人信息接口
     */
    @PostMapping("info")
    @ApiOperation("个人信息接口")
    public R info(@RequestBody UsFundInfoParam fundInfoParam) {
        ValidatorUtils.validateEntity(fundInfoParam);
        return usFundInfoService.info(fundInfoParam);
    }

    @Autowired
    public void setUsFundInfoService(UsFundInfoService usFundInfoService) {
        this.usFundInfoService = usFundInfoService;
    }

}
