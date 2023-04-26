package com.meiling.oms.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.service.loginService
import com.meiling.common.utils.InputTextManager
import com.meiling.oms.databinding.ActivityRegisterBinding
import com.meiling.oms.viewmodel.RegisterViewModel
import com.meiling.oms.widget.CaptchaCountdownTool
import com.meiling.oms.widget.showToast

class RegisterActivity : BaseActivity<RegisterViewModel, ActivityRegisterBinding>() {
    private val captchaCountdownTool: CaptchaCountdownTool =
        CaptchaCountdownTool(object : CaptchaCountdownTool.CaptchaCountdownListener {
            override fun onCountdownTick(countDownText: String) {
                mDatabind.txtAuthCode.text = "$countDownText s"
                mDatabind.txtAuthCode.isClickable = false
            }

            override fun onCountdownFinish() {
                mDatabind.txtAuthCode.isClickable = true
                mDatabind.txtAuthCode.text = "重新获取"
            }
        })

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {
        super.initData()
        mDatabind.btnLogin.let {
            InputTextManager.with(this)
                .addView(mDatabind.etPhone)
                .addView(mDatabind.etPassword)
                .setMain(it)
                .build()
        }

        mDatabind.etPhone.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().isNotEmpty()) {
                    mDatabind.ivClearAccount.visibility = View.VISIBLE
                } else {
                    mDatabind.ivClearAccount.visibility = View.GONE
                }
            }

        })

        mDatabind.ivClearAccount.setOnClickListener {
            mDatabind.etPhone.setText("")
        }

        mDatabind.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().isNotEmpty()) {
                    mDatabind.ivClearPwd.visibility = View.VISIBLE
                } else {
                    mDatabind.ivClearPwd.visibility = View.GONE
                }
            }

        })
        mDatabind.ivClearPwd.setOnClickListener {
            mDatabind.etPassword.setText("")
        }


        mDatabind.txtAuthCode.setOnClickListener {
            if (mDatabind.etPhone.text.toString().trim().isNullOrBlank()) {
                showToast("登录账号未填写")
                return@setOnClickListener
            }
            if (!isPhoneNumber(mDatabind.etPhone.text.toString().trim())) {
                showToast("请输入正确手机号")
                return@setOnClickListener
            }
            showLoading("")
            mViewModel.launchRequest({
                loginService.sendCode(mDatabind.etPhone.text.toString(), "3")
            }, onSuccess = {
                captchaCountdownTool.startCountdown()
                disLoading()
            }, onError = {
                captchaCountdownTool.stopCountdown()
                mDatabind.txtAuthCode.isClickable = true
                mDatabind.txtAuthCode.text = "重新获取"
                disLoading()
                it?.let { it1 -> showToast(it1) }
            })
        }

        mDatabind.btnLogin.setOnClickListener {
            if (mDatabind.etPhone.text.toString().trim().isNullOrBlank()) {
                showToast("登录账号未填写")
                return@setOnClickListener
            }
            if (!isPhoneNumber(mDatabind.etPhone.text.toString().trim())) {
                showToast("请输入正确手机号")
                return@setOnClickListener
            }
            if (mDatabind.etPassword.text.toString().trim().isNullOrBlank()) {
                showToast("验证码未填写")
                return@setOnClickListener
            }
            showLoading("")
            mViewModel.launchRequest(
                {
                    loginService.businessCode(
                        mDatabind.etPhone.text.toString().trim(),
                        mDatabind.etPassword.text.toString().trim())
                }, onSuccess =  {
                    disLoading()
                    startActivity(Intent(this,RegisterNextActivity::class.java).putExtra("phone",mDatabind.etPhone.text.toString().trim()))
                }, onError = {
                    disLoading()
                    mDatabind.etPassword.text.toString().trim()
                    if (it != null) {
                        showToast(it)
                    }
                }
            )
        }
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityRegisterBinding {
        return ActivityRegisterBinding.inflate(layoutInflater)
    }

    private fun isPhoneNumber(input: String): Boolean {
        val regex = Regex("^1[3-9]\\d{9}$")
        return regex.matches(input)
    }
}
