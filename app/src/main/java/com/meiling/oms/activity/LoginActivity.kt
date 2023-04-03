package com.meiling.oms.activity

import android.os.Bundle
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import android.view.inputmethod.EditorInfo
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.hjq.bar.TitleBar
import com.hjq.widget.view.RegexEditText.Companion.REGEX_MOBILE
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.common.utils.InputTextManager
import com.meiling.common.utils.SpannableUtils
import com.meiling.common.utils.TextDrawableUtils
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityLoginBinding
import com.meiling.oms.widget.MMKVUtils
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast

@Route(path = "/app/LoginActivity")
class LoginActivity : BaseActivity<BaseViewModel, ActivityLoginBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        var conet = "登录即代表同意《美零云店用户协议及隐私政策》"
        SpannableUtils.setTextcolor(conet, mDatabind.tvAgreement, 7, conet.length)
        mDatabind.tvTitle.text = "账号密码登录"
        mDatabind.cbPwdShow.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mDatabind.etPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
            } else {
                mDatabind.etPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            }
            mDatabind.etPassword.text?.length?.let { mDatabind.etPassword.setSelection(it) }
        }
        var isAgreement = false
        mDatabind.cbAgreementv.setOnCheckedChangeListener { buttonView, isChecked ->
            isAgreement = isChecked
        }
        loginSwitch(1)
        mDatabind.tvOtherLoginMethods.setOnClickListener {
            if (mDatabind.tvTitle.text.toString() == "账号密码登录") {
                loginSwitch(0)
            } else {
                loginSwitch(1)
            }
        }

        mDatabind.btnLogin.let {
            InputTextManager.with(this)
                .addView(mDatabind.etPhone)
                .addView(mDatabind.etPassword)
                .setMain(it)
                .build()
        }
        mDatabind.btnLogin.setSingleClickListener {
            if (!isAgreement) {
                showToast("请同意并勾选用户协议和隐私政策")
            } else {
                MMKVUtils.putBoolean("isLogin", true)
                ARouter.getInstance().build("/app/MainActivity").navigation()
                finish()
            }
        }

    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }




    //  登陆切换
    private fun loginSwitch(type: Int) {
        if (type == 0) {
            mDatabind.tvTitle.text = "验证码登录"
            mDatabind.etPassword.hint = "请输入验证码"
            mDatabind.etPhone.hint = "请输入手机号"
            mDatabind.tvOtherLoginMethods.text = "账号登录"
            mDatabind.ivPhone.setBackgroundResource(R.drawable.iv_phone)
            mDatabind.ivPassword.setBackgroundResource(R.drawable.ic_password)
            mDatabind.cbPwdShow.visibility = View.GONE
            mDatabind.txtAuthCode.visibility = View.VISIBLE
            mDatabind.etPassword.inputType = EditorInfo.TYPE_CLASS_NUMBER
            mDatabind.etPhone.setInputRegex(REGEX_MOBILE)
            mDatabind.etPhone.inputType = EditorInfo.TYPE_CLASS_PHONE
            TextDrawableUtils.setTopDrawable(mDatabind.tvOtherLoginMethods, R.drawable.ic_account)
        } else {
            TextDrawableUtils.setTopDrawable(mDatabind.tvOtherLoginMethods, R.drawable.phone_log)
            mDatabind.etPassword.hint = "请输入密码"
            mDatabind.etPhone.hint = "请输入账号"
            mDatabind.tvTitle.text = "账号密码登录"
            mDatabind.tvOtherLoginMethods.text = "手机登录"
            mDatabind.cbPwdShow.visibility = View.VISIBLE
            mDatabind.txtAuthCode.visibility = View.INVISIBLE
            mDatabind.etPhone.inputType = EditorInfo.TYPE_CLASS_TEXT
            mDatabind.etPassword.inputType  = EditorInfo.TYPE_TEXT_VARIATION_PASSWORD
            mDatabind.ivPhone.setBackgroundResource(R.drawable.login_phone)
            mDatabind.ivPassword.setBackgroundResource(R.drawable.ic_password)
        }
    }

}