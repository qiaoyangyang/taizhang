package com.meiling.account.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.account.R
import com.meiling.account.bean.Info
import com.meiling.common.GlideApp
import com.meiling.common.utils.XNumberUtils

//良品明细
class DefectiveDetailAdapter :
    BaseQuickAdapter<Info?, BaseViewHolder>(R.layout.item_defective_detail) {
    override fun convert(holder: BaseViewHolder, item: Info?) {
        holder.setText(R.id.tv_name,item?.goodsName)
        holder.setText(R.id.tv_sku,item?.skuCode)
        holder.setText(R.id.tv_Select_Sum,XNumberUtils.formatteddata(item?.realGoodsNumber,3))
        holder.setText(R.id.tv_Library_sign,item?.userPhone)
        holder.setText(R.id.tv_specification,item?.goodsSpecsValues)
        holder.setText(R.id.tv_time,item?.createTime)
        holder.setText(R.id.tv_uni,item?.goodsUnit)
        GlideApp.with(context)
            .load(item?.goodsImageUrl)
            .into(holder.getView(R.id.iv_commodity_url))
        if (item?.isRevoke==1){
            holder.setVisible(R.id.btn_withdraw,true)

        }else{
            holder.setVisible(R.id.btn_withdraw,false)
        }
    }
}