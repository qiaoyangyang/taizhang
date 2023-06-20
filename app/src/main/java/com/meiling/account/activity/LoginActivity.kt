package com.meiling.account.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.meiling.account.databinding.ActivityLoginBinding
import com.meiling.account.databinding.ActivityMainBinding
import com.meiling.account.viewmodel.MainViewModel
import com.meiling.account.widget.KeyBoardUtil
import com.meiling.account.widget.setSingleClickListener
import com.meiling.common.activity.BaseActivity
import com.meiling.common.utils.InputTextManager

//登陆页面
class LoginActivity : BaseActivity<MainViewModel, ActivityLoginBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.edtName.setText("1")
        mDatabind.edtPaswd.setText("1")
        mDatabind.btnLogin.let {
            InputTextManager.with(this)
                .addView(mDatabind.edtName)
                .addView(mDatabind.edtPaswd)
                .setMain(it)
                .build()
            KeyBoardUtil.hideKeyBoard(this,mDatabind.btnLogin)
        }

        mDatabind.btnLogin.setSingleClickListener {
            startActivity(Intent(this, SelectStoreActiviy::class.java))
        }
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityLoginBinding {
        return ActivityLoginBinding.inflate(layoutInflater)
    }
}