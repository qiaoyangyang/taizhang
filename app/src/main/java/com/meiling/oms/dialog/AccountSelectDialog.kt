package com.meiling.oms.dialog

import android.os.Bundle
import android.view.Gravity
import android.widget.ImageView
import android.widget.TextView
import com.hjq.shape.view.ShapeButton
import com.meiling.common.base.WheelItemView
import com.meiling.common.network.data.AccountItemSelect
import com.meiling.oms.R
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder


@Suppress("DEPRECATION")
class AccountSelectDialog : BaseNiceDialog() {
    init {
        setGravity(Gravity.BOTTOM)
        setOutCancel(false)
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_account_select
    }

    fun newInstance(
        title: String,
        selectItemBean: ArrayList<AccountItemSelect>
    ): AccountSelectDialog {
        val args = Bundle()
        args.putSerializable("selectItemBean", selectItemBean)
        args.putString("title", title)
        val dialog = AccountSelectDialog()
        dialog.arguments = args
        return dialog
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        var selectItemBean =
            arguments?.getSerializable("selectItemBean") as ArrayList<AccountItemSelect>

        var selectView = holder?.getView<WheelItemView>(R.id.wheel_view_left)
        var btnSelect = holder?.getView<ShapeButton>(R.id.btn_ok_select_role)
        var close = holder?.getView<ImageView>(R.id.iv_close_select_shop)
        var title = holder?.getView<TextView>(R.id.txt_title_item)
        title?.text = arguments?.getString("title")
        btnSelect?.setOnClickListener {
            var shop = selectItemBean[selectView?.selectedIndex!!]
            okSelectItemClickLister?.invoke(
                shop.id,
                shop.name,
            )
            dismiss()
        }
        close?.setOnClickListener {
            dismiss()
        }
        if (selectItemBean.size != 0) {
            loadData(selectView!!, selectItemBean)
        }
    }

    private fun loadData(wheelItemView: WheelItemView, label: ArrayList<AccountItemSelect>) {
        val items = arrayOfNulls<AccountItemSelect>(label.size)
        label.forEachIndexed { index, selectItemBean ->
            items[index] = selectItemBean
        }
        wheelItemView.setItems(items)
    }

    var okSelectItemClickLister: ((id: String, name: String) -> Unit)? = null
    fun setOkClickItemLister(okClickLister: (id: String, name: String) -> Unit) {
        this.okSelectItemClickLister = okClickLister
    }
}