package com.meiling.oms

import com.amap.api.maps.MapsInitializer
import com.meiling.common.BaseApplication
import com.meiling.oms.jpush.AppConfig
import com.meiling.oms.jpush.PushHelper

class OmsApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        MapsInitializer.loadWorldGridMap(true);
        MapsInitializer.setNetWorkEnable(true);
        PushHelper.preInit(this);

        //建议在线程中执行初始化
        Thread { PushHelper.init(this) }.start()
        AppConfig.init(this)
    }

}