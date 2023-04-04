package com.meiling.oms.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.meiling.common.activity.BaseActivity
import com.meiling.common.constant.SPConstants
import com.meiling.common.utils.MMKVUtils
import com.meiling.common.utils.SpannableUtils
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityForgetPwdSuccessBinding
import com.meiling.oms.viewmodel.LoginViewModel
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast

@Route(path = "/app/ForgetPwdFinishActivity")
class ForgetPwdFinishActivity : BaseActivity<LoginViewModel, ActivityForgetPwdSuccessBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        var conet = "登录即代表同意《美零云店用户协议及隐私政策》"
        SpannableUtils.setTextcolor(
            this,
            conet,
            mDatabind.tvAgreement,
            7,
            conet.length,
            R.color.red
        )
    }
    override fun isStatusBarDarkFont(): Boolean {
        return true
    }
    override fun getBind(layoutInflater: LayoutInflater): ActivityForgetPwdSuccessBinding {
        return ActivityForgetPwdSuccessBinding.inflate(layoutInflater)
    }

    override fun initListener() {
        val account = intent.getStringExtra("account")
        val pwd = intent.getStringExtra("password")
        var isAgreement = false
        mDatabind.cbAgreementv.setOnCheckedChangeListener { buttonView, isChecked ->
            isAgreement = isChecked
        }

        mDatabind.btnLoginNext.setSingleClickListener {
            if (!isAgreement){
                showToast("请同意并勾选用户协议和隐私政策")
            }else{
                mViewModel.accountLogin(
                    account!!,
                    pwd!!
                )

            }

        }
        mDatabind.btnLoginReturn.setSingleClickListener {
            ARouter.getInstance().build("/app/LoginActivity")
                .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).navigation()
            finish()
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
            finish()
        }
        mViewModel.loginData.onError.observe(this) {
            disLoading()
            showToast("${it.msg}")
        }
    }

}