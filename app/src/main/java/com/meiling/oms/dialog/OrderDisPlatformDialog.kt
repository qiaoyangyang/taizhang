package com.meiling.oms.dialog

import android.view.Gravity
import android.widget.ImageView
import com.hjq.shape.view.ShapeButton
import com.meiling.common.base.WheelItemView
import com.meiling.common.network.data.OrderSendShopSelect
import com.meiling.oms.R
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder


@Suppress("DEPRECATION")
class OrderDisPlatformDialog : BaseNiceDialog() {
    init {
        setGravity(Gravity.BOTTOM)
        setOutCancel(false)
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_order_dis_goods_select
    }

    fun newInstance(): OrderDisPlatformDialog {
        return OrderDisPlatformDialog()
    }

    var shopBean = ArrayList<OrderSendShopSelect>()

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        var cityid_view = holder?.getView<WheelItemView>(R.id.wheel_view_left)
        var wheel_view_center = holder?.getView<WheelItemView>(R.id.wheel_view_center)
        var btn_ok_exit = holder?.getView<ShapeButton>(R.id.btn_ok_exit)
        var close = holder?.getView<ImageView>(R.id.iv_close_select_shop)
//        cityid_view?.setOnSelectedListener { context, selectedIndex ->
//            loadData1(wheel_view_center!!, shopBean, selectedIndex)
//        }

        shopBean.add(OrderSendShopSelect("10", "其它"))
        shopBean.add(OrderSendShopSelect("20", "达达"))
        shopBean.add(OrderSendShopSelect("30", "顺丰同城"))
        shopBean.add(OrderSendShopSelect("40", "顺丰医药"))
        shopBean.add(OrderSendShopSelect("50", "顺丰快递"))
        shopBean.add(OrderSendShopSelect("60", "EMS"))
        shopBean.add(OrderSendShopSelect("70", "UU跑腿"))
        shopBean.add(OrderSendShopSelect("80", "美团同城"))
        shopBean.add(OrderSendShopSelect("90", "闪送"))
//        wheel_view_center?.setOnSelectedListener { context, selectedIndex ->
//            Log.d("yjk", "convertView: $selectedIndex")
//        }
        btn_ok_exit?.setOnClickListener {
            var shop = shopBean[cityid_view?.selectedIndex!!]
            okSelectClickLister?.invoke(
                shop.id,
                shop.name,
            )
            dismiss()
        }
        close?.setOnClickListener {
            dismiss()
        }
        if (shopBean.size != 0) {
            loadData(cityid_view!!, shopBean)
        }
    }

    private fun loadData(wheelItemView: WheelItemView, label: ArrayList<OrderSendShopSelect>) {
        val items = arrayOfNulls<OrderSendShopSelect>(label.size)
        label.forEachIndexed { index, shopBean ->
            items[index] = shopBean
        }
        wheelItemView.setItems(items)
    }

    var okSelectClickLister: ((id: String, name: String) -> Unit)? = null
    fun setOkClickLister(okClickLister: (id: String, name: String) -> Unit) {
        this.okSelectClickLister = okClickLister
    }
}