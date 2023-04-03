package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.databinding.ActivityForgetPwdRasetBinding
import com.meiling.oms.viewmodel.LoginViewModel
import com.meiling.oms.widget.setSingleClickListener

@Route(path = "/app/ForgetPwdResetActivity")
class ForgetPwdResetActivity : BaseActivity<LoginViewModel, ActivityForgetPwdRasetBinding>() {
    override fun initView(savedInstanceState: Bundle?) {

    }


    override fun initData() {

    }


    override fun initListener() {
        mDatabind.btnNext.setSingleClickListener {
            ARouter.getInstance().build("/app/ForgetPwdFinishActivity").navigation()
        }
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityForgetPwdRasetBinding {
        return ActivityForgetPwdRasetBinding.inflate(layoutInflater)
    }
}