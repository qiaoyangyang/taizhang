package com.meiling.oms.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.webkit.*
import com.alibaba.android.arouter.facade.annotation.Route
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.databinding.ActivityAgreementBinding
import com.meiling.oms.viewmodel.VoucherinspectionViewModel

@Route(path = "/app/AgreementActivity")
class AgreementActivity : BaseActivity<VoucherinspectionViewModel, ActivityAgreementBinding>() {

    @SuppressLint("SetJavaScriptEnabled")
    override fun initView(savedInstanceState: Bundle?) {
        var stringExtraType = intent?.getStringExtra("YSXY")
        // 开启javascript 渲染
        mDatabind.mWebView.settings.javaScriptEnabled = true;

        var url = "http://www.baidu.com/"
        if (stringExtraType == "1") {
            mDatabind.TitleBar.title = "小喵来客隐私政策"
//            url = "http://dev-oms.igoodsale.com/#/privacyPolicy"
            url = "https://www.baidu.com/"
        } else {
            mDatabind.TitleBar.title = "小喵来客用户协议"
            url = "http://dev-oms.igoodsale.com/#/userAgreement/"
        }

        // 载入内容
//        mDatabind.mWebView.loadUrl(url);
//        val webView: WebView = findViewById(R.id.webView)

// 启用JavaScript执行
        mDatabind.mWebView.settings.javaScriptEnabled = true

// 允许网页缩放
        mDatabind.mWebView.settings.setSupportZoom(true)
        mDatabind.mWebView.settings.builtInZoomControls = true
        mDatabind.mWebView.settings.displayZoomControls = false

// 缓存设置
//         mDatabind.mWebView.settings.cacheMode = WebSettings.LOAD_DEFAULT
//         mDatabind.mWebView.settings.setAppCacheEnabled(true)
//         mDatabind.mWebView.settings.setAppCachePath(cacheDir.path)

// 允许混合内容（HTTP和HTTPS）
        mDatabind.mWebView.settings.mixedContentMode = WebSettings.MIXED_CONTENT_ALWAYS_ALLOW

// 允许在 mDatabind.mWebView中打开链接，而不是打开默认的浏览器
//         mDatabind.mWebView.webViewClient = WebViewClient()

// 允许在WebView中打开多个窗口
        mDatabind.mWebView.webChromeClient = WebChromeClient()

// 加载网页链接
        mDatabind.mWebView.loadUrl("http://dev-oms.igoodsale.com/#/userAgreement")
//        mDatabind.mWebView.loadUrl("https://blog.csdn.net/qq_43515862/article/details/110293455")


    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityAgreementBinding {
        return ActivityAgreementBinding.inflate(layoutInflater)
    }
}