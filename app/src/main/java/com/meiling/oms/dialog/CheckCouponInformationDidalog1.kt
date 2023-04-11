package com.meiling.oms.dialog

import android.graphics.Paint
import android.os.Bundle
import android.text.TextUtils
import android.widget.TextView
import com.meiling.common.network.data.Meituan
import com.meiling.common.network.data.ThrillBen
import com.meiling.common.utils.GsonUtils
import com.meiling.oms.R
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder

//请核对券码验证信息
class CheckCouponInformationDidalog1 : BaseNiceDialog() {
    init {
        setMargin(30)
        setOutCancel(true)
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_check_coupon_information
    }

    fun newInstance(meituan: String): CheckCouponInformationDidalog1 {
        val args = Bundle()
        args.putSerializable("meituan", meituan)
        val dialog = CheckCouponInformationDidalog1()
        dialog.arguments = args
        return dialog
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        var meituan = arguments?.getString("meituan")
        var jsonData = GsonUtils.getJsonData(meituan)
        var person = GsonUtils.getPerson(jsonData, Meituan::class.java)

        holder?.setText(R.id.tv_name, person.dealTitle)
        holder?.setText(R.id.tv_payAmount, person.couponBuyPrice)
        holder?.setText(R.id.tv_num,"顾客共购买${person.count}张")

        var tv_original_cost = holder?.getView<TextView>(R.id.tv_original_cost)
        tv_original_cost?.paint?.flags = Paint.STRIKE_THRU_TEXT_FLAG
        tv_original_cost?.text="¥"+person.dealValue

        holder?.setOnClickListener(
            R.id.btn_cancel_exit
        ) {
            dismiss()
        }

        holder?.setOnClickListener(
            R.id.btn_ok_exit
        ) {
            if (onresilience!=null){

                onresilience?.resilience(person.couponCode!!,person.count!!,person)
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
        fun resilience(encryptedCode:String,count:String,meituan:Meituan)
    }
}