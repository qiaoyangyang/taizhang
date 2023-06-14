package com.meiling.account.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.meiling.common.activity.BaseVmDbActivity
import com.meiling.account.databinding.ActivitySelectPrintDeviceBinding
import com.meiling.account.viewmodel.RegisterViewModel

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
        mDatabind.btnPrintBind.setOnClickListener {
            startActivity(Intent(this,BindPrintDeviceActivity::class.java).putExtra("name","芯烨云"))
        }
        mDatabind.btnPrintBind2.setOnClickListener {
            startActivity(Intent(this,BindPrintDeviceActivity::class.java).putExtra("name","佳博云"))
        }
        mDatabind.btnPrintBind3.setOnClickListener {
            startActivity(Intent(this,BindPrintDeviceActivity::class.java).putExtra("name","商米云"))
        }
    }
    override fun isStatusBarDarkFont(): Boolean {
        return false
    }

}
