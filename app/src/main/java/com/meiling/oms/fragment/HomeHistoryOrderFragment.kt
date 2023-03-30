package com.meiling.oms.fragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.angcyo.tablayout.delegate2.ViewPager2Delegate
import com.meiling.common.fragment.BaseFragment
import com.meiling.oms.adapter.BaseFragmentPagerAdapter
import com.meiling.oms.databinding.FragmentHomeOrderHistoryBinding
import com.meiling.oms.viewmodel.RankingViewModel

/**
 * 历史订单
 * **/
class HomeHistoryOrderFragment : BaseFragment<RankingViewModel, FragmentHomeOrderHistoryBinding>() {

    private val fragmentList: MutableList<Fragment> = ArrayList()

    companion object {
        fun newInstance() = HomeHistoryOrderFragment()
    }


    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.viewPager.isUserInputEnabled = false
    }

    override fun initData() {

        fragmentList.add(BaseOrderFragment.newInstance(1))
        fragmentList.add(BaseOrderFragment.newInstance(2))
        mDatabind.viewPager.adapter =
            BaseFragmentPagerAdapter(childFragmentManager, lifecycle, fragmentList)
        mDatabind.viewPager.setCurrentItem(0, false)
        ViewPager2Delegate.install(mDatabind.viewPager, mDatabind.tabLayout)

        mDatabind.tabLayout.updateTabBadge(0) {
            badgeGravity = Gravity.RIGHT or Gravity.TOP
            badgeText = "99+"
            badgeTextSize = 30f
            badgeOffsetX = 5
            badgeOffsetY = 30

        }
        mDatabind.tabLayout.updateTabBadge(1) {
            badgeGravity = Gravity.RIGHT or Gravity.TOP
            badgeText = "1"
            badgeTextSize = 30f
            badgeOffsetX = 5
            badgeOffsetY = 30
        }
    }

    override fun getBind(inflater: LayoutInflater): FragmentHomeOrderHistoryBinding {
        return FragmentHomeOrderHistoryBinding.inflate(inflater)
    }

}