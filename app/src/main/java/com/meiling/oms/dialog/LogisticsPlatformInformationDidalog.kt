package com.meiling.oms.dialog

import android.os.Bundle
import android.text.TextUtils
import android.view.Gravity
import android.view.View
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.meiling.common.network.data.Merchant
import com.meiling.common.view.ClearEditText
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

    var onclickListener:((merchant:Merchant) ->Unit)?=null
    var onGoWebListener:((merchant:Merchant) ->Unit)?=null

    @JvmName("setOnclickListener1")
    fun setOnclickListener(onclickListener:((merchant:Merchant) ->Unit)){
        this.onclickListener=onclickListener
    }

    @JvmName("setOnGoWebListener1")
    fun setOnGoWebListener(onclickListener22:((merchant:Merchant) ->Unit)){
        this.onGoWebListener=onclickListener22
    }

    fun newInstance(merchant:Merchant):LogisticsPlatformInformationDidalog{
        val args = Bundle()
        args?.putSerializable("merchant",merchant)
        var dialog=LogisticsPlatformInformationDidalog()
        dialog.arguments=args
        return dialog
    }


    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        var merchant=arguments?.getSerializable("merchant") as Merchant
        var tv_logistics_business=holder?.getView<TextView>(R.id.tv_logistics_business)//物流商
        var tv_merchant_code=holder?.getView<ClearEditText>(R.id.tv_merchant_code)//物流商平台商户编号
        var tv_merchant_key=holder?.getView<ClearEditText>(R.id.tv_merchant_key)//物流商平台商户key
        var tv_merchant_keys=holder?.getView<ClearEditText>(R.id.tv_merchant_keys)//物流商平台商户密钥
        var conslay=holder?.getView<ConstraintLayout>(R.id.conslay)
        if(merchant.type=="sf_tc"){
            conslay?.visibility= View.GONE
        }else{
            conslay?.visibility= View.VISIBLE
        }
        if(!merchant.thirdMerchantId.isNullOrBlank()){
            tv_merchant_code?.setText(merchant.thirdMerchantId)
        }
        if(!merchant.appSecret.isNullOrBlank()){
            tv_merchant_keys?.setText(merchant.appSecret)
        }
        if(!merchant.appId.isNullOrBlank()){
            tv_merchant_key?.setText(merchant.appId)
        }

        holder?.setOnClickListener(R.id.txtGoWeb){
            onGoWebListener?.invoke(merchant)
        }
        //关闭
        holder?.setOnClickListener(R.id.iv_close_recharge) {
            dismiss()

        }

        tv_logistics_business?.text=merchant.typeName

        holder?.setOnClickListener(R.id.btn_ok_select_time) {
            if (TextUtils.isEmpty(tv_logistics_business?.text.toString())){
                showToast("物流商名字")
                return@setOnClickListener
            }
            if (TextUtils.isEmpty(tv_merchant_code?.text.toString())){
                showToast("请输入物流商平台商户编号")
                return@setOnClickListener
            }
            if(merchant.type!="sf_tc"){
                if (TextUtils.isEmpty(tv_merchant_key?.text.toString())){
                    showToast("请输入物流商平台商户key")
                    return@setOnClickListener
                }
            }

            if (TextUtils.isEmpty(tv_merchant_keys?.text.toString())){
                showToast("请输入物流商平台商户密钥")
                return@setOnClickListener
            }
            var m=merchant.copy()
            m.thirdMerchantId=tv_merchant_code?.text.toString()
            m.appSecret=tv_merchant_keys?.text.toString()
            m.appId=tv_merchant_key?.text.toString()
            m.status="1"
            onclickListener?.invoke(m)
            dismiss()

        }

    }
}