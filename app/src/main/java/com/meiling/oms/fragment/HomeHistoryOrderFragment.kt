package com.meiling.oms.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.angcyo.tablayout.delegate2.ViewPager2Delegate
import com.meiling.common.fragment.BaseFragment
import com.meiling.common.network.data.SelectDialogDto
import com.meiling.oms.EventBusData.MessageHistoryEventTime
import com.meiling.oms.adapter.BaseFragmentPagerAdapter
import com.meiling.oms.databinding.FragmentHomeOrderHistoryBinding
import com.meiling.oms.dialog.OrderSelectDialog
import com.meiling.oms.viewmodel.BaseOrderFragmentViewModel
import com.meiling.oms.widget.formatCurrentDate
import com.meiling.oms.widget.formatCurrentDateBeforeWeek
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 历史订单
 * **/
class HomeHistoryOrderFragment :
    BaseFragment<BaseOrderFragmentViewModel, FragmentHomeOrderHistoryBinding>() {

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
        fragmentList.add(BaseHistoryOrderFragment.newInstance("", true))
        fragmentList.add(BaseHistoryOrderFragment.newInstance("70", true))
        mDatabind.viewPager.adapter =
            BaseFragmentPagerAdapter(childFragmentManager, lifecycle, fragmentList)
        mDatabind.viewPager.setCurrentItem(0, false)
        ViewPager2Delegate.install(mDatabind.viewPager, mDatabind.tabLayout)

        mDatabind.txtSelectOrder.setSingleClickListener {

            var orderSelectDialog = OrderSelectDialog().newInstance(
                SelectDialogDto(
                    startDate = formatCurrentDate(),
                    endDate = formatCurrentDate(),
                    timetype = 2,
                    status = "1", isVoucher = "1"
                )
            )
            orderSelectDialog.show(childFragmentManager)

        }
    }

    override fun onResume() {
        super.onResume()
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
            if (it.deliveryAll != 0) {
                mDatabind.tabLayout.updateTabBadge(0) {
                    badgeTextSize = 30f
                    badgeGravity = Gravity.RIGHT or Gravity.TOP
                    badgeText = it.deliveryAll.toString()
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
            showToast("${it.msg}")
        }

    }

    override fun getBind(inflater: LayoutInflater): FragmentHomeOrderHistoryBinding {
        return FragmentHomeOrderHistoryBinding.inflate(inflater)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun eventSelectTime(messageHistoryEventTime: MessageHistoryEventTime) {
        mViewModel.statusCount(
            logisticsStatus = "",
            startTime = messageHistoryEventTime.starTime,
            endTime = messageHistoryEventTime.endTime,
            businessNumberType = "1",
            pageIndex = "1",
            pageSize = "20",
            orderTime = "1",
            deliverySelect = "0",
            isValid = "0",
            businessNumber = ""
        )
    }

}