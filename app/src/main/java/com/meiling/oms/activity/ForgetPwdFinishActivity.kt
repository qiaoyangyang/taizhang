package com.meiling.oms.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.common.utils.MMKVUtils
import com.meiling.common.utils.SpannableUtils
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityForgetPwdSuccessBinding
import com.meiling.oms.widget.setSingleClickListener

@Route(path = "/app/ForgetPwdFinishActivity")
class ForgetPwdFinishActivity : BaseActivity<BaseViewModel, ActivityForgetPwdSuccessBinding>() {
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

    override fun getBind(layoutInflater: LayoutInflater): ActivityForgetPwdSuccessBinding {
        return ActivityForgetPwdSuccessBinding.inflate(layoutInflater)
    }

    override fun initListener() {

        mDatabind.btnLoginNext.setSingleClickListener {
            MMKVUtils.putBoolean("isLogin", true)
            ARouter.getInstance().build("/app/MainActivity").navigation()
            finish()
        }
        mDatabind.btnLoginReturn.setSingleClickListener {
            ARouter.getInstance().build("/app/LoginActivity").withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).navigation()
            finish()
        }
    }


}