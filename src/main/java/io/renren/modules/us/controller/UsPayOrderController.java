package io.renren.modules.us.controller;

import io.renren.common.utils.HttpContextUtils;
import io.renren.common.utils.R;
import io.renren.modules.us.param.UsPayOrderNotifyParam;
import io.renren.modules.us.param.UsPayOrderParam;
import io.renren.modules.us.service.UsPayOrderService;
import io.swagger.annotations.Api;
import io.swagger.annotations.ApiOperation;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.web.bind.annotation.PostMapping;
import org.springframework.web.bind.annotation.RequestBody;
import org.springframework.web.bind.annotation.RequestMapping;
import org.springframework.web.bind.annotation.RestController;

import java.io.IOException;


/**
 * @author sys
 * @email
 * @date 2018-09-17 14:43:06
 */
@RestController
@RequestMapping("/api/pay")
@Api("支付接口")
public class UsPayOrderController {

    private UsPayOrderService payOrderService;

    @PostMapping("order")
    @ApiOperation("下单接口")
    public R order(@RequestBody UsPayOrderParam param) throws IOException {
        return payOrderService.order(param, HttpContextUtils.getHttpServletRequest());
    }

    @RequestMapping("orderNotify")
    @ApiOperation("支付回调接口")
    public String orderNotify(UsPayOrderNotifyParam param) {
        return payOrderService.orderNotify(param);
    }

    @Autowired
    public void setUsPayOrderService(UsPayOrderService usPayOrderService) {
        this.payOrderService = usPayOrderService;
    }
}