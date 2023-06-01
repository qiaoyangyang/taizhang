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
import com.meiling.common.network.data.SelectOrderDialogDto
import com.meiling.oms.activity.OrderCreateActivity
import com.meiling.oms.adapter.BaseFragmentPagerAdapter
import com.meiling.oms.databinding.FragmentHomeOrderOningBinding
import com.meiling.oms.dialog.OrderFilterSortDialog
import com.meiling.oms.dialog.OrderSelectStoreDialog
import com.meiling.oms.eventBusData.MessageEventUpDataTip
import com.meiling.oms.eventBusData.MessageHistoryEventSelect
import com.meiling.oms.eventBusData.MessageOrderEventSelect
import com.meiling.oms.viewmodel.BaseOrderFragmentViewModel
import com.meiling.oms.widget.*
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
        mDatabind.viewPager.isUserInputEnabled = true
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
        mDatabind.viewPager.setCurrentItem(0, true)
        ViewPager2Delegate.install(mDatabind.viewPager, mDatabind.tabLayout)
        mDatabind.viewPager.offscreenPageLimit = 1
       // ImmersionBar.with(this).statusBarDarkFont(true) .autoDarkModeEnable(true, 0.2f).init()
        ImmersionBar.setTitleBar(this, mDatabind.clMy)
    }
    var selectDialogDto = SelectOrderDialogDto(
        channelId = "0",
        orderSort = "1",
    )
    var poiId = "0"
    override fun initListener() {
        mDatabind.imgSearchOrder.setOnClickListener {
            ARouter.getInstance().build("/app/Search1Activity").navigation()
        }
        mDatabind.imgCreateOrder.setOnClickListener {
            startActivity(Intent(requireContext(), OrderCreateActivity::class.java))
        }
        mDatabind.imgOrderScreen.setOnClickListener {
            val orderFilterSortDialog = OrderFilterSortDialog().newInstance(selectDialogDto)
            orderFilterSortDialog.setSelectOrder {
                selectDialogDto = it
                EventBus.getDefault().post(MessageOrderEventSelect(it,poiId))
                mViewModel.statusCount(
                    logisticsStatus = "",
                    startTime = formatCurrentDateBeforeMouth(),
                    endTime = formatCurrentDate(),
                    businessNumberType = "1",
                    pageIndex = "1",
                    pageSize = "10",
                    orderTime = "",
                    deliverySelect = "0",
                    isValid = "",
                    businessNumber = "",
                    channelId = it.channelId!!
                )
            }

            orderFilterSortDialog.setSelectCloseOrder {
                selectDialogDto = it
            }
            orderFilterSortDialog.show(childFragmentManager)
        }

        mDatabind.txtSelectStore.setSingleClickListener {
            var orderSelectStoreDialog = OrderSelectStoreDialog().newInstance(
                mDatabind.txtSelectStore.text.toString(),
            )
            orderSelectStoreDialog.show(childFragmentManager)
            orderSelectStoreDialog.setOkClickItemLister { arrayList, isSelectAll ->
                mDatabind.txtSelectStore.text = isSelectAll
                poiId = arrayList[0].poiIds!!
                EventBus.getDefault().post(MessageOrderEventSelect(selectDialogDto,poiId))
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
            startTime = formatCurrentDateBeforeMouth(),
            endTime = formatCurrentDate(),
            businessNumberType = "1",
            pageIndex = "1",
            pageSize = "20",
            orderTime = "1",
            deliverySelect = "0",
            isValid = "",
            businessNumber = "",
            channelId = selectDialogDto.channelId.toString()
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
            showToast(it.msg)
        }
    }

    override fun getBind(inflater: LayoutInflater): FragmentHomeOrderOningBinding {
        return FragmentHomeOrderOningBinding.inflate(inflater)
    }


}