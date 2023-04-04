package com.meiling.oms.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.launcher.ARouter
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.common.utils.MMKVUtils
import com.meiling.oms.databinding.ActivitySplashBinding

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<BaseViewModel, ActivitySplashBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        if (MMKVUtils.getBoolean("isLogin")) {
            ARouter.getInstance().build("/app/MainActivity").navigation()
            finish()
        } else {
            ARouter.getInstance().build("/app/LoginActivity").navigation()
            finish()

        }
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

}