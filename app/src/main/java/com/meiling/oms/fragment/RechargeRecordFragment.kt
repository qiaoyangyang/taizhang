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
import com.meiling.oms.widget.SS
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
    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
    override fun initView(savedInstanceState: Bundle?) {
        rechargeAdapter =
            object : BaseQuickAdapter<PageData, BaseViewHolder>(R.layout.item_recharge_record),
                LoadMoreModule {
                override fun convert(holder: BaseViewHolder, item: PageData) {
                    holder.setText(R.id.txt_channel_name, item.payTypeName)
                    holder.setText(R.id.txt_service_charge_money, "+" + item.payAmount)
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
    var endData = formatCurrentDate()
    private fun initViewData() {
        mViewModel.getRecord(
            RechargeRecordListReq(
                createUserId = "",
                startDate = startDate,
                endDate = endData,
                pageIndex = 1,
                pageSize = "20",
                tenantId = MMKVUtils.getString(SPConstants.tenantId)
            )
        )
        rechargeAdapter.loadMoreModule.loadMoreView = SS()
        rechargeAdapter.loadMoreModule.setOnLoadMoreListener {
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

    override fun getBind(inflater: LayoutInflater): FragmentRechargeRecordBinding {
        return FragmentRechargeRecordBinding.inflate(inflater)
    }

    override fun createObserver() {
        mViewModel.rechargeRecord.onStart.observe(this) {
            showLoading("请求中")
        }
        mViewModel.rechargeRecord.onSuccess.observe(this) {
            dismissLoading()
            mDatabind.srfRechargeRecord.isRefreshing = false
            if (it?.pageData.isNullOrEmpty()) {
                rechargeAdapter.setList(null)
                rechargeAdapter.footerWithEmptyEnable = false
                rechargeAdapter.setEmptyView(R.layout.empty_record_center)
            }
            if (it.pageNum == 1) {
                rechargeAdapter.setList(it.pageData as MutableList<PageData>)
            } else {
                rechargeAdapter.addData(it.pageData as MutableList<PageData>)
            }

            if (it.pageData!!.size < 20) {
                rechargeAdapter.footerWithEmptyEnable = false
                rechargeAdapter.loadMoreModule.loadMoreEnd()
            } else {
                rechargeAdapter.loadMoreModule.loadMoreComplete()
            }
        }
        mViewModel.rechargeRecord.onError.observe(this) {
            dismissLoading()
            mDatabind.srfRechargeRecord.isRefreshing = false
            showToast(it.msg)
        }
    }

    override fun onResume() {
        super.onResume()
        EventBus.getDefault().post(MessageEventTimeShow())
        pageIndex = 1
        startDate = formatCurrentDateBeforeWeek()
        endData = formatCurrentDate()
        initViewData()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun eventDay(messageEventTime: MessageEventTime) {
        pageIndex = 1
        startDate = messageEventTime.starTime
        endData = messageEventTime.endTime
        initViewData()
    }

    override fun onDestroy() {
        super.onDestroy()
    }
}