package com.meiling.account.dialog

import android.widget.TextView
import com.meiling.account.R
import com.meiling.account.widget.setSingleClickListener
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder


/**
 * 客服
 * **/
class AboutKFELMDialog : BaseNiceDialog() {

    init {
        setMargin(30)
        setOutCancel(false)
    }

    fun newInstance(): AboutKFELMDialog {
        return AboutKFELMDialog()
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_about_kf_elm_tip
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        var btnOkTip = holder?.getView<TextView>(R.id.btn_ok_sure)

        btnOkTip?.setSingleClickListener {
            dismiss()
        }
    }
}