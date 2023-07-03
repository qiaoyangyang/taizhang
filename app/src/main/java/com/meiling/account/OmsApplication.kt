package com.meiling.account

import android.util.Log
import com.amap.api.maps.MapsInitializer
import com.blankj.utilcode.util.SPStaticUtils
import com.meiling.common.network.NetworkMonitorManager
import com.meiling.common.BaseApplication
import com.meiling.common.utils.CrashManagerUtil
import com.meiling.account.jpush.AppConfig
import com.meiling.account.jpush.PushHelper
import com.tencent.bugly.crashreport.CrashReport
import org.android.agoo.huawei.HuaWeiRegister
import org.android.agoo.xiaomi.MiPushRegistar

class OmsApplication : BaseApplication() {
    override fun onCreate() {
        super.onCreate()
        AppConfig.init(this)
        NetworkMonitorManager.getInstance().init(this, 1_500)
        if (SPStaticUtils.getBoolean("isFirstInstall")) {
            MapsInitializer.loadWorldGridMap(true);
            MapsInitializer.setNetWorkEnable(true);
            PushHelper.preInit(this);
            HuaWeiRegister.register(this);
            //建议在线程中执行初始化
            Thread { PushHelper.init(this) }.start()
            MiPushRegistar.register(this, "2882303761520240526", "5862024070526", false)
            CrashManagerUtil.getInstance(this).init()
            CrashReport.initCrashReport(this, "0e93bafb3e", false);

            /**
             * 初始化
             * @param application 上下文
             * @param jitterTime 设置抖动时间(即多久之后网络状态没有发生变化，才回调网络状态，单位：毫秒，默认1500毫秒)
             */
            Log.d("yjk", "onCreate: ")

        }
    }

}