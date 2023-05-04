package com.meiling.oms.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.databinding.ActivityAccountManagerBinding
import com.meiling.oms.viewmodel.AccountViewModel
import com.meiling.oms.widget.setSingleClickListener


class AccountManagerActivity : BaseActivity<AccountViewModel, ActivityAccountManagerBinding>() {

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityAccountManagerBinding {
        return ActivityAccountManagerBinding.inflate(layoutInflater)

    }

    override fun initListener() {
        mDatabind.txtCreateNewAccount.setSingleClickListener {
            startActivity(Intent(this, AccountNewCreateActivity::class.java))
        }
    }

}