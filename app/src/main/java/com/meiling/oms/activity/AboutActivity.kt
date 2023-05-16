package com.meiling.oms.activity

import android.content.Context
import android.content.Intent
import android.net.Uri
import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import androidx.annotation.RequiresApi
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.AppUtils
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.BuildConfig
import com.meiling.oms.databinding.ActivityAboutBinding
import com.meiling.oms.dialog.AboutKFDialog
import com.meiling.oms.dialog.SelectUrlDialog
import com.meiling.oms.viewmodel.LoginViewModel
import com.meiling.oms.widget.UpdateVersion
import com.meiling.oms.widget.copyText
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast


@Route(path = "/app/AboutActivity")
class AboutActivity : BaseActivity<LoginViewModel, ActivityAboutBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
    }
    var BREAK_THROUGH_TIMES = 7
    var breakthrough = BREAK_THROUGH_TIMES
    var lastClickTime: Long = 0
    val ACCESS_INSTALL_LOCATION = 1

    @RequiresApi(Build.VERSION_CODES.O)
    override fun initListener() {
        mDatabind.slCopyUrl.setSingleClickListener {
            copyText(this, "http://ods.igoodsale.com/")
            showToast("地址已经复制")
        }
        mDatabind.slXy.setSingleClickListener {
            ARouter.getInstance().build("/app/AgreementActivity").withString("YSXY", "0")
                .navigation()
        }
        mDatabind.slYs.setSingleClickListener {
            ARouter.getInstance().build("/app/AgreementActivity").withString("YSXY", "1")
                .navigation()
        }
        mDatabind.txtVersion.text = "当前版本:V${BuildConfig.VERSION_NAME}"
            mDatabind.imgAbout.setOnClickListener {
                if (breakthrough > 0) {
                    if (isFastClickEachOneSec()) {
                        --breakthrough
                        if (breakthrough == 0) {
//                        AbToastUtil.showInterruptToast(getActivity(), "已开放手动权限");
                            // 开放手动切换地址权限后实现的功能
                            var selectUrlDialog= SelectUrlDialog().newInstance()
                            selectUrlDialog.setOnclose {
                                breakthrough=BREAK_THROUGH_TIMES
                                selectUrlDialog.dismiss()
                                if(it){
                                    AppUtils.relaunchApp(true)
                                }
                            }



                            selectUrlDialog.show(supportFragmentManager)
                            return@setOnClickListener;
                        }
//                    if (breakthrough < BREAK_THROUGH_TIMES - 1)
//                        AbToastUtil.showInterruptToast(getActivity(), "点击" + breakthrough + "次开放手动权限");
                    } else
                        breakthrough = BREAK_THROUGH_TIMES;
                }

            }

        mDatabind.slVersion.setSingleClickListener {

//            if (ContextCompat.checkSelfPermission(
//                    this,
//                    Manifest.permission.WRITE_EXTERNAL_STORAGE
//                ) == PackageManager.PERMISSION_GRANTED
//            ) {
//
//                UpdateVersion.getUpdateVersion(this, "1")
//            } else {
//                // 如果没有权限，申请权限
//                ActivityCompat.requestPermissions(
//                    this,
//                    arrayOf(Manifest.permission.WRITE_EXTERNAL_STORAGE),
//                    ACCESS_INSTALL_LOCATION
//                )
//            }
//            val hasInstallPermission: Boolean = isHasInstallPermissionWithO(this)
//            if (!hasInstallPermission) {
//                startInstallPermissionSettingActivity(this)
//            }else{
            UpdateVersion.getUpdateVersion(this, "1")
//            }

        }
        mDatabind.stKf.setSingleClickListener {
            AboutKFDialog().newInstance().show(supportFragmentManager)
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
    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun isHasInstallPermissionWithO(context: Context?): Boolean {
        return if (context == null) {
            false
        } else context.getPackageManager().canRequestPackageInstalls()
    }

    var REQUEST_CODE_APP_INSTALL = 1

    /**
     * 开启设置安装未知来源应用权限界面
     * @param context
     */
    @RequiresApi(api = Build.VERSION_CODES.O)
    private fun startInstallPermissionSettingActivity(context: Context?) {
        if (context == null) {
            return
        }
        var intent = Intent();
        //获取当前apk包URI，并设置到intent中（这一步设置，可让“未知应用权限设置界面”只显示当前应用的设置项）
        var packageURI = Uri.parse("package:" + context.packageName);
        intent.setData(packageURI);
        if (Build.VERSION.SDK_INT >= 26) {
            intent.action = android.provider.Settings.ACTION_MANAGE_UNKNOWN_APP_SOURCES;
        } else {
            intent.setAction(android.provider.Settings.ACTION_SECURITY_SETTINGS);
        }
        this.startActivityForResult(intent, REQUEST_CODE_APP_INSTALL);
    }

    override fun onActivityResult(requestCode: Int, resultCode: Int, data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (resultCode == REQUEST_CODE_APP_INSTALL) {
            UpdateVersion.getUpdateVersion(this, "1")
        }
    }

    /**
     * 防止1秒内连续操作
     * @return true为连续操作，false则不是连续操作
     */
    fun isFastClickEachOneSec(): Boolean {
        val time = System.currentTimeMillis()
        if (time - lastClickTime < 1000) {
            lastClickTime = time
            return true
        }
        lastClickTime = time
        return false
    }

}