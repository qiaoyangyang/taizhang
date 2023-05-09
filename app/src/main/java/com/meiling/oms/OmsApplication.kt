package com.meiling.oms

import com.amap.api.maps.MapsInitializer
import com.meiling.common.BaseApplication
import com.meiling.oms.jpush.AppConfig
import com.meiling.oms.jpush.PushHelper
import org.android.agoo.huawei.HuaWeiRegister
import org.android.agoo.xiaomi.MiPushRegistar

class OmsApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        MapsInitializer.loadWorldGridMap(true);
        MapsInitializer.setNetWorkEnable(true);
        PushHelper.preInit(this);
        HuaWeiRegister.register(this);
        //建议在线程中执行初始化
        Thread { PushHelper.init(this) }.start()
        AppConfig.init(this)
        MiPushRegistar.register(this, "2882303761520240526", "5862024070526", false);
    }

}