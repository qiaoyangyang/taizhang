package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.meiling.common.activity.BaseActivity
import com.meiling.common.utils.SpannableUtils
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityForgetPwdGetCodeBinding
import com.meiling.oms.viewmodel.LoginViewModel
import com.meiling.oms.widget.setSingleClickListener

@Route(path = "/app/ForgetPwdGetCodeActivity")
class ForgetPwdGetCodeActivity : BaseActivity<LoginViewModel, ActivityForgetPwdGetCodeBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        var conet = "验证码将发送到绑定手机号 ${hidePhoneNumber("13993365940")}"

        SpannableUtils.setTextcolor(
            this,
            conet,
            mDatabind.txtTipPhone,
            12,
            conet.length,
            R.color.red
        )
    }

    private fun hidePhoneNumber(phoneNumber: String): String {
        val startIndex = 3
        val endIndex = 7
        val asterisks = "*".repeat(endIndex - startIndex)
        return phoneNumber.replaceRange(startIndex, endIndex, asterisks)
    }

    override fun initData() {

    }


    override fun initListener() {
        mDatabind.btnNext.setSingleClickListener {
            ARouter.getInstance().build("/app/ForgetPwdResetActivity").navigation()
        }
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityForgetPwdGetCodeBinding {
        return ActivityForgetPwdGetCodeBinding.inflate(layoutInflater)
    }
}