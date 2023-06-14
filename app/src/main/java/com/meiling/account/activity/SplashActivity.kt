package com.meiling.account.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.amap.api.maps.MapsInitializer
import com.blankj.utilcode.util.SPStaticUtils
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.common.utils.CrashManagerUtil
import com.meiling.common.utils.MMKVUtils
import com.meiling.account.databinding.ActivitySplashBinding
import com.meiling.account.dialog.AgreementDialog
import com.meiling.account.jpush.AppConfig
import com.meiling.account.jpush.PushHelper
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
        var agreementDialog = AgreementDialog()

        agreementDialog.setOkClickLister {
            if (it == "1") {
                SPStaticUtils.put("isFirstInstall", true)
                initAndStartSDK()
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
            } else {
                SPStaticUtils.put("isFirstInstall", false)
                finish()
            }
        }
        if (SPStaticUtils.getBoolean("isFirstInstall")) {
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
        } else {
            agreementDialog.show(supportFragmentManager)
        }



    }


    fun initAndStartSDK() {
        MapsInitializer.loadWorldGridMap(true);
        MapsInitializer.setNetWorkEnable(true);
        PushHelper.preInit(AppConfig.getApplication());
        HuaWeiRegister.register(AppConfig.getApplication());
        //建议在线程中执行初始化
        Thread { PushHelper.init(AppConfig.getApplication()) }.start()
        MiPushRegistar.register(AppConfig.getApplication(), "2882303761520240526", "5862024070526", false)
        CrashManagerUtil.getInstance(AppConfig.getApplication()).init()
        CrashReport.initCrashReport(AppConfig.getApplication(), "0e93bafb3e", false);
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivitySplashBinding {
        return ActivitySplashBinding.inflate(layoutInflater)
    }

    override fun isStatusBarDarkFont(): Boolean {
        return true
    }


}