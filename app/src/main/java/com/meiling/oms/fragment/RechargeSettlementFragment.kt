package com.meiling.oms.fragment

import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.fragment.BaseFragment
import com.meiling.oms.R
import com.meiling.oms.adapter.RecommendAdapter
import com.meiling.oms.databinding.FragmentRechargeSettlementBinding
import com.meiling.oms.viewmodel.RecommendViewModel

/**
 * 财务结算
 * ***/
class RechargeSettlementFragment :
    BaseFragment<RecommendViewModel, FragmentRechargeSettlementBinding>() {

    lateinit var chargeAdapter: BaseQuickAdapter<String, BaseViewHolder>

    private val mRecommendAdapter: RecommendAdapter by lazy { RecommendAdapter() }

    companion object {
        fun newInstance() = RechargeSettlementFragment()
    }


    override fun initView(savedInstanceState: Bundle?) {

    }


    override fun initData() {

        chargeAdapter =
            object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_recharge_charge) {
                override fun convert(holder: BaseViewHolder, item: String) {
                    holder.setText(R.id.txt_service_charge_name, item)
                }

            }


        var list = ArrayList<String>()
        list.add("技术服务费结算")
        list.add("运输费")
        list.add("技术费用")
        list.add("技术服务费")


        mDatabind.rvServerChargeList.adapter = chargeAdapter
        chargeAdapter.setList(list)

        chargeAdapter.setOnItemClickListener { adapter, view, position ->
            ARouter.getInstance().build("/app/MySettlementDetailActivity")
                .withString("settlementName", adapter.data[position].toString()).navigation()
        }

    }


    override fun getBind(inflater: LayoutInflater): FragmentRechargeSettlementBinding {
        return FragmentRechargeSettlementBinding.inflate(inflater)
    }

    override fun initListener() {

    }

}