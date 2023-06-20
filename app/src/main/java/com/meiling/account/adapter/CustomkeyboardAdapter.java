package com.meiling.account.adapter;


import com.chad.library.adapter.base.BaseQuickAdapter;
import com.chad.library.adapter.base.viewholder.BaseViewHolder;
import com.meiling.account.R;

import org.jetbrains.annotations.NotNull;

//键盘
public class CustomkeyboardAdapter extends BaseQuickAdapter<String, BaseViewHolder> {


    public CustomkeyboardAdapter(int layoutResId) {
        super(layoutResId);
    }

    @Override
    protected void convert(@NotNull BaseViewHolder baseViewHolder, String s) {
        baseViewHolder.setText(R.id.tv_dialog_pay_key,s);
        if (s.equals("20")){
            baseViewHolder.setText(R.id.tv_dialog_pay_key,"");
            baseViewHolder.setGone(R.id.iv_clear,false);
        }else {
            baseViewHolder.setGone(R.id.iv_clear,true);
        }
    }
}
