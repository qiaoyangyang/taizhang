package com.meiling.oms.dialog

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hjq.shape.view.ShapeButton
import com.meiling.common.base.WheelItemView
import com.meiling.common.network.data.AccountCityOrShopDto
import com.meiling.common.network.data.AccountItemSelect
import com.meiling.oms.R
import com.meiling.oms.widget.setSingleClickListener
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder


@Suppress("DEPRECATION")
class AccountSelectShopOrCityDialog : BaseNiceDialog() {
    init {
        setHeight(400)
        setGravity(Gravity.BOTTOM)
        setOutCancel(false)
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_account_select_shop_city
    }

    lateinit var rvSelectAdapter: BaseQuickAdapter<AccountCityOrShopDto, BaseViewHolder>

    fun newInstance(
        title: String, selectItemBean: ArrayList<AccountCityOrShopDto>
    ): AccountSelectShopOrCityDialog {
        val args = Bundle()
        args.putSerializable("selectItemBean", selectItemBean)
        args.putString("title", title)
        val dialog = AccountSelectShopOrCityDialog()
        dialog.arguments = args
        return dialog
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        var selectItemBean =
            arguments?.getSerializable("selectItemBean") as ArrayList<AccountCityOrShopDto>
        var rvSelect = holder?.getView<RecyclerView>(R.id.rv_shop_or_city)
        var btnSelect = holder?.getView<ShapeButton>(R.id.btn_ok_select_shop_city)
        var close = holder?.getView<ImageView>(R.id.iv_close_select_shop_city)
        var ivSelectAll = holder?.getView<ImageView>(R.id.img_select_all)
        var llSelectAll = holder?.getView<LinearLayout>(R.id.ll_select_all)
        var title = holder?.getView<TextView>(R.id.txt_title_item_city)
        title?.text = arguments?.getString("title")
//        btnSelect?.setOnClickListener {
//            var shop = selectItemBean[selectView?.selectedIndex!!]
//            okSelectItemClickLister?.invoke(
//                shop.id,
//                shop.name,
//            )
//            dismiss()
//        }
        close?.setOnClickListener {
            dismiss()
        }
        var isSelectAll = false
        ivSelectAll?.setImageDrawable(resources.getDrawable(R.drawable.icon_checkbox_false))
        rvSelectAdapter = object :
            BaseQuickAdapter<AccountCityOrShopDto, BaseViewHolder>(R.layout.item_dialog_account_select_city_or_shop) {
            @SuppressLint("UseCompatLoadingForDrawables")
            override fun convert(holder: BaseViewHolder, item: AccountCityOrShopDto) {
                var imgShopOrCity = holder.getView<ImageView>(R.id.img_shop_or_city)
                holder.setText(R.id.txt_shop_or_city, item.name)
                if (item.isSelect) {
                    imgShopOrCity.setImageDrawable(resources.getDrawable(R.drawable.icon_checkbox_true))
                } else {
                    imgShopOrCity.setImageDrawable(resources.getDrawable(R.drawable.icon_checkbox_false))
                }
            }
        }
        rvSelect?.adapter = rvSelectAdapter
        rvSelectAdapter.setNewInstance(selectItemBean)
        rvSelectAdapter.setOnItemClickListener { adapter, view, position ->
            (adapter.data[position] as AccountCityOrShopDto).isSelect =
                !(adapter.data[position] as AccountCityOrShopDto).isSelect
            rvSelectAdapter.notifyDataSetChanged()

//            val hasSelected = rvSelectAdapter.data.any { it.isSelect }

            if (rvSelectAdapter.data.any { it.isSelect }) {
                isSelectAll = false
                ivSelectAll?.setImageDrawable(resources.getDrawable(R.drawable.icon_checkbox_false))
            } else {
                isSelectAll = true
                ivSelectAll?.setImageDrawable(resources.getDrawable(R.drawable.icon_checkbox_true))
            }

        }


        llSelectAll?.setSingleClickListener {
            if (isSelectAll) {
                for (item in rvSelectAdapter.data) {
                    item.isSelect = false
                }
            } else {
                for (item in rvSelectAdapter.data) {
                    item.isSelect = true
                }
            }
        }
    }


    var okSelectItemClickLister: ((id: String, name: String) -> Unit)? = null
    fun setOkClickItemLister(okClickLister: (id: String, name: String) -> Unit) {
        this.okSelectItemClickLister = okClickLister
    }
}