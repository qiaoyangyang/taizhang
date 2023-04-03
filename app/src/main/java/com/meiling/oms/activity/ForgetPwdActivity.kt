package com.meiling.oms.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.meiling.common.activity.BaseActivity
import com.meiling.common.utils.SpannableUtils
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityForgetPwdBinding
import com.meiling.oms.viewmodel.LoginViewModel
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast

@Route(path = "/app/ForgetPwdActivity")
class ForgetPwdActivity : BaseActivity<LoginViewModel, ActivityForgetPwdBinding>() {
    override fun initView(savedInstanceState: Bundle?) {

    }


    override fun initData() {

    }

    var type: Int = 1;//1 判断账号输入， 2，判断输入密码  3.判断修改密码

    override fun initListener() {

        mDatabind.edtAccount.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().isNotEmpty()) {
                    mDatabind.ivCleanShow.visibility = View.VISIBLE
                } else {
                    mDatabind.ivCleanShow.visibility = View.GONE
                }
            }
        })

        mDatabind.ivCleanShow.setSingleClickListener {
            mDatabind.edtAccount.text.clear()
        }

        mDatabind.btnNext.setSingleClickListener {
            if (mDatabind.edtAccount.text.isNotEmpty()) {
                ARouter.getInstance().build("/app/ForgetPwdGetCodeActivity").navigation()
            } else {
                showToast("请输入账号")
            }
        }
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityForgetPwdBinding {
        return ActivityForgetPwdBinding.inflate(layoutInflater)
    }
}