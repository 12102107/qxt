package io.renren.modules.us.util;

import net.sf.json.JSONObject;
import org.apache.http.HttpResponse;
import org.apache.http.util.EntityUtils;

import java.util.HashMap;
import java.util.Map;

/**
 * 实名认证工具类
 */
public class RealCertUtil {
    final static String host = "https://naidcard.market.alicloudapi.com";
    final static String path = "/nidCard";
    final static String method = "GET";
    final static String appcode = "c379749a229c44a88e5d7bb789549461";
    public static boolean realNameCert(String name,String idcard){
        Map<String, String> headers = new HashMap<String, String>();
        headers.put("Authorization", "APPCODE " + appcode);
        Map<String, String> querys = new HashMap<String, String>();
        querys.put("idCard",idcard);
        querys.put("name", name);
        try {
            HttpResponse response = HttpUtils.doGet(host, path, method, headers, querys);
            String result = EntityUtils.toString(response.getEntity());
            JSONObject jsonObject = JSONObject.fromObject(result);
            if(jsonObject.get("status").equals("01")){
                return true;
            }

        } catch (Exception e) {
            e.printStackTrace();
        }
        return false;
    }
    public static void main(String[] args) {

        boolean b = RealCertUtil.realNameCert("伍帅","510703198602170052");
        System.err.println(b);
    }
}
