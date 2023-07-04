package com.meiling.account.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.account.R
import com.meiling.common.GlideApp

//良品明细
class DefectiveDetailAdapter :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_defective_detail) {
    override fun convert(holder: BaseViewHolder, item: String) {
        GlideApp.with(context)
            .load("https://t7.baidu.com/it/u=3208595851,3710378865&fm=193&f=GIF")
            .into(holder.getView(R.id.iv_commodity_url))
    }
}