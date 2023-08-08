package com.meiling.account.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.meiling.account.adapter.FragAdapter
import com.meiling.account.databinding.ActivitySelectStoreBinding
import com.meiling.account.databinding.ActivitySetttingBinding
import com.meiling.account.fragment.BasicInformationFragment
import com.meiling.account.fragment.HomeFragment
import com.meiling.account.fragment.LoginPasswordFragment
import com.meiling.account.fragment.RecordsCenterFragment
import com.meiling.account.viewmodel.LoginViewModel
import com.meiling.account.widget.setSingleClickListener
import com.meiling.common.activity.BaseActivity

//设置页面
class SettingActivity : BaseActivity<LoginViewModel, ActivitySetttingBinding>() {
    var fragments = ArrayList<Fragment>()
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.tvBack.setSingleClickListener {
            finish()
        }
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivitySetttingBinding {
        return ActivitySetttingBinding.inflate(layoutInflater)
    }

    override fun initData() {

        fragments.add(BasicInformationFragment())

        fragments.add(LoginPasswordFragment())
        var fragAdapter = FragAdapter(supportFragmentManager, fragments)
        mDatabind.viewpager.adapter = fragAdapter
    }

}