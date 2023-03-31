package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.databinding.ActivityLoginBinding
import com.meiling.oms.widget.MMKVUtils
import com.meiling.oms.widget.setSingleClickListener

@Route(path = "/app/LoginActivity")
class LoginActivity : BaseActivity<BaseViewModel, ActivityLoginBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.btnLogin.setSingleClickListener {
            MMKVUtils.putBoolean("isLogin", true)
            ARouter.getInstance().build("/app/MainActivity").navigation()
            finish()
        }

    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

}