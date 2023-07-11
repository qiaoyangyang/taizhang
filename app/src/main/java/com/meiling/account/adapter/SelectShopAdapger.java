package com.meiling.account.adapter;

import androidx.annotation.NonNull;

import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.meiling.account.R;
import com.meiling.common.network.data.UserStoreList;

//选择店铺
public class SelectShopAdapger extends BaseQuickAdapter<UserStoreList, BaseViewHolder> {
    public SelectShopAdapger() {
        super(R.layout.item_recy_select_shop);
    }

    @Override
    protected void convert(@NonNull BaseViewHolder baseViewHolder, UserStoreList s) {
        baseViewHolder.setText(R.id.txtRecySelectShopItem,s.getStoreName());

    }
}
