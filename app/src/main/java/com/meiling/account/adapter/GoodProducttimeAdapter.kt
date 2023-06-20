package com.meiling.account.adapter

import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.account.R
import com.meiling.account.widget.InputUtil

//良品明细
class GoodProducttimeAdapter :
    BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_goods_products) {
    var goodProductDetailAdapter: GoodProductDetailAdapter? = null
    override fun convert(holder: BaseViewHolder, item: String) {
        var recyclerView1 = holder.getView<RecyclerView>(R.id.rv_goos)
        recyclerView1?.layoutManager = GridLayoutManager(context, 3)
        goodProductDetailAdapter = GoodProductDetailAdapter()
        recyclerView1?.adapter = goodProductDetailAdapter
        goodProductDetailAdapter?.setList(InputUtil.getyouhuiString())

    }
}

