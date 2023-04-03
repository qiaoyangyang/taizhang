package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
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

    override fun initView(savedInstanceState: Bundle?) {
        captchaCountdownTool =
            CaptchaCountdownTool(object : CaptchaCountdownTool.CaptchaCountdownListener {
                override fun onCountdownTick(countDownText: String) {
                    mDatabind.txtAuthCode.text = "$countDownText s"
                }

                override fun onCountdownFinish() {
                    mDatabind.txtAuthCode.text = "重新获取"
                }
            })
    }

    private fun hidePhoneNumber(phoneNumber: String): String {
        val startIndex = 3
        val endIndex = 7
        val asterisks = "*".repeat(endIndex - startIndex)
        return phoneNumber.replaceRange(startIndex, endIndex, asterisks)
    }
    var phoneSend = ""
    override fun initData() {
        val phone = intent.getStringExtra("phone")
        val phoneCenter = intent.getStringExtra("phone_center")
        phoneSend = phone!!.replace("****", phoneCenter!!
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
        mDatabind.btnNext.setSingleClickListener {
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
            showToast("验证码发送成功")
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