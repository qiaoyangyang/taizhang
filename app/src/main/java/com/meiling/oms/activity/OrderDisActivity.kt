package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.angcyo.tablayout.delegate2.ViewPager2Delegate
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.adapter.BaseFragmentPagerAdapter
import com.meiling.oms.databinding.ActivityDisBinding
import com.meiling.oms.fragment.*

@Route(path = "/app/OrderDisActivity")
class OrderDisActivity : BaseActivity<BaseViewModel, ActivityDisBinding>() {

    private val fragmentList: MutableList<Fragment> = ArrayList()

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.viewPager.isUserInputEnabled = false
        setBar(this,mDatabind.txtOrderDis)

        fragmentList.add(OrderDisFragment1.newInstance())
        fragmentList.add(OrderDisFragment2.newInstance())
        fragmentList.add(OrderDisFragment3.newInstance())
        mDatabind.viewPager.setCurrentItem(0, false)
        mDatabind.viewPager.adapter =
            BaseFragmentPagerAdapter(supportFragmentManager, lifecycle, fragmentList)
        ViewPager2Delegate.install(mDatabind.viewPager, mDatabind.tabLayout)

    }


    override fun getBind(layoutInflater: LayoutInflater): ActivityDisBinding {
        return ActivityDisBinding.inflate(layoutInflater)
    }



    override fun initListener() {
        mDatabind.aivBack.setOnClickListener { finish() }
    }
}