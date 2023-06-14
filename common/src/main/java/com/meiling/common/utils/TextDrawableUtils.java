package com.meiling.common.utils;

import android.content.Context;
import android.graphics.drawable.Drawable;
import android.widget.TextView;

import androidx.core.content.ContextCompat;

public class TextDrawableUtils {

    public static void setLeftDrawable(TextView view, Integer drawableRes) {
        if(drawableRes==null){
            view.setCompoundDrawables(null,null,null,null);
            return;
        }
        //Drawable drawable = view.getResources().getDrawable(drawableRes);
        Drawable drawable = ContextCompat.getDrawable(view.getContext(), drawableRes);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 设置边界
        // param 左上右下
        view.setCompoundDrawables(drawable,null,null,null);
        view.setCompoundDrawablePadding(px2dp(view.getContext(),5)) ;
    }
    public static void setTopDrawable(TextView view, Integer drawableRes) {
        if(drawableRes==null){
            view.setCompoundDrawables(null,null,null,null);
            return;
        }
       // Drawable drawable = view.getResources().getDrawable(drawableRes);
        Drawable drawable = ContextCompat.getDrawable(view.getContext(), drawableRes);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 设置边界
        // param 左上右下
        view.setCompoundDrawables(null,drawable,null,null);
        view.setCompoundDrawablePadding(px2dp(view.getContext(),5)) ;
    }

    public static void setRightDrawable(TextView view, Integer drawableRes) {
        if(drawableRes==null){
            view.setCompoundDrawables(null,null,null,null);
            return;
        }
        Drawable drawable = ContextCompat.getDrawable(view.getContext(), drawableRes);

        // Drawable drawable = view.getResources().getDrawable(drawableRes);
        drawable.setBounds(0, 0, drawable.getMinimumWidth(), drawable.getMinimumHeight());// 设置边界
        // param 左上右下
        view.setCompoundDrawables(null,null,drawable,null);
        view.setCompoundDrawablePadding(px2dp(view.getContext(),1)) ;
    }

    public static int px2dp(Context context, float pxValue) {
        final float scale = context.getResources().getDisplayMetrics().density;
        return (int) (pxValue * scale + 0.5f);

    }
}

