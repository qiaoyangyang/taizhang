package com.meiling.oms.dialog

import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.meiling.common.network.data.GoodsListVo
import com.meiling.oms.R
import com.meiling.oms.adapter.OrderBaseShopListAdapter
import com.meiling.oms.widget.setSingleClickListener
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder


/**
 * 数据提示
 * **/
class OrderGoodsListDetailDialog : BaseNiceDialog() {

    init {
        setHeight(400)
        setGravity(Gravity.BOTTOM)
        setOutCancel(false)
    }

    fun newInstance(orderId: String): OrderGoodsListDetailDialog {
        val args = Bundle()
        args.putString("orderId", orderId)
        val dialog = OrderGoodsListDetailDialog()
        dialog.arguments = args
        return dialog
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_goods_list_detail
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        val recyclerViewList = holder?.getView<RecyclerView>(R.id.recy_dialog_shop_list)
        val closeGoodsDialog = holder?.getView<ImageView>(R.id.iv_close_goods)
        var orderDisAdapter = OrderBaseShopListAdapter()
        var goods = ArrayList<GoodsListVo>()
        goods.add(GoodsListVo(gname = "张三"))
        goods.add(GoodsListVo(gname = "张三1"))
        goods.add(GoodsListVo(gname = "张三2"))
        goods.add(GoodsListVo(gname = "张三3"))
        goods.add(GoodsListVo(gname = "张三3"))
        goods.add(GoodsListVo(gname = "张三3"))
        goods.add(GoodsListVo(gname = "张三3"))
        goods.add(GoodsListVo(gname = "张三3"))
        goods.add(GoodsListVo(gname = "张三3"))
        goods.add(GoodsListVo(gname = "张三3"))
        goods.add(GoodsListVo(gname = "张三3"))
        goods.add(GoodsListVo(gname = "张三3"))
        goods.add(GoodsListVo(gname = "张三3"))
        goods.add(GoodsListVo(gname = "张三3"))
        goods.add(GoodsListVo(gname = "张三3"))
        goods.add(GoodsListVo(gname = "张三3"))
        goods.add(GoodsListVo(gname = "张三3"))
        goods.add(GoodsListVo(gname = "张三3"))
        goods.add(GoodsListVo(gname = "张三3"))
        goods.add(GoodsListVo(gname = "张三3"))
        goods.add(GoodsListVo(gname = "张三3"))
        orderDisAdapter.setList(goods)
        recyclerViewList!!.adapter = orderDisAdapter
        closeGoodsDialog?.setSingleClickListener {
            dismiss()
        }
    }
}