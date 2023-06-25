package com.meiling.account.adapter;

import android.view.View;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.meiling.account.R;
import com.meiling.account.bean.Goods;
import com.meiling.account.widget.CustomRoundAngleImageView;
import com.meiling.common.GlideApp;

import java.util.List;

//商品
public class GoodaAdapter extends BaseQuickAdapter<Goods, BaseViewHolder> {
    public GoodaAdapter(int layoutResId) {
        super(layoutResId);
    }


    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, Goods s) {

        if (s.isExpen()){
            baseViewHolder.setBackgroundResource(R.id.cl_bg,R.drawable.bg_goods_true);
        }else {
            baseViewHolder.setBackgroundResource(R.id.cl_bg,R.drawable.bg_goods_fase);
        }
        CustomRoundAngleImageView view = baseViewHolder.getView(R.id.iv_Img);
        GlideApp.with(getContext())
                .load("https://t7.baidu.com/it/u=3208595851,3710378865&fm=193&f=GIF")
                .into(view);
    }
}
