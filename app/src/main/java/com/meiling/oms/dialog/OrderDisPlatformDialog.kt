package com.meiling.oms.dialog

import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import com.hjq.shape.view.ShapeButton
import com.meiling.common.base.WheelItemView
import com.meiling.common.network.data.SelectLabel
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

    fun newInstance(arrayList: ArrayList<SelectLabel>): OrderDisPlatformDialog {
        val args = Bundle()
        args.putSerializable("selectLabel", arrayList)
        var dialog = OrderDisPlatformDialog()
        dialog.arguments = args
        return dialog
    }

    var shopBean = ArrayList<SelectLabel>()

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        shopBean = requireArguments().getSerializable("selectLabel") as ArrayList<SelectLabel>
        var cityid_view = holder?.getView<WheelItemView>(R.id.wheel_view_left)
        var wheel_view_center = holder?.getView<WheelItemView>(R.id.wheel_view_center)
        var btn_ok_exit = holder?.getView<ShapeButton>(R.id.btn_ok_exit)
        var close = holder?.getView<ImageView>(R.id.iv_close_select_shop)
//        cityid_view?.setOnSelectedListener { context, selectedIndex ->
//            loadData1(wheel_view_center!!, shopBean, selectedIndex)
//        }

//        wheel_view_center?.setOnSelectedListener { context, selectedIndex ->
//            Log.d("yjk", "convertView: $selectedIndex")
//        }
        btn_ok_exit?.setOnClickListener {
            var shop = shopBean[cityid_view?.selectedIndex!!]
            okSelectClickLister?.invoke(
                shop.value,
                shop.label,
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

    private fun loadData(wheelItemView: WheelItemView, label: ArrayList<SelectLabel>) {
        val items = arrayOfNulls<SelectLabel>(label.size)
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