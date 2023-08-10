package com.meiling.account.adapter;

import android.graphics.Color;
import android.view.View;
import android.widget.TextView;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.meiling.account.R;
import com.meiling.account.bean.SetBaen;

public class SetAdapter extends BaseQuickAdapter<SetBaen, BaseViewHolder> {
    public SetAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder holder, SetBaen item) {
        holder.setText(R.id.txtRecyCategoryItem, item.getName());
        //View view = holder.getView(R.id.iv_close);
        if (item.getIsselect()) {
            holder.setBackgroundResource(R.id.iv_close,R.drawable.red_class);
            holder.setTextColor(R.id.txtRecyCategoryItem, Color.parseColor("#FE4B48"));
        } else {
            holder.setBackgroundResource(R.id.iv_close,R.drawable.red_class_no);
            holder.setTextColor(R.id.txtRecyCategoryItem, Color.parseColor("#2E2E2E"));
        }


    }
}
