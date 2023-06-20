package com.meiling.account.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.meiling.account.R;
import com.meiling.account.widget.CustomRoundAngleImageView;
import com.meiling.common.GlideApp;

import java.util.List;

//商品
public class GoodaAdapter extends BaseQuickAdapter<String, BaseViewHolder> {
    public GoodaAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, String s) {
        CustomRoundAngleImageView view = baseViewHolder.getView(R.id.iv_Img);
        GlideApp.with(getContext())
                .load("https://t7.baidu.com/it/u=3208595851,3710378865&fm=193&f=GIF")
                .into(view);
    }
}
