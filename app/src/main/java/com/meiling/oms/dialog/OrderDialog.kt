package com.meiling.oms.dialog

import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder

class OrderDialog : BaseNiceDialog() {

    var okClickLister: ((payAmount:String,giveAmount:String,textAmoun:String) -> Unit)? = null

    fun setOkClickLister(okClickLister: (payAmount:String,giveAmount:String,textAmoun:String) -> Unit) {
        this.okClickLister = okClickLister
    }

    fun newInstance(): OrderDialog {
        return OrderDialog()
    }


    override fun intLayoutId(): Int {
        return 1
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
    }
}