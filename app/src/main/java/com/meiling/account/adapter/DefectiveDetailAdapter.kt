package com.meiling.account.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.account.R
import com.meiling.account.bean.Info
import com.meiling.common.GlideApp

//良品明细
class DefectiveDetailAdapter :
    BaseQuickAdapter<Info?, BaseViewHolder>(R.layout.item_defective_detail) {
    override fun convert(holder: BaseViewHolder, item: Info?) {
        holder.setText(R.id.tv_name,item?.goodsName)
        holder.setText(R.id.tv_sku,item?.skuCode)
        holder.setText(R.id.tv_Select_Sum,item?.goodsNumber)
        holder.setText(R.id.tv_Library_sign,item?.userPhone)
        holder.setText(R.id.tv_specification,item?.goodsSpecsValues)
        holder.setText(R.id.tv_time,item?.createTime)
        GlideApp.with(context)
            .load(item?.goodsImageUrl)
            .into(holder.getView(R.id.iv_commodity_url))
    }
}