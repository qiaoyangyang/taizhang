package com.meiling.common.utils;


import android.content.Context;
import android.graphics.Color;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;

import com.alibaba.android.arouter.launcher.ARouter;

public class SpannableUtils {
    /**
     * @param content  文字内容
     * @param textView 加载文字的textview
     */

    public static void setText(Context context, String content, TextView textView, int color) {
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        int i = content.indexOf("《");//截取文字开始的下标
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                ARouter.getInstance().build("/app/AgreementActivity").withString("YSXY", "0").navigation();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(context.getResources().getColor(color));       //设置文字颜色
                ds.setUnderlineText(false);      //设置下划线//根据需要添加
            }
        }, i, i + 6, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                ARouter.getInstance().build("/app/AgreementActivity").withString("YSXY", "1").navigation();
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(context.getResources().getColor(color));       //设置文字颜色
                ds.setUnderlineText(false);      //设置下划线//根据需要添加
            }
        }, 18, 24, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
        textView.setText(builder);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public static void setTextcolor(Context context, String content, TextView textView, int ling, int begin, int color) {
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Log.d("yjk", "iiii");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(context.getResources().getColor(color));       //设置文字颜色
                ds.setUnderlineText(false);      //设置下划线//根据需要添加
            }
        }, ling, begin, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
        textView.setText(builder);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }
    public static void setTiktokBindingTextcolor(Context context, String content, TextView textView, int ling, int begin, int color,int i) {
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                Log.d("yjk", "iiii");
            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setColor(context.getResources().getColor(color));       //设置文字颜色
                ds.setUnderlineText(false);      //设置下划线//根据需要添加
            }
        }, ling, begin, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
        textView.setText(builder);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

}
