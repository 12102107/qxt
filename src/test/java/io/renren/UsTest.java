package io.renren;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import com.baomidou.mybatisplus.plugins.Page;
import io.renren.common.utils.Query;
import io.renren.modules.us.entity.TSCategoryEntity;
import io.renren.modules.us.entity.UsSmsEntity;
import io.renren.modules.us.entity.UsUserCooperationEntity;
import io.renren.modules.us.service.TSCategoryService;
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
        String session = UsSessionUtil.getUserid("12345");
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
}
