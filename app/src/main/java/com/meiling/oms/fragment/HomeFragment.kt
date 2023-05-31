package com.meiling.oms.fragment

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.gyf.immersionbar.ImmersionBar
import com.meiling.common.fragment.BaseFragment
import com.meiling.oms.R
import com.meiling.oms.activity.OrderCreateActivity
import com.meiling.oms.adapter.BaseFragmentPagerAdapter
import com.meiling.oms.databinding.FragmentHomeBinding
import com.meiling.oms.eventBusData.MessageEventTabChange
import com.meiling.oms.viewmodel.HomeViewModel
import com.meiling.oms.widget.showToast
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class HomeFragment : BaseFragment<HomeViewModel, FragmentHomeBinding>() {


    companion object {
        fun newInstance() = HomeFragment()
    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.viewPagerOrder.isUserInputEnabled = false
        ImmersionBar.with(this).init()
        ImmersionBar.setTitleBar(this, mDatabind.clMy)

    }

    override fun getBind(inflater: LayoutInflater): FragmentHomeBinding {
        return FragmentHomeBinding.inflate(inflater)
    }

    var changeTab = false

    override fun initData() {
        val fragmentList: MutableList<Fragment> = ArrayList()
        fragmentList.add(HomeOningOrderFragment.newInstance())
        fragmentList.add(HomeHistoryOrderFragment.newInstance())
        mDatabind.viewPagerOrder.adapter =
            BaseFragmentPagerAdapter(childFragmentManager, lifecycle, fragmentList)
        resetting()
        mDatabind.viewPagerOrder.setCurrentItem(0, false)
//        mDatabind.txtPendingOrder.isSelected = true
    }

    override fun initListener() {
        mDatabind.txtPendingOrder.setOnClickListener {
            resetting()
            //  mDatabind.txtPendingOrder.isSelected = true
            //   mDatabind.txtPendingOrder.setTextColor(Color.WHITE)
            //  mDatabind.txtPendingOrder.setBackgroundResource(R.drawable.bg_order_tab1)
            mDatabind.viewPagerOrder.setCurrentItem(0, false)

        }
        mDatabind.txtHistoryOrder.setOnClickListener {
            resetting()
            mDatabind.txtHistoryOrder.isSelected = true
            mDatabind.tvHistoryOrder.visibility = View.VISIBLE
            mDatabind.tvPendingOrder.visibility = View.GONE
            // mDatabind.txtHistoryOrder.setTextColor(Color.WHITE)
            // mDatabind.txtHistoryOrder.setBackgroundResource(R.drawable.bg_order_tab2)
            mDatabind.viewPagerOrder.setCurrentItem(1, false)
        }

        mDatabind.imgSearchOrder.setOnClickListener {
            ARouter.getInstance().build("/app/PushDialogActivity")
                .withString("pushTitle", "1212" ?: "小喵来客")
                .withString("pushDetail","1212小喵来客")
                .withString("pushOrderId", "1212" ?: null)
                .navigation()
//            ARouter.getInstance().build("/app/Search1Activity").navigation()
        }
        mDatabind.imgCreateOrder.setOnClickListener {
            startActivity(Intent(requireContext(), OrderCreateActivity::class.java))
        }
//        mDatabind.aivSearch.setOnClickListener {
//            ARouter.getInstance().build("/app/Search1Activity").navigation()
//        }
//        mDatabind.aivPost.setOnClickListener {
//            ARouter.getInstance().build("/app/PostDetailActivity").navigation()
//        }
    }

    private fun resetting() {
        mDatabind.tvHistoryOrder.visibility = View.GONE
        mDatabind.tvPendingOrder.visibility = View.VISIBLE
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun eventChange(eventTabChange: MessageEventTabChange) {
        resetting()
        mViewModel.setUmToken()
        mDatabind.tvHistoryOrder.visibility = View.GONE
        mDatabind.tvPendingOrder.visibility = View.VISIBLE
//        mDatabind.txtPendingOrder.isSelected = true
//        mDatabind.txtPendingOrder.setTextColor(Color.WHITE)
//        mDatabind.txtPendingOrder.setBackgroundResource(R.drawable.bg_order_tab1)
        mDatabind.viewPagerOrder.setCurrentItem(0, false)
    }

    override fun onResume() {
        super.onResume()

//        val fragmentList: MutableList<Fragment> = ArrayList()
//        fragmentList.add(HomeOningOrderFragment.newInstance())
//        fragmentList.add(HomeHistoryOrderFragment.newInstance())
//        mDatabind.viewPagerOrder.offscreenPageLimit = 1
//        mDatabind.viewPagerOrder.adapter =
//            BaseFragmentPagerAdapter(childFragmentManager, lifecycle, fragmentList)
//        mDatabind.viewPagerOrder.setCurrentItem(0, true)
//        resetting()
//        mDatabind.txtPendingOrder.isSelected = true
//        mDatabind.txtPendingOrder.setTextColor(Color.WHITE)
//        mDatabind.txtPendingOrder.setBackgroundResource(R.drawable.bg_order_tab1)
//        mDatabind.viewPagerOrder.setCurrentItem(0, false)
    }
}