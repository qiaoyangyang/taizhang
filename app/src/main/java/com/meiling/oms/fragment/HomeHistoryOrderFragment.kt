package com.meiling.oms.fragment

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.angcyo.tablayout.delegate2.ViewPager2Delegate
import com.meiling.common.fragment.BaseFragment
import com.meiling.common.network.data.SelectDialogDto
import com.meiling.oms.adapter.BaseFragmentPagerAdapter
import com.meiling.oms.databinding.FragmentHomeOrderHistoryBinding
import com.meiling.oms.dialog.OrderSelectDialog
import com.meiling.oms.eventBusData.MessageEventHistoryUpDataTip
import com.meiling.oms.eventBusData.MessageHistoryEventSelect
import com.meiling.oms.viewmodel.BaseOrderFragmentViewModel
import com.meiling.oms.widget.formatCurrentDate
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast
import org.greenrobot.eventbus.EventBus
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

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    var selectDialogDto = SelectDialogDto(
        startDate = formatCurrentDate(),
        endDate = formatCurrentDate(),
        timetype = 2,
        orderTime = "1",
        channelId = "0",
        isValid = ""
    )

    //    logisticsStatus：0.待配送  20.带抢单 30.待取货 50.配送中 70.取消 80.已送达
    @SuppressLint("RtlHardcoded")
    override fun initData() {
        fragmentList.add(OrderBaseHistoryFragment.newInstance("", true))
        fragmentList.add(OrderBaseHistoryFragment.newInstance("70", true))
        mDatabind.viewPager.adapter =
            BaseFragmentPagerAdapter(childFragmentManager, lifecycle, fragmentList)
        mDatabind.viewPager.setCurrentItem(0, false)
        ViewPager2Delegate.install(mDatabind.viewPager, mDatabind.tabLayout)

        mDatabind.txtSelectOrder.setSingleClickListener {
            var orderSelectDialog = OrderSelectDialog().newInstance(
                selectDialogDto
            )
            orderSelectDialog.setSelectOrder {
                selectDialogDto = it
                EventBus.getDefault().post(MessageHistoryEventSelect(it, ""))
                mViewModel.statusCount(
                    logisticsStatus = "",
                    startTime = it.startDate,
                    endTime = it.endDate,
                    businessNumberType = "1",
                    pageIndex = "1",
                    pageSize = "20",
                    orderTime = it.orderTime,
                    deliverySelect = "0",
                    isValid = it.isValid,
                    businessNumber = "",
                    channelId = it.channelId!!
                )
            }

            orderSelectDialog.setSelectCloseOrder {
                selectDialogDto = it
            }
            orderSelectDialog.show(childFragmentManager)
        }
    }

    override fun onResume() {
        super.onResume()
        selectDialogDto = SelectDialogDto(
            startDate = formatCurrentDate(),
            endDate = formatCurrentDate(),
            timetype = 2,
            orderTime = "1",
            channelId = "0",
            isValid = ""
        )
        EventBus.getDefault().post(MessageHistoryEventSelect(selectDialogDto, ""))
        mViewModel.statusCount(
            logisticsStatus = "",
            startTime = formatCurrentDate(),
            endTime = formatCurrentDate(),
            businessNumberType = "1",
            pageIndex = "1",
            pageSize = "20",
            orderTime = "1",
            deliverySelect = "0",
            isValid = "",
            businessNumber = "",
            channelId = "0"
        )
    }

    override fun createObserver() {

        mViewModel.statusCountDto.onSuccess.observe(this) {
            dismissLoading()
            mDatabind.tabLayout.updateTabBadge(0) {
                badgeGravity = Gravity.RIGHT or Gravity.TOP
                badgeText = if (it.deliveryAll == 0) {
                    "--"
                } else {
                    it.deliveryAll.toString()
                }
                badgeOffsetY = -20

            }

            mDatabind.tabLayout.updateTabBadge(1) {
                badgeGravity = Gravity.CENTER or Gravity.TOP
                badgeText = if (it.deliveryCancel == 0) {
                    "--"
                } else {
                    it.deliveryCancel.toString()
                }
                badgeOffsetY = -20
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
    fun eventSelectTime(messageEventHistoryUpDataTip: MessageEventHistoryUpDataTip) {
        mViewModel.statusCount(
            logisticsStatus = "",
            startTime = selectDialogDto.startDate,
            endTime = selectDialogDto.endDate,
            businessNumberType = "1",
            pageIndex = "1",
            pageSize = "20",
            orderTime = selectDialogDto.orderTime,
            deliverySelect = "0",
            isValid = "",
            businessNumber = selectDialogDto.isValid,
            channelId = selectDialogDto.channelId!!
        )
    }

}