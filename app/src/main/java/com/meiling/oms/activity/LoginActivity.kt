package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.inputmethod.EditorInfo
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
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

@Route(path = "/app/LoginActivity")
class LoginActivity : BaseActivity<BaseViewModel, ActivityLoginBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.btnLogin?.let {
            InputTextManager.with(this)
                .addView(mDatabind.etPhone)
                .addView(mDatabind.etPassword)
                .setMain(it)
                .build()
        }
        var conet = "登录即代表同意《美零云店用户协议及隐私政策》"
        SpannableUtils.setTextcolor(conet, mDatabind.tvAgreement, 7, conet.length)
        mDatabind.tvTitle.text = "账号密码登录"
        Loginswitch(0)
        mDatabind.tvOtherLoginMethods.setOnClickListener {
            if (mDatabind.tvTitle.text.toString()=="账号密码登录") {
                Loginswitch(0)
            }else{
                Loginswitch(1)
            }
        }

    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }


    //  登陆切换
    private fun Loginswitch(type:Int) {


        if (type==0) {
            mDatabind.tvTitle.text = "验证码登录"
            mDatabind.etPassword.hint = "请输入验证码"
            mDatabind.etPhone.hint = "请输入账号"


            mDatabind.tvOtherLoginMethods.text = "账号登录"
            mDatabind.ivPhone.setBackgroundResource(R.drawable.iv_phone)
            mDatabind.ivPassword.setBackgroundResource(R.drawable.ic_password)
            TextDrawableUtils.setTopDrawable(mDatabind.tvOtherLoginMethods, R.drawable.ic_account)

        } else {
            TextDrawableUtils.setTopDrawable(mDatabind.tvOtherLoginMethods, R.drawable.phone_log)
            mDatabind.etPassword.hint = "请输入密码"
            mDatabind.etPhone.hint = "请输入手机号"
            mDatabind.tvTitle.text = "账号密码登录"
            mDatabind.tvOtherLoginMethods.text = "手机登录"
            mDatabind.etPhone.setInputRegex(REGEX_MOBILE)
            mDatabind.etPhone.setInputType(EditorInfo.TYPE_CLASS_PHONE)
            mDatabind.ivPhone.setBackgroundResource(R.drawable.login_phone)
            mDatabind.ivPassword.setBackgroundResource(R.drawable.ic_password)
        }

    }

}