package com.meiling.account.dialog

import android.view.Gravity
import android.widget.ImageView
import com.hjq.shape.view.ShapeButton
import com.meiling.common.base.WheelItemView
import com.meiling.common.network.data.OrderSendShopSelect
import com.meiling.account.R
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder


@Suppress("DEPRECATION")
class OrderDisGoodsSelectDialog : BaseNiceDialog() {
    init {
        setGravity(Gravity.BOTTOM)
        setOutCancel(false)
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_order_dis_goods_select
    }

    fun newInstance(): OrderDisGoodsSelectDialog {
        return OrderDisGoodsSelectDialog()
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

        shopBean.add(OrderSendShopSelect("10", "美食"))
        shopBean.add(OrderSendShopSelect("20", "鲜花"))
        shopBean.add(OrderSendShopSelect("30", "甜品"))
        shopBean.add(OrderSendShopSelect("40", "果蔬"))
        shopBean.add(OrderSendShopSelect("50", "生鲜水产"))
        shopBean.add(OrderSendShopSelect("60", "医药"))
        shopBean.add(OrderSendShopSelect("70", "酒水饮料"))
        shopBean.add(OrderSendShopSelect("80", "母婴"))
        shopBean.add(OrderSendShopSelect("90", "文件"))
        shopBean.add(OrderSendShopSelect("100", "日用百货"))
        shopBean.add(OrderSendShopSelect("110", "服装"))
        shopBean.add(OrderSendShopSelect("120", "珠宝"))
        shopBean.add(OrderSendShopSelect("130", "3c电子产品"))
        shopBean.add(OrderSendShopSelect("140", "汽配"))
        shopBean.add(OrderSendShopSelect("150", "宠物"))
        shopBean.add(OrderSendShopSelect("999", "其它"))
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