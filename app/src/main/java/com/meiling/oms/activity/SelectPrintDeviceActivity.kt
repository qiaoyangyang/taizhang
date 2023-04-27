package com.meiling.oms.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.meiling.common.activity.BaseVmDbActivity
import com.meiling.oms.databinding.ActivitySelectPrintDeviceBinding
import com.meiling.oms.viewmodel.RegisterViewModel

/**
 * 选择打印机
 */
class SelectPrintDeviceActivity : BaseVmDbActivity<RegisterViewModel,ActivitySelectPrintDeviceBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun createObserver() {

    }

    override fun getBind(layoutInflater: LayoutInflater): ActivitySelectPrintDeviceBinding {
        return ActivitySelectPrintDeviceBinding.inflate(layoutInflater)
    }

    override fun initData() {
        super.initData()

    }

}
