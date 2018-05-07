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
    private String qqTokenUrl = "https://graph.qq.com/oauth2.0/token?grant_type=authorization_code";
    private String qqClientId = "101474222";
    private String qqClientSecret = "89b6fd82cd2aedee1c6567ca9d15e0de";
    private String qqRedirectUri = "http://www.dq-city.com/pages/login/login.html";
    private String qqOpenIdUrl = "https://graph.qq.com/oauth2.0/me";

    private JSONObject getWeChatOpenId(UsUserCooperationInfoParam infoParam) throws IOException {
        UsOkHttpUtil okHttp = UsOkHttpUtil.getInstance();
        String url = weChatOpenIdUrl + "&appid=" + weChatAppid + "&secret=" + weChatSecret + "&code=" + infoParam.getCode();
        Response response = okHttp.getDataSync(url);
        return JSONObject.parseObject(response.body().string());
    }

    private JSONObject getWeChatInfo(JSONObject object) throws IOException {
        UsOkHttpUtil okHttp = UsOkHttpUtil.getInstance();
        String u2 = weChatInfoUrl + "&openid=" + object.get("openid") + "&access_token=" + object.get("access_token");
        Response response = okHttp.getDataSync(u2);
        return JSONObject.parseObject(response.body().string());
    }

    private JSONObject getQqToken(UsUserCooperationInfoParam infoParam) throws IOException {
        UsOkHttpUtil okHttp = UsOkHttpUtil.getInstance();
        String url = qqTokenUrl + "&client_id=" + qqClientId + "&client_secret=" + qqClientSecret +
                "&code=" + infoParam.getCode() + "&redirect_uri=" + qqRedirectUri;
        Response response = okHttp.getDataSync(url);
        String result = response.body().string();
        JSONObject object = new JSONObject();
        if (result.indexOf("code") >= 0) {
            object.put("errcode", result);
            return object;
        }
        for (String s : result.split("&")) {
            String[] temp = s.split("=");
            object.put(temp[0], temp[1]);
        }
        return object;
    }

    private JSONObject getQqOpenId(JSONObject param) throws IOException {
        UsOkHttpUtil okHttp = UsOkHttpUtil.getInstance();
        String url = qqOpenIdUrl + "?access_token=" + param.get("access_token");
        Response response = okHttp.getDataSync(url);
        String result = response.body().string();
        result = result.substring(result.indexOf("{"), result.indexOf("}") + 1);
        JSONObject object = JSONObject.parseObject(result);
        if (object.containsKey("code")) {
            object.put("errcode", object.get("code"));
        }
        return object;
    }

    public JSONObject getInfo(UsUserCooperationInfoParam infoParam) {
        String type = infoParam.getType();
        switch (type) {
            case "4": {
                try {
                    //获取openid
                    JSONObject o1 = getWeChatOpenId(infoParam);
                    if (o1.containsKey("errcode")) {
                        return o1;
                    }
                    //获取用户信息
//                    JSONObject o2 = getWeChatInfo(o1);
//                    if (o2.containsKey("errcode")) {
//                        return o2;
//                    }
//                    o1.putAll(o2);
                    return o1;
                } catch (IOException e) {
                    e.printStackTrace();
                    JSONObject object = new JSONObject();
                    object.put("errcode", e.getMessage());
                    return object;
                }
            }
            case "3": {
                try {
                    //获取access_token
                    JSONObject o1 = getQqToken(infoParam);
                    if (o1.containsKey("errcode")) {
                        return o1;
                    }
                    //获取openid
                    JSONObject o2 = getQqOpenId(o1);
                    if (o2.containsKey("errcode")) {
                        return o2;
                    }
                    o1.putAll(o2);
                    return o1;
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
