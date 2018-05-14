package io.renren.modules.us.util;

import java.util.Random;

public class UsRandomUtil {

    private UsRandomUtil() {
    }

    public static int getRandom(int min, int max) {
        Random random = new Random();
        return random.nextInt(max - min + 1) + min;
    }

}
