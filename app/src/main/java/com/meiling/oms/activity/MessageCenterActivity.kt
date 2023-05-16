package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.module.LoadMoreModuleConfig
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.MessageDto
import com.meiling.common.network.data.OrderDto
import com.meiling.common.network.data.PageResult
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityMessageCenterBinding
import com.meiling.oms.databinding.ActivitySearchBinding
import com.meiling.oms.viewmodel.MessageViewModel
import com.meiling.oms.widget.SS
import com.meiling.oms.widget.formatCurrentDate
import com.meiling.oms.widget.formatCurrentDateBeforeWeek
import com.meiling.oms.widget.showToast

@Route(path = "/app/MessageCenterActivity")
class MessageCenterActivity : BaseActivity<MessageViewModel, ActivityMessageCenterBinding>() {

    lateinit var msgCenterAdapter: BaseQuickAdapter<MessageDto.Content, BaseViewHolder>

    override fun initView(savedInstanceState: Bundle?) {


        msgCenterAdapter = object :
            BaseQuickAdapter<MessageDto.Content, BaseViewHolder>(R.layout.item_message_center),
            LoadMoreModule {
            override fun convert(holder: BaseViewHolder, item: MessageDto.Content) {
                holder.setText(
                    R.id.txt_msg_center_name, item.content
                )
                holder.setText(R.id.txt_msg_center_content, item.title)
                var iconMsg = holder?.getView<ImageView>(R.id.img_msg_center_icon)
                holder.setText(R.id.txt_msg_center_time, item.pushTime)
                if (item.pushMessageType == "2") {
                    iconMsg?.setImageDrawable(resources.getDrawable(R.drawable.icon_center_wl))
                } else {
                    iconMsg?.setImageDrawable(resources.getDrawable(R.drawable.icon_msg_center_order))
                }
            }
        }
        mDatabind.rvMsgCenter.adapter = msgCenterAdapter

        msgCenterAdapter.setOnItemClickListener { adapter, view, position ->
            ARouter.getInstance().build("/app/Search1Activity")
                .withString(
                    "pushOrderId",
                    (adapter.data[position] as MessageDto.Content).orderViewId
                ).navigation()
        }

        msgCenterAdapter.setEmptyView(R.layout.empty_msg_center)
        mDatabind.srfMsgCenter.setOnRefreshListener {
            pageInSex = 1
            initViewData()
        }

        msgCenterAdapter.loadMoreModule.setOnLoadMoreListener {
            pageInSex++
            mViewModel.getMessage(pageInSex)
        }
        msgCenterAdapter.loadMoreModule.loadMoreView = SS()
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
            msgCenterAdapter.isUseEmpty = true
            if (it.pageIndex == 1) {
                if (it.content.isNullOrEmpty()) {
                    msgCenterAdapter.setList(null)
                    msgCenterAdapter.setEmptyView(R.layout.empty_msg_center)
                } else {
                    msgCenterAdapter.setNewInstance(it.content as MutableList<MessageDto.Content>)
                }
            } else {
                msgCenterAdapter.addData(it.content as MutableList<MessageDto.Content>)
            }
            msgCenterAdapter.removeAllFooterView()
            if (it.content.size < 50) {
                msgCenterAdapter.footerViewAsFlow = true
                msgCenterAdapter.loadMoreModule.isLoadEndMoreGone
                msgCenterAdapter.loadMoreModule.loadMoreEnd()
            } else {
                msgCenterAdapter.loadMoreModule.loadMoreComplete()
            }
        }

        mViewModel.msgCenterDto.onError.observe(this) {
            disLoading()
            mDatabind.srfMsgCenter.isRefreshing = false
            showToast(it.msg)
        }
    }

}


