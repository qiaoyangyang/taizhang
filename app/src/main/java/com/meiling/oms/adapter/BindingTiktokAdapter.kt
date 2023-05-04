package com.meiling.oms.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.oms.R

//绑定抖音列表
class BindingTiktokAdapter :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_binding_tiktok) {
    override fun convert(holder: BaseViewHolder, item: String) {
    }
}