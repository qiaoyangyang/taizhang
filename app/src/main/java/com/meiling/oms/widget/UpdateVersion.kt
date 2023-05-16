package com.meiling.oms.widget

import android.app.Activity
import android.app.AlertDialog
import android.app.Dialog
import android.text.TextUtils
import android.view.Gravity
import android.view.LayoutInflater
import android.view.View
import android.widget.LinearLayout
import android.widget.ProgressBar
import android.widget.TextView
import android.widget.Toast
import com.blankj.utilcode.util.SPStaticUtils
import com.google.gson.Gson
import com.meiling.common.constant.SPConstants
import com.meiling.common.utils.MMKVUtils
import com.meiling.oms.BuildConfig
import com.meiling.oms.R
import com.meiling.oms.UpdateAppHttpUtil
import com.meiling.oms.data.AppUpdate
import com.vector.update_app.UpdateAppBean
import com.vector.update_app.UpdateAppManager
import com.vector.update_app.UpdateCallback
import com.vector.update_app.service.DownloadService.DownloadCallback
import com.vector.update_app.utils.AppUpdateUtils
import java.io.File


object UpdateVersion {

    fun getUpdateVersion(context: Activity, type: String = "0") {
        val map: HashMap<String, String> = HashMap()
        map.put("versioncode", BuildConfig.VERSION_CODE.toString())
        map.put("appId", "1")
        UpdateAppManager.Builder().setActivity(context)
            .setHttpManager(UpdateAppHttpUtil())
            .setUpdateUrl(
                SPStaticUtils.getString(
                    SPConstants.IP,
                    "https://ods-api.igoodsale.com"
                ) + "/saas/Version/CheckUpdate"
            )
//            .setUpdateUrl("http://dev-oms-api.igoodsale.com/saas/Version/CheckUpdate")
            .setPost(true)
            .setParams(map)
            .build()
            .checkNewApp(object : UpdateCallback() {
                override fun parseJson(json: String): UpdateAppBean {
                    val updateAppBean = UpdateAppBean()
                    val (code, data) = Gson().fromJson(json, AppUpdate::class.java)

                    if (code == 200) {
                        if (data!!.updateId != null && data.downloadUrl != null) {
//                            updateInstall 1 强制更新
                            val i: Int = data.versionCode!!.toInt()
//                            是否更新
                            if (i > BuildConfig.VERSION_CODE) {
                                updateAppBean.isConstraint = data.updateInstall == 1
                                updateAppBean.update = "Yes"
                            } else {
                                updateAppBean.update = "No"
                            }
//
                            //版本号
                            updateAppBean.updateLog = data.versionName
                            updateAppBean.newVersion = data.versionCode
                            //下载地址
                            updateAppBean.apkFileUrl = data.downloadUrl
                        }
                    }
                    return updateAppBean
                }

                override fun hasNewApp(
                    updateApp: UpdateAppBean,
                    updateAppManager: UpdateAppManager
                ) {
                    showDiyDialogNew(context, updateApp, updateAppManager, type)
                }

                override fun noNewApp(error: String) {
                    super.noNewApp(error)
                    if (type == "1") {
                        val toast = Toast.makeText(
                            context,
                            "当前已经是最新版本",
                            Toast.LENGTH_SHORT
                        )
                        toast.setGravity(Gravity.CENTER, 0, 0)
                        toast.show()
                    }
                }

            })
    }

    /**
     * 自定义对话框
     *
     * @param updateApp
     * @param updateAppManager
     */
    private fun showDiyDialogNew(
        context: Activity,
        updateApp: UpdateAppBean,
        updateAppManager: UpdateAppManager,
        type: String
    ) {
        val targetSize = updateApp.targetSize
        val updateLog = updateApp.updateLog
        var msg: String? = ""
        if (!TextUtils.isEmpty(targetSize)) {
            msg = "新版本大小：$targetSize\n\n"
        }
        if (!TextUtils.isEmpty(updateLog)) {
            msg += updateLog
        }
        var builder: AlertDialog.Builder = AlertDialog.Builder(context, R.style.dialogNoBg)
        val inflater = LayoutInflater.from(context)
        val v: View = inflater.inflate(R.layout.dialog_updata_app, null)
        val txtVersion = v.findViewById(R.id.txtVersionName) as TextView
        val txtPro = v.findViewById(R.id.txtPro) as TextView
        val name: TextView = v.findViewById(R.id.name) as TextView
        val btnSure: TextView = v.findViewById(R.id.ok_update) as TextView
        val cancel: TextView = v.findViewById(R.id.cancel_update) as TextView
        val line1: View = v.findViewById(R.id.line1) as View
        val line: View = v.findViewById(R.id.line) as View
        val serverUpdate: TextView = v.findViewById(R.id.server_update) as TextView
        val llUpdate: LinearLayout = v.findViewById(R.id.ll_update) as LinearLayout
        val progressBar = v.findViewById(R.id.pro) as ProgressBar
        txtVersion.text = "快来升级至V${updateLog}版本，体验最新功能吧～"
        val dialog: Dialog = builder.create()
        dialog.setCanceledOnTouchOutside(false)
        if (MMKVUtils.getBoolean("isUpdate")) {
            dialog.show()
        } else {
                dialog.show()
        }
        if (updateApp.isConstraint) {
            cancel.visibility = View.GONE
            line1.visibility = View.GONE
            serverUpdate.visibility = View.GONE
        }
        dialog.window!!.setContentView(v) //自定义布局应该在这里添加，要在dialog.show()的后面
        btnSure.setOnClickListener(object : View.OnClickListener {
            override fun onClick(v: View?) {
                name.text = "正在更新，请耐心等待～"
                progressBar.visibility = View.VISIBLE
                txtPro.visibility = View.VISIBLE
                btnSure.visibility = View.GONE
                llUpdate.visibility = View.GONE
                cancel.visibility = View.GONE
                txtVersion.visibility = View.GONE
                if (updateApp.isConstraint) {
                    serverUpdate.visibility = View.INVISIBLE
                    line.visibility = View.GONE
                } else {
                    line.visibility = View.VISIBLE
                    serverUpdate.visibility = View.VISIBLE
                }
                updateAppManager.download(object : DownloadCallback {
                    override fun onStart() {}

                    /**
                     * 进度
                     *
                     * @param progress  进度 0.00 -1.00 ，总大小
                     * @param totalSize 总大小 单位B
                     */
                    override fun onProgress(progress: Float, totalSize: Long) { //
                        txtPro.text = "已经下载 " + Math.round(progress * 100) + " %"
                        progressBar.progress = Math.round(progress * 100)
                    }

                    /**
                     *
                     * @param total 总大小 单位B
                     */
                    override fun setMax(total: Long) {}
                    override fun onFinish(file: File?): Boolean {
                        dialog.dismiss()
                        AppUpdateUtils.installApp(context, file)
                        return true
                    }

                    override fun onError(msg: String) {
                        Toast.makeText(context, msg, Toast.LENGTH_SHORT).show()
                    }

                    override fun onInstallAppAndAppOnForeground(file: File?): Boolean {
                        return false
                    }


                }


                )
            }
        })

        cancel.setOnClickListener {
            MMKVUtils.putBoolean("isUpdate",false)
            dialog.dismiss()
        }
        serverUpdate.setOnClickListener {
            dialog.dismiss()
        }

    }

}

