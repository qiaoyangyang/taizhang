package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.databinding.RegistSuccessLayoutBinding
import com.meiling.oms.viewmodel.RegistSuccessViewModel

class RegistSuccessActivity :BaseActivity<RegistSuccessViewModel,RegistSuccessLayoutBinding>(){
    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun getBind(layoutInflater: LayoutInflater): RegistSuccessLayoutBinding {
        return RegistSuccessLayoutBinding.inflate(layoutInflater)
    }

}