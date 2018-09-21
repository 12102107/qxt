package io.renren.modules.us.service;

import com.google.zxing.WriterException;
import io.renren.common.utils.R;
import io.renren.modules.us.param.UsBaseParam;

import java.io.IOException;

public interface UsQrCodeService {
    R getQrCode(UsBaseParam param) throws IOException, WriterException;
}