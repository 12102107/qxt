package io.renren.modules.us.util;

import io.renren.common.utils.Constant;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Li
 */
@Component
public class UsSmsUtil {

    private UsSmsUtil() {

    }

    public static String getCode(String mobile) {
        Logger logger = LoggerFactory.getLogger(UsSmsUtil.class);
        String url = "http://utf8.api.smschinese.cn/?Uid=gophagroup&Key=2b96c2d083a4bffa6e6f";
        String smsMobUrl = "&smsMob=";
        String smsTextUrl = "&smsText=验证码:";
        url += smsMobUrl + mobile;
        int i = UsRandomUtil.getRandom(100000, 999999);
        String code = String.valueOf(i);
        url += smsTextUrl + code;
        try {
            Response response = UsOkHttpUtil.getInstance().getDataSync(url);
            String result = response.body().string();
            if (Integer.parseInt(result) > 0) {
                return code;
            } else {
                return Constant.Message.SMS_FAIL.getValue();
            }
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
            return Constant.Message.SMS_FAIL.getValue();
        }
    }

}
