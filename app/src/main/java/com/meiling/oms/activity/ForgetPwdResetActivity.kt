package com.meiling.oms.activity

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.databinding.ActivityForgetPwdRasetBinding
import com.meiling.oms.viewmodel.LoginViewModel
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast

@Route(path = "/app/ForgetPwdResetActivity")
class ForgetPwdResetActivity : BaseActivity<LoginViewModel, ActivityForgetPwdRasetBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.etAgainPassword.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        mDatabind.etPassword.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
    }

    override fun isStatusBarDarkFont(): Boolean {
        return true
    }

    override fun initData() {

    }

    var account = ""
    override fun initListener() {
        account = intent.getStringExtra("account").toString()
        val phone = intent.getStringExtra("phone")
        val code = intent.getStringExtra("code")

        mDatabind.etPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().isNotEmpty()) {
                    mDatabind.ivCleanPwdShow.visibility = View.VISIBLE
                } else {
                    mDatabind.ivCleanPwdShow.visibility = View.GONE
                }
            }
        })
        mDatabind.etAgainPassword.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().isNotEmpty()) {
                    mDatabind.ivCleanPwdAgainShow.visibility = View.VISIBLE
                } else {
                    mDatabind.ivCleanPwdAgainShow.visibility = View.GONE
                }
            }
        })


        mDatabind.ivCleanPwdShow.setSingleClickListener {
            mDatabind.etPassword.text.clear()
        }
        mDatabind.ivCleanPwdAgainShow.setSingleClickListener {
            mDatabind.etAgainPassword.text.clear()
        }

        mDatabind.cbPwdShow.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mDatabind.etPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
            } else {
                mDatabind.etPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            }
            mDatabind.etPassword.text?.length?.let { mDatabind.etPassword.setSelection(it) }
        }

        mDatabind.cbPwdAgainShow.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mDatabind.etAgainPassword.transformationMethod =
                    PasswordTransformationMethod.getInstance()
            } else {
                mDatabind.etAgainPassword.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            }
            mDatabind.etAgainPassword.text?.length?.let { mDatabind.etAgainPassword.setSelection(it) }
        }

        mDatabind.btnNext.setSingleClickListener {
            if (mDatabind.etPassword.text.trim().toString().isEmpty()) {
                mDatabind.txtPwdTipError.visibility = View.VISIBLE
                mDatabind.txtPwdTipError.text = "密码不能为空"
                return@setSingleClickListener
            }

            if (mDatabind.etPassword.text.trim()
                    .toString().length > 20 || mDatabind.etPassword.text.trim()
                    .toString().length < 8
            ) {
                mDatabind.txtPwdTipError.visibility = View.VISIBLE
                mDatabind.txtPwdTipError.text = "密码长度需要在8-20位字符之间"
                return@setSingleClickListener
            }
            if (!isPasswordValid(mDatabind.etPassword.text.trim().toString())) {
                mDatabind.txtPwdTipError.visibility = View.VISIBLE
                mDatabind.txtPwdTipError.text = "密码不能是纯数字/纯字母/纯字符"
                return@setSingleClickListener
            }
            if (mDatabind.etPassword.text.trim().toString() == mDatabind.etAgainPassword.text.trim()
                    .toString()
            ) {

                mViewModel.resetPwd(
                    account!!,
                    phone!!,
                    code!!,
                    mDatabind.etPassword.text.trim().toString()
                )
            } else {
                mDatabind.txtPwdAgainTip.visibility = View.VISIBLE
                mDatabind.txtPwdTipError.visibility = View.GONE
                mDatabind.txtPwdAgainTip.text = "两次输入密码不一致"
            }
        }
    }


    override fun createObserver() {
        mViewModel.repData.onStart.observe(this) {
            showLoading("请求中")
        }
        mViewModel.repData.onSuccess.observe(this) {
            mDatabind.txtPwdTipError.visibility = View.GONE
            mDatabind.txtPwdTipError.visibility = View.GONE
            disLoading()
            ARouter.getInstance().build("/app/ForgetPwdFinishActivity")
                .withString("password", mDatabind.etPassword.text.trim().toString())
                .withString("account", account)
                .withString("title", "忘记密码")
                .withString("context", "修改完成")
                .navigation()
        }
        mViewModel.repData.onError.observe(this) {
            disLoading()
            showToast("${it.msg}")
        }
    }

    private fun isPasswordValid(password: String): Boolean {

        Log.d("lwq", "=========1111${password}")
        // 判断是否是纯数字或纯字母
        if (password.matches(Regex("\\d+")) || password.matches(Regex("[a-zA-Z]+"))) {
            return false
        }

        // 判断是否包含字母和数字
        if (password.matches(Regex("[a-zA-Z]+")) && password.matches(Regex("\\d+"))) {
            return true
        }
        return true
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityForgetPwdRasetBinding {
        return ActivityForgetPwdRasetBinding.inflate(layoutInflater)
    }
}