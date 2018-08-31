package io.renren.modules.us.controller;

import io.renren.common.utils.R;
import io.renren.modules.us.param.UsCardDetailParam;
import io.renren.modules.us.param.UsSessionParam;
import io.renren.modules.us.service.UsCardService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;


/**
 * @author sys
 */
@RestController
@RequestMapping("/api/card")
@Api("卡包接口")
public class UsCardController {

    private UsCardService cardService;

    /**
     * 列表
     */
    @PostMapping("list")
    @ApiOperation("卡包列表接口")
    public R list(@RequestBody UsSessionParam param) {
        return cardService.list(param);
    }

    @PostMapping("detail")
    @ApiOperation("卡包详请接口")
    public R detail(@RequestBody UsCardDetailParam param) {
        return cardService.detail(param);
    }

    @Autowired
    public void setUsCardService(UsCardService usCardService) {
        this.cardService = usCardService;
    }
}
