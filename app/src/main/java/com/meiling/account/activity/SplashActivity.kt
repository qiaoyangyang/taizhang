package com.meiling.account.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.amap.api.maps.MapsInitializer
import com.blankj.utilcode.util.SPStaticUtils
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.common.utils.CrashManagerUtil
import com.meiling.common.utils.MMKVUtils
import com.meiling.account.databinding.ActivitySplashBinding
import com.meiling.account.jpush.AppConfig
import com.meiling.account.jpush.PushHelper
import com.meiling.common.constant.SPConstants
import com.tencent.bugly.crashreport.CrashReport
import com.umeng.message.PushAgent
import kotlinx.coroutines.GlobalScope
import kotlinx.coroutines.delay
import kotlinx.coroutines.launch
import org.android.agoo.huawei.HuaWeiRegister
import org.android.agoo.xiaomi.MiPushRegistar

@SuppressLint("CustomSplashScreen")
class SplashActivity : BaseActivity<BaseViewModel, ActivitySplashBinding>() {
    override fun initView(savedInstanceState: Bundle?) {

        var boolean = MMKVUtils.getBoolean(SPConstants.LOGINSTASTS, false)

        if (boolean &&userStoreList()!=null) {
            finish()

            startActivity(Intent(this, MainActivity::class.java))
            Log.d("qi", "initView:1 ")

        } else {
            Log.d("qi", "initView:kkkkkkk ")
            finish()
            startActivity(Intent(this, LoginActivity::class.java))
        }

    }



    override fun getBind(layoutInflater: LayoutInflater): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun isStatusBarDarkFont(): Boolean {
        return true
    }


}