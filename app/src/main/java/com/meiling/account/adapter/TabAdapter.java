package com.meiling.account.adapter;

import android.graphics.Color;
import android.graphics.Typeface;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.meiling.account.R;
import com.meiling.account.bean.GoosClassify;
//分类
public class TabAdapter extends BaseQuickAdapter<GoosClassify, BaseViewHolder> {
    public TabAdapter(int i) {
        super(i);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, GoosClassify item) {
        holder.setText(R.id.txtRecyCategoryItem, item.getSortName());
        TextView textView = holder.getView(R.id.txtRecyCategoryItem);
        if (item.getSelect()) {
            holder.setTextColor(R.id.txtRecyCategoryItem, Color.parseColor("#FE4B48"));
            textView.setTextSize(18f);
            textView.setTypeface(textView.getTypeface(), Typeface.BOLD);


            holder.setGone(R.id.recyCategoryItemline, false);

        } else {
            textView.setTypeface(textView.getTypeface(), Typeface.NORMAL);
            textView.setTextSize(16f);
            holder.setGone(R.id.recyCategoryItemline, true);
            holder.setTextColor(R.id.txtRecyCategoryItem, Color.parseColor("#7A7A7A"));
        }
    }
}
