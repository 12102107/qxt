package io.renren.common.utils;

import com.google.gson.Gson;
import org.springframework.beans.factory.annotation.Autowired;
import org.springframework.data.redis.core.RedisTemplate;
import org.springframework.data.redis.core.ValueOperations;
import org.springframework.stereotype.Component;

import java.util.Set;
import java.util.concurrent.TimeUnit;

/**
 * Redis工具类
 *
 * @author chenshun
 * @email sunlightcs@gmail.com
 * @date 2017-07-17 21:12
 */
@Component
public class RedisUtils {

    //不设置过期时长
    private static final long NOT_EXPIRE = -1;
    //默认过期时长,单位:秒 60*60*24
    private static final long DEFAULT_EXPIRE = 86400;
    //过期时长,单位:秒 3600
    private static final long DEFAULT_EXPIRE_TIME = 3600;

    private static final Gson gson = new Gson();

    private RedisTemplate<String, Object> redisTemplate;

    private ValueOperations<String, String> valueOperations;

//    private HashOperations<String, String, Object> hashOperations;
//
//    private ListOperations<String, Object> listOperations;
//
//    private SetOperations<String, Object> setOperations;
//
//    private ZSetOperations<String, Object> zSetOperations;

    /**
     * Object转成JSON数据
     */
    private String toJson(Object object) {
        if (object instanceof Integer || object instanceof Long || object instanceof Float ||
                object instanceof Double || object instanceof Boolean || object instanceof String) {
            return String.valueOf(object);
        }
        return gson.toJson(object);
    }

    /**
     * JSON数据，转成Object
     */
    private <T> T fromJson(String json, Class<T> clazz) {
        return gson.fromJson(json, clazz);
    }

    public void set(String key, Object value, long expire) {
        valueOperations.set(key, toJson(value));
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
    }

    public void set(String key, Object value) {
        set(key, value, DEFAULT_EXPIRE);
    }
    public void setTimes(String key, Object value) {
        set(key, value, DEFAULT_EXPIRE_TIME);
    }

    public boolean hasKey(String key) {
        return redisTemplate.hasKey(key);
    }

    public Set<String> getKeys(String pattern) {
        return redisTemplate.keys(pattern);
    }

    public <T> T get(String key, Class<T> clazz, long expire) {
        String value = valueOperations.get(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value == null ? null : fromJson(value, clazz);
    }

    public <T> T get(String key, Class<T> clazz) {
        return get(key, clazz, NOT_EXPIRE);
    }

    public String get(String key, long expire) {
        String value = valueOperations.get(key);
        if (expire != NOT_EXPIRE) {
            redisTemplate.expire(key, expire, TimeUnit.SECONDS);
        }
        return value;
    }

    public String get(String key) {
        return get(key, NOT_EXPIRE);
    }

    public void delete(String key) {
        redisTemplate.delete(key);
    }

    @Autowired
    public void setRedisTemplate(RedisTemplate<String, Object> redisTemplate) {
        this.redisTemplate = redisTemplate;
    }

    @Autowired
    public void setValueOperations(ValueOperations<String, String> valueOperations) {
        this.valueOperations = valueOperations;
    }

//    @Autowired
//    public void setHashOperations(HashOperations<String, String, Object> hashOperations) {
//        this.hashOperations = hashOperations;
//    }
//
//    @Autowired
//    public void setListOperations(ListOperations<String, Object> listOperations) {
//        this.listOperations = listOperations;
//    }
//
//    @Autowired
//    public void setSetOperations(SetOperations<String, Object> setOperations) {
//        this.setOperations = setOperations;
//    }
//
//    @Autowired
//    public void setzSetOperations(ZSetOperations<String, Object> zSetOperations) {
//        this.zSetOperations = zSetOperations;
//    }

}
