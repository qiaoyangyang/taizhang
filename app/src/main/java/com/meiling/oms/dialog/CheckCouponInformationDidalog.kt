package com.meiling.oms.dialog

import com.meiling.oms.R
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder

class CheckCouponInformationDidalog : BaseNiceDialog() {
    init {
        setMargin(30)
        setOutCancel(false)
    }
    override fun intLayoutId(): Int {
        return R.layout.dialog_check_coupon_information
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
    }
}