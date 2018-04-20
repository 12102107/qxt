package io.renren.modules.us.util;

import io.renren.common.utils.Constant;
import okhttp3.Response;
import org.springframework.stereotype.Component;

/**
 * @author Li
 */
@Component
public class UsSmsUtil {

    public static String getCode(String mobile) {
        String url = "http://utf8.api.smschinese.cn/?Uid=gophagroup&Key=2b96c2d083a4bffa6e6f";
        String smsMobUrl = "&smsMob=";
        String smsTextUrl = "&smsText=验证码:";
        url += smsMobUrl + mobile;
        int i = (int) ((Math.random() * 9 + 1) * 100000);
        String code = String.valueOf(i);
        url += smsTextUrl + code;
        try {
            Response response = UsOkHttpUtil.getInstance().getDataSync(url);
            String relust = response.body().string();
            if (Integer.parseInt(relust) > 0) {
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
