package com.meiling.common.utils;

import android.content.Context;
import android.os.Vibrator;

public class VibrationUtils {
    private static final long[] DEFAULT_PATTERN = {0, 1000}; // 默认的震动模式

    private Vibrator vibrator;

    public VibrationUtils(Context context) {
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void vibrate() {
        vibrate(DEFAULT_PATTERN, -1);
    }

    public void vibrate(long[] pattern, int repeat) {
        if (vibrator != null && vibrator.hasVibrator()) {
            vibrator.vibrate(pattern, repeat);
        }
    }

    public void cancel() {
        if (vibrator != null && vibrator.hasVibrator()) {
            vibrator.cancel();
        }
    }
}