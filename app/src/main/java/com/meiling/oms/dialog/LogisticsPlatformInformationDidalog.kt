package com.meiling.oms.dialog

import android.text.TextUtils
import android.view.Gravity
import android.widget.TextView
import com.meiling.oms.R
import com.meiling.oms.widget.showToast
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder

//填写物流平台信息
class LogisticsPlatformInformationDidalog : BaseNiceDialog() {
    init {
        setGravity(Gravity.BOTTOM)
        setOutCancel(false)
    }
    override fun intLayoutId(): Int {
        return R.layout.dialog_logistics_platform_information;
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        var tv_logistics_business=holder?.getView<TextView>(R.id.tv_logistics_business)//物流商
        var tv_merchant_code=holder?.getView<TextView>(R.id.tv_merchant_code)//物流商平台商户编号
        var tv_merchant_key=holder?.getView<TextView>(R.id.tv_merchant_key)//物流商平台商户key
        var tv_merchant_keys=holder?.getView<TextView>(R.id.tv_merchant_keys)//物流商平台商户密钥
        //关闭
        holder?.setOnClickListener(R.id.iv_close_recharge) {
            dismiss()

        }
        holder?.setOnClickListener(R.id.btn_ok_select_time) {
            if (TextUtils.isEmpty(tv_logistics_business?.text.toString())){
                showToast("物流商名字")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(tv_merchant_code?.text.toString())){
                showToast("请输入物流商平台商户编号")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(tv_merchant_key?.text.toString())){
                showToast("请输入物流商平台商户key")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(tv_merchant_keys?.text.toString())){
                showToast("请输入物流商平台商户密钥")
                return@setOnClickListener
            }
            dismiss()

        }

    }
}