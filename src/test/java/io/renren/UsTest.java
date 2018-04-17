package io.renren;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.modules.us.entity.UsUserCooperationEntity;
import io.renren.modules.us.service.UsUserCooperationService;
import io.renren.modules.us.util.UsIdUtil;
import io.renren.modules.us.util.UsSessionUtil;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

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
        System.out.println("==========="+session);
    }
}
