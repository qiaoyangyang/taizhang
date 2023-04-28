package com.meiling.oms.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.launcher.ARouter
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.common.utils.MMKVUtils
import com.meiling.oms.databinding.ActivitySplashBinding
import com.umeng.message.PushAgent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<BaseViewModel, ActivitySplashBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
//
        val job = GlobalScope.launch {
            delay(2000)
            if (MMKVUtils.getBoolean("isLogin")) {
                var intent = Intent(this@SplashActivity, MainActivity::class.java)
                startActivity(intent)
                //  ARouter.getInstance().build("/app/MainActivity").navigation()
                finish()
            } else {
                var intent = Intent(this@SplashActivity, LoginActivity::class.java)
                startActivity(intent)
                // ARouter.getInstance().build("/app/LoginActivity").navigation()
                finish()

            }

        }

        //友盟统计用户活跃度
        PushAgent.getInstance(this).onAppStart();
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun isStatusBarDarkFont(): Boolean {
        return true
    }


}