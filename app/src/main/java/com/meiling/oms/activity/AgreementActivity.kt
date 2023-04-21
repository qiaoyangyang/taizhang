package com.meiling.oms.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.webkit.WebResourceRequest
import android.webkit.WebView
import android.webkit.WebViewClient
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
        mDatabind.mWebView.loadUrl(url);

//        mDatabind.mWebView.webViewClient = object :WebViewClient(){
//            override fun shouldOverrideUrlLoading(
//                view: WebView?,
//                request: WebResourceRequest?
//            ): Boolean {
//                return super.shouldOverrideUrlLoading(view, request)
//                    view?.loadUrl(url);
//            }
//
//        }


    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityAgreementBinding {
        return ActivityAgreementBinding.inflate(layoutInflater)
    }
}