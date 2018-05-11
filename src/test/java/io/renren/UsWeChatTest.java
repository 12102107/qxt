package io.renren;

import com.alibaba.fastjson.JSONObject;
import io.renren.modules.us.param.UsUserCooperationInfoParam;
import io.renren.modules.us.util.UsOkHttpUtil;
import io.renren.modules.us.util.UsWebSignInUtil;
import okhttp3.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsWeChatTest {

    @Autowired
    private UsWebSignInUtil webSignInUtil;

    @Test
    public void getOpenid() throws IOException {
        //{"access_token":"9_19VzxGRQqCKqTv_XD-NgIxCffUGw-Hncch2y-EFGfU9eY5uYRMbm0-yQ2ew0Y9y9kOsvX5M_4iIE0FkQX4Hi4g","expires_in":7200,
// "refresh_token":"9_--9Aa1-H-j6yecjZveqcYlh_krBY3nMkFNZsUAeFepQugXq9MKuMM_hk45mWhoualDma2CsOBe20Kn6UJDLaZw",
// "openid":"o_I2m1cyndsNMBOuGxDQ8Za5E3Aw","scope":"snsapi_userinfo"}
        String url = "https://api.weixin.qq.com/sns/oauth2/access_token?appid=wx93f27b60fc14fef6" +
                "&secret=0e7f028970e083307cd238ab73803f26" +
                "&code=081zV6ic2JDHyD09Iwhc2tI3ic2zV6is" +
                "&grant_type=authorization_code";
        UsOkHttpUtil okHttpUtil = UsOkHttpUtil.getInstance();
        Response response = okHttpUtil.getDataSync(url);
        try {
            String reulst = response.body().string();
            System.out.println("短信接口==============================" + reulst);
        } catch (IOException e) {
            System.out.println("短信接口==============================" + e.getMessage());
        }
    }

    @Test
    public void getUserInfo() throws IOException {
//"openid":"o_I2m1cyndsNMBOuGxDQ8Za5E3Aw","nickname":"不畏浮云遮望眼","sex":1,"language":"zh_CN",
// "city":"哈尔滨","province":"黑龙江","country":"中国",
// "headimgurl":"http:\/\/thirdwx.qlogo.cn\/mmopen\/vi_32\/Q0j4TwGTfTLRBcdJV3jicTd9bNvo4uI9QypwtiaOHxjd8mNP1wt77ZG1wRbxGHSNbpZAYr6e27tlr9ibFouM2egAw\/132",
// "privilege":[]}
        String url = "https://api.weixin.qq.com/sns/userinfo?access_token=9_19VzxGRQqCKqTv_XD-NgIxCffUGw-Hncch2y-EFGfU9eY5uYRMbm0-yQ2ew0Y9y9kOsvX5M_4iIE0FkQX4Hi4g" +
                "&openid=o_I2m1cyndsNMBOuGxDQ8Za5E3Aw" +
                "&lang=zh_CN";
        UsOkHttpUtil okHttpUtil = UsOkHttpUtil.getInstance();
        Response response = okHttpUtil.getDataSync(url);
        try {
            String reulst = response.body().string();
            JSONObject object = JSONObject.parseObject(reulst);
            System.out.println("短信接口==============================" + object.get("openid"));
            System.out.println("短信接口==============================" + reulst);
        } catch (IOException e) {
            System.out.println("短信接口==============================" + e.getMessage());
        }
    }

    @Test
    public void testWeChat() {
        UsUserCooperationInfoParam webSignInParam = new UsUserCooperationInfoParam();
        webSignInParam.setType("4");
        webSignInParam.setCode("021kF4Hb17RGLt0xq3Ib1DwNGb1kF4HB");
        JSONObject object = webSignInUtil.getInfo(webSignInParam);
        System.out.println("web登录接口==============================" + object.toString());
    }

    @Test
    public void testTokenQQ() throws IOException {
        //access_token=1D976DEAFFE44DBF9AE358BFADC1DBAB&expires_in=7776000&refresh_token=CDABAD5788911FC5F14A143457CBA019
        String url = "https://graph.qq.com/oauth2.0/token" +
                "?grant_type=authorization_code" +
                "&client_id=101474222" +
                "&client_secret=89b6fd82cd2aedee1c6567ca9d15e0de" +
                "&code=A9F360BF1B62E64E6EACBDCFE64EE120" +
                "&redirect_uri=http://www.dq-city.com/pages/login/login.html";
        UsOkHttpUtil okHttpUtil = UsOkHttpUtil.getInstance();
        Response response = okHttpUtil.getDataSync(url);
        String reulst = response.body().string();
        System.out.println("QQ接口=" + reulst);
    }

    @Test
    public void testOpenIdQQ() throws IOException {
        //callback( {"client_id":"101474222","openid":"D11B246F053569687480C725F17FCDCA"} );
        String url = "https://graph.qq.com/oauth2.0/me" +
                "?access_token=1D976DEAFFE44DBF9AE358BFADC1DBAB";
        UsOkHttpUtil okHttpUtil = UsOkHttpUtil.getInstance();
        Response response = okHttpUtil.getDataSync(url);
        String reulst = response.body().string();
        System.out.println("QQ接口=" + reulst);
    }

    @Test
    public void testOpenIdJson() {
        String str = "callback( {\"client_id\":\"101474222\",\"openid\":\"D11B246F053569687480C725F17FCDCA\"} );";
//        JSONObject object = JSONObject.parseObject(str);
//        System.out.println(object);
        String s = str.substring(str.indexOf("{"), str.indexOf("}") + 1);
        System.out.println(s);
    }

    @Test
    public void testJsonObject() {
        JSONObject o1 = new JSONObject();
        o1.put("o1", "o1");
        o1.put("o2", "o2");
        JSONObject o2 = new JSONObject();
        o2.put("o2", "o2");
        o2.put("o21", "o21");
        o1.putAll(o2);
        System.out.println(o1);
    }

    @Test
    public void test1() {
        UsUserCooperationInfoParam webSignInParam = new UsUserCooperationInfoParam();
        webSignInParam.setType("3");
        webSignInParam.setCode("260C96144197B610CBBDC78F29792D43");
        JSONObject object = webSignInUtil.getInfo(webSignInParam);
        System.out.println("web登录接口==============================" + object.toString());
    }

    @Test
    public void test2() {
        String str = "access_token=1D976DEAFFE44DBF9AE358BFADC1DBAB&expires_in=7776000&refresh_token=CDABAD5788911FC5F14A143457CBA019";
        JSONObject object = new JSONObject();
        for (String s : str.split("&")) {
            String[] temp = s.split("=");
            object.put(temp[0], temp[1]);
        }
        System.out.println(object);
    }

    @Test
    public void test3() {
        String str = "";
        System.out.println("测试==========================" + str.isEmpty());
    }

    @Test
    public void test4() {
        Integer refundableGoodsNum = (160 + 1) * 14 / 100;
        System.out.println("refundableGoodsNum = " + refundableGoodsNum);
    }
}
