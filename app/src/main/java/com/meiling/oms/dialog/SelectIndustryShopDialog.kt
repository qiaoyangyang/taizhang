package com.meiling.oms.dialog

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.View.OnClickListener
import com.hjq.shape.view.ShapeButton
import com.meiling.common.base.WheelItem
import com.meiling.common.base.WheelItemView
import com.meiling.common.base.WheelView.OnSelectedListener
import com.meiling.common.network.data.Children
import com.meiling.common.network.data.Shop
import com.meiling.common.network.data.ShopBean
import com.meiling.oms.R
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder

/***
 * 选择所属行业
 */
@Suppress("DEPRECATION")
class SelectIndustryShopDialog : BaseNiceDialog() {
    init {
        setGravity(Gravity.BOTTOM)
        setOutCancel(false)
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_select_industry
    }

    fun newInstance(childrenList: ArrayList<Children>): SelectIndustryShopDialog {
        val args = Bundle()
        args.putSerializable("list", childrenList)
        val dialog = SelectIndustryShopDialog()
        dialog.arguments = args
        return dialog
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        val shopBean = arguments?.getSerializable("list") as ArrayList<Children>
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
            Log.d("yjk","context---")
        }
        wheel_view_center?.setOnSelectedListener { context, selectedIndex ->

        }
        btn_ok_exit?.setOnClickListener {
            if (onresilience != null) {
                if(shopBean[cityid_view?.selectedIndex!!].children==null){
                    onresilience?.resilience(
                        0,
                        "",
                        0,
                        Children(name=shopBean[cityid_view?.selectedIndex!!].name,id=shopBean[cityid_view?.selectedIndex!!].id)
                    )
                    dismiss()
                    return@setOnClickListener
                }
                var shop = shopBean[cityid_view?.selectedIndex!!].children?.get(wheel_view_center!!.selectedIndex) as Children
                onresilience?.resilience(
                    cityid_view?.selectedIndex!!,
                    shopBean.get(cityid_view?.selectedIndex!!).name!!,
                    wheel_view_center?.selectedIndex!!,
                    shop!!
                )
                dismiss()
            }

        }
        if (shopBean.size != 0) {
            loadData(cityid_view!!, shopBean)
            loadData1(wheel_view_center!!, shopBean, 0)
        }

    }

    private fun loadData(wheelItemView: WheelItemView, label: ArrayList<Children>) {
        val items = arrayOfNulls<Children>(label.size)
        label.forEachIndexed { index, shopBean ->
            items[index] = shopBean
        }

        wheelItemView.setItems(items)
    }

    private fun loadData1(wheelItemView: WheelItemView, label: ArrayList<Children>, int: Int) {
        if(label.get(int)!!.children==null){
            wheelItemView.setItems(arrayOfNulls<Children>(0))
        }else{
            val items = arrayOfNulls<Children>(label.get(int)!!.children!!.size)
            label.get(int).children?.forEachIndexed { index, shop ->
                items[index] = shop
            }
            wheelItemView.setItems(items)
        }


    }

    fun setOnresilience(onresilience: Onresilience) {
        this.onresilience = onresilience
    }

    private var onresilience: Onresilience? = null

    interface Onresilience {
        fun resilience(cityid: Int,cityidname: String, shopid: Int, shop: Children)
        fun Ondismiss()
    }
}