package com.meiling.account.dialog

import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.util.Log
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import android.widget.ImageView
import android.widget.LinearLayout
import android.widget.TextView
import com.just.agentwebX5.AgentWeb
import com.just.agentwebX5.WebDefaultSettingsManager
import com.just.agentwebX5.WebSettings
import com.meiling.account.R
import com.meiling.common.utils.CopyAndPaste
import com.meiling.common.utils.ScreenManager
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder
import com.tencent.smtt.sdk.WebView


class HelfenDialog : BaseNiceDialog() {
    private fun fullScreenImmersive(view: View) {
        val uiOptions = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_FULLSCREEN)
        view.systemUiVisibility = uiOptions
    }

    override fun onStart() {
        super.onStart()
        var lp = dialog?.window?.attributes
        lp?.gravity = Gravity.CENTER
        lp?.height = (ScreenManager.getInstance().screenHeight * 0.83).toInt()
        lp?.width = (ScreenManager.getInstance().screenWidth * 0.63).toInt()
        dialog?.window?.attributes = lp
        dialog?.window?.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        dialog?.window?.setFlags(
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE,
            WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE
        )
        dialog?.setCanceledOnTouchOutside(true)
        dialog?.window?.setWindowAnimations(R.style.BottomAnimation)
        dialog?.window?.getDecorView()?.let { fullScreenImmersive(it) }
        dialog?.window?.clearFlags(WindowManager.LayoutParams.FLAG_NOT_FOCUSABLE)
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_helfen
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        var mLinearLayout = holder?.getView<LinearLayout>(R.id.container)
        var tv_title = holder?.getView<TextView>(R.id.tv_title)
        var iv_automatisch = holder?.getView<ImageView>(R.id.iv_automatisch)
        iv_automatisch?.setOnClickListener {
            dismiss()
        }
        tv_title?.setOnClickListener {
            CopyAndPaste.copy("wviC",activity)
        }
        AgentWeb.with(this)
            .setAgentWebParent(mLinearLayout!!, LinearLayout.LayoutParams(-1, -1))
            .useDefaultIndicator()
            .setWebSettings(object : WebDefaultSettingsManager() {
                override fun toSetting(webView: WebView?): WebSettings<*> {
                    return super.toSetting(webView)
                    webSettings.useWideViewPort = true
                    webSettings.builtInZoomControls = true // 显示放大缩小 controler

                    webSettings.setSupportZoom(true) // 可以缩放

                    //设置隐藏缩放控件
                    //设置隐藏缩放控件
                    webSettings.displayZoomControls = false
                    webSettings.loadWithOverviewMode = true

                    return this

                }
            })
            .createAgentWeb()
            .ready()
            .go("https://igoodsale.feishu.cn/docx/VRlidNCgnoYk2LxrrzLcAj0SnDN")
    }
}


