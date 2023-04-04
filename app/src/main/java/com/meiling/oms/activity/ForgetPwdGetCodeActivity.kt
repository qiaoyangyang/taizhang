package com.meiling.oms.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.meiling.common.activity.BaseActivity
import com.meiling.common.utils.SpannableUtils
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityForgetPwdGetCodeBinding
import com.meiling.oms.viewmodel.LoginViewModel
import com.meiling.oms.widget.CaptchaCountdownTool
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast

@Route(path = "/app/ForgetPwdGetCodeActivity")
class ForgetPwdGetCodeActivity : BaseActivity<LoginViewModel, ActivityForgetPwdGetCodeBinding>() {

    private lateinit var captchaCountdownTool: CaptchaCountdownTool
    override fun isStatusBarDarkFont(): Boolean {
        return true
    }
    override fun initView(savedInstanceState: Bundle?) {
        captchaCountdownTool =
            CaptchaCountdownTool(object : CaptchaCountdownTool.CaptchaCountdownListener {
                override fun onCountdownTick(countDownText: String) {
                    mDatabind.txtAuthCode.text = "$countDownText s"
                    mDatabind.txtAuthCode.isClickable = false
                }

                override fun onCountdownFinish() {
                    mDatabind.txtAuthCode.text = "重新获取"
                    mDatabind.txtAuthCode.isClickable = true
                }
            })
    }

    private fun hidePhoneNumber(phoneNumber: String): String {
        val startIndex = 3
        val endIndex = 7
        val asterisks = "*".repeat(endIndex - startIndex)
        return phoneNumber.replaceRange(startIndex, endIndex, asterisks)
    }

    private var phoneSend = ""
    var account = ""
    override fun initData() {
        account = intent.getStringExtra("account").toString()
        val phone = intent.getStringExtra("phone")
        val phoneCenter = intent.getStringExtra("phone_center")
        phoneSend = phone!!.replace(
            "****", phoneCenter!!
        )

        var conet = "验证码将发送到绑定手机号 $phone"
        SpannableUtils.setTextcolor(
            this,
            conet,
            mDatabind.txtTipPhone,
            12,
            conet.length,
            R.color.red
        )

    }

    override fun initListener() {
        mDatabind.edtCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().isNotEmpty()) {
                    mDatabind.ivCleanShow.visibility = View.VISIBLE
                } else {
                    mDatabind.ivCleanShow.visibility = View.GONE
                }
            }
        })
        mDatabind.ivCleanShow.setSingleClickListener {
            mDatabind.edtCode.setText("")
        }
        mDatabind.btnNext.setSingleClickListener {
            if (mDatabind.edtCode.text.trim().toString().isNotEmpty()) {
                captchaCountdownTool.stopCountdown()
                ARouter.getInstance().build("/app/ForgetPwdResetActivity").withString("account",account).withString("phone",phoneSend).withString("code",mDatabind.edtCode.text.trim().toString()).navigation()
            } else {
                showToast("请输入验证码")
            }
        }
        mDatabind.txtAuthCode.setSingleClickListener {
            mViewModel.sendCode(phoneSend)
            captchaCountdownTool.startCountdown()
        }
    }

    override fun createObserver() {
        mViewModel.sendCode.onStart.observe(this) {
            showLoading("发送中")
        }
        mViewModel.sendCode.onSuccess.observe(this) {
            disLoading()
        }
        mViewModel.sendCode.onError.observe(this) {
            disLoading()
            showToast("${it.message}")
        }


    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityForgetPwdGetCodeBinding {
        return ActivityForgetPwdGetCodeBinding.inflate(layoutInflater)
    }
}