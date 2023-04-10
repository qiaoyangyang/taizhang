package com.meiling.oms

import com.amap.api.maps.MapsInitializer
import com.meiling.common.BaseApplication

class OmsApplication : BaseApplication() {

    override fun onCreate() {
        super.onCreate()
        MapsInitializer.loadWorldGridMap(true);
        MapsInitializer.setNetWorkEnable(true);
    }


}