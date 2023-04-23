package com.meiling.oms.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.FrameLayout
import com.alibaba.android.arouter.facade.annotation.Route
import com.just.agentweb.AgentWeb
import com.just.agentweb.DefaultWebClient
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.databinding.ActivityAgreementBinding
import com.meiling.oms.viewmodel.VoucherinspectionViewModel


@Route(path = "/app/AgreementActivity")
class AgreementActivity : BaseActivity<VoucherinspectionViewModel, ActivityAgreementBinding>() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView(savedInstanceState: Bundle?) {
        var stringExtraType = intent?.getStringExtra("YSXY")
        var url = "file:///android_asset/xy.html"
        if (stringExtraType == "1") {
            mDatabind.TitleBar.title = "小喵来客隐私政策"
            url = "file:///android_asset/xy.html"
            url = "http://dev-oms.igoodsale.com/#/privacyPolicy"
        } else {
            mDatabind.TitleBar.title = "小喵来客用户协议"
//            url = "file:///android_asset/xy.html"
            url = "http://dev-oms.igoodsale.com/#/userAgreement"
        }

        var mAgentWeb = AgentWeb.with(this)
            .setAgentWebParent(
                mDatabind.mWebView,
                FrameLayout.LayoutParams(
                    ViewGroup.LayoutParams.MATCH_PARENT,
                    ViewGroup.LayoutParams.MATCH_PARENT
                )
            )
            .closeIndicator()
            //设置报错布局 .setMainFrameErrorView(R.layout.agentweb_error_page, -1)
            //打开其他应用时，弹窗咨询用户是否前往其他应用
            .setOpenOtherPageWays(DefaultWebClient.OpenOtherPageWays.ASK)
            .createAgentWeb()
            .ready()
            //打开页面
            .go(url)
        mAgentWeb.agentWebSettings.webSettings.javaScriptEnabled = true;
        mAgentWeb.clearWebCache();
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityAgreementBinding {
        return ActivityAgreementBinding.inflate(layoutInflater)
    }
}