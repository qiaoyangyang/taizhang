package com.meiling.account.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.meiling.account.databinding.ActivityLoginBinding
import com.meiling.account.databinding.ActivityMainBinding
import com.meiling.account.viewmodel.MainViewModel
import com.meiling.common.activity.BaseActivity

//登陆页面
class LoginActivity : BaseActivity<MainViewModel, ActivityLoginBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }
}