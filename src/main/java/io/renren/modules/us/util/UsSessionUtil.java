package io.renren.modules.us.util;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.utils.SpringContextUtils;
import io.renren.modules.us.entity.UsUserEntity;
import io.renren.modules.us.service.UsUserService;
import org.springframework.stereotype.Component;

import java.util.List;

/**
 * @author Li
 */
@Component
public class UsSessionUtil {

    public static String generateSession() {
        try {
            return UsCryptoUtil.encrypt(UsIdUtil.generateId());
        } catch (Exception e) {
            e.printStackTrace();
        }
        return "";
    }

    public static String getUserid(String session) {
        EntityWrapper<UsUserEntity> wrapper = new EntityWrapper<>();
        wrapper.setEntity(new UsUserEntity());
        wrapper.where("session = {0}", session);
        UsUserService userService = (UsUserService) SpringContextUtils.getBean("usUserService");
        List<UsUserEntity> list = userService.selectList(wrapper);
        if (list.isEmpty()) {
            return "";
        } else if (list.size() == 1) {
            return list.get(0).getId();
        } else {
            return "";
        }
    }

}
