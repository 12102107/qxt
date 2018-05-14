package io.renren.modules.us.util;

import org.springframework.stereotype.Component;

import java.util.UUID;

/**
 * @author Li
 */
@Component
public class UsIdUtil {

    private UsIdUtil() {

    }

    public static String generateId() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
    }
    
}
