package com.meiling.account.dialog

import android.view.Gravity
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import com.meiling.account.R
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder

/**
 * 达达充值输入金额
 */
class DadaRechargeDialog : BaseNiceDialog() {
    init {
        setGravity(Gravity.BOTTOM)
        setOutCancel(false)
    }
    override fun intLayoutId(): Int {
        return R.layout.dialog_dada_recharge
    }

    var sureOnclickListener:((money:String)->Unit)?=null
    fun setMySureOnclickListener(listener: (money:String)->Unit){
        this.sureOnclickListener=listener
    }


    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        var btn=holder?.getView<Button>(R.id.btn_uu_sure)
        var txtMoney=holder?.getView<EditText>(R.id.txtMoney)
        var ivCloseRecharge=holder?.getView<ImageView>(R.id.iv_close_recharge)
        ivCloseRecharge?.setOnClickListener { dismiss() }
        btn?.setOnClickListener {
            sureOnclickListener?.invoke(txtMoney?.text.toString().trim())
        }

    }
}