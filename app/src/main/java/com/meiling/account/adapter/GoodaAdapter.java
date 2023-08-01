package com.meiling.account.adapter;

import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.meiling.account.R;
import com.meiling.account.bean.Goods;
import com.meiling.account.widget.CustomRoundAngleImageView;
import com.meiling.common.GlideApp;
import com.meiling.common.utils.GlideAppUtils;

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
        baseViewHolder.setText(R.id.tv_goodsUnit,s.getGoodsUnit());
        baseViewHolder.setText(R.id.tv_name,s.getGoodsName());
        baseViewHolder.setText(R.id.tv_goodsSpecsValus,s.getGoodsSpecsValus());
        ImageView view = baseViewHolder.getView(R.id.iv_Img);
        GlideApp.with(getContext())
                .load(s.getGoodsImgurl())
                .into(view);
//
    }
}
