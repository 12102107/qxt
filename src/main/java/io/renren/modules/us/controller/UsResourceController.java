package io.renren.modules.us.controller;

import io.renren.modules.us.service.UsResourceService;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author sys
 * @date 2018-04-25 14:29:11
 */
@RestController
//@RequestMapping("/api/resource")
//@Api("资源接口")
public class UsResourceController {
    private UsResourceService usResourceService;

    /**
     * 资源接口
     */
//    @PostMapping("list")
//    @ApiOperation("资源接口")
//    public R list(@RequestBody UsResourceParam resourceParam) {
//        ValidatorUtils.validateEntity(resourceParam);
//        return usResourceService.list(resourceParam);
//    }
    @Autowired
    public void setUsResourceService(UsResourceService usResourceService) {
        this.usResourceService = usResourceService;
    }

}
