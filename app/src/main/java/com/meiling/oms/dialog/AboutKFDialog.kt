package com.meiling.oms.dialog

import android.widget.TextView
import com.meiling.oms.R
import com.meiling.oms.widget.setSingleClickListener
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder


/**
 * 客服
 * **/
class AboutKFDialog : BaseNiceDialog() {

    init {
        setMargin(30)
        setOutCancel(false)
    }

    fun newInstance(): AboutKFDialog {
        return AboutKFDialog()
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_about_kf_tip
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        var btnOkTip = holder?.getView<TextView>(R.id.btn_ok_sure)

        btnOkTip?.setSingleClickListener {
            dismiss()
        }
    }
}