package com.meiling.oms.dialog

import com.meiling.oms.R
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder

class PushMsgDialog : BaseNiceDialog() {


    fun newInstance(): PushMsgDialog {
        return PushMsgDialog()
    }


    override fun intLayoutId(): Int {
        return R.layout.dialog_order_jpush
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
    }
}