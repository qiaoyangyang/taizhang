package com.meiling.common.utils;

import android.content.Context;
import android.os.Vibrator;
import android.util.Log;

import com.meiling.common.R;

public class VibrationUtils {
    private static final long[] DEFAULT_PATTERN = {0, 1000}; // 默认的震动模式

    private Vibrator vibrator;

    public VibrationUtils(Context context) {
        vibrator = (Vibrator) context.getSystemService(Context.VIBRATOR_SERVICE);
    }

    public void vibrate() {
       // vibrate(DEFAULT_PATTERN, -1);
        // 检查设备是否支持振动
        Log.d("", "vibrate: 检查设备是否支持振动---"+vibrator.hasVibrator());
        if (vibrator.hasVibrator()) {
            // 使用vibration.xml文件中定义的振动效果
          //  vibrator.vibrate(R.xml.common_vibration);
            // 执行振动，使用默认的振动模式
            long duration = 100;

            vibrator.vibrate(duration);
        }else {

        }
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