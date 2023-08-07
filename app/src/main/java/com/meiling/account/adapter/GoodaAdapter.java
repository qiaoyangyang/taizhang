package com.meiling.account.adapter;

import android.text.TextUtils;
import android.util.Log;
import android.view.View;
import android.widget.ImageView;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.google.gson.Gson;
import com.google.gson.reflect.TypeToken;
import com.meiling.account.R;
import com.meiling.account.bean.Goods;
import com.meiling.account.widget.CustomRoundAngleImageView;
import com.meiling.account.widget.InputUtil;
import com.meiling.common.GlideApp;
import com.meiling.common.flowlayout.FlowLayout;
import com.meiling.common.utils.GlideAppUtils;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;

import kotlin.reflect.KVariance;

//商品
public class GoodaAdapter extends BaseQuickAdapter<Goods, BaseViewHolder> {
    public GoodaAdapter(int layoutResId) {
        super(layoutResId);
    }

    private MainFlowLayoutAdapter mainFlowLayoutAdapter;

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, Goods s) {
        mainFlowLayoutAdapter = new MainFlowLayoutAdapter(InputUtil.getSpezifikation(s.getGoodsSpecsValus()));
        FlowLayout FlowLayout = baseViewHolder.getView(R.id.flash_light_layout);
        FlowLayout.setAdapter(mainFlowLayoutAdapter);
        if (s.isExpen()) {
            mainFlowLayoutAdapter.setiscompile(true);
            baseViewHolder.setBackgroundResource(R.id.cl_bg, R.drawable.bg_goods_true);
        } else {
            mainFlowLayoutAdapter.setiscompile(false);
            baseViewHolder.setBackgroundResource(R.id.cl_bg, R.drawable.bg_goods_fase);
        }

        Log.d("规格---", new Gson().toJson(InputUtil.getSpezifikation(s.getGoodsSpecsValus())));


        baseViewHolder.setText(R.id.tv_goodsUnit, s.getGoodsUnit());
        baseViewHolder.setText(R.id.tv_name, s.getGoodsName());
        baseViewHolder.setText(R.id.tv_goodsSpecsValus, s.getGoodsSpecsValus());
        ImageView view = baseViewHolder.getView(R.id.iv_Img);
        GlideApp.with(getContext())
                .load(s.getGoodsImgurl())
                .into(view);
//
    }
}
