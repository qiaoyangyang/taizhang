package com.meiling.common.utils;

import android.content.ClipboardManager;
import android.content.Context;
import android.widget.Toast;

public class CopyAndPaste {
    /**
     * 复制
     *
     * @param content
     * @param context
     */
    public static void copy(String content, Context context) {
        ClipboardManager cbm = (ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        cbm.setText(content.trim());
        Toast.makeText(context, "复制成功", 0).show();

    }

    /**
     * 粘贴
     *
     * @param context
     * @return
     */
    public static String Paste(Context context) {
        ClipboardManager cbm = (ClipboardManager) context
                .getSystemService(Context.CLIPBOARD_SERVICE);
        return cbm.getText().toString().trim();
    }

}
