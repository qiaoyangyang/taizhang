package com.meiling.oms.fragment

import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.angcyo.tablayout.delegate2.ViewPager2Delegate
import com.meiling.common.fragment.BaseFragment
import com.meiling.oms.eventBusData.MessageEventUpDataTip
import com.meiling.oms.adapter.BaseFragmentPagerAdapter
import com.meiling.oms.databinding.FragmentHomeOrderOningBinding
import com.meiling.oms.viewmodel.BaseOrderFragmentViewModel
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

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
    override fun initView(savedInstanceState: Bundle?) {

        mDatabind.viewPager.isUserInputEnabled = false
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
        mDatabind.viewPager.offscreenPageLimit = 1
    }


    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        eventDay(MessageEventUpDataTip())
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun eventDay(messageEventTime: MessageEventUpDataTip) {
        mViewModel.statusCount(
            logisticsStatus = "",
            startTime = formatCurrentDateBeforeWeek(),
            endTime = formatCurrentDate(),
            businessNumberType = "1",
            pageIndex = "1",
            pageSize = "20",
            orderTime = "1",
            deliverySelect = "0",
            isValid = "",
            businessNumber = ""
        )
    }

    override fun createObserver() {
        mViewModel.statusCountDto.onStart.observe(this) {
        }
        mViewModel.statusCountDto.onSuccess.observe(this) {
            mDatabind.tabLayout.updateTabBadge(0) {
                badgeGravity =  Gravity.CENTER or Gravity.TOP
                badgeText = if (it.deliveryNot == 0) {
                    "--"
                } else {
                    it.deliveryNot.toString()
                }
                badgeOffsetY = -20
            }
            mDatabind.tabLayout.updateTabBadge(1) {
                badgeGravity =  Gravity.CENTER or Gravity.TOP
                badgeText = if (it.deliveryOrder == 0) {
                    "--"
                } else {
                    it.deliveryOrder.toString()
                }
                badgeOffsetY = -20

            }
            mDatabind.tabLayout.updateTabBadge(2) {
                badgeGravity =  Gravity.CENTER or Gravity.TOP
                badgeText = if (it.deliveryGoods == 0) {
                    "--"
                } else {
                    it.deliveryGoods.toString()
                }
                badgeOffsetY = -20

            }
            mDatabind.tabLayout.updateTabBadge(3) {
                badgeGravity =  Gravity.CENTER or Gravity.TOP
                badgeText = if (it.deliverying == 0) {
                    "--"
                } else {
                    it.deliverying.toString()
                }
                badgeOffsetY = -20
            }
            mDatabind.tabLayout.updateTabBadge(4) {
                badgeGravity =  Gravity.CENTER or Gravity.TOP
                badgeText = if (it.deliveryCancel == 0) {
                    "--"
                } else {
                    it.deliveryCancel.toString()
                }
                badgeOffsetY = -20
            }
//            mDatabind.tabLayout.updateTabBadge(5) {
//                badgeTextSize = 30f
//                badgeGravity = Gravity.RIGHT or Gravity.TOP
//                badgeText = if (it.deliveryComplete == 0) {
//                    null
//                } else {
//                    it.deliveryComplete.toString()
//                }
//                badgeOffsetX = 10
//                badgeOffsetY = 30
//
//            }
            Log.e("order", "createObserver: " + it)
        }
        mViewModel.statusCountDto.onError.observe(this) {
            showToast("${it.msg}")
        }
    }

    override fun getBind(inflater: LayoutInflater): FragmentHomeOrderOningBinding {
        return FragmentHomeOrderOningBinding.inflate(inflater)
    }


}