package com.meiling.account.dialog

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
    init {
        var Height = (ScreenManager.getInstance().screenHeight * 0.62).toInt()
        var Width = (ScreenManager.getInstance().screenWidth *  0.53).toInt()
        setHeight(Height)
        setWidth(Width)
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


