package com.meiling.oms.dialog

import android.os.Bundle
import android.widget.TextView
import com.meiling.oms.R
import com.meiling.oms.widget.setSingleClickListener
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder

class OrderDisRuleTipDialog : BaseNiceDialog() {


    init {
        setMargin(30)
        setOutCancel(false)
    }

    fun newInstance(errorContext: String): OrderDisRuleTipDialog {
        val args = Bundle()
        args.putSerializable("errorContext", errorContext)
        val dialog = OrderDisRuleTipDialog()
        dialog.arguments = args
        return dialog
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_dis_rule_tip
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        var context = arguments?.getString("errorContext")
        var txtContext = holder?.getView<TextView>(R.id.tv_rule_content)
        var btnRuleCancel = holder?.getView<TextView>(R.id.btn_rule_cancel)
        var btnRuleTip = holder?.getView<TextView>(R.id.btn_rule_tip)
        txtContext?.text = "异常：${context}，请前往配送规则查看解决方案。"
        btnRuleCancel?.setSingleClickListener {
            dismiss()
        }
        btnRuleTip?.setSingleClickListener {
            var orderDisRuleTipDialog = OrderDisRuleTipCheckDialog().newInstance()
            orderDisRuleTipDialog.setRuleTip {
                dismiss()
            }
            orderDisRuleTipDialog.show(this.childFragmentManager)
        }
    }
}