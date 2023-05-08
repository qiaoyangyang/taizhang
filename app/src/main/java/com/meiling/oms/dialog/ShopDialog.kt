package com.meiling.oms.dialog

import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.View.OnClickListener
import com.hjq.shape.view.ShapeButton
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
    init {
        setGravity(Gravity.BOTTOM)
        setOutCancel(false)
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_shop
    }

    fun newInstance(shopBean: ArrayList<ShopBean>,tetle:String?=""): ShopDialog {
        val args = Bundle()
        args.putSerializable("shopBean", shopBean)
        args.putString("tetle", tetle)
        val dialog = ShopDialog()
        dialog.arguments = args
        return dialog
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        val shopBean = arguments?.getSerializable("shopBean") as ArrayList<ShopBean>
        val tetle = arguments?.getString("tetle").toString()
        if (!TextUtils.isEmpty(tetle)){
            holder?.setText(R.id.tv_title,tetle)
        }
        var cityid_view = holder?.getView<WheelItemView>(R.id.wheel_view_left)
        var wheel_view_center = holder?.getView<WheelItemView>(R.id.wheel_view_center)
        var btn_ok_exit = holder?.getView<ShapeButton>(R.id.btn_ok_exit)
        holder?.setOnClickListener(R.id.iv_close_recharge, object : OnClickListener {
            override fun onClick(v: View?) {

                dismiss()
                onresilience?.Ondismiss()
            }

        })
        cityid_view?.setOnSelectedListener { context, selectedIndex ->
            loadData1(wheel_view_center!!, shopBean, selectedIndex)
        }
        wheel_view_center?.setOnSelectedListener { context, selectedIndex ->
            Log.d("yjk", "convertView: $selectedIndex")
        }
        btn_ok_exit?.setOnClickListener {
            if (onresilience != null) {
                var shop = shopBean[cityid_view?.selectedIndex!!].shopList?.get(wheel_view_center!!.selectedIndex) as Shop

                onresilience?.resilience(
                    cityid_view?.selectedIndex!!,
                    shopBean.get(cityid_view?.selectedIndex!!).name!!,
                    wheel_view_center?.selectedIndex!!,
                    shop!!
                )
                if (tetle!="设置发货门店") {
                    dismiss()
                }

            }

        }
        if (shopBean.size != 0) {
            loadData(cityid_view!!, shopBean)
            loadData1(wheel_view_center!!, shopBean, 0)
        }

    }

    private fun loadData(wheelItemView: WheelItemView, label: ArrayList<ShopBean>) {
        val items = arrayOfNulls<ShopBean>(label.size)
        label.forEachIndexed { index, shopBean ->
            items[index] = shopBean
        }

        wheelItemView.setItems(items)
    }

    private fun loadData1(wheelItemView: WheelItemView, label: ArrayList<ShopBean>, int: Int) {
        val items = arrayOfNulls<Shop>(label.get(int)!!.shopList!!.size)

        label.get(int).shopList?.forEachIndexed { index, shop ->
            items[index] = shop
        }


        wheelItemView.setItems(items)
    }

    fun setOnresilience(onresilience: Onresilience) {
        this.onresilience = onresilience
    }

    private var onresilience: Onresilience? = null

    interface Onresilience {
        fun resilience(cityid: Int,cityidname: String, shopid: Int, shop: Shop)
        fun Ondismiss()
    }
}