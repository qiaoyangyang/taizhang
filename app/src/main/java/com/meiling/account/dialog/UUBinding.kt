package com.meiling.account.dialog

import android.app.Activity
import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.core.widget.addTextChangedListener
import com.meiling.common.utils.InputTextManager
import com.meiling.account.R
import com.meiling.account.widget.CaptchaCountdownTool2
import com.meiling.account.widget.showToast
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

    var getCodeListener: ((phone:String) -> Unit?)? = null
    var sureOnclickListener:((phone:String,code:String)->Unit)?=null

    fun setCodeListener(listener:(phone:String) -> Unit){
        this.getCodeListener=listener
    }

    fun setUUSureOnclickListener(listener: (phone:String,code:String)->Unit){
        this.sureOnclickListener=listener
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
        btn?.setOnClickListener {
            if(!edtUuBindPhone?.text?.trim().toString().isNullOrBlank()){
                if(!isPhoneNumber(edtUuBindPhone?.text?.trim().toString())){
                    showToast("请输入正确手机号")
                    return@setOnClickListener
                }
            }
            if(edtUuBindCode?.text?.trim().toString().isNullOrBlank()){
                showToast("请输入验证码")
                return@setOnClickListener
            }
            sureOnclickListener?.invoke(edtUuBindPhone?.text?.trim().toString(),edtUuBindCode?.text?.trim().toString())
        }
        edtUuBindPhone?.addTextChangedListener {
            if(!it?.toString().isNullOrBlank()){
                txtAuthCode?.visibility= View.VISIBLE
            }
        }


        val captchaCountdownTool: CaptchaCountdownTool2 =
            CaptchaCountdownTool2(object : CaptchaCountdownTool2.CaptchaCountdownListener {
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
                getCodeListener?.invoke(edtUuBindPhone?.text?.trim().toString())
                captchaCountdownTool.startCountdown()
            }
        }




    }

    private fun isPhoneNumber(input: String): Boolean {
        val regex = Regex("^1[3-9]\\d{9}$")
        return regex.matches(input)
    }

}