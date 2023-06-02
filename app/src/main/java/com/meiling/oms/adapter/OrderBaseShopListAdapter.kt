package com.meiling.oms.adapter

import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.network.data.OrderGoodsVo
import com.meiling.common.utils.GlideAppUtils
import com.meiling.oms.R

class OrderBaseShopListAdapter :
    BaseQuickAdapter<OrderGoodsVo, BaseViewHolder>(R.layout.item_home_order_shop),
    LoadMoreModule {
    override fun convert(
        holder: BaseViewHolder, item: OrderGoodsVo
    ) {
        val view = holder.getView<ImageView>(R.id.img_order_shop_icon)
        holder.setText(R.id.txt_order_shop_name, item.gname)
        holder.setText(R.id.txt_order_shop_spec, item.sku)
        holder.setText(R.id.txt_order_shop_num, "X" + item.number)
        holder.setText(R.id.txt_order_shop_price, "¥" + item.price)
        val txtRefund = holder.getView<TextView>(R.id.txt_order_refund)

        if (item.refundNum == item.number) {
            txtRefund.visibility = View.VISIBLE
        } else {
            txtRefund.visibility = View.GONE
        }
        GlideAppUtils.loadUrl(view, item.avater ?: "https://static.igoodsale.com/default-goods.png")
    }
}