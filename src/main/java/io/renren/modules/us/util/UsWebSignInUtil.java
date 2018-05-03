package io.renren.modules.us.util;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.us.param.UsUserCooperationInfoParam;
import okhttp3.Response;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Li
 */
@Component
public class UsWebSignInUtil {

    private String weChatOpenIdUrl = "https://api.weixin.qq.com/sns/oauth2/access_token?grant_type=authorization_code";
    private String weChatAppid = "wx93f27b60fc14fef6";
    private String weChatSecret = "0e7f028970e083307cd238ab73803f26";
    private String weChatInfoUrl = "https://api.weixin.qq.com/sns/userinfo?lang=zh_CN";

    public JSONObject getInfo(UsUserCooperationInfoParam infoParam) {
        String type = infoParam.getType();
        switch (type) {
            case "4": {
                try {
                    UsOkHttpUtil okHttp = UsOkHttpUtil.getInstance();
                    //获取openid
                    String u1 = weChatOpenIdUrl + "&appid=" + weChatAppid + "&secret=" + weChatSecret + "&code=" + infoParam.getCode();
                    Response response = okHttp.getDataSync(u1);
                    String reulst = response.body().string();
                    JSONObject object = JSONObject.parseObject(reulst);
                    if (object.containsKey("errcode")) {
                        return object;
                    }
                    //获取accessToken
                    String accessToken = object.getString("access_token");
                    //获取用户信息
                    String u2 = weChatInfoUrl + "&openid=" + object.get("openid") + "&access_token=" + object.get("access_token");
                    response = okHttp.getDataSync(u2);
                    reulst = response.body().string();
                    object = JSONObject.parseObject(reulst);
                    if (object.containsKey("errcode")) {
                        return object;
                    }
                    object.put("accessToken", accessToken);
                    return object;
                } catch (IOException e) {
                    e.printStackTrace();
                    JSONObject object = new JSONObject();
                    object.put("errcode", e.getMessage());
                    return object;
                }
            }
            default: {
                return null;
            }
        }
    }
}
