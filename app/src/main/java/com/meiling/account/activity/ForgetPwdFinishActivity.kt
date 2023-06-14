package com.meiling.account.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ActivityUtils
import com.meiling.common.activity.BaseActivity
import com.meiling.common.constant.SPConstants
import com.meiling.common.utils.MMKVUtils
import com.meiling.common.utils.SpannableUtils
import com.meiling.account.R
import com.meiling.account.databinding.ActivityForgetPwdSuccessBinding
import com.meiling.account.viewmodel.LoginViewModel
import com.meiling.account.widget.setSingleClickListener
import com.meiling.account.widget.showToast

@Route(path = "/app/ForgetPwdFinishActivity")
class ForgetPwdFinishActivity : BaseActivity<LoginViewModel, ActivityForgetPwdSuccessBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        var conet = "登录即代表同意小喵来客《用户协议》及《隐私政策》"
        SpannableUtils.setText(
            this,
            conet,
            mDatabind.tvAgreement,
            R.color.pwd_1180FF
        )

    }

    override fun isStatusBarDarkFont(): Boolean {
        return true
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityForgetPwdSuccessBinding {
        return ActivityForgetPwdSuccessBinding.inflate(layoutInflater)
    }
    var type:String?=""
    override fun onLeftClick(view: View) {
        if(type=="注册成功"){
            ARouter.getInstance().build("/app/LoginActivity")
                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).navigation()
            ActivityUtils.finishAllActivities()
        }
    }
    override fun onBackPressed() {
        if(type=="注册成功"){
            ARouter.getInstance().build("/app/LoginActivity")
                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).navigation()
            ActivityUtils.finishAllActivities()
        }
    }
    override fun initListener() {
        val account = intent.getStringExtra("account")
        val pwd = intent.getStringExtra("password")
        type = intent.getStringExtra("title")
        val context = intent.getStringExtra("context")
        var isAgreement = false
        mDatabind.TitleBar.title = type
        mDatabind.txtTipTip.text = context
        if(type=="注册成功"){
           mDatabind.btnLoginNext.text="开始使用"
        }
        mDatabind.cbAgreementv.setOnCheckedChangeListener { buttonView, isChecked ->
            isAgreement = isChecked
        }

        mDatabind.btnLoginNext.setSingleClickListener {
            if (!isAgreement) {
                showToast("请同意并勾选用户协议和隐私政策")
            } else {
                mViewModel.accountLogin(
                    account!!,
                    pwd!!
                )

            }

        }
        mDatabind.btnLoginReturn.setSingleClickListener {
            ARouter.getInstance().build("/app/LoginActivity")
                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).navigation()
            ActivityUtils.finishAllActivities()
        }
    }

    override fun createObserver() {
        mViewModel.loginData.onStart.observe(this) {
            showLoading("正在登录")
        }
        mViewModel.loginData.onSuccess.observe(this) {
            disLoading()
            MMKVUtils.putBoolean("isLogin", true)
            MMKVUtils.putString(SPConstants.PHONE, it.adminUser?.phone!!)
            MMKVUtils.putString(SPConstants.TOKEN, it.adminUser?.token!!)
            MMKVUtils.putString(SPConstants.ACCOUNT, it.adminUser?.username!!)
            MMKVUtils.putString(SPConstants.AVATAR, it.adminUser?.avatar!!)
            MMKVUtils.putString(SPConstants.NICK_NAME, it.adminUser?.nickname!!)
            MMKVUtils.putString(SPConstants.tenantId, it.adminUser?.tenantId!!)
            MMKVUtils.putString(SPConstants.adminViewId, it.adminUser?.viewId!!)
            MMKVUtils.putInt(SPConstants.ROLE, 1)
            ARouter.getInstance().build("/app/MainActivity").navigation()
            ActivityUtils.finishAllActivities()
        }
        mViewModel.loginData.onError.observe(this) {
            disLoading()
            showToast("${it.msg}")
        }
    }

}