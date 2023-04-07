package com.meiling.oms.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.Gravity.RIGHT
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.angcyo.tablayout.delegate2.ViewPager2Delegate
import com.meiling.common.fragment.BaseFragment
import com.meiling.oms.adapter.BaseFragmentPagerAdapter
import com.meiling.oms.databinding.FragmentHomeOrderHistoryBinding
import com.meiling.oms.viewmodel.BaseOrderFragmentViewModel
import com.meiling.oms.viewmodel.RankingViewModel
import com.meiling.oms.widget.showToast

/**
 * 历史订单
 * **/
class HomeHistoryOrderFragment : BaseFragment<BaseOrderFragmentViewModel, FragmentHomeOrderHistoryBinding>() {

    private val fragmentList: MutableList<Fragment> = ArrayList()

    companion object {
        fun newInstance(): Fragment {
            return HomeHistoryOrderFragment()
        }
    }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.viewPager.isUserInputEnabled = false
    }

    //    logisticsStatus：0.待配送  20.带抢单 30.待取货 50.配送中 70.取消 80.已送达
    @SuppressLint("RtlHardcoded")
    override fun initData() {
        var isSelect = arguments?.getBoolean("isSelect", true)
        fragmentList.add(BaseOrderFragment.newInstance("", true))
        fragmentList.add(BaseOrderFragment.newInstance("70", true))
        mDatabind.viewPager.adapter =
            BaseFragmentPagerAdapter(childFragmentManager, lifecycle, fragmentList)
        mDatabind.viewPager.setCurrentItem(0, false)
        ViewPager2Delegate.install(mDatabind.viewPager, mDatabind.tabLayout)
        mViewModel.statusCount(
            logisticsStatus = "",
            startTime = "2023-04-06",
            endTime = "2023-04-06",
            businessNumberType = "1",
            pageIndex = "1",
            pageSize = "20",
            orderTime = "1",
            deliverySelect = "0",
            isValid = "0",
            businessNumber = ""
        )
    }
    override fun createObserver() {

        mViewModel.statusCountDto.onSuccess.observe(this) {
            dismissLoading()
            if (it.deliveryNot != 0) {
                mDatabind.tabLayout.updateTabBadge(0) {
                    badgeTextSize = 30f
                    badgeGravity = Gravity.RIGHT or Gravity.TOP
                    badgeText = it.deliveryNot.toString()
                    badgeOffsetX = 5
                    badgeOffsetY = 30

                }
            }

            if (it.deliveryCancel != 0) {
                mDatabind.tabLayout.updateTabBadge(1) {
                    badgeTextSize = 30f
                    badgeGravity = Gravity.RIGHT or Gravity.TOP
                    badgeText = it.deliveryCancel.toString()
                    badgeOffsetX = 5
                    badgeOffsetY = 30
                }
            }

        }
        mViewModel.statusCountDto.onError.observe(this) {
            dismissLoading()
            showToast("${it.message}")
        }

    }

    override fun getBind(inflater: LayoutInflater): FragmentHomeOrderHistoryBinding {
        return FragmentHomeOrderHistoryBinding.inflate(inflater)
    }

}