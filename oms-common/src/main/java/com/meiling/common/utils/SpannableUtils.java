package com.meiling.common.utils;


import android.content.Context;
import android.content.Intent;
import android.graphics.Color;
import android.graphics.Paint;
import android.graphics.Typeface;
import android.net.Uri;
import android.text.SpannableStringBuilder;
import android.text.Spanned;
import android.text.TextPaint;
import android.text.method.LinkMovementMethod;
import android.text.style.ClickableSpan;
import android.util.Log;
import android.view.View;
import android.widget.TextView;
import android.widget.Toast;

import androidx.annotation.NonNull;

import com.alibaba.android.arouter.launcher.ARouter;
import com.hjq.permissions.OnPermissionCallback;
import com.hjq.permissions.Permission;
import com.hjq.permissions.XXPermissions;
import com.meiling.common.R;

import java.util.List;

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

    public static void setTiktokBindingTextcolor(Context context, String content, TextView textView, int ling, int begin, int color, int i,ontestonClick ontestonClick) {
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                ontestonClick.ononClick(i);

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
    public static void setTiktokBindingTextcolor1(Context context, String content, TextView textView, int ling, int begin, int color) {
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {

            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setTextSize(context.getResources().getDimension(R.dimen.sp_32));
                ds.setTypeface(Typeface.defaultFromStyle(Typeface.BOLD));
                ds.setColor(context.getResources().getColor(color));       //设置文字颜色
                ds.setUnderlineText(false);      //设置下划线//根据需要添加
            }
        }, ling, begin, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
        textView.setText(builder);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public static void dialPhoneNumber(String phoneNumber, Context context) {
        Intent intent = new Intent(Intent.ACTION_CALL);
        Uri data = Uri.parse("tel:" + phoneNumber);
        intent.setData(data);
        context.startActivity(intent);

    }

    public interface ontestonClick{
        public void ononClick(int type);
    }

}
