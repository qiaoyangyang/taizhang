package com.meiling.account.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import com.meiling.account.R
import com.meiling.account.adapter.FragAdapter
import com.meiling.account.adapter.SetAdapter
import com.meiling.account.adapter.TabAdapter
import com.meiling.account.databinding.ActivitySelectStoreBinding
import com.meiling.account.databinding.ActivitySetttingBinding
import com.meiling.account.fragment.*
import com.meiling.account.viewmodel.LoginViewModel
import com.meiling.account.widget.InputUtil
import com.meiling.account.widget.setSingleClickListener
import com.meiling.common.activity.BaseActivity

//设置页面
class SettingActivity : BaseActivity<LoginViewModel, ActivitySetttingBinding>() {
    var fragments = ArrayList<Fragment>()
    private var setAdapter: SetAdapter? = null
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.tvBack.setSingleClickListener {
            finish()
        }

        mDatabind.rvSet.layoutManager = LinearLayoutManager(this)
        setAdapter = SetAdapter(R.layout.layout_item_set)
        mDatabind.rvSet.adapter = setAdapter
        setAdapter?.setList(InputUtil.gettsettinglist())
        setAdapter?.setOnItemClickListener { adapter, view, position ->
            setAdapter?.data?.forEach {
                it.isselect = false
            }
            setAdapter?.data?.get(position)?.isselect = true
            setAdapter?.notifyDataSetChanged()
            mDatabind.viewpager.setCurrentItem(position, false)
        }


    }

    override fun getBind(layoutInflater: LayoutInflater): ActivitySetttingBinding {
        return ActivitySetttingBinding.inflate(layoutInflater)
    }

    override fun initData() {

        fragments.add(BasicInformationFragment())
        fragments.add(LoginPasswordFragment())
        fragments.add(WechatLoginFragment())
        fragments.add(SwitchStoresFragment())
        fragments.add(SystemSettingsFragment())
        var fragAdapter = FragAdapter(supportFragmentManager, fragments)
        mDatabind.viewpager.adapter = fragAdapter
    }

}