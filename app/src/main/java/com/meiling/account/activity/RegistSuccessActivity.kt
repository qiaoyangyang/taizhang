package com.meiling.account.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.meiling.common.activity.BaseActivity
import com.meiling.account.databinding.RegistSuccessLayoutBinding
import com.meiling.account.viewmodel.RegistSuccessViewModel

class RegistSuccessActivity :BaseActivity<RegistSuccessViewModel,RegistSuccessLayoutBinding>(){
    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun getBind(layoutInflater: LayoutInflater): RegistSuccessLayoutBinding {
        return RegistSuccessLayoutBinding.inflate(layoutInflater)
    }

}