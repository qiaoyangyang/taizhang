package com.meiling.account.adapter

import android.util.Log
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.account.R
import com.meiling.account.bean.Ranking
import com.meiling.account.widget.InputUtil

class RankingAdapter : BaseQuickAdapter<Ranking, BaseViewHolder>(R.layout.item_ranking) {
    override fun convert(holder: BaseViewHolder, item: Ranking) {

        holder.setText(R.id.tv_ranking, holder.layoutPosition.plus(1).toString())
        holder.setTextColor(R.id.tv_mun, item?.textcolor!!)


    }
}