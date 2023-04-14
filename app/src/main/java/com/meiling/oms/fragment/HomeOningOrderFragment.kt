package com.meiling.oms.fragment

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.angcyo.tablayout.delegate2.ViewPager2Delegate
import com.meiling.common.fragment.BaseFragment
import com.meiling.common.network.data.OrderDto
import com.meiling.oms.EventBusData.MessageEventTime
import com.meiling.oms.EventBusData.MessageEventUpDataTip
import com.meiling.oms.R
import com.meiling.oms.adapter.BaseFragmentPagerAdapter
import com.meiling.oms.databinding.FragmentHomeOrderOningBinding
import com.meiling.oms.viewmodel.BaseOrderFragmentViewModel
import com.meiling.oms.viewmodel.NewsViewModel
import com.meiling.oms.widget.formatCurrentDate
import com.meiling.oms.widget.formatCurrentDateBeforeWeek
import com.meiling.oms.widget.showToast
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class HomeOningOrderFragment :
    BaseFragment<BaseOrderFragmentViewModel, FragmentHomeOrderOningBinding>() {


    companion object {
        fun newInstance() = HomeOningOrderFragment()
    }


    private val fragmentList: MutableList<Fragment> = ArrayList()

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.viewPager.isUserInputEnabled = false
        EventBus.getDefault().register(this)
    }

    //    logisticsStatus：0.待配送  20.带抢单 30.待取货 50.配送中 70.取消 80.已送达
    override fun initData() {
        fragmentList.add(BaseOrderFragment.newInstance("0", false))
        fragmentList.add(BaseOrderFragment.newInstance("20", false))
        fragmentList.add(BaseOrderFragment.newInstance("30", false))
        fragmentList.add(BaseOrderFragment.newInstance("50", false))
        fragmentList.add(BaseOrderFragment.newInstance("70", false))
        fragmentList.add(BaseOrderFragment.newInstance("80", false))
        mDatabind.viewPager.adapter =
            BaseFragmentPagerAdapter(childFragmentManager, lifecycle, fragmentList)
        mDatabind.viewPager.setCurrentItem(0, false)
        ViewPager2Delegate.install(mDatabind.viewPager, mDatabind.tabLayout)
        mViewModel.statusCount(
            logisticsStatus = "",
            startTime = formatCurrentDateBeforeWeek(),
            endTime = formatCurrentDate(),
            businessNumberType = "1",
            pageIndex = "1",
            pageSize = "20",
            orderTime = "1",
            deliverySelect = "0",
            isValid = "0",
            businessNumber = ""
        )

    }


    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun eventDay(messageEventTime: MessageEventUpDataTip) {
        showToast("更新气泡")
        mViewModel.statusCount(
            logisticsStatus = "",
            startTime = formatCurrentDateBeforeWeek(),
            endTime = formatCurrentDate(),
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
            if (it.deliveryOrder != 0) {
                mDatabind.tabLayout.updateTabBadge(1) {
                    badgeTextSize = 30f
                    badgeGravity = Gravity.RIGHT or Gravity.TOP
                    badgeText = it.deliveryOrder.toString()
                    badgeOffsetX = 5
                    badgeOffsetY = 30

                }
            }
            if (it.deliveryGoods != 0) {
                mDatabind.tabLayout.updateTabBadge(2) {
                    badgeTextSize = 30f
                    badgeGravity = Gravity.RIGHT or Gravity.TOP
                    badgeText = it.deliveryGoods.toString()
                    badgeOffsetX = 5
                    badgeOffsetY = 30

                }
            }
            if (it.deliverying != 0) {
                mDatabind.tabLayout.updateTabBadge(3) {
                    badgeTextSize = 30f
                    badgeGravity = Gravity.RIGHT or Gravity.TOP
                    badgeText = it.deliverying.toString()
                    badgeOffsetX = 5
                    badgeOffsetY = 30

                }
            }
            if (it.deliveryCancel != 0) {
                mDatabind.tabLayout.updateTabBadge(4) {
                    badgeTextSize = 30f
                    badgeGravity = Gravity.RIGHT or Gravity.TOP
                    badgeText = it.deliveryCancel.toString()
                    badgeOffsetX = 20
                    badgeOffsetY = 30

                }
            }
            if (it.deliveryComplete != 0) {
                mDatabind.tabLayout.updateTabBadge(5) {
                    badgeTextSize = 30f
                    badgeGravity = Gravity.RIGHT or Gravity.TOP
                    badgeText = it.deliveryComplete.toString()
                    badgeOffsetX = 10
                    badgeOffsetY = 30

                }
            }
            Log.e("order", "createObserver: " + it)
        }
        mViewModel.statusCountDto.onError.observe(this) {
            dismissLoading()
            showToast("${it.message}")
        }

    }

    override fun getBind(inflater: LayoutInflater): FragmentHomeOrderOningBinding {
        return FragmentHomeOrderOningBinding.inflate(inflater)
    }


}