package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.angcyo.tablayout.delegate2.ViewPager2Delegate
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.adapter.BaseFragmentPagerAdapter
import com.meiling.oms.databinding.ActivityMessageCenterBinding
import com.meiling.oms.fragment.MessageCenterFragment

@Route(path = "/app/MessageCenterActivity")
class MessageCenterActivity : BaseActivity<BaseViewModel, ActivityMessageCenterBinding>() {


    override fun initView(savedInstanceState: Bundle?) {
        val fragments: MutableList<Fragment> = arrayListOf()
        fragments.add(
            MessageCenterFragment.newInstance()
        )
        fragments.add(
            MessageCenterFragment.newInstance()
        )
        fragments.add(
            MessageCenterFragment.newInstance()
        )
        mDatabind.viewPager.adapter =
            BaseFragmentPagerAdapter(supportFragmentManager, lifecycle, fragments)
        ViewPager2Delegate.install(mDatabind.viewPager, mDatabind.tabLayout)
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityMessageCenterBinding {
        return ActivityMessageCenterBinding.inflate(layoutInflater)
    }


    override fun initListener() {
        mDatabind.aivBack.setOnClickListener { finish() }
    }

}