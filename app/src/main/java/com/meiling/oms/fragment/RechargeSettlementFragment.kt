package com.meiling.oms.fragment

import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.constant.SPConstants
import com.meiling.common.fragment.BaseFragment
import com.meiling.common.network.data.FinancialRecord
import com.meiling.common.network.data.RechargeRecordListReq
import com.meiling.common.utils.MMKVUtils
import com.meiling.oms.eventBusData.MessageEventTime
import com.meiling.oms.eventBusData.MessageEventTimeShow
import com.meiling.oms.R
import com.meiling.oms.databinding.FragmentRechargeSettlementBinding
import com.meiling.oms.viewmodel.RechargeViewModel
import com.meiling.oms.widget.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

/**
 * 财务结算
 * ***/
class RechargeSettlementFragment :
    BaseFragment<RechargeViewModel, FragmentRechargeSettlementBinding>() {

    lateinit var chargeAdapter: BaseQuickAdapter<FinancialRecord.PageResult.PageData, BaseViewHolder>

    companion object {
        fun newInstance() = RechargeSettlementFragment()
    }


    override fun initView(savedInstanceState: Bundle?) {
        EventBus.getDefault().register(this)
        chargeAdapter = object :
            BaseQuickAdapter<FinancialRecord.PageResult.PageData, BaseViewHolder>(R.layout.item_recharge_charge),
            LoadMoreModule {
            override fun convert(
                holder: BaseViewHolder, item: FinancialRecord.PageResult.PageData
            ) {
                holder.setText(R.id.txt_service_charge_name, item.settlementTypeName)
                holder.setText(R.id.txt_service_charge_name, item.settlementTypeName)
                holder.setText(R.id.txt_service_charge_num, item.count + "笔")
                holder.setText(R.id.txt_service_charge_money, item.settlementAmount)
                holder.setText(R.id.txt_day, formatCurrentDateDay(item.settlementDate))
                holder.setText(R.id.txt_month, formatCurrentDateMM(item.settlementDate) + "月")
            }
        }
        mDatabind.rvServerChargeList.adapter = chargeAdapter

        chargeAdapter.setOnItemClickListener { adapter, view, position ->
            ARouter.getInstance().build("/app/MySettlementDetailActivity").withString(
                "settlementName",
                (adapter.data[position] as FinancialRecord.PageResult.PageData).description
            ).withString(
                "viewId",
                (adapter.data[position] as FinancialRecord.PageResult.PageData).viewId
            ).withString(
                "settlementDate",
                (adapter.data[position] as FinancialRecord.PageResult.PageData).settlementDate
            ).navigation()
        }

        mDatabind.srfRechargeFeeRecord.setOnRefreshListener {
            pageIndex = 1
            initViewData()
            EventBus.getDefault().post(MessageEventTimeShow())
        }
    }

    var pageIndex = 1
    var startDate = formatCurrentDateBeforeWeek()
    var endData = formatCurrentDate()
    private fun initViewData() {
        mViewModel.getFinancialRecord(
            RechargeRecordListReq(
                createUserId = "",
                startDate = startDate,
                endDate = endData,
                pageIndex = 1,
                pageSize = "20",
                tenantId = MMKVUtils.getString(SPConstants.tenantId)
            )
        )
        chargeAdapter.loadMoreModule.setOnLoadMoreListener {
            pageIndex++
            mViewModel.getFinancialRecord(
                RechargeRecordListReq(
                    createUserId = "",
                    startDate = startDate,
                    endDate = endData,
                    pageIndex = pageIndex,
                    pageSize = "20",
                    tenantId = MMKVUtils.getString(SPConstants.tenantId)
                )
            )
        }
    }

    override fun createObserver() {
        mViewModel.financialRecord.onSuccess.observe(this) {
            mDatabind.srfRechargeFeeRecord.isRefreshing = false
            if (it.pageResult?.pageNum == 1) {
                chargeAdapter.setList(it.pageResult?.pageData as MutableList<FinancialRecord.PageResult.PageData>)
            } else {
                chargeAdapter.addData(it.pageResult?.pageData as MutableList<FinancialRecord.PageResult.PageData>)
            }

            if (it.pageResult?.pageData!!.size < 20) {
                chargeAdapter.loadMoreModule.loadMoreEnd()

            } else {
                chargeAdapter.loadMoreModule.loadMoreComplete()
            }

        }
        mViewModel.financialRecord.onError.observe(this) {
            mDatabind.srfRechargeFeeRecord.isRefreshing = false
            showToast(it.msg)
        }

    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().post(MessageEventTimeShow())
        pageIndex = 1
        initViewData()
    }

    override fun getBind(inflater: LayoutInflater): FragmentRechargeSettlementBinding {
        return FragmentRechargeSettlementBinding.inflate(inflater)
    }

    override fun initListener() {

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun eventDay(messageEventTime: MessageEventTime) {
        pageIndex = 1
        startDate = messageEventTime.starTime
        endData = messageEventTime.starTime
        initViewData()
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

}