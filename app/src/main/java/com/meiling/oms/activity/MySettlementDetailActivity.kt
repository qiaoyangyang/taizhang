package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.activity.BaseActivity
import com.meiling.common.constant.SPConstants
import com.meiling.common.network.data.FinancialRecord
import com.meiling.common.network.data.FinancialRecordDetailListReq
import com.meiling.common.network.data.PageResult
import com.meiling.common.network.data.RechargeRecordListReq
import com.meiling.common.utils.MMKVUtils
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityRechargeSettlementDetailBinding
import com.meiling.oms.fragment.CommunityFragment
import com.meiling.oms.viewmodel.BaseOrderFragmentViewModel
import com.meiling.oms.viewmodel.RechargeViewModel
import com.meiling.oms.widget.formatCurrentDate
import com.meiling.oms.widget.formatCurrentDateBeforeWeek
import com.meiling.oms.widget.showToast
import com.meiling.oms.widget.transToString

/**
 * 结算明细
 * **/
@Route(path = "/app/MySettlementDetailActivity")
class MySettlementDetailActivity :
    BaseActivity<RechargeViewModel, ActivityRechargeSettlementDetailBinding>() {

    lateinit var settlementAdapter: BaseQuickAdapter<PageResult.PageData, BaseViewHolder>


    override fun initView(savedInstanceState: Bundle?) {
        val fragments: MutableList<Fragment> = arrayListOf()
        fragments.add(
            CommunityFragment.newInstance()
        )
        setBar(this, mDatabind.cosTitle)

        settlementAdapter =
            object :
                BaseQuickAdapter<PageResult.PageData, BaseViewHolder>(R.layout.item_recharge_record) {
                override fun convert(holder: BaseViewHolder, item: PageResult.PageData) {
                    holder.setText(
                        R.id.txt_channel_name,
                        item.orderChannelName + "#" + item.orderSerialNumber
                    )
                    holder.setText(R.id.txt_service_charge_money, "-" + item.settlementAmount)
//                    holder.setText(R.id.txt_recharge_name, transToString(item.createTime!!.toLong()))
                    holder.setText(R.id.txt_recharge_name, item.createTime)
                }
            }
        mDatabind.rvSettlement.adapter = settlementAdapter
    }

    override fun initData() {
        val settlementName = intent.getStringExtra("settlementName")
        val settlementDate = intent.getStringExtra("settlementDate")
        val viewId = intent.getStringExtra("viewId")
        mDatabind.txtSettlementName.text = settlementName
        mDatabind.txtSettlementTime.text = settlementDate
        mViewModel.getFinancialRecordDetail(
            viewId = viewId!!,
            pageIndex = "1",
            pageSize = "20",
        )
    }


    override fun getBind(layoutInflater: LayoutInflater): ActivityRechargeSettlementDetailBinding {
        return ActivityRechargeSettlementDetailBinding.inflate(layoutInflater)
    }

    override fun createObserver() {
        mViewModel.financialRecordDetail.onSuccess.observe(this) {
            settlementAdapter.setList(it.pageResult?.pageData as MutableList<PageResult.PageData>)
        }
        mViewModel.financialRecord.onError.observe(this) {
            showToast(it.msg)
        }
    }

    override fun initListener() {
        mDatabind.imgSettlememtBack.setOnClickListener { finish() }
    }

}