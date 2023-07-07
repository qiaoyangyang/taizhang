package com.meiling.account.adapter

import android.graphics.Color
import android.graphics.Typeface
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.account.R
import com.meiling.account.bean.GoosClassify

class ClassificationAdapter :BaseQuickAdapter<GoosClassify, BaseViewHolder>(R.layout.item_tab_p) {
    override fun convert(holder: BaseViewHolder, item: GoosClassify) {
        holder.setText(R.id.txtRecyCategoryItem, item.name)
        val textView = holder.getView<TextView>(R.id.txtRecyCategoryItem)
        if (item.select) {
            holder.setTextColor(R.id.txtRecyCategoryItem, Color.parseColor("#FE4B48"))
            textView.textSize = 18f
            textView.setTypeface(textView.typeface, Typeface.BOLD)
        } else {
            textView.setTypeface(textView.typeface, Typeface.NORMAL)
            textView.textSize = 16f
            holder.setTextColor(R.id.txtRecyCategoryItem, Color.parseColor("#7A7A7A"))
        }


    }
}