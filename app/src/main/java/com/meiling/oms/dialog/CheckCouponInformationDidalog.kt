package com.meiling.oms.dialog

import android.graphics.Paint
import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView
import com.meiling.common.network.data.ThrillBen
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

    fun newInstance(thrillBen: ArrayList<ThrillBen>): CheckCouponInformationDidalog {
        val args = Bundle()
        args.putSerializable("ThrillBen", thrillBen)
        val dialog = CheckCouponInformationDidalog()
        dialog.arguments = args
        return dialog
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        var thrillBen = arguments?.getSerializable("ThrillBen") as ArrayList<ThrillBen>

        holder?.setText(R.id.tv_name, thrillBen.get(0).sku?.title)
        holder?.setText(R.id.tv_payAmount, thrillBen.get(0).amount?.payAmount)
        holder?.setText(R.id.tv_num,"顾客共购买${thrillBen.size}张")

        var tv_original_cost = holder?.getView<TextView>(R.id.tv_original_cost)
        tv_original_cost?.paint?.flags = Paint.STRIKE_THRU_TEXT_FLAG
        tv_original_cost?.text=thrillBen.get(0).amount?.originalAmount

        holder?.setOnClickListener(
            R.id.btn_cancel_exit
        ) {
            dismiss()
        }

        holder?.setOnClickListener(
            R.id.btn_ok_exit
        ) {
            if (onresilience!=null){

                onresilience?.resilience(setencryptedCode(thrillBen,1))
                dismiss()
            }
        }

    }
    fun setencryptedCode(thrillBen:ArrayList<ThrillBen>,int: Int):String{
        var encryptedCode=""
        thrillBen.forEach {
            if (TextUtils.isEmpty(encryptedCode)){
                encryptedCode=it?.encryptedCode!!
            }
        }

        return encryptedCode

    }
    fun setOnresilience(onresilience: Onresilience) {
        this.onresilience = onresilience
    }

    private var onresilience: Onresilience? = null

    interface Onresilience {
        fun resilience(encryptedCode:String)
    }
}