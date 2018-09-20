package io.renren.modules.us.util;

import com.alibaba.fastjson.JSON;
import com.alibaba.fastjson.JSONObject;
import okhttp3.*;
import org.springframework.beans.factory.annotation.Value;
import org.springframework.stereotype.Component;

import java.io.IOException;
import java.util.Map;

@Component
public class UsPayUtil {

    @Value("${us.pay.mchId}")
    private String mchId;
    // 加签key
    @Value("${us.pay.reqKey}")
    private String reqKey;
    // 验签key
    @Value("${us.pay.repKey}")
    private String repKey;
    @Value("${us.pay.createOrderUrl}")
    private String createOrderUrl;
    @Value("${us.pay.notifyUrl}")
    private String notifyUrl;

    private UsPayUtil() {

    }

    public String createOrder(String mchOrderNo, String channelId, int amount, String currency, String clientIp
            , String device, String subject, String body, String param1, String param2, String extra) throws IOException {
        JSONObject paramMap = new JSONObject();
        //商户ID
        paramMap.put("mchId", mchId);
        //商户订单号
        paramMap.put("mchOrderNo", mchOrderNo);
        //支付渠道ID,WX_NATIVE(微信扫码),WX_JSAPI(微信公众号或微信小程序),WX_APP(微信APP),WX_MWEB(微信H5),ALIPAY_WAP(支付宝手机支付),ALIPAY_PC(支付宝网站支付),ALIPAY_MOBILE(支付宝移动支付)
        paramMap.put("channelId", channelId);
        //支付金额,单位分
        paramMap.put("amount", amount);
        //币种,cny人民币
        paramMap.put("currency", currency);
        //用户地址,微信H5支付时要真实的
        paramMap.put("clientIp", clientIp);
        //设备
        paramMap.put("device", device);
        paramMap.put("subject", subject);
        paramMap.put("body", body);
        //回调URL
        paramMap.put("notifyUrl", notifyUrl);
        //扩展参数1
        if (!"".equals(param1)) {
            paramMap.put("param1", param1);
        }
        //扩展参数2
        if (!"".equals(param2)) {
            paramMap.put("param2", param2);
        }
        paramMap.put("extra", extra);
        String reqSign = UsPayDigestUtil.getSign(paramMap, reqKey);
        //签名
        paramMap.put("sign", reqSign);
        String reqData = "params=" + paramMap.toJSONString();
        System.out.println("请求支付中心下单接口,请求数据:" + reqData);

        OkHttpClient okHttpClient = UsOkHttpUtil.getInstance().getOkHttpClient();
        RequestBody requestBody = FormBody.create(MediaType.parse("application/json; charset=utf-8"), paramMap.toJSONString());
        Request request = new Request.Builder().post(requestBody).url(createOrderUrl).build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        String result = response.body().string();
        System.out.println("请求支付中心下单接口,响应数据:" + result);
        return result;
    }

    public boolean orderSignIsValid(String result) {
        Map retMap = JSON.parseObject(result);
        if ("SUCCESS".equals(retMap.get("retCode")) && "SUCCESS".equalsIgnoreCase(retMap.get("resCode").toString())) {
            // 验签
            String checkSign = UsPayDigestUtil.getSign(retMap, repKey, "sign", "payParams");
            String retSign = (String) retMap.get("sign");
            if (checkSign.equals(retSign)) {
                System.out.println("=========支付中心下单验签成功=========");
                return true;
            } else {
                System.out.println("=========支付中心下单验签失败=========");
                return false;
            }
        } else {
            return false;
        }
    }

}
