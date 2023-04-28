package com.meiling.oms.dialog

import android.view.Gravity
import com.meiling.oms.R
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder

class OrderDialog : BaseNiceDialog() {
    init {
        setGravity(Gravity.TOP)
    }
    fun newInstance(): OrderDialog {
        return OrderDialog()
    }


    override fun intLayoutId(): Int {
        return R.layout.dialog_order_jpush
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
    }
}