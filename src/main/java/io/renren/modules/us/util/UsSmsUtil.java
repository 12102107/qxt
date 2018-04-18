package io.renren.modules.us.util;

import io.renren.common.utils.Constant;
import okhttp3.Response;
import org.springframework.stereotype.Component;

/**
 * @author Li
 */
@Component
public class UsSmsUtil {

    private static String url = "http://utf8.api.smschinese.cn/?Uid=gophagroup&Key=2b96c2d083a4bffa6e6f";
    private static String smsMobUrl = "&smsMob=";
    private static String smsTextUrl = "&smsText=验证码:";

    public static String message(String mobile) {
        url += smsMobUrl + mobile;
        int i = (int) ((Math.random() * 9 + 1) * 100000);
        String code = String.valueOf(i);
        url += smsTextUrl + code;
        try {
            Response response = UsOkHttpUtil.getInstance().getDataSync(url);
            String relust = response.body().string();
            if ("1".equals(relust)) {
                return code;
            } else {
                return Constant.Message.SMS_FAIL.getValue();
            }
        } catch (Exception e) {
            e.printStackTrace();
            return Constant.Message.SMS_FAIL.getValue();
        }
    }

}
