package com.meiling.oms.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import androidx.core.widget.addTextChangedListener
import com.meiling.common.activity.BaseActivity
import com.meiling.common.activity.BaseVmActivity
import com.meiling.common.activity.BaseVmDbActivity
import com.meiling.common.network.service.loginService
import com.meiling.common.utils.InputTextManager
import com.meiling.oms.databinding.ActivityRegisterBinding
import com.meiling.oms.databinding.ActivityRegisterNextBinding
import com.meiling.oms.viewmodel.RegisterViewModel
import com.meiling.oms.widget.CaptchaCountdownTool
import com.meiling.oms.widget.showToast

class RegisterNextActivity : BaseActivity<RegisterViewModel, ActivityRegisterNextBinding>() {

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {
        super.initData()






    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityRegisterNextBinding {
        return ActivityRegisterNextBinding.inflate(layoutInflater)
    }


}
