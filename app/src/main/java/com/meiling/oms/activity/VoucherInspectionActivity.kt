package com.meiling.oms.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.core.app.ActivityCompat
import com.huawei.hms.hmsscankit.ScanUtil
import com.huawei.hms.ml.scan.HmsScan
import com.huawei.hms.ml.scan.HmsScanAnalyzerOptions
import com.meiling.common.activity.BaseActivity
import com.meiling.common.utils.TextDrawableUtils
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityVoucherinspectionBinding
import com.meiling.oms.viewmodel.VoucherinspectionViewModel

//验券
class VoucherInspectionActivity :
    BaseActivity<VoucherinspectionViewModel, ActivityVoucherinspectionBinding>() {
    var type = ""
    override fun initView(savedInstanceState: Bundle?) {
        TextDrawableUtils.setRightDrawable(mDatabind.TitleBar.titleView, R.drawable.xia)
        //验券历史
        mDatabind.tvVoucherInspectionHistory.setOnClickListener {
            startActivity(
                Intent(this, VoucherInspectionHistoryActivity::class.java)
            )
        }
        //  输码验券
        mDatabind.tvInputBoredom.setOnClickListener {
            startActivity(
                Intent(this, InputBoredomActivity::class.java)
            )
        }


        mDatabind.clScan.setOnClickListener {
            requestPermission(CAMERA_REQ_CODE,DECODE)
        }

    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityVoucherinspectionBinding {
        return ActivityVoucherinspectionBinding.inflate(layoutInflater)
    }

    override fun initData() {
        super.initData()
        type = intent?.getStringExtra("type").toString()

    }

    override fun onTitleClick(view: View) {
        super.onTitleClick(view)
        Log.d("yjk", "onTitleClick: ")
    }
    //编辑请求权限
    open fun requestPermission(requestCode: Int, mode: Int) {
        ActivityCompat.requestPermissions(
            this, arrayOf(Manifest.permission.CAMERA, Manifest.permission.READ_EXTERNAL_STORAGE),
            requestCode
        )
    }
    val DECODE = 1
    val CAMERA_REQ_CODE = 111;
    val REQUEST_CODE_SCAN_ONE = 0X01

    //权限申请返回
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<out String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)

        if (permissions == null || grantResults == null) {
            return
        }
        if (grantResults.size < 2 || grantResults[0] != PackageManager.PERMISSION_GRANTED || grantResults[1] != PackageManager.PERMISSION_GRANTED) {
            return
        }
        if (requestCode == CAMERA_REQ_CODE) {
            ScanUtil.startScan(
                this,
                REQUEST_CODE_SCAN_ONE,
                HmsScanAnalyzerOptions.Creator().create()
            )
        }
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE_SCAN_ONE) {
            if (data != null) {
                val obj: HmsScan? = data!!.getParcelableExtra(ScanUtil.RESULT)

                Log.d("yjk-----",obj!!.originalValue)
                return
            }
        }
    }
}