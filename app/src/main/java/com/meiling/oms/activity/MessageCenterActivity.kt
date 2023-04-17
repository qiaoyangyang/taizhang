package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.MessageDto
import com.meiling.common.network.data.PageResult
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityMessageCenterBinding
import com.meiling.oms.databinding.ActivitySearchBinding
import com.meiling.oms.viewmodel.MessageViewModel
import com.meiling.oms.widget.showToast

@Route(path = "/app/MessageCenterActivity")
class MessageCenterActivity : BaseActivity<MessageViewModel, ActivityMessageCenterBinding>() {

    lateinit var msgCenterAdapter: BaseQuickAdapter<MessageDto.Content, BaseViewHolder>

    override fun initView(savedInstanceState: Bundle?) {
        msgCenterAdapter = object :
            BaseQuickAdapter<MessageDto.Content, BaseViewHolder>(R.layout.item_message_center) {
            override fun convert(holder: BaseViewHolder, item: MessageDto.Content) {
                holder.setText(
                    R.id.txt_msg_center_name, item.content
                )
                holder.setText(R.id.txt_msg_center_content, item.title)
            }
        }
        mDatabind.rvMsgCenter.adapter = msgCenterAdapter

        msgCenterAdapter.setEmptyView(R.layout.empty_msg_center)
        mDatabind.srfMsgCenter.setOnRefreshListener {
            pageInSex = 1
            initViewData()
        }
    }


    override fun onResume() {
        super.onResume()
        initViewData()
    }

    var pageInSex = 1
    private fun initViewData() {
        mViewModel.getMessage(pageInSex)
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityMessageCenterBinding {
        return ActivityMessageCenterBinding.inflate(layoutInflater)
    }

    override fun initListener() {
    }

    override fun createObserver() {
        mViewModel.msgCenterDto.onStart.observe(this) {
            showLoading("加载中")
        }

        mViewModel.msgCenterDto.onSuccess.observe(this) {
            disLoading()
            mDatabind.srfMsgCenter.isRefreshing = false
            msgCenterAdapter.setList(it.content as MutableList<MessageDto.Content>)
        }

        mViewModel.msgCenterDto.onError.observe(this) {
            disLoading()
            mDatabind.srfMsgCenter.isRefreshing = false
            showToast(it.msg)
        }
    }

}