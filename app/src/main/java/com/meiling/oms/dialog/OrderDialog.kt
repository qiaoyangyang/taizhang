package com.meiling.oms.dialog

import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder

class OrderDialog : BaseNiceDialog() {


    fun newInstance(): OrderDialog {
        return OrderDialog()
    }


    override fun intLayoutId(): Int {
        return 1
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
    }
}