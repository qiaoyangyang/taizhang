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
import java.util.regex.Matcher;
import java.util.regex.Pattern;

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
    public static void setAgreeTextColor(Context context, String content, TextView textView, int ling, int begin, int color) {
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
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
        }, ling, begin, Spanned.SPAN_EXCLUSIVE_EXCLUSIVE);

        textView.setHighlightColor(Color.TRANSPARENT); //设置点击后的颜色为透明，否则会一直出现高亮
        textView.setText(builder);
        textView.setMovementMethod(LinkMovementMethod.getInstance());
    }

    public static void setTiktokBindingTextcolor(Context context, String content, TextView textView, int ling, int begin, int color, int i, ontestonClick ontestonClick) {
        SpannableStringBuilder builder = new SpannableStringBuilder(content);
        builder.setSpan(new ClickableSpan() {
            @Override
            public void onClick(View widget) {
                ontestonClick.ononClick(i);

            }

            @Override
            public void updateDrawState(TextPaint ds) {
                super.updateDrawState(ds);
                ds.setFakeBoldText(true);
                ds.setTextSize(context.getResources().getDimension(R.dimen.sp_28));
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
                ds.setTextSize(context.getResources().getDimension(R.dimen.sp_28));
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

    public interface ontestonClick {
        public void ononClick(int type);
    }

    /**
     * 截取字符串，超出最大字数截断并显示"..."
     *
     * @param str    原始字符串
     * @param length 最大字数限制（以最大字数限制7个为例，当含中文时，length应设为2*7，不含中文时设为7）
     * @return 处理后的字符串
     */
    public static String subStrByLen(String str, int length) {
        if (str == null || str.length() == 0) {
            return "";
        }
        int chCnt = getStrLen(str);
        // 超出进行截断处理
        if (chCnt > length) {
            int cur = 0;
            int cnt = 0;
            StringBuilder sb = new StringBuilder();
            while (cnt <= length && cur < str.length()) {
                char nextChar = str.charAt(cur);
                if (isChCharacter(String.valueOf(nextChar))) {
                    cnt += 2;
                } else {
                    cnt++;
                }
                if (cnt <= length) {
                    sb.append(nextChar);
                } else {
                    return sb.toString() + "...";
                }
                cur++;
            }
            return sb.toString() + "...";
        }
        // 未超出直接返回
        return str;
    }

    /**
     * 获取字符长度，中文算作2个字符，其他都算1个字符
     */
    public static int getStrLen(String str) {
        if (str == null || str.length() == 0) {
            return 0;
        }
        return str.length() + getChCount(str);
    }

    private static String regEx = "[\u4e00-\u9fa5]"; // 中文范围

    /**
     * 判断字符是不是中文
     */
    private static boolean isChCharacter(String str) {
        if (str == null || str.length() == 0) {
            return false;
        }
        if (str.length() > 1) {
            return false;
        }
        return Pattern.matches(regEx, str);
    }

    /**
     * 获取字符串中的中文字数
     */
    private static int getChCount(String str) {
        int cnt = 0;
        Pattern pattern = Pattern.compile(regEx);
        Matcher matcher = pattern.matcher(str);;
        while(matcher.find()) {
            cnt++;
        }
        return cnt;
    }


}
