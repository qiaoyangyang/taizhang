package com.meiling.account.dialog

import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.View.OnClickListener
import com.hjq.shape.view.ShapeButton
import com.meiling.common.base.WheelItemView
import com.meiling.common.network.data.Shop
import com.meiling.common.network.data.ShopBean
import com.meiling.account.R
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
    //cityposition,shopidposition
    fun newInstance(shopBean: ArrayList<ShopBean>,tetle:String?="",cityposition:Int=0,shopidposition:Int=0): ShopDialog {
        val args = Bundle()
        args.putSerializable("shopBean", shopBean)
        args.putString("tetle", tetle)
        args.putInt("cityposition", cityposition)
        args.putInt("shopidposition", shopidposition)
        val dialog = ShopDialog()
        dialog.arguments = args
        return dialog
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        val shopBean = arguments?.getSerializable("shopBean") as ArrayList<ShopBean>
        val tetle = arguments?.getString("tetle").toString()
        var cityposition = arguments?.getInt("cityposition",0)
        var shopidposition = arguments?.getInt("shopidposition",0)
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
            loadData1(wheel_view_center!!, shopBean, selectedIndex,0)
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
                if (tetle!="修改发货门店") {
                    dismiss()
                }

            }

        }
        if (shopBean.size != 0) {
            loadData(cityid_view!!, shopBean,cityposition!!)
            loadData1(wheel_view_center!!, shopBean, cityposition,shopidposition!!)
        }

    }

    private fun loadData(wheelItemView: WheelItemView, label: ArrayList<ShopBean>,int: Int) {
        val items = arrayOfNulls<ShopBean>(label.size)
        label.forEachIndexed { index, shopBean ->
            items[index] = shopBean
        }

        wheelItemView.setItems(items)
        wheelItemView.selectedIndex = int
    }

    private fun loadData1(wheelItemView: WheelItemView, label: ArrayList<ShopBean>, int: Int,shopidposition: Int) {
        val items = arrayOfNulls<Shop>(label.get(int)!!.shopList!!.size)

        label.get(int).shopList?.forEachIndexed { index, shop ->
            items[index] = shop
        }


        wheelItemView.setItems(items)
        wheelItemView.selectedIndex = shopidposition
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