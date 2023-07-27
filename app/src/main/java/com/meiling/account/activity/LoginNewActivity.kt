package com.meiling.account.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.meiling.account.databinding.ActivityLoginBinding
import com.meiling.account.viewmodel.LoginViewModel
import com.meiling.common.activity.BaseActivity
import com.tencent.mm.opensdk.diffdev.DiffDevOAuthFactory
import com.tencent.mm.opensdk.diffdev.IDiffDevOAuth
import com.tencent.mm.opensdk.diffdev.OAuthErrCode
import com.tencent.mm.opensdk.diffdev.OAuthListener


class LoginNewActivity: BaseActivity<LoginViewModel, ActivityLoginBinding>() , OAuthListener {
    private var oauth: IDiffDevOAuth? = null
    private val TIME_FORMAT = "yyyyMMddHHmmss"

    override fun initView(savedInstanceState: Bundle?) {
        oauth = DiffDevOAuthFactory.getDiffDevOAuth();
        wechatfirst()
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }


    override fun onAuthGotQrcode(p0: String?, p1: ByteArray?) {
    }

    override fun onQrcodeScanned() {
    }

    override fun onAuthFinish(p0: OAuthErrCode?, p1: String?) {
    }
    //第一步
    private fun wechatfirst(){
        mViewModel.WeChatLogin()


    }

}