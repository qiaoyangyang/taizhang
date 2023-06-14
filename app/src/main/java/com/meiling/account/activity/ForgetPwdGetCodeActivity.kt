package com.meiling.account.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.meiling.common.activity.BaseActivity
import com.meiling.common.utils.SpannableUtils
import com.meiling.account.R
import com.meiling.account.databinding.ActivityForgetPwdGetCodeBinding
import com.meiling.account.viewmodel.LoginViewModel
import com.meiling.account.widget.CaptchaCountdownTool
import com.meiling.account.widget.setSingleClickListener
import com.meiling.account.widget.showToast

@Route(path = "/app/ForgetPwdGetCodeActivity")
class ForgetPwdGetCodeActivity : BaseActivity<LoginViewModel, ActivityForgetPwdGetCodeBinding>() {

    private var captchaCountdownTool =
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

    override fun isStatusBarDarkFont(): Boolean {
        return true
    }

    override fun initView(savedInstanceState: Bundle?) {

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
                mViewModel.businessCode(phoneSend, mDatabind.edtCode.text.trim().toString())

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
            showToast("验证码发送成功")
        }
        mViewModel.sendCode.onError.observe(this) {
            disLoading()
            captchaCountdownTool.stopCountdown()
            mDatabind.txtAuthCode.isClickable = true
            mDatabind.txtAuthCode.text = "获取验证码"
            showToast("${it.msg}")
        }

        mViewModel.repData.onStart.observe(this) {
            showLoading("请求中")
        }

        mViewModel.repData.onSuccess.observe(this) {
            disLoading()
            captchaCountdownTool.stopCountdown()
            mDatabind.txtAuthCode.isClickable = true
            mDatabind.txtAuthCode.text = "重新获取"
            ARouter.getInstance().build("/app/ForgetPwdResetActivity")
                .withString("account", account).withString("phone", phoneSend)
                .withString("code", mDatabind.edtCode.text.trim().toString())
                .withString("title", "忘记密码")
                .withString("context", "修改完成").navigation()
        }
        mViewModel.repData.onError.observe(this) {
            disLoading()
            showToast("${it.msg}")
        }


    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityForgetPwdGetCodeBinding {
        return ActivityForgetPwdGetCodeBinding.inflate(layoutInflater)
    }
}