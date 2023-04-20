package com.meiling.oms.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.view.LayoutInflater
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.BuildConfig
import com.meiling.oms.databinding.ActivityAboutBinding
import com.meiling.oms.dialog.AboutKFDialog
import com.meiling.oms.viewmodel.LoginViewModel
import com.meiling.oms.widget.UpdateVersion
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast

@Route(path = "/app/AboutActivity")
class AboutActivity : BaseActivity<LoginViewModel, ActivityAboutBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
    }

    val ACCESS_INSTALL_LOCATION = 1
    override fun initListener() {
        mDatabind.slCopyUrl.setSingleClickListener { showToast("地址已经复制") }
        mDatabind.slXy.setSingleClickListener {
            ARouter.getInstance().build("/app/AgreementActivity").withString("YSXY", "0")
                .navigation()
        }
        mDatabind.slYs.setSingleClickListener {
            ARouter.getInstance().build("/app/AgreementActivity").withString("YSXY", "1")
                .navigation()
        }
        mDatabind.txtVersion.text = "V${BuildConfig.VERSION_NAME}"
        mDatabind.slVersion.setSingleClickListener {

//            if (ContextCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.REQUEST_INSTALL_PACKAGES
//                ) == PackageManager.PERMISSION_GRANTED
//            ) {
                UpdateVersion.getUpdateVersion(this, "1")
//            } else {
//                // 如果没有权限，申请权限
//                ActivityCompat.requestPermissions(
//                    this,
//                    arrayOf(Manifest.permission.REQUEST_INSTALL_PACKAGES),
//                    ACCESS_INSTALL_LOCATION
//                )
//            }


//            showToast("最新版本")
        }
        mDatabind.stKf.setSingleClickListener {
            AboutKFDialog().newInstance().newInstance().show(supportFragmentManager)
        }
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityAboutBinding {
        return ActivityAboutBinding.inflate(layoutInflater)
    }

//    override fun onRequestPermissionsResult(
//        requestCode: Int,
//        permissions: Array<String>,
//        grantResults: IntArray
//    ) {
//        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
//        if (requestCode == ACCESS_INSTALL_LOCATION) {
//            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
//                UpdateVersion.getUpdateVersion(this, "1")
//            } else {
//                showToast("您已经禁止权限，请手动开启")
//                // 如果用户拒绝了权限，可以在这里处理相应的逻辑
//            }
//        }
//    }
}