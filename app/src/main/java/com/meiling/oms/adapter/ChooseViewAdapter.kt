package com.meiling.oms.adapter

import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.network.data.ListSelectModel
import com.meiling.oms.R

class ChooseViewAdapter<T: ListSelectModel> :BaseQuickAdapter<T, BaseViewHolder>(R.layout.item_dialog_shop_list) {
    override fun convert(holder: BaseViewHolder, item: T) {
        holder.setText(R.id.txt_shop_or_city,item.getSelectTypeName())
        if(item.isSelect){
            holder.itemView.setBackgroundResource(R.color.red)
            holder.setTextColorRes(R.id.txt_shop_or_city,R.color.white)
        }else{
            holder.itemView.setBackgroundResource(R.color.white)
            holder.setTextColorRes(R.id.txt_shop_or_city,R.color.home_333333)
        }
    }


}