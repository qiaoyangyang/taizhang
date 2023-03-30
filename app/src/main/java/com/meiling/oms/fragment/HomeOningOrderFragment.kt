package com.meiling.oms.fragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.angcyo.tablayout.delegate2.ViewPager2Delegate
import com.meiling.common.fragment.BaseFragment
import com.meiling.oms.adapter.BaseFragmentPagerAdapter
import com.meiling.oms.databinding.FragmentHomeOrderOningBinding
import com.meiling.oms.viewmodel.NewsViewModel

class HomeOningOrderFragment : BaseFragment<NewsViewModel, FragmentHomeOrderOningBinding>() {


    companion object {
        fun newInstance() = HomeOningOrderFragment()
    }


    private val fragmentList: MutableList<Fragment> = ArrayList()

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.viewPager.isUserInputEnabled = false
    }

    override fun initData() {
        fragmentList.add(BaseOrderFragment.newInstance(1))
        fragmentList.add(BaseOrderFragment.newInstance(2))
        fragmentList.add(BaseOrderFragment.newInstance(3))
        fragmentList.add(BaseOrderFragment.newInstance(4))
        fragmentList.add(BaseOrderFragment.newInstance(5))
        fragmentList.add(BaseOrderFragment.newInstance(6))
        mDatabind.viewPager.adapter =
            BaseFragmentPagerAdapter(childFragmentManager, lifecycle, fragmentList)
        mDatabind.viewPager.setCurrentItem(0, false)
        ViewPager2Delegate.install(mDatabind.viewPager, mDatabind.tabLayout)
        mDatabind.tabLayout.updateTabBadge(0) {
            badgeTextSize = 30f
            badgeGravity = Gravity.RIGHT or Gravity.TOP
            badgeText = "9"
            badgeOffsetX = 5
            badgeOffsetY = 30

        }
        mDatabind.tabLayout.updateTabBadge(1) {
            badgeGravity = Gravity.RIGHT or Gravity.TOP
            badgeTextSize = 30f
            badgeText = "99+"
            badgeOffsetX = 5
            badgeOffsetY = 30

        }

    }

    override fun getBind(inflater: LayoutInflater): FragmentHomeOrderOningBinding {
        return FragmentHomeOrderOningBinding.inflate(inflater)
    }


}