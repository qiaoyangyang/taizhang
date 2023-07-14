package com.meiling.account.adapter

import android.util.Log
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hjq.base.BasePopupWindow
import com.meiling.account.R
import com.meiling.account.bean.DateSplitList
import com.meiling.account.bean.GoodsSplit
import com.meiling.account.dialog.MineExitDialog
import com.meiling.account.dialog.OptionDatePopWindow
import com.meiling.account.widget.InputUtil

//良品明细
class GoodProducttimeAdapter :
    BaseQuickAdapter<GoodsSplit, BaseViewHolder>(R.layout.item_goods_products) {
    private var listener: OnListener? = null
    var goodProductDetailAdapter: GoodProductDetailAdapter? = null

    override fun convert(holder: BaseViewHolder, item: GoodsSplit) {
        holder.setText(R.id.tv_time,item.dateValue)
        holder.setText(R.id.tv_lsit_mun,item.infoList?.size.toString())

        var recyclerView1 = holder.getView<RecyclerView>(R.id.rv_goos)
        recyclerView1?.layoutManager = GridLayoutManager(context, 3)
        goodProductDetailAdapter = GoodProductDetailAdapter()
        recyclerView1?.adapter = goodProductDetailAdapter
        goodProductDetailAdapter?.setList(item?.infoList!!)
        goodProductDetailAdapter?.addChildClickViewIds(R.id.btn_withdraw,R.id.btn_Receipt)
        goodProductDetailAdapter?.setOnItemChildClickListener { adapter, view, position ->
            if (listener!=null){
                listener?.onSelected(position,view.id,holder.position,goodProductDetailAdapter!!)
            }
        }


    }
    fun setListener(listener: OnListener?) {
        this.listener = listener
    }
}
interface OnListener {

    /**
     * 选择条目时回调
     */
    fun onSelected(position:Int,id:Int,int: Int,goodProductDetailAdapter:GoodProductDetailAdapter)
}


