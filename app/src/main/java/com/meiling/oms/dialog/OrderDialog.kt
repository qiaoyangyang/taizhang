package com.meiling.oms.dialog

import com.meiling.oms.R
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder

class OrderDialog : BaseNiceDialog() {


    fun newInstance(): OrderDialog {
        return OrderDialog()
    }


    override fun intLayoutId(): Int {
        return R.layout.empty_msg_center
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
    }
}