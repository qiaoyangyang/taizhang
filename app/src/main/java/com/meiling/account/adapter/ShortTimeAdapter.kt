package com.meiling.account.adapter

import android.graphics.Color
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hjq.shape.layout.ShapeConstraintLayout
import com.hjq.shape.view.ShapeTextView
import com.meiling.account.R
import com.meiling.account.bean.DateSplitList

//  数据中心的时间
class ShortTimeAdapter : BaseQuickAdapter<DateSplitList, BaseViewHolder>(R.layout.item_short_time) {
    override fun convert(holder: BaseViewHolder, item: DateSplitList) {
        var cl_bg = holder.getView<ShapeConstraintLayout>(R.id.cl_bg)
        var tv_time_num = holder.getView<ShapeTextView>(R.id.tv_time_num)
        var tv_time = holder.getView<TextView>(R.id.tv_time)
        tv_time_num.text = item.goodsTotalNumber
        tv_time.text = item.dateValue
        if (item.boolean == true){
            cl_bg.setBackgroundResource(R.drawable.bg_data_time_ture)
            tv_time_num.setBackgroundResource(R.drawable.bg_data_mun_red_true)
            tv_time_num.setTextColor(Color.parseColor("#FFFFFF"))
            tv_time.setTextColor(Color.parseColor("#FE4B48"))
        }else{
            tv_time_num.setTextColor(Color.parseColor("#434343"))
            tv_time.setTextColor(Color.parseColor("#868686"))
            tv_time_num.setBackgroundResource(R.drawable.bg_data_mun_red_fase)
            cl_bg.setBackgroundResource(R.drawable.bg_data_time_fase)
        }

    }
}