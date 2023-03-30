package com.meiling.oms.fragment

import android.os.Bundle
import android.view.LayoutInflater
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.fragment.BaseFragment
import com.meiling.oms.R
import com.meiling.oms.databinding.FragmentRechargeRecordBinding
import com.meiling.oms.viewmodel.SelectedViewModel

/**
 * 充值记录
 * */
class RechargeRecordFragment : BaseFragment<SelectedViewModel, FragmentRechargeRecordBinding>() {


   lateinit var rechargeAdapter :BaseQuickAdapter<String,BaseViewHolder>


    companion object {
        fun newInstance() = RechargeRecordFragment()
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun initData() {
        rechargeAdapter =
            object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_recharge_record) {
                override fun convert(holder: BaseViewHolder, item: String) {
                    holder.setText(R.id.txt_channel_name, item)
                }

            }
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


}