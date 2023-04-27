package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.meiling.common.activity.BaseVmDbActivity
import com.meiling.oms.databinding.ActivityBindPrintDeviceBinding
import com.meiling.oms.viewmodel.RegisterViewModel

/**
 * 绑定打印机页面
 */
class BindPrintDeviceActivity : BaseVmDbActivity<RegisterViewModel,ActivityBindPrintDeviceBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun createObserver() {

    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityBindPrintDeviceBinding {
        return ActivityBindPrintDeviceBinding.inflate(layoutInflater)
    }

    override fun initData() {
        super.initData()

    }

}
