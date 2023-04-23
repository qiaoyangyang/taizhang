package com.meiling.oms.dialog

import android.graphics.Paint
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.View
import android.view.View.OnClickListener
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
        setOutCancel(false)
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
        var stock_add_jia = holder?.getView<TextView>(R.id.stock_add_jia)
        holder?.setText(R.id.tv_name, person.dealTitle)
        holder?.setText(R.id.tv_payAmount, person.couponBuyPrice)
        holder?.setText(R.id.tv_num, "顾客共购买${person.count}张")


        var stock_add_num = holder?.getView<TextView>(R.id.stock_add_num)

        stock_add_num?.text = person.count

        isadd(stock_add_num?.text.toString(), stock_add_jia!!, person?.count!!)
        //加
        holder?.setOnClickListener(R.id.stock_add_jia) {
            var count1 = holder.getView<TextView>(R.id.stock_add_num).text.toString()

            if (count1 == person.count) {
                return@setOnClickListener
            }


            var i = count1.toInt() + 1
            holder?.setText(R.id.stock_add_num, i.toString())

        }
        //减
        holder?.setOnClickListener(R.id.stock_add_jian) {
            var count = holder.getView<TextView>(R.id.stock_add_num).text.toString()

            if (count == "1") {
                return@setOnClickListener
            }


            var i = count.toInt() - 1
            holder?.setText(R.id.stock_add_num, i.toString())

        }

        stock_add_num?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (!TextUtils.isEmpty(s)) {

                    isadd(s.toString(), stock_add_jia, person?.count!!)
                }

            }

        })

        var tv_original_cost = holder?.getView<TextView>(R.id.tv_original_cost)
        tv_original_cost?.paint?.flags = Paint.STRIKE_THRU_TEXT_FLAG
        tv_original_cost?.text = "原价:" + person.dealValue

        holder?.setOnClickListener(
            R.id.btn_cancel_exit
        ) {
            dismiss()
        }

        holder?.setOnClickListener(
            R.id.btn_ok_exit
        ) {
            if (onresilience != null) {

                onresilience?.resilience(
                    person.couponCode!!,
                    stock_add_num?.text.toString(),
                    person
                )
                dismiss()
            }
        }

    }

    fun setencryptedCode(thrillBen: ArrayList<ThrillBen>, int: Int): String {
        var encryptedCode = ""
        thrillBen.forEach {
            if (TextUtils.isEmpty(encryptedCode)) {
                encryptedCode = it?.encryptedCode!!
            }
        }

        return encryptedCode

    }

    fun isadd(S: String, textView: TextView, count: String) {
        if (S == count) {
            textView.setBackgroundResource(R.drawable.jiad)
        } else {
            textView.setBackgroundResource(R.drawable.jiaz)
        }
    }

    fun setOnresilience(onresilience: Onresilience) {
        this.onresilience = onresilience
    }

    private var onresilience: Onresilience? = null

    interface Onresilience {
        fun resilience(encryptedCode: String, count: String, meituan: Meituan)
    }
}