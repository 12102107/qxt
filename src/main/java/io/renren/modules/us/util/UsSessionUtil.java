package io.renren.modules.us.util;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.utils.SpringContextUtils;
import io.renren.modules.us.entity.UsUserEntity;
import io.renren.modules.us.service.UsUserService;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.stereotype.Component;

/**
 * @author Li
 */
@Component
public class UsSessionUtil {

    private static Logger logger = LoggerFactory.getLogger(UsSessionUtil.class);

    private UsSessionUtil() {

    }

    public static String generateSession() {
        try {
            return UsCryptoUtil.encrypt(UsIdUtil.generateId());
        } catch (Exception e) {
            logger.error(e.getMessage(), e);
        }
        return null;
    }

    public static String getUserid(String session) {
        EntityWrapper<UsUserEntity> wrapper = new EntityWrapper<>();
        wrapper.setSqlSelect("id");
        wrapper.where("session = {0}", session);
        UsUserService userService = (UsUserService) SpringContextUtils.getBean("usUserService");
        Object obj = userService.selectObj(wrapper);
        return obj == null ? null : obj.toString();
    }
    
}