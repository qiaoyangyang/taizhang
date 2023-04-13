package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.PageResult
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityMessageCenterBinding
import com.meiling.oms.databinding.ActivitySearchBinding
import com.meiling.oms.viewmodel.MessageViewModel
import com.meiling.oms.widget.showToast

@Route(path = "/app/MessageCenterActivity")
class MessageCenterActivity : BaseActivity<MessageViewModel, ActivityMessageCenterBinding>() {

    lateinit var msgCenterAdapter: BaseQuickAdapter<PageResult.PageData, BaseViewHolder>

    override fun initView(savedInstanceState: Bundle?) {
        msgCenterAdapter =  object :
            BaseQuickAdapter<PageResult.PageData, BaseViewHolder>(R.layout.item_recharge_record) {
            override fun convert(holder: BaseViewHolder, item: PageResult.PageData) {
                holder.setText(
                    R.id.txt_channel_name,
                    item.orderChannelName + "#" + item.orderChannel
                )
                holder.setText(R.id.txt_service_charge_money, item.settlementAmount)
                holder.setText(R.id.txt_recharge_name, item.createTime)
            }
        }
        mDatabind.rvMsgCenter.adapter = msgCenterAdapter

        msgCenterAdapter.setEmptyView(R.layout.empty_msg_center)
        mDatabind.srfMsgCenter.setOnRefreshListener {
            initViewData()
        }
    }


    override fun onResume() {
        super.onResume()
        initViewData()
    }

    private fun initViewData() {
        mViewModel.getMessage()
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityMessageCenterBinding {
        return ActivityMessageCenterBinding.inflate(layoutInflater)
    }

    override fun initListener() {
    }

    override fun createObserver() {
        mViewModel.msgCenterDto.onStart.observe(this) {

        }

        mViewModel.msgCenterDto.onSuccess.observe(this) {
            mDatabind.srfMsgCenter.isRefreshing = false
        }

        mViewModel.msgCenterDto.onError.observe(this) {
            mDatabind.srfMsgCenter.isRefreshing = false
            showToast(it.msg)
        }
    }

}