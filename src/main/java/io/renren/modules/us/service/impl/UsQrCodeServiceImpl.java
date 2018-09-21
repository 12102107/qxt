package io.renren.modules.us.service.impl;

import com.alibaba.fastjson.JSONObject;
import com.google.zxing.WriterException;
import io.renren.common.utils.R;
import io.renren.common.validator.ValidatorUtils;
import io.renren.modules.us.param.UsBaseParam;
import io.renren.modules.us.service.UsQrCodeService;
import io.renren.modules.us.util.UsIdUtil;
import io.renren.modules.us.util.UsQrCodeUtil;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Service;

import java.io.IOException;

@Service("usQrCodeService")
public class UsQrCodeServiceImpl implements UsQrCodeService {

    @Value("${us.qrcode.path}")
    private String qrCodePath;

    @Value("${us.qrcode.expireTime}")
    private Long qrCodeExpireTime;

    @Value("${us.qrcode.logoPath}")
    private String logoPath;

    @Value("${us.qrcode.url}")
    private String qrCodeUrl;

    private UsQrCodeUtil qrCodeUtil;

    @Override
    public R getQrCode(UsBaseParam param) throws IOException, WriterException {
        ValidatorUtils.validateEntity(param);
        JSONObject object = new JSONObject();
        long time = System.currentTimeMillis();
        object.put("expireDate", time + qrCodeExpireTime);
        String fileName = UsIdUtil.generateId();
        qrCodeUtil.encode(object.toJSONString(), logoPath, qrCodePath, true, fileName);
        object.put("url", qrCodeUrl + fileName + ".jpg");
        return R.ok(object);
    }

    @Autowired
    public void setQrCodeUtil(UsQrCodeUtil qrCodeUtil) {
        this.qrCodeUtil = qrCodeUtil;
    }
}
