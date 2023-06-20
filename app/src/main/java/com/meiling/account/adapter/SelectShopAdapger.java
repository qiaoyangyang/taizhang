package com.meiling.account.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.meiling.account.R;

//选择店铺
public class SelectShopAdapger extends BaseQuickAdapter<String, BaseViewHolder> {
    public SelectShopAdapger() {
        super(R.layout.item_recy_select_shop);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, String s) {

    }
}
