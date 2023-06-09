package com.meiling.oms.activity

import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.angcyo.tablayout.delegate2.ViewPager2Delegate
import com.gyf.immersionbar.ImmersionBar
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.SelectDialogDto
import com.meiling.oms.adapter.BaseFragmentPagerAdapter
import com.meiling.oms.databinding.ActivityOrderHistoryBinding
import com.meiling.oms.dialog.OrderSelectDialog
import com.meiling.oms.dialog.OrderSelectStoreDialog
import com.meiling.oms.eventBusData.MessageEventHistoryUpDataTip
import com.meiling.oms.eventBusData.MessageHistoryEventSelect
import com.meiling.oms.fragment.OrderBaseHistoryFragment
import com.meiling.oms.viewmodel.BaseOrderFragmentViewModel
import com.meiling.oms.widget.formatCurrentDate
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class OrderHistoryActivity :
    BaseActivity<BaseOrderFragmentViewModel, ActivityOrderHistoryBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.viewPager.isUserInputEnabled = true
        EventBus.getDefault().register(this)


    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityOrderHistoryBinding {
        return ActivityOrderHistoryBinding.inflate(layoutInflater)
    }

    override fun createObserver() {
        mViewModel.statusCountDto.onStart.observe(this) {
        }
        mViewModel.statusCountDto.onSuccess.observe(this) {
            mDatabind.tabLayout.updateTabBadge(0) {
                badgeGravity = Gravity.CENTER or Gravity.TOP
                badgeText = if (it.deliveryAll == 0) {
                    "--"
                } else {
                    it.deliveryAll.toString()
                }
                badgeOffsetY = -23
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
            showToast("${it.msg}")
        }
    }

    private val fragmentList: MutableList<Fragment> = ArrayList()
    override fun initData() {
        fragmentList.add(OrderBaseHistoryFragment.newInstance("", true))
        fragmentList.add(OrderBaseHistoryFragment.newInstance("70", true))
        mDatabind.viewPager.adapter =
            BaseFragmentPagerAdapter(supportFragmentManager, lifecycle, fragmentList)
        mDatabind.viewPager.setCurrentItem(0, true)
        ViewPager2Delegate.install(mDatabind.viewPager, mDatabind.tabLayout)
        mDatabind.viewPager.offscreenPageLimit = 1
        // ImmersionBar.with(this).statusBarDarkFont(true) .autoDarkModeEnable(true, 0.2f).init()
        ImmersionBar.setTitleBar(this, mDatabind.clMy)
    }

    var poiId = "0"

    private var selectDialogDto = SelectDialogDto(
        startDate = formatCurrentDate(),
        endDate = formatCurrentDate(),
        timetype = 2,
        orderTime = "1",
        channelId = "0",
        isValid = "",
    )

    override fun initListener() {
        mDatabind.imgSearchOrder.setOnClickListener {
            mDatabind.imgSearchOrder.setOnClickListener {
                ARouter.getInstance().build("/app/Search1Activity").navigation()
            }
        }
        mDatabind.imgOrderScreen.setOnClickListener {
            var orderSelectDialog = OrderSelectDialog().newInstance(
                selectDialogDto
            )
            orderSelectDialog.setSelectOrder {
                selectDialogDto = it
                EventBus.getDefault().post(MessageHistoryEventSelect(it, poiId))
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
            orderSelectDialog.show(supportFragmentManager)
        }

        mDatabind.txtSelectStore.setSingleClickListener {
            var orderSelectStoreDialog = OrderSelectStoreDialog().newInstance(
                mDatabind.txtSelectStore.text.toString(),
            )
            orderSelectStoreDialog.show(supportFragmentManager)
            orderSelectStoreDialog.setOkClickItemLister { arrayList, isSelectAll ->
                mDatabind.txtSelectStore.text = isSelectAll
                poiId = arrayList[0].poiIds!!
                EventBus.getDefault().post(MessageHistoryEventSelect(selectDialogDto, poiId))
            }
        }

    }

    override fun onDestroy() {
        super.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().post(MessageHistoryEventSelect(selectDialogDto, ""))
        eventSelectTime(MessageEventHistoryUpDataTip())
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
            isValid = selectDialogDto.isValid,
            businessNumber = "",
            channelId = selectDialogDto.channelId!!,
            poiId = poiId
        )
    }
}