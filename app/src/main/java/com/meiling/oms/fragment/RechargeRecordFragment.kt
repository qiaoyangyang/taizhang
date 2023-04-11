package com.meiling.oms.fragment

import android.os.Bundle
import android.view.LayoutInflater
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.constant.SPConstants
import com.meiling.common.fragment.BaseFragment
import com.meiling.common.network.data.RechargeRecordListReq
import com.meiling.common.utils.MMKVUtils
import com.meiling.oms.EventBusData.MessageEventTime
import com.meiling.oms.R
import com.meiling.oms.databinding.FragmentRechargeRecordBinding
import com.meiling.oms.viewmodel.RechargeViewModel
import com.meiling.oms.viewmodel.SelectedViewModel
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


    lateinit var rechargeAdapter: BaseQuickAdapter<String, BaseViewHolder>


    companion object {
        fun newInstance() = RechargeRecordFragment()
    }

    override fun initView(savedInstanceState: Bundle?) {
        rechargeAdapter =
            object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_recharge_record),
                LoadMoreModule {
                override fun convert(holder: BaseViewHolder, item: String) {
                    holder.setText(R.id.txt_channel_name, item)
                }
            }

        mDatabind.srfRechargeRecord.setOnRefreshListener {
            initData()
        }

    }

    override fun initData() {
        mViewModel.getRecord(
            RechargeRecordListReq(
                createUserId = "",
                endDate = formatCurrentDateBeforeWeek(),
                startDate = formatCurrentDate(),
                pageIndex = 1,
                pageSize = "20",
                tenantId = MMKVUtils.getString(SPConstants.tenantId)
            )
        )
        var list = ArrayList<String>()
        list.add("微信")
        list.add("支付宝")
        list.add("银行卡")
        mDatabind.rvRechargeRecord.adapter = rechargeAdapter
        rechargeAdapter.setList(list)
    }

    override fun getBind(inflater: LayoutInflater): FragmentRechargeRecordBinding {
        return FragmentRechargeRecordBinding.inflate(inflater)
    }

    override fun createObserver() {
        mViewModel.rechargeRecord.onSuccess.observe(this) {
            mDatabind.srfRechargeRecord.isRefreshing = false
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun eventDay(messageEventTime: MessageEventTime) {
        showToast(messageEventTime.starTime)
        mViewModel.getRecord(
            RechargeRecordListReq(
                createUserId = "",
                endDate = formatCurrentDateBeforeWeek(),
                startDate = messageEventTime.starTime,
                pageIndex = 1,
                pageSize = "20",
                tenantId = MMKVUtils.getString(SPConstants.tenantId)
            )
        )
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }
}