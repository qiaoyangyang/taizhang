package com.meiling.oms.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
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
        var url2=intent?.getStringExtra("url")
        var title=intent?.getStringExtra("title")
        var url = "file:///android_asset/xy.html"
        stringExtraType?.let {
            if (stringExtraType == "1") {
                mDatabind.TitleBar.title = "小喵来客隐私政策"
                url = "file:///android_asset/xy.html"
            } else {
                mDatabind.TitleBar.title = "小喵来客用户协议"
                url = "file:///android_asset/xy.html"
            }
        }

        url2?.let {
            url =it
        }
        title?.let {
            mDatabind.TitleBar.title=it
        }
        // 开启javascript 渲染
        mDatabind.mWebView.settings.javaScriptEnabled = true;
//        mDatabind.mWebView.webViewClient = WebViewClient();

        // 载入内容
        mDatabind.mWebView.loadUrl(url);

    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityAgreementBinding {
        return ActivityAgreementBinding.inflate(layoutInflater)
    }
}