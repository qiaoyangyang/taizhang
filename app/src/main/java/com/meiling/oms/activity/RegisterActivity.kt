package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.databinding.ActivityRegisterBinding
import com.meiling.oms.viewmodel.RegisterViewModel

class RegisterActivity:BaseActivity<RegisterViewModel,ActivityRegisterBinding>(){
    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityRegisterBinding {
        return ActivityRegisterBinding.inflate(layoutInflater)
    }

}