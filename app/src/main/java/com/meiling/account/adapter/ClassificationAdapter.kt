package com.meiling.account.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.account.R
import com.meiling.account.bean.GoosClassify

class ClassificationAdapter :BaseQuickAdapter<GoosClassify, BaseViewHolder>(R.layout.item_tab_p) {
    override fun convert(holder: BaseViewHolder, item: GoosClassify) {
        holder.setText(R.id.txtRecyCategoryItem,item.name)

    }
}