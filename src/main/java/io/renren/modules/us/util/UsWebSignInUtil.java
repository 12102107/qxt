package io.renren.modules.us.util;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.us.param.UsUserCooperationInfoParam;
import okhttp3.Response;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;

/**
 * @author Li
 */
@Component
public class UsWebSignInUtil {

    private static final String ERROR_CODE = "errcode";

    private Logger logger = LoggerFactory.getLogger(UsWebSignInUtil.class);

    @Value("${us.cooperation.weChatOpenIdUrl}")
    private String weChatOpenIdUrl;

    @Value("${us.cooperation.weChatAppid}")
    private String weChatAppid;

    @Value("${us.cooperation.weChatSecret}")
    private String weChatSecret;

    @Value("${us.cooperation.weChatInfoUrl}")
    private String weChatInfoUrl;

    @Value("${us.cooperation.qqTokenUrl}")
    private String qqTokenUrl;

    @Value("${us.cooperation.qqClientId}")
    private String qqClientId;

    @Value("${us.cooperation.qqClientSecret}")
    private String qqClientSecret;

    @Value("${us.cooperation.qqRedirectUri}")
    private String qqRedirectUri;

    @Value("${us.cooperation.qqOpenIdUrl}")
    private String qqOpenIdUrl;

    private JSONObject getWeChatOpenId(UsUserCooperationInfoParam infoParam) throws IOException {
        UsOkHttpUtil okHttp = UsOkHttpUtil.getInstance();
        String url = weChatOpenIdUrl + "&appid=" + weChatAppid + "&secret=" + weChatSecret + "&code=" + infoParam.getCode();
        Response response = okHttp.getDataSync(url);
        return JSONObject.parseObject(response.body().string());
    }

    private JSONObject getWeChatUserInfo(JSONObject object) throws IOException {
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
        if (result.contains("code")) {
            object.put(ERROR_CODE, result);
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
        result = result.substring(result.indexOf('{'), result.indexOf('}') + 1);
        JSONObject object = JSONObject.parseObject(result);
        if (object.containsKey("code")) {
            object.put(ERROR_CODE, object.get("code"));
        }
        return object;
    }

    public JSONObject getInfo(UsUserCooperationInfoParam infoParam) {
        String type = infoParam.getType();
        switch (type) {
            case "4":
                try {
                    //获取openid
                    JSONObject o1 = getWeChatOpenId(infoParam);
                    if (o1.containsKey(ERROR_CODE)) {
                        return o1;
                    }
                    //获取用户信息
//                    JSONObject o2 = getWeChatInfo(o1);
//                    if (o2.containsKey(ERROR_CODE)) {
//                        return o2;
//                    }
//                    o1.putAll(o2);
                    return o1;
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                    JSONObject object = new JSONObject();
                    object.put(ERROR_CODE, e.getMessage());
                    return object;
                }
            case "3":
                try {
                    //获取access_token
                    JSONObject o1 = getQqToken(infoParam);
                    if (o1.containsKey(ERROR_CODE)) {
                        return o1;
                    }
                    //获取openid
                    JSONObject o2 = getQqOpenId(o1);
                    if (o2.containsKey(ERROR_CODE)) {
                        return o2;
                    }
                    o1.putAll(o2);
                    return o1;
                } catch (IOException e) {
                    logger.error(e.getMessage(), e);
                    JSONObject object = new JSONObject();
                    object.put(ERROR_CODE, e.getMessage());
                    return object;
                }
            default:
                return null;
        }
    }
}
