package com.meiling.oms.dialog

import com.meiling.oms.R
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder

class OrderDisRuleDialog : BaseNiceDialog() {


    fun newInstance(): OrderDisRuleDialog {
        return OrderDisRuleDialog()
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_order_rule
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
    }
}