package com.meiling.oms.fragment

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import com.meiling.common.fragment.BaseFragment
import com.meiling.oms.databinding.FragmentHomeOrderHistoryBinding
import com.meiling.oms.viewmodel.RankingViewModel

class HomeHistoryOrderFragment : BaseFragment<RankingViewModel, FragmentHomeOrderHistoryBinding>() {
    companion object {
        fun newInstance() = HomeHistoryOrderFragment()
    }


    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {
        mDatabind.tabLayout.updateTabBadge(0) {
            badgeGravity = Gravity.RIGHT or Gravity.TOP
            badgeText = "99+"
            badgeTextSize = 30f
            badgeOffsetX =5
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