package com.meiling.oms.dialog

import android.graphics.Paint
import android.view.View
import android.view.View.OnClickListener
import android.widget.TextView
import com.meiling.oms.R
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder

//请核对券码验证信息
class CheckCouponInformationDidalog : BaseNiceDialog() {
    init {
        setMargin(30)
        setOutCancel(true)
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_check_coupon_information
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        var tv_original_cost = holder?.getView<TextView>(R.id.tv_original_cost)
        tv_original_cost?.paint?.flags= Paint. STRIKE_THRU_TEXT_FLAG

        holder?.setOnClickListener(
            R.id.btn_cancel_exit
        ) {
            dismiss()
        }
    }
}