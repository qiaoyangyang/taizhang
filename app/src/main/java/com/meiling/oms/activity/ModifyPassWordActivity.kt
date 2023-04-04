package com.meiling.oms.activity

import android.os.Bundle
import android.text.Editable
import android.text.InputType
import android.text.TextWatcher
import android.text.method.HideReturnsTransformationMethod
import android.text.method.PasswordTransformationMethod
import android.view.LayoutInflater
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.common.constant.SPConstants
import com.meiling.common.utils.MMKVUtils
import com.meiling.oms.databinding.ActivityModifyPasswordBinding
import com.meiling.oms.viewmodel.LoginViewModel
import com.meiling.oms.widget.CaptchaCountdownTool
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast

@Route(path = "/app/ModifyPassWordActivity")
class ModifyPassWordActivity : BaseActivity<LoginViewModel, ActivityModifyPasswordBinding>() {

    private val captchaCountdownTool: CaptchaCountdownTool =
        CaptchaCountdownTool(object : CaptchaCountdownTool.CaptchaCountdownListener {
            override fun onCountdownTick(countDownText: String) {
                mDatabind.txtGetAuthCode.text = "$countDownText s"
            }

            override fun onCountdownFinish() {
                mDatabind.txtGetAuthCode.text = "重新获取"
            }
        })

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.edtInputPwd.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
        mDatabind.edtAgainInputPwd.inputType =
            InputType.TYPE_CLASS_TEXT or InputType.TYPE_TEXT_VARIATION_PASSWORD
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityModifyPasswordBinding {
        return ActivityModifyPasswordBinding.inflate(layoutInflater)
    }

    override fun initListener() {
//        mDatabind.aivBack.setOnClickListener { finish() }

        mDatabind.txtLoginAccountShow.text = MMKVUtils.getString(SPConstants.ACCOUNT)
        mDatabind.txtLoginPhone.text = MMKVUtils.getString(SPConstants.PHONE)

        mDatabind.edtInputPwd.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().isNotEmpty()) {
                    mDatabind.imgPwdInputClear.visibility = View.VISIBLE
                } else {
                    mDatabind.imgPwdInputClear.visibility = View.GONE
                }
            }
        })
        mDatabind.edtAgainInputPwd.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().isNotEmpty()) {
                    mDatabind.imgPwdAgainClear.visibility = View.VISIBLE
                } else {
                    mDatabind.imgPwdAgainClear.visibility = View.GONE
                }
            }
        })
        mDatabind.edtAuthCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().isNotEmpty()) {
                    mDatabind.imgCodeClear.visibility = View.VISIBLE
                } else {
                    mDatabind.imgCodeClear.visibility = View.GONE
                }
            }
        })


        mDatabind.imgPwdInputClear.setSingleClickListener {
            mDatabind.edtInputPwd.text.clear()
        }
        mDatabind.imgCodeClear.setSingleClickListener {
            mDatabind.edtAuthCode.text.clear()
        }
        mDatabind.imgPwdAgainClear.setSingleClickListener {
            mDatabind.edtAgainInputPwd.text.clear()
        }

        mDatabind.cbPwdInputShow.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mDatabind.edtInputPwd.transformationMethod =
                    PasswordTransformationMethod.getInstance()
            } else {
                mDatabind.edtInputPwd.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            }
            mDatabind.edtInputPwd.text?.length?.let { mDatabind.edtInputPwd.setSelection(it) }
        }

        mDatabind.cbPwdAgainShow.setOnCheckedChangeListener { buttonView, isChecked ->
            if (isChecked) {
                mDatabind.edtAgainInputPwd.transformationMethod =
                    PasswordTransformationMethod.getInstance()
            } else {
                mDatabind.edtAgainInputPwd.transformationMethod =
                    HideReturnsTransformationMethod.getInstance()
            }
            mDatabind.edtAgainInputPwd.text?.length?.let {
                mDatabind.edtAgainInputPwd.setSelection(
                    it
                )
            }
        }



        mDatabind.txtGetAuthCode.setSingleClickListener {
            if (mDatabind.txtLoginPhone.text.trim().toString().isNotEmpty()) {
                mViewModel.sendCode(mDatabind.txtLoginPhone.text.trim().toString())
                captchaCountdownTool.startCountdown()
            }
        }

        mDatabind.btnSureChange.setSingleClickListener {
            if (mDatabind.edtInputPwd.text.trim().toString().isEmpty()) {
                showToast("密码不能为空")
                return@setSingleClickListener
            }
            if (mDatabind.edtInputPwd.text.trim().toString().length < 8
            ) {
                showToast("密码长度需要在8-20位字符之间")
                return@setSingleClickListener
            }

            if (!isPasswordValid(mDatabind.edtInputPwd.text.trim().toString())) {
                showToast("密码不能是纯数字/纯字母/纯字符")
                return@setSingleClickListener
            }
            if (mDatabind.edtAuthCode.text.trim().toString().isEmpty()) {
                showToast("验证码不能为空")
                return@setSingleClickListener
            }

            if (mDatabind.edtInputPwd.text.trim()
                    .toString() == mDatabind.edtAgainInputPwd.text.trim()
                    .toString()
            ) {
                mViewModel.resetPwd(
                    mDatabind.txtLoginAccountShow.text.trim().toString(),
                    mDatabind.txtLoginPhone.text.trim().toString(),
                    mDatabind.edtAuthCode.text.trim().toString(),
                    mDatabind.edtInputPwd.text.trim().toString()
                )
            } else {
                showToast("两次输入密码不一致")
            }
        }
    }

    override fun createObserver() {

        mViewModel.sendCode.onStart.observe(this) {
            showLoading("发送中")
        }
        mViewModel.sendCode.onSuccess.observe(this) {
            disLoading()
        }
        mViewModel.sendCode.onError.observe(this) {
            disLoading()
            showToast("${it.message}")
        }


        mViewModel.repData.onStart.observe(this) {
            showLoading("发送中")
        }
        mViewModel.repData.onSuccess.observe(this) {
            disLoading()
            ARouter.getInstance().build("/app/ForgetPwdFinishActivity")
                .withString("password", mDatabind.edtInputPwd.text.trim().toString())
                .withString("account", mDatabind.txtLoginAccountShow.text.trim().toString())
                .navigation()
        }
        mViewModel.repData.onError.observe(this) {
            disLoading()
            showToast("${it.message}")
        }
    }

    private fun isPasswordValid(password: String): Boolean {

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
}