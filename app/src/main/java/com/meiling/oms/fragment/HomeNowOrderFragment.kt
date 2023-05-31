package com.meiling.oms.fragment

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.angcyo.tablayout.delegate2.ViewPager2Delegate
import com.gyf.immersionbar.ImmersionBar
import com.meiling.common.fragment.BaseFragment
import com.meiling.common.network.data.SelectDialogDto
import com.meiling.oms.activity.OrderCreateActivity
import com.meiling.oms.adapter.BaseFragmentPagerAdapter
import com.meiling.oms.databinding.FragmentHomeOrderOningBinding
import com.meiling.oms.dialog.OrderSelectDialog
import com.meiling.oms.dialog.OrderSelectStoreDialog
import com.meiling.oms.eventBusData.MessageEventUpDataTip
import com.meiling.oms.eventBusData.MessageHistoryEventSelect
import com.meiling.oms.viewmodel.BaseOrderFragmentViewModel
import com.meiling.oms.widget.formatCurrentDate
import com.meiling.oms.widget.formatCurrentDateBeforeWeek
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class HomeNowOrderFragment :
    BaseFragment<BaseOrderFragmentViewModel, FragmentHomeOrderOningBinding>() {


    companion object {
        fun newInstance() = HomeNowOrderFragment()
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
        fragmentList.add(OrderBaseFragment.newInstance("0", false))
        fragmentList.add(OrderBaseFragment.newInstance("20", false))
        fragmentList.add(OrderBaseFragment.newInstance("30", false))
        fragmentList.add(OrderBaseFragment.newInstance("50", false))
        fragmentList.add(OrderBaseFragment.newInstance("70", false))
        fragmentList.add(OrderBaseFragment.newInstance("80", false))
        mDatabind.viewPager.adapter =
            BaseFragmentPagerAdapter(childFragmentManager, lifecycle, fragmentList)
        mDatabind.viewPager.setCurrentItem(0, false)
        ViewPager2Delegate.install(mDatabind.viewPager, mDatabind.tabLayout)
        mDatabind.viewPager.offscreenPageLimit = 1
       // ImmersionBar.with(this).statusBarDarkFont(true) .autoDarkModeEnable(true, 0.2f).init()
        ImmersionBar.setTitleBar(this, mDatabind.clMy)
    }
    var selectDialogDto = SelectDialogDto(
        startDate = formatCurrentDate(),
        endDate = formatCurrentDate(),
        timetype = 2,
        orderTime = "1",
        channelId = "0",
    )
    override fun initListener() {
        mDatabind.imgSearchOrder.setOnClickListener {
            ARouter.getInstance().build("/app/Search1Activity").navigation()
        }
        mDatabind.imgCreateOrder.setOnClickListener {
            var orderSelectDialog = OrderSelectDialog().newInstance(
                selectDialogDto
            )
            orderSelectDialog.setSelectOrder {
                selectDialogDto = it
//                EventBus.getDefault().post(MessageHistoryEventSelect(it))
                mViewModel.statusCount(
                    logisticsStatus = "",
                    startTime = it.startDate,
                    endTime = it.endDate,
                    businessNumberType = "1",
                    pageIndex = "1",
                    pageSize = "20",
                    orderTime = it.orderTime,
                    deliverySelect = "0",
                    isValid = "",
                    businessNumber = "",
                    channelId = it.channelId!!
                )
            }

            orderSelectDialog.setSelectCloseOrder {
                selectDialogDto = it
            }
            orderSelectDialog.show(childFragmentManager)
        }
        mDatabind.imgOrderScreen.setOnClickListener {
            startActivity(Intent(requireContext(), OrderCreateActivity::class.java))
        }

        mDatabind.txtSelectStore.setSingleClickListener {
            var orderSelectStoreDialog = OrderSelectStoreDialog().newInstance(
                "授权发货门店",
                "",
                "",
                ArrayList(),
                false
            )
            orderSelectStoreDialog.show(childFragmentManager)
            orderSelectStoreDialog.setOkClickItemLister { arrayList, isSelectAll ->
                mDatabind.txtSelectStore.text = "选中选中选中选中选中选中选中选中选中选中个门店"
            }
        }

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
                badgeOffsetY = -23
            }
            mDatabind.tabLayout.updateTabBadge(1) {
                badgeGravity =  Gravity.CENTER or Gravity.TOP
                badgeText = if (it.deliveryOrder == 0) {
                    "--"
                } else {
                    it.deliveryOrder.toString()
                }
                badgeOffsetY = -23

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
            mDatabind.tabLayout.updateTabBadge(5) {
                badgeGravity =  Gravity.CENTER or Gravity.TOP
                badgeText = if (it.deliveryComplete == 0) {
                    "--"
                } else {
                    it.deliveryComplete.toString()
                }
                badgeOffsetY = -20

            }
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