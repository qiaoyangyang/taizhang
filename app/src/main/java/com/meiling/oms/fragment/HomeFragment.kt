package com.meiling.oms.fragment

import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.meiling.common.fragment.BaseFragment
import com.meiling.oms.R
import com.meiling.oms.adapter.BaseFragmentPagerAdapter
import com.meiling.oms.databinding.FragmentHomeBinding
import com.meiling.oms.viewmodel.HomeViewModel

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {


    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    private val fragmentList: MutableList<Fragment> = ArrayList()

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.viewPagerOrder.isUserInputEnabled = false

    }

    override fun getBind(inflater: LayoutInflater): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater)
    }

    override fun initData() {
        fragmentList.add(HomeOningOrderFragment.newInstance())
        fragmentList.add(HomeHistoryOrderFragment.newInstance())
        mDatabind.viewPagerOrder.adapter =
            BaseFragmentPagerAdapter(childFragmentManager, lifecycle, fragmentList)
        mDatabind.viewPagerOrder.setCurrentItem(0, true)
        mDatabind.txtPendingOrder.isSelected = true
    }

    override fun initListener() {
        mDatabind.txtPendingOrder.setOnClickListener {
            resetting()
            mDatabind.txtPendingOrder.isSelected = true
            mDatabind.txtPendingOrder.setTextColor(Color.WHITE)
            mDatabind.txtPendingOrder.setBackgroundResource(R.drawable.bg_order_tab1)
            mDatabind.viewPagerOrder.setCurrentItem(0, false)
        }
        mDatabind.txtHistoryOrder.setOnClickListener {
            resetting()
            mDatabind.txtHistoryOrder.isSelected = true
            mDatabind.txtHistoryOrder.setTextColor(Color.WHITE)
            mDatabind.txtHistoryOrder.setBackgroundResource(R.drawable.bg_order_tab2)
            mDatabind.viewPagerOrder.setCurrentItem(1, false)
        }

        mDatabind.llSearch.setOnClickListener {
            ARouter.getInstance().build("/app/Search1Activity").navigation()
        }
//        mDatabind.aivSearch.setOnClickListener {
//            ARouter.getInstance().build("/app/Search1Activity").navigation()
//        }
//        mDatabind.aivPost.setOnClickListener {
//            ARouter.getInstance().build("/app/PostDetailActivity").navigation()
//        }
    }

    private fun resetting() {
        mDatabind.txtPendingOrder.isSelected = false
        mDatabind.txtHistoryOrder.isSelected = false
        mDatabind.txtPendingOrder.setBackgroundResource(0)
        mDatabind.txtHistoryOrder.setBackgroundResource(0)
        mDatabind.txtPendingOrder.setTextColor(resources.getColor(R.color.home_A8ABB2))
        mDatabind.txtHistoryOrder.setTextColor(resources.getColor(R.color.home_A8ABB2))
        mDatabind.llChangeOrder.setBackgroundResource(R.drawable.bg_order_tab)
    }

    override fun onResume() {
        super.onResume()
        mViewModel.setUmToken()

    }
}