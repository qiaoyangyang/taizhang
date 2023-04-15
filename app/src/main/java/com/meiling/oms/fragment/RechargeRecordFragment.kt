package com.meiling.oms.fragment

import android.os.Bundle
import android.view.LayoutInflater
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.constant.SPConstants
import com.meiling.common.fragment.BaseFragment
import com.meiling.common.network.data.PageData
import com.meiling.common.network.data.RechargeRecordListReq
import com.meiling.common.utils.MMKVUtils
import com.meiling.oms.eventBusData.MessageEventTime
import com.meiling.oms.eventBusData.MessageEventTimeShow
import com.meiling.oms.R
import com.meiling.oms.databinding.FragmentRechargeRecordBinding
import com.meiling.oms.viewmodel.RechargeViewModel
import com.meiling.oms.widget.formatCurrentDate
import com.meiling.oms.widget.formatCurrentDateBeforeWeek
import com.meiling.oms.widget.showToast
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 充值记录
 * */
class RechargeRecordFragment : BaseFragment<RechargeViewModel, FragmentRechargeRecordBinding>() {


    lateinit var rechargeAdapter: BaseQuickAdapter<PageData, BaseViewHolder>

    companion object {
        fun newInstance() = RechargeRecordFragment()
    }

    override fun initView(savedInstanceState: Bundle?) {
        EventBus.getDefault().register(this)
        rechargeAdapter =
            object : BaseQuickAdapter<PageData, BaseViewHolder>(R.layout.item_recharge_record),
                LoadMoreModule {
                override fun convert(holder: BaseViewHolder, item: PageData) {
                    holder.setText(R.id.txt_channel_name, item.payTypeName)
                    holder.setText(R.id.txt_service_charge_money, item.payAmount)
                    holder.setText(R.id.txt_recharge_name, item.createTime)
                }
            }
        mDatabind.rvRechargeRecord.adapter = rechargeAdapter

        mDatabind.srfRechargeRecord.setOnRefreshListener {
            pageIndex = 1
            initViewData()
            EventBus.getDefault().post(MessageEventTimeShow())
        }
    }

    var pageIndex = 1
    var startDate = formatCurrentDateBeforeWeek()
    private fun initViewData() {
        mViewModel.getRecord(
            RechargeRecordListReq(
                createUserId = "",
                startDate = startDate,
                endDate = formatCurrentDate(),
                pageIndex = 1,
                pageSize = "20",
                tenantId = MMKVUtils.getString(SPConstants.tenantId)
            )
        )
        rechargeAdapter.loadMoreModule.setOnLoadMoreListener {
            pageIndex++
            mViewModel.getFinancialRecord(
                RechargeRecordListReq(
                    createUserId = "",
                    startDate = startDate,
                    endDate = formatCurrentDate(),
                    pageIndex = pageIndex,
                    pageSize = "20",
                    tenantId = MMKVUtils.getString(SPConstants.tenantId)
                )
            )
        }
    }

    override fun getBind(inflater: LayoutInflater): FragmentRechargeRecordBinding {
        return FragmentRechargeRecordBinding.inflate(inflater)
    }

    override fun createObserver() {
        mViewModel.rechargeRecord.onSuccess.observe(this) {
            mDatabind.srfRechargeRecord.isRefreshing = false
            if (it?.pageData.isNullOrEmpty()) {
                rechargeAdapter.data.clear()
                rechargeAdapter.setEmptyView(R.layout.empty_record_center)
            }
            rechargeAdapter.setList(it.pageData as MutableList<PageData>)
        }
        mViewModel.rechargeRecord.onError.observe(this) {
            mDatabind.srfRechargeRecord.isRefreshing = false
            showToast(it.msg)
        }
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().post(MessageEventTimeShow())
        pageIndex = 1
        startDate = formatCurrentDateBeforeWeek()
        initViewData()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun eventDay(messageEventTime: MessageEventTime) {
        pageIndex = 1
        startDate = messageEventTime.starTime
        initViewData()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}