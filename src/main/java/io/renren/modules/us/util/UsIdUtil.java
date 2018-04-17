package io.renren.modules.us.util;

import java.util.UUID;

public class UsIdUtil {

    public static String generateId() {
        return UUID.randomUUID().toString().replaceAll("-", "").substring(0, 32);
    }
}
