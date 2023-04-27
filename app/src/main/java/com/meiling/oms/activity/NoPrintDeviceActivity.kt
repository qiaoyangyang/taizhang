package com.meiling.oms.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.meiling.common.activity.BaseVmDbActivity
import com.meiling.oms.databinding.ActivityNoPrintDeviceBinding
import com.meiling.oms.viewmodel.RegisterViewModel

class NoPrintDeviceActivity : BaseVmDbActivity<RegisterViewModel,ActivityNoPrintDeviceBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun createObserver() {

    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityNoPrintDeviceBinding {
        return ActivityNoPrintDeviceBinding.inflate(layoutInflater)
    }

    override fun initData() {
        super.initData()
        mDatabind.creatStoreBtn.setOnClickListener {
            startActivity(Intent(this,SelectPrintDeviceActivity::class.java))
            finish()
        }
    }

}
