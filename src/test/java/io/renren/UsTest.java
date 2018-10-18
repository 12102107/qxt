package io.renren;

import com.alibaba.fastjson.JSONObject;
import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.renren.common.utils.Constant;
import io.renren.common.utils.Query;
import io.renren.modules.us.entity.*;
import io.renren.modules.us.service.*;
import io.renren.modules.us.util.*;
import okhttp3.*;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.util.ArrayList;
import java.util.HashMap;
import java.util.List;
import java.util.Map;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsTest {

    @Autowired
    private UsIdUtil usIdUtil;
    @Autowired
    private UsUserCooperationService cooperationService;
    @Autowired
    private UsSmsService smsService;
    @Autowired
    private TSCategoryService categoryService;
    @Autowired
    private UsAppApiService appApiService;
    @Autowired
    private UsSessionUtil sessionUtil;
    @Autowired
    private UsUserService userService;
    @Autowired
    private UsApiService apiService;
    @Autowired
    private UsUserEidService eidService;
    @Autowired
    private UsCardNumberUtil cardNumberUtil;
    @Autowired
    private UsQrCodeUtil qrCodeUtil;

    @Test
    public void test1() {
        System.out.println(UsIdUtil.generateId());
        System.out.println(UsIdUtil.generateId());
        System.out.println(UsIdUtil.generateId());
        System.out.println(UsIdUtil.generateId().length());
    }

    @Test
    public void test2() {
        EntityWrapper<UsUserCooperationEntity> wrapper = new EntityWrapper<>();
        //wrapper.setSqlSelect("select * from us_user_cooperation");
        wrapper.where("appid={0}", "1")
                .and("type={0}", "1")
                .and("openid={0}", "1");
        wrapper.setEntity(new UsUserCooperationEntity());
        String str = wrapper.getSqlSegment();
        System.out.println(str);
        str = wrapper.getSqlSelect();
        System.out.println(str);
        UsUserCooperationEntity entity = wrapper.getEntity();
        System.out.println(entity == null);
        List<UsUserCooperationEntity> list = cooperationService.selectList(wrapper);
        for (UsUserCooperationEntity e : list) {
            System.out.println(e.toString());
        }
    }

    @Test
    public void test8() {
        EntityWrapper<UsSmsEntity> wrapper = new EntityWrapper<>();
        wrapper.where("appid={0}", "12345")
                .and("mobile={0}", "12345")
                .orderBy("create_date", false)
                .last("limit 1");
        wrapper.setEntity(new UsSmsEntity());
        UsSmsEntity smsEntity = smsService.selectOne(wrapper);
        System.out.println("测试代码===========" + (smsEntity == null));
//        Date expireDate = smsEntity.getCreateDate();
//        Date now = new Date();
//        System.out.println("日期比较=====" + now.before(expireDate));
    }

    @Test
    public void test3() {
        String session = sessionUtil.getUserId("3210a0abe5c73c042fb5d520e46a07b3e2aa7de41eae9695b8aeea732954cddb5743d46aaf3dce1c15413c19e880d2ee");
        System.out.println("===========" + session);
    }

    @Test
    public void test4() {
        String url = "http://utf8.api.smschinese.cn/?Uid=gophagroup&Key=2b96c2d083a4bffa6e6f&smsMob=15904607121&smsText=验证码:751231";
        UsOkHttpUtil okHttpUtil = UsOkHttpUtil.getInstance();
        okHttpUtil.getDataAsync(url, new UsOkHttpUtil.MyNetCall() {
            @Override
            public void success(Call call, Response response) {
                String reulst = null;
                try {
                    reulst = response.body().string();
                } catch (IOException e) {
                    System.out.println("短信接口==============================" + e.getMessage());
                }
                System.out.println("短信接口==============================" + reulst);
            }

            @Override
            public void failed(Call call, IOException e) {
                System.out.println("短信接口==============================" + e.getCause());
            }
        });
    }

    @Test
    public void test5() throws IOException {
        String url = "http://utf8.api.smschinese.cn/?Uid=gophagroup&Key=2b96c2d083a4bffa6e6f&smsMob=13613654070&smsText=验证码:543256";
        UsOkHttpUtil okHttpUtil = UsOkHttpUtil.getInstance();
        Response response = okHttpUtil.getDataSync(url);
        try {
            String reulst = response.body().string();
            System.out.println("短信接口==============================" + reulst.equals("1"));
        } catch (IOException e) {
            System.out.println("短信接口==============================" + e.getMessage());
        }
    }

    @Test
    public void test6() {
        String code = UsSmsUtil.getCode("15904607121");
        System.out.println("短信接口==============================" + code);
    }

    @Test
    public void test7() {
        int str = (int) ((Math.random() * 9 + 1) * 100000);
        System.out.println("短信接口==============================" + str);
    }

    @Test
    public void test9() {
        int code = smsService.checkCode("12345", "1234", "666");
        System.out.println("短信接口==============================" + code);
        code = smsService.checkCode("12345", "15904607121", "666");
        System.out.println("短信接口==============================" + code);
        code = smsService.checkCode("12345", "15904607121", "138584");
        System.out.println("短信接口==============================" + code);
    }

    @Test
    public void test10() {
        EntityWrapper<TSCategoryEntity> wrapper = new EntityWrapper<>();
        wrapper.where("code REGEXP {0}", "^[A]{1}[0-9]{1,}$");
        //wrapper.setEntity(new TSCategoryEntity());
        wrapper.setSqlSelect("id", "code", "name");
        List<Map<String, Object>> list = categoryService.selectMaps(wrapper);
        for (Map<String, Object> map : list) {
            for (String s : map.keySet()) {
                System.out.println(map.get(s));
            }
        }
    }

    @Test
    public void test11() {
        Map<String, Object> map = new HashMap<>(2);
        map.put("limit", "10");
        map.put("page", "1");
        EntityWrapper<TSCategoryEntity> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("id", "icon_id", "code", "name", "parent_code");
        wrapper.where("parent_code like {0}", "A01%");
        Page<Map<String, Object>> page = categoryService.selectMapsPage(new Query<TSCategoryEntity>(map).getPage(), wrapper);
        List<Map<String, Object>> list = new ArrayList<>();
        for (Map<String, Object> m1 : page.getRecords()) {
            //如果是二级分类
            if (m1.get("parent_code").toString().matches("^[A]{1}[0-9]{1,}$")) {
                List<Map<String, Object>> tempList = new ArrayList<>();
                String code = m1.get("code").toString();
                for (Map<String, Object> m2 : page.getRecords()) {
                    if (m2.get("parent_code").toString().equals(code)) {
                        tempList.add(m2);
                    }
                }
                m1.put("list", tempList);
                list.add(m1);
            }
        }
        page.setRecords(list);
        System.out.println("1");
    }

    @Test
    public void test12() {
        EntityWrapper<TSCategoryEntity> wrapper = new EntityWrapper<>();
        wrapper.where("find_in_set({0},code)", "A01A01");
        //wrapper.setEntity(new TSCategoryEntity());
        wrapper.setSqlSelect("id", "code", "name");
        List<Map<String, Object>> list = categoryService.selectMaps(wrapper);
        for (Map<String, Object> map : list) {
            for (String s : map.keySet()) {
                System.out.println(map.get(s));
            }
        }
    }

    @Test
    public void test13() throws IOException {
        //公积金个人基本信息
        String url = "http://10.66.68.15:8087/gateway/api/open/zggjjgrjbxx_hmrhggfwxm/1.0" +
                "?api_key=32303137303831363134353832353130373730302361306539646562622d376238342d343132622d623830652d6539a1f70ba3a3fd01c86ccb" +
                "&ZGXM=杨晓圆" +
                "&SFZH=152104198803022847";
        UsOkHttpUtil okHttpUtil = UsOkHttpUtil.getInstance();
        Response response = okHttpUtil.getDataSync(url);
        try {
            String reulst = response.body().string();
            System.out.println("接口==============================" + reulst);
        } catch (IOException e) {
            System.out.println("接口==============================" + e.getMessage());
        }
    }

    @Test
    public void test14() {
        EntityWrapper<UsAppApiEntity> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("select count(appapi.id) from us_app_api as appapi,us_app as app,us_api as api");
//        wrapper.addFilter("select count(appapi.id) from us_app_api appapi \n" +
//                "join us_app app on appapi.appid = app.id\n" +
//                "join us_api api on appapi.apiid = api.id\n" +
//                "where app.appkey = {0} and api.url = {1}", "12345", "/api/category/list");
        wrapper.addFilter("join us_app as app on appapi.appid = app.id " +
                "join us_api as api on appapi.apiid = api.id");
        wrapper.where("app.appkey = {0}", "12345")
                .and("api.url = {0}", "/api/category/list")
                .and("appapi.appid = app.id")
                .and("appapi.apiid = api.id");
        int i = appApiService.selectCount(wrapper);
        System.out.println("测试测试测试测试测试测试测试测试测试=====" + i);
    }

    @Test
    public void test15() {
        Map<String, String> map = new HashMap<>();
        map.put("appid", "123456");
        map.put("url", "/api/category/list");
        int i = appApiService.countId("123456", "/api/category/list");
        System.out.println("测试测试测试测试测试测试测试测试测试=====" + i);
    }

    @Test
    public void test16() {
        boolean b = eidService.updateEidLevel("66b4e22ce0ec4bf59969b3d71494f026", Constant.EidLevel.EID_LEVEL_1.getValue());
        System.out.println(b);
    }

    @Test
    public void test17() {
        UsApiEntity api = apiService.getWithoutAppId("/api/meth/callback");
        System.out.println("测试测试测试测试测试测试测试测试测试=====" + api.toString());
    }

    @Test
    public void test18() throws InterruptedException {
        String str = "";
        for (int i = 0; i < 18; i++) {
            Thread.sleep(5);
            str = str + cardNumberUtil.generateTrafficCardNumber() + ",";
        }
        System.out.println(str);
    }

    @Test
    public void test19() throws Exception {
        JSONObject object = new JSONObject();
        object.put("time", "1233455");
        object.put("session", "sdasdsadasdasdasdasdasd");
        String imgPath = "C:\\Users\\X\\Desktop\\1.png";
        String destPath = "C:\\Users\\X\\Desktop";
        qrCodeUtil.encode(object.toJSONString(), imgPath, destPath, true, "111");
    }

    @Test
    public void test20() {
        double d1 = 6;
        double d2 = 6.00;
        double d3 = 6.0000;
        double d4 = 6.10;
        double d5 = 6.01;
        DecimalFormat df = new DecimalFormat("#.00");
        System.out.println("测试======");
        System.out.println(Double.valueOf(df.format(d1)));
        System.out.println(Double.valueOf(df.format(d2)));
        System.out.println(Double.valueOf(df.format(d3)));
        System.out.println(Double.valueOf(df.format(d4)));
        System.out.println(Double.valueOf(df.format(d5)));
    }

    @Test
    public void test21() {
        BigDecimal bg = new BigDecimal(6);
        System.out.println(bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        bg = new BigDecimal(6.00);
        System.out.println(bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        bg = new BigDecimal(6.0000);
        System.out.println(bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        bg = new BigDecimal(6.10);
        System.out.println(bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        bg = new BigDecimal(6.01);
        System.out.println(bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
        bg = new BigDecimal(6.99);
        System.out.println(bg.setScale(2, BigDecimal.ROUND_HALF_UP).doubleValue());
    }

    @Test
    public void test22() {
        Double d1 = 0.03;
        Double d2 = 0.01;
        System.out.println("测试余额:" + (d1 - d2));
        BigDecimal bd1 = new BigDecimal("0.03");
        BigDecimal bd2 = new BigDecimal("0.01");
        System.out.println("测试余额:" + bd1.subtract(bd2).doubleValue());
        double d3 = 1.03;
        double d4 = 0.01;
        System.out.println("测试余额:" + (d3 * 100 - d4 * 100) / 100);
    }

    @Test
    public void test23() throws IOException {
        OkHttpClient okHttpClient = UsOkHttpUtil.getInstance().getOkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("username", "15904607121@139.com")
                .add("password", "15904607121@139.com").build();
        Request request = new Request.Builder().post(body).url("http://42.159.5.20/api2/auth-token/").build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        String str = response.body().string();
        int status = response.code();
        System.out.println(status + "测试测试" + str);
        JSONObject object = JSONObject.parseObject(str);
        System.out.println(status + "测试测试" + object.getString("token"));
    }

    @Test
    public void test24() throws IOException {
        OkHttpClient okHttpClient = UsOkHttpUtil.getInstance().getOkHttpClient();
        RequestBody body = new FormBody.Builder().build();
        //必须使用管理账号token
        Request request = new Request.Builder().post(body).url("http://42.159.5.20/api2/accounts/" + "15904607121@139.com")
                .header("Authorization", "Token " + "aa619273ed6ab6eb4b6955d32e59231de785a138")
                .header("Accept", "application/json; indent=4")
                .build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        String str = response.body().string();
        int status = response.code();
        System.out.println(status + "测试测试" + str);
    }

    @Test
    public void test25() throws IOException {
        OkHttpClient okHttpClient = UsOkHttpUtil.getInstance().getOkHttpClient();
        RequestBody body = new FormBody.Builder()
                .add("password", "15904607122@139.com")
                .build();
        //必须使用管理账号token
        Request request = new Request.Builder().put(body).url("http://42.159.5.20/api2/accounts/" + "15904607122@139.com" + "/")
                .header("Authorization", "Token " + "aa619273ed6ab6eb4b6955d32e59231de785a138")
                .header("Accept", "application/json; indent=4")
                .build();
        Call call = okHttpClient.newCall(request);
        Response response = call.execute();
        String str = response.body().string();
        int status = response.code();
        //第一次创建返回201 第二次创建返回200
        System.out.println(status + "测试测试" + str);
    }

}
