package com.meiling.account.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.account.R

//良品明细
class GoodProductDetailAdapter :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_good_product_detail) {
    override fun convert(holder: BaseViewHolder, item: String) {
    }
}