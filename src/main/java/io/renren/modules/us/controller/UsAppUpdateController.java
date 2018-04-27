package io.renren.modules.us.controller;

import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.us.param.UsBaseParam;
import io.renren.modules.us.service.UsAppUpdateService;
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
@RequestMapping("/api/update")
@Api("更新接口")
public class UsAppUpdateController {

    private UsAppUpdateService updateService;

    /**
     * 更新接口
     */
    @PostMapping("list")
    @ApiOperation("更新接口")
    public R list(@RequestBody UsBaseParam baseParam) {
        ValidatorUtils.validateEntity(baseParam);
        return updateService.list(baseParam);
    }

    @Autowired
    public void setUpdateService(UsAppUpdateService updateService) {
        this.updateService = updateService;
    }
}
