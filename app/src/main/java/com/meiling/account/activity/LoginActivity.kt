package com.meiling.account.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.meiling.common.network.NetworkMonitorManager
import com.meiling.common.network.enums.NetworkState
import com.meiling.common.network.interfaces.NetworkMonitor
import com.meiling.common.network.util.NetworkStateUtils
import com.meiling.account.databinding.ActivityLoginBinding
import com.meiling.account.viewmodel.LoginViewModel
import com.meiling.account.widget.KeyBoardUtil
import com.meiling.account.widget.setSingleClickListener
import com.meiling.account.widget.showToast
import com.meiling.common.activity.BaseActivity
import com.meiling.common.utils.InputTextManager

//登陆页面
class LoginActivity : BaseActivity<LoginViewModel, ActivityLoginBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
//        mDatabind.edtName.setText("1")
//        mDatabind.edtPaswd.setText("1")
        mDatabind.btnLogin.let {
            InputTextManager.with(this)
                .addView(mDatabind.edtName)
                .addView(mDatabind.edtPaswd)
                .setMain(it)
                .build()
            KeyBoardUtil.hideKeyBoard(this,mDatabind.btnLogin)
        }

        mDatabind.btnLogin.setSingleClickListener {
          // mViewModel.mobileLogin(mDatabind.edtName.text.toString(),mDatabind.edtPaswd.text.toString())
            startActivity(Intent(this, SelectStoreActiviy::class.java))

        }
        mDatabind.imgClear.setSingleClickListener {
            mDatabind.edtName.setText("")
        }
        mDatabind.edtName.addTextChangedListener(object : TextWatcher{
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (TextUtils.isEmpty(mDatabind.edtName.text.toString())){
                    mDatabind.imgClear.visibility=View.GONE
                }else{
                    mDatabind.imgClear.visibility=View.VISIBLE
                }
            }

        })




    }


    override fun getBind(layoutInflater: LayoutInflater): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.loginData.onStart.observe(this){
            showLoading("请稍后...")
        }
        mViewModel.loginData.onSuccess.observe(this){
            disLoading()
            // TODO:
            startActivity(Intent(this, SelectStoreActiviy::class.java))
        }
        mViewModel.loginData.onError.observe(this){
            showToast(it.msg)
            //disLoading()
        }
    }

    override fun onResume() {
        super.onResume()
        hasNetWork(true)

    }




}