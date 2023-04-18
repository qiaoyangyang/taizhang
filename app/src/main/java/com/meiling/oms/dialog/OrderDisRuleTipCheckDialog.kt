package com.meiling.oms.dialog

import android.view.Gravity
import android.widget.TextView
import com.meiling.oms.R
import com.meiling.oms.widget.setSingleClickListener
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder

class OrderDisRuleTipCheckDialog : BaseNiceDialog() {


    init {
        setGravity(Gravity.BOTTOM)
        setOutCancel(true)
    }

    fun newInstance(): OrderDisRuleTipCheckDialog {
        return OrderDisRuleTipCheckDialog()
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_order_rule
    }


    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        var btnRuleTip = holder?.getView<TextView>(R.id.btn_rule_ok)

        btnRuleTip?.setSingleClickListener {
            dismiss()
        }
    }
}