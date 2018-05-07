package io.renren;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.renren.common.utils.Query;
import io.renren.modules.us.entity.TSCategoryEntity;
import io.renren.modules.us.entity.UsAppApiEntity;
import io.renren.modules.us.entity.UsSmsEntity;
import io.renren.modules.us.entity.UsUserCooperationEntity;
import io.renren.modules.us.service.TSCategoryService;
import io.renren.modules.us.service.UsAppApiService;
import io.renren.modules.us.service.UsSmsService;
import io.renren.modules.us.service.UsUserCooperationService;
import io.renren.modules.us.util.UsIdUtil;
import io.renren.modules.us.util.UsOkHttpUtil;
import io.renren.modules.us.util.UsSessionUtil;
import io.renren.modules.us.util.UsSmsUtil;
import okhttp3.Call;
import okhttp3.Response;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

import java.io.IOException;
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
        String session = UsSessionUtil.getUserid("3210a0abe5c73c042fb5d520e46a07b3e2aa7de41eae9695b8aeea732954cddb5743d46aaf3dce1c15413c19e880d2ee");
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

}
