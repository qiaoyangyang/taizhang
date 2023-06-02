package com.meiling.oms.dialog

import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.meiling.common.network.data.OrderGoodsVo
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

    fun newInstance(orderId: String, orderGoodsVo: ArrayList<OrderGoodsVo?>): OrderGoodsListDetailDialog {
        val args = Bundle()
        args.putString("orderId", orderId)
        args.putSerializable("orderGoodsVo", orderGoodsVo)
        val dialog = OrderGoodsListDetailDialog()
        dialog.arguments = args
        return dialog
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_goods_list_detail
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        val orderGoodsVoList = requireArguments().getSerializable("orderGoodsVo") as ArrayList<OrderGoodsVo>
        val recyclerViewList = holder?.getView<RecyclerView>(R.id.recy_dialog_shop_list)
        val closeGoodsDialog = holder?.getView<ImageView>(R.id.iv_close_goods)
        var orderDisAdapter = OrderBaseShopListAdapter()
        var goods = ArrayList<OrderGoodsVo>()
        goods.addAll(orderGoodsVoList)
        recyclerViewList!!.adapter = orderDisAdapter
        closeGoodsDialog?.setSingleClickListener {
            dismiss()
        }
    }
}

