package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.gyf.immersionbar.ImmersionBar
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityRechargeSettlementDetailBinding
import com.meiling.oms.fragment.CommunityFragment
import com.meiling.oms.viewmodel.CommunityViewModel

/**
 * 结算明细
 * **/
@Route(path = "/app/MySettlementDetailActivity")
class MySettlementDetailActivity :
    BaseActivity<CommunityViewModel, ActivityRechargeSettlementDetailBinding>() {

    lateinit var settlementAdapter: BaseQuickAdapter<String, BaseViewHolder>


    override fun initView(savedInstanceState: Bundle?) {
        val fragments: MutableList<Fragment> = arrayListOf()
        fragments.add(
            CommunityFragment.newInstance()
        )
        setBar(this,mDatabind.cosTitle)
    }

    override fun initData() {

        var settlementName = intent.getStringExtra("settlementName")
        mDatabind.txtSettlementName.text = settlementName
        settlementAdapter =
            object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_recharge_record) {
                override fun convert(holder: BaseViewHolder, item: String) {
                    holder.setText(R.id.txt_channel_name, item + "#${1}")
                }

            }
        var list = ArrayList<String>()
        list.add("美团")
        list.add("饿了么")
        list.add("美团购物")
        mDatabind.rvSettlement.adapter = settlementAdapter
        settlementAdapter.setList(list)
    }


    override fun getBind(layoutInflater: LayoutInflater): ActivityRechargeSettlementDetailBinding {
        return ActivityRechargeSettlementDetailBinding.inflate(layoutInflater)
    }


    override fun initListener() {
        mDatabind.imgSettlememtBack.setOnClickListener { finish() }
    }

}