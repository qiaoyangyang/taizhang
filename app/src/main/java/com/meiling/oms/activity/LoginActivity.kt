package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.common.utils.InputTextManager
import com.meiling.common.utils.SpannableUtils
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
        var conet="登录即代表同意《美零云店用户协议及隐私政策》"
        SpannableUtils.setTextcolor(conet,mDatabind.tvAgreement,7,conet.length)

    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

}