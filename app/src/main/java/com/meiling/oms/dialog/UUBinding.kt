package com.meiling.oms.dialog

import android.app.Activity
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.meiling.common.utils.InputTextManager
import com.meiling.oms.R
import com.meiling.oms.widget.CaptchaCountdownTool
import com.meiling.oms.widget.showToast
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder

class UUBinding: BaseNiceDialog() {
    init {
        setGravity(Gravity.BOTTOM)
        setOutCancel(false)
    }
    override fun intLayoutId(): Int {
        return R.layout.dialog_uu_binding
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {



        var btn=holder?.getView<Button>(R.id.btn_uu_sure)
        var txtAuthCode=holder?.getView<TextView>(R.id.txt_auth_code)
        var edtUuBindCode=holder?.getView<EditText>(R.id.edt_uu_bind_code)
        var edtUuBindPhone=holder?.getView<EditText>(R.id.edt_uu_bind_phone)
        var ivCloseRecharge=holder?.getView<ImageView>(R.id.iv_close_recharge)
        ivCloseRecharge?.setOnClickListener{
            dismiss()
        }
        btn?.let {
            InputTextManager.with(context as Activity)
                .addView(edtUuBindCode)
                .addView(edtUuBindPhone)
                .setMain(it)
                .build()
        }
        edtUuBindPhone?.addTextChangedListener {
            if(!it?.toString().isNullOrBlank()){
                txtAuthCode?.visibility= View.VISIBLE
            }
        }


        val captchaCountdownTool: CaptchaCountdownTool =
            CaptchaCountdownTool(object : CaptchaCountdownTool.CaptchaCountdownListener {
                override fun onCountdownTick(countDownText: String) {
                    txtAuthCode?.text = "$countDownText s"
                    txtAuthCode?.isClickable = false
                }

                override fun onCountdownFinish() {
                    txtAuthCode?.isClickable = true
                    txtAuthCode?.text = "重新获取"
                }
            })

        txtAuthCode?.setOnClickListener {
            if(!edtUuBindPhone?.text?.trim().toString().isNullOrBlank()){
                if(!isPhoneNumber(edtUuBindPhone?.text?.trim().toString())){
                    showToast("请输入正确手机号")
                    return@setOnClickListener
                }
                captchaCountdownTool.startCountdown()
            }
        }




    }

    private fun isPhoneNumber(input: String): Boolean {
        val regex = Regex("^1[3-9]\\d{9}$")
        return regex.matches(input)
    }

}