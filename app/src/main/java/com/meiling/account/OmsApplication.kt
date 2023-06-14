package com.meiling.account

import com.amap.api.maps.MapsInitializer
import com.blankj.utilcode.util.SPStaticUtils
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
        }
    }

}