package com.meiling.account.activity

import android.content.Intent
import android.os.Bundle
import android.preference.Preference
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ActivityUtils
import com.blankj.utilcode.util.ToastUtils
import com.meiling.common.network.NetworkMonitorManager
import com.meiling.common.network.enums.NetworkState
import com.meiling.common.network.interfaces.NetworkMonitor
import com.meiling.common.network.util.NetworkStateUtils
import com.meiling.account.databinding.ActivityLoginBinding
import com.meiling.account.dialog.MineExitDialog
import com.meiling.account.viewmodel.LoginViewModel
import com.meiling.account.widget.KeyBoardUtil
import com.meiling.account.widget.setSingleClickListener
import com.meiling.account.widget.showToast
import com.meiling.common.activity.BaseActivity
import com.meiling.common.constant.ARouteConstants
import com.meiling.common.constant.SPConstants
import com.meiling.common.utils.Constant
import com.meiling.common.utils.InputTextManager
import com.meiling.common.utils.MMKVUtils

//登陆页面
@Route(path = "/app/LoginActivity")
class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>() {


    override fun initView(savedInstanceState: Bundle?) {

        mDatabind.edtName.setText("15535958281")
        mDatabind.edtPaswd.setText("123456")
        //监听是否添加用户名和密码
        mDatabind.btnLogin.let {
            InputTextManager.with(this)
                .addView(mDatabind.edtName)
                .addView(mDatabind.edtPaswd)
                .setMain(it)
                .build()
            KeyBoardUtil.hideKeyBoard(this, mDatabind.btnLogin)
        }

        //登陆按钮
        mDatabind.btnLogin.setSingleClickListener {
            mViewModel.mobileLogin(
                mDatabind.edtName.text.toString(),
                mDatabind.edtPaswd.text.toString()
            )

            //setSaveaccount()
        }
        //清楚账号
        mDatabind.imgClear.setSingleClickListener {
            mDatabind.edtName.setText("")
        }
        //
        mDatabind.edtName.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (TextUtils.isEmpty(mDatabind.edtName.text.toString())) {
                    mDatabind.imgClear.visibility = View.GONE
                } else {
                    mDatabind.imgClear.visibility = View.VISIBLE
                }
            }

        })


    }


    override fun getBind(layoutInflater: LayoutInflater): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.loginData.onStart.observe(this) {
            showLoading("请稍后...")
        }
        mViewModel.loginData.onSuccess.observe(this) {
            disLoading()
            MMKVUtils.putString(SPConstants.TOKEN, it.token!!)
            MMKVUtils.putString(SPConstants.tenantId, it.tenantId!!)
            MMKVUtils.putString(SPConstants.userViewId, it.userViewId!!)
            MMKVUtils.putString(SPConstants.userViewId, it.userViewId!!)
            showLoading("请稍后...")
            mViewModel.userInfo()

            // TODO:
            // startActivity(Intent(this, SelectStoreActiviy::class.java))
        }
        mViewModel.loginData.onError.observe(this) {
            disLoading()
            showToast(it.msg)
            //disLoading()
        }

        mViewModel.userBean.onStart.observe(this) {
            showLoading("请稍后...")
        }
        mViewModel.userBean.onSuccess.observe(this) {
            disLoading()
            SaveUserBean(it)
            MMKVUtils.putBoolean(SPConstants.LOGINSTASTS,true)
            startActivity(Intent(this, SelectStoreActiviy::class.java))
        }
        mViewModel.userBean.onError.observe(this) {
            disLoading()
            showToast(it.msg)
        }
    }

    override fun onResume() {
        super.onResume()
        hasNetWork(true)

    }

    fun setSaveaccount() {
        val dialog: MineExitDialog =
            MineExitDialog().newInstance("温馨提示", "是否记住密码，以便下次直接登录？", "取消", "确认", false)
        dialog.setOkClickLister {

            // mViewModel.setUmengToken()
            startActivity(Intent(this, SelectStoreActiviy::class.java))

        }
        dialog.show(supportFragmentManager)
    }


}