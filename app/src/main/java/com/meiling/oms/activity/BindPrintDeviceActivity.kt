package com.meiling.oms.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.huawei.hms.hmsscankit.ScanKitActivity
import com.meiling.common.activity.BaseVmDbActivity
import com.meiling.oms.databinding.ActivityBindPrintDeviceBinding
import com.meiling.oms.dialog.SelectPrintPageSizeDialog
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
        var name=intent.getStringExtra("name")
        mDatabind.txtPinPai2.text=name
        mDatabind.scanLin.setOnClickListener {
            startActivity(Intent(this,ScanKitActivity::class.java))
        }
        mDatabind.selectShop.setOnClickListener {
            var selectPrintPageSizeDialog= SelectPrintPageSizeDialog()
            selectPrintPageSizeDialog.setSelectPageClick {
                it?.let {

                }
            }
            selectPrintPageSizeDialog.show(supportFragmentManager)
        }

        mDatabind.txtPrintWidth2.setOnClickListener {

            var selectPrintPageSizeDialog= SelectPrintPageSizeDialog()
            selectPrintPageSizeDialog.setSelectPageClick {
                it?.let {
                    mDatabind.txtPrintWidth2.text="${it}mm"
                }
            }
            selectPrintPageSizeDialog.show(supportFragmentManager)
        }
    }

}
