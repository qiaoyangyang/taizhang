package com.meiling.common.utils;

public class PreventUtils {
    private static long lastClickTime = 0;
    private static final int MIN_CLICK_DELAY_TIME = 1000; // 设置最小点击间隔时间为1秒

    public static boolean isFastClick() {
        long currentTime = System.currentTimeMillis();
        if (currentTime - lastClickTime < MIN_CLICK_DELAY_TIME) {
            return true;
        }
        lastClickTime = currentTime;
        return false;
    }
}
