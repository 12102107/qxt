package io.renren.modules.us.util;

import com.baomidou.mybatisplus.mapper.EntityWrapper;
import io.renren.common.utils.RedisUtils;
import io.renren.common.utils.SpringContextUtils;
import io.renren.modules.us.entity.UsUserEntity;
import io.renren.modules.us.service.UsUserService;
import org.apache.shiro.session.SessionException;
import org.slf4j.Logger;
import org.slf4j.LoggerFactory;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.stereotype.Component;

import java.util.Set;

/**
 * @author Li
 */
@Component
public class UsSessionUtil {

    private static final String SESSION_KEY_PREFIX = "hm:session:";
    private static Logger logger = LoggerFactory.getLogger(UsSessionUtil.class);
    private RedisUtils redisUtil;

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

    public String getUserId(String session) {
        if (redisUtil.hasKey(SESSION_KEY_PREFIX + session)) {
            return redisUtil.get(SESSION_KEY_PREFIX + session);
        } else {
            EntityWrapper<UsUserEntity> wrapper = new EntityWrapper<>();
            wrapper.setSqlSelect("id");
            wrapper.where("session = {0}", session);
            UsUserService userService = (UsUserService) SpringContextUtils.getBean("usUserService");
            Object obj = userService.selectObj(wrapper);
            if (obj != null) {
                this.saveSession(obj.toString(), session);
                return obj.toString();
            } else {
                return null;
            }
        }
    }

    public void saveSession(String userId, String session) {
        if (userId != null && session != null && !userId.isEmpty() && !session.isEmpty()) {
            redisUtil.set(SESSION_KEY_PREFIX + session, userId, -1);
        } else {
            throw new SessionException("用户ID或Session为空");
        }
    }

    public void updateSession(String userId, String session) {
        if (userId == null || session == null || userId.isEmpty() || session.isEmpty()) {
            throw new SessionException("用户ID或Session为空");
        }
        //当Session在缓存中存在时,值应该与userId相等,如果值相等不需要更新,如果值不相等抛出异常
        if (redisUtil.hasKey(SESSION_KEY_PREFIX + session)) {
            String value = redisUtil.get(SESSION_KEY_PREFIX + session);
            if (!value.equals(userId)) {
                throw new SessionException("Session值与用户ID不匹配");
            }
        }
        //当Session在缓存中不存在时,清理缓存中与userId相等的key,保存新的key
        if (!redisUtil.hasKey(SESSION_KEY_PREFIX + session)) {
            Set<String> keys = redisUtil.getKeys(SESSION_KEY_PREFIX + "*");
            for (String key : keys) {
                if (redisUtil.get(key).equals(userId)) {
                    redisUtil.delete(key);
                }
            }
            redisUtil.set(SESSION_KEY_PREFIX + session, userId, -1);
        }
    }

    @Autowired
    public void setRedisUtil(RedisUtils redisUtil) {
        this.redisUtil = redisUtil;
    }

}