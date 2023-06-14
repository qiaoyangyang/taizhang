package com.meiling.account.dialog

import com.meiling.account.R
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder

class OrderDisDetailDialog : BaseNiceDialog() {


    fun newInstance(): OrderDisDetailDialog {
        return OrderDisDetailDialog()
    }


    override fun intLayoutId(): Int {
        return R.layout.dialog_order_dis_detail
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
    }
}