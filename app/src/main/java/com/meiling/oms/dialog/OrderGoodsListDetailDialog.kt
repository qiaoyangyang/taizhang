package com.meiling.oms.dialog

import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
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

    fun newInstance(
        number: Int,
        money: String,
        orderGoodsVo: ArrayList<OrderGoodsVo?>
    ): OrderGoodsListDetailDialog {
        val args = Bundle()
        args.putInt("number", number)
        args.putString("money", money)
        args.putSerializable("orderGoodsVo", orderGoodsVo)
        val dialog = OrderGoodsListDetailDialog()
        dialog.arguments = args
        return dialog
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_goods_list_detail
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        val orderGoodsVoList =
            requireArguments().getSerializable("orderGoodsVo") as ArrayList<OrderGoodsVo>
        val recyclerViewList = holder?.getView<RecyclerView>(R.id.recy_dialog_shop_list)
        val closeGoodsDialog = holder?.getView<ImageView>(R.id.iv_close_goods)
        val money = holder?.getView<TextView>(R.id.tv_shop_money)
        val num = holder?.getView<TextView>(R.id.tv_shop_num)

        num?.text = "商品${requireArguments().getInt("number")}件，共"
        money?.text = requireArguments().getString("money")
        var orderDisAdapter = OrderBaseShopListAdapter()
        var goods = ArrayList<OrderGoodsVo>()
        goods.addAll(orderGoodsVoList)
        orderDisAdapter.setList(goods)
        recyclerViewList!!.adapter = orderDisAdapter
        closeGoodsDialog?.setSingleClickListener {
            dismiss()
        }
    }
}

