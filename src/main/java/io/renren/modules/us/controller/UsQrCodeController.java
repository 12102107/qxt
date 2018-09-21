package io.renren.modules.us.controller;

import com.google.zxing.WriterException;
import io.renren.common.utils.R;
import io.renren.modules.us.param.UsBaseParam;
import io.renren.modules.us.service.UsQrCodeService;
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
 * @date 2018-04-18 15:54:19
 */
@RestController
@RequestMapping("/api/qrcode")
@Api("二维码接口")
public class UsQrCodeController {

    private UsQrCodeService service;

    @PostMapping("get")
    @ApiOperation("获取二维码接口")
    public R getQrCode(@RequestBody UsBaseParam param) throws IOException, WriterException {
        return service.getQrCode(param);
    }

    @Autowired
    public void setService(UsQrCodeService service) {
        this.service = service;
    }

}