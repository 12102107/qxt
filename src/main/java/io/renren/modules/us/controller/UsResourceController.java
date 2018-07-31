package io.renren.modules.us.controller;

import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.us.param.UsResourceListParam;
import io.renren.modules.us.service.UsResourceService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author sys
 * @date 2018-04-25 14:29:11
 */
@RestController
@RequestMapping("/api/resource")
@Api("资源接口")
public class UsResourceController {
    private UsResourceService usResourceService;

    /**
     * 资源接口
     */
    @PostMapping("list")
    @ApiOperation("资源接口")
   public R list(@RequestBody UsResourceListParam resourceParam) {
        ValidatorUtils.validateEntity(resourceParam);
        return usResourceService.list(resourceParam);
   }
    @Autowired
    public void setUsResourceService(UsResourceService usResourceService) {
        this.usResourceService = usResourceService;
    }

}
