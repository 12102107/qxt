package io.renren;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.modules.us.entity.UsUserCooperationEntity;
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
import java.util.List;

@RunWith(SpringRunner.class)
@SpringBootTest
public class UsTest {

    @Autowired
    private UsIdUtil usIdUtil;
    @Autowired
    private UsUserCooperationService cooperationService;

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
        String code = UsSmsUtil.message("15904607121");
        System.out.println("短信接口==============================" + code);
    }

    @Test
    public void test7(){
        int str = (int)((Math.random() * 9 + 1) * 100000);
        System.out.println("短信接口==============================" + str);
    }
}
