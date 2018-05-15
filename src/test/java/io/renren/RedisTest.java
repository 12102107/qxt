package io.renren;

import io.renren.common.utils.RedisUtils;
import io.renren.modules.sys.entity.SysUserEntity;
import io.renren.modules.us.entity.UsUserEntity;
import io.renren.modules.us.util.UsSessionUtil;
import org.apache.commons.lang.builder.ToStringBuilder;
import org.junit.Test;
import org.junit.runner.RunWith;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.boot.test.context.SpringBootTest;
import org.springframework.test.context.junit4.SpringRunner;

@RunWith(SpringRunner.class)
@SpringBootTest
public class RedisTest {
    @Autowired
    private RedisUtils redisUtils;
    @Autowired
    private UsSessionUtil sessionUtil;

    @Test
    public void contextLoads() {
        SysUserEntity user = new SysUserEntity();
        user.setEmail("qqq@qq.com");
        redisUtils.set("user", user);
        System.out.println(ToStringBuilder.reflectionToString(redisUtils.get("user", SysUserEntity.class)));
    }

    @Test
    public void test2() {
        redisUtils.set("hm:session:xxx0001", "0001");
        redisUtils.set("hm:session:xxx0002", "0002");
    }

    @Test
    public void test3() {
        System.out.println("redis===================" + redisUtils.get("hm:session:xxx0001"));
    }

    @Test
    public void test4() {
        for (String key : redisUtils.getKeys("hm:session:*")) {
            System.out.println("redis===================" + key);
        }
    }

    @Test
    public void test5() {
        UsUserEntity user = new UsUserEntity();
        user.setId("id12345");
        user.setSession("session12345");
        sessionUtil.saveSession("id12345", "session12345");
    }

    @Test
    public void test6() {
        sessionUtil.updateSession("id12345", "session123456");
    }
    
}
