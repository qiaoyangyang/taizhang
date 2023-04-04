package com.meiling.oms.dialog

import android.content.Context
import android.os.Bundle
import com.meiling.common.base.WheelItem
import com.meiling.common.base.WheelItemView
import com.meiling.common.base.WheelView.OnSelectedListener
import com.meiling.common.network.data.Shop
import com.meiling.common.network.data.ShopBean
import com.meiling.oms.R
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder


@Suppress("DEPRECATION")
class ShopDialog : BaseNiceDialog() {
    override fun intLayoutId(): Int {
        return R.layout.dialog_shop
    }

    fun newInstance(shopBean: ArrayList<ShopBean>): ShopDialog {
        val args = Bundle()
        args.putSerializable("shopBean", shopBean)
        val dialog = ShopDialog()
        dialog.arguments = args
        return dialog
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        val title = arguments?.getSerializable("shopBean") as ArrayList<ShopBean>
        var view = holder?.getView<WheelItemView>(R.id.wheel_view_left)
        var wheel_view_center = holder?.getView<WheelItemView>(R.id.wheel_view_center)
        view?.setOnSelectedListener { context, selectedIndex ->
            loadData1(wheel_view_center!!, title,selectedIndex)
        }

        loadData(view!!, title)
        loadData1(wheel_view_center!!, title,0)

    }

    private fun loadData(wheelItemView: WheelItemView, label: ArrayList<ShopBean>) {
        val items = arrayOfNulls<ShopBean>(label.size)
       label.forEachIndexed { index, shopBean ->
           items[index]=shopBean
       }

        wheelItemView.setItems(items)
    }
    private fun loadData1(wheelItemView: WheelItemView, label: ArrayList<ShopBean>,int: Int) {
        val items = arrayOfNulls<Shop>(label.size)
        label.get(int).shopList?.forEachIndexed { index, shopBean ->
            items[index]=shopBean
        }

        wheelItemView.setItems(items)
    }
}