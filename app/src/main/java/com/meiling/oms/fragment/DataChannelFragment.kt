package com.meiling.oms.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.lifecycle.Observer
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.fragment.BaseFragment
import com.meiling.common.network.data.ChannelDataList
import com.meiling.common.network.data.DataListDto
import com.meiling.oms.R
import com.meiling.oms.databinding.FragmentDataChannelBinding
import com.meiling.oms.dialog.DataSelectTimeDialog
import com.meiling.oms.eventBusData.MessageSelectShopPo
import com.meiling.oms.viewmodel.DataFragmentViewModel
import com.meiling.oms.widget.formatCurrentDate
import com.meiling.oms.widget.formatCurrentDateBeforeDay
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class DataChannelFragment : BaseFragment<DataFragmentViewModel, FragmentDataChannelBinding>() {

    lateinit var dataChannelAdapter: BaseQuickAdapter<ChannelDataList, BaseViewHolder>
    lateinit var dataHistoryChannelAdapter: BaseQuickAdapter<ChannelDataList, BaseViewHolder>


    companion object {
        fun newInstance() = DataChannelFragment()
    }

    override fun initView(savedInstanceState: Bundle?) {
        dataChannelAdapter =
            object : BaseQuickAdapter<ChannelDataList, BaseViewHolder>(R.layout.item_data_shop) {
                override fun convert(holder: BaseViewHolder, item: ChannelDataList) {
                    holder.setText(R.id.txt_data_time, item.channelName)
                    holder.setText(R.id.txt_all_data_order_num, item.orderNum)
                    holder.setText(R.id.txt_data_receive_money, item.incomeNum)
                }
            }
        dataHistoryChannelAdapter =
            object : BaseQuickAdapter<ChannelDataList, BaseViewHolder>(R.layout.item_data_shop) {
                override fun convert(holder: BaseViewHolder, item: ChannelDataList) {
                    holder.setText(R.id.txt_data_time, item.channelName)
                    holder.setText(R.id.txt_all_data_order_num, item.orderNum)
                    holder.setText(R.id.txt_data_receive_money, item.incomeNum)
                }
            }
        mDatabind.rvDataChannel.adapter = dataChannelAdapter
        mDatabind.rvDataChannelHistory.adapter = dataHistoryChannelAdapter

        mDatabind.srfDataChannel.setOnRefreshListener {
            initViewData()
        }
    }

    override fun getBind(inflater: LayoutInflater): FragmentDataChannelBinding {
        return FragmentDataChannelBinding.inflate(inflater)
    }

    var poiId = ArrayList<String>()

    private fun initViewData() {
        mViewModel.channelDataList(
            DataListDto(
                startTime = formatCurrentDate(),
                endTime = formatCurrentDate(),
                poiId = poiId
            )
        )
        mViewModel.channelHistoryDataList(
            DataListDto(
                startTime = startTime,
                endTime = formatCurrentDateBeforeDay(),
                poiId = poiId
            )
        )
//        LiveDataShopData.INSTANCE.observe(this, changeObserver)
    }

    var startTime = formatCurrentDateBeforeDay()

    override fun initListener() {
        mDatabind.txtHistorySelectTime.setSingleClickListener {
            var dataSelectTimeDialog = DataSelectTimeDialog().newInstance()
            dataSelectTimeDialog.show(childFragmentManager)
            dataSelectTimeDialog.setSelectTime { it, name ->
                mDatabind.txtHistorySelectTime.text = name
                startTime = it
                mViewModel.channelHistoryDataList(
                    DataListDto(
                        startTime = it,
                        endTime = formatCurrentDateBeforeDay(),
                        poiId = poiId
                    )
                )
            }
        }
    }
    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    private val changeObserver = Observer<String> { value ->
        value?.let {
            Log.e("lwq", "observer:$value")
        }
    }

    override fun onResume() {
        super.onResume()
        initViewData()
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun eventSelectTime(messageSelectShopPo: MessageSelectShopPo) {
        poiId = messageSelectShopPo.idArrayList
        initViewData()
    }


    override fun createObserver() {
        mViewModel.channelDataList.onStart.observe(this) {
            showLoading("请求中")
        }
        mViewModel.channelDataList.onSuccess.observe(this) {
            mDatabind.srfDataChannel.isRefreshing = false
            dismissLoading()
            if (it.isNullOrEmpty()) {
                dataChannelAdapter.setList(ArrayList())
            } else {
                dataChannelAdapter.setList(it)
            }

        }
        mViewModel.channelDataList.onError.observe(this) {
            dismissLoading()
            mDatabind.srfDataChannel.isRefreshing = false
            showToast(it.msg)
        }
        mViewModel.channelHistoryDataList.onStart.observe(this) {
            showLoading("请求中")
        }
        mViewModel.channelHistoryDataList.onSuccess.observe(this) {
            mDatabind.srfDataChannel.isRefreshing = false
            dismissLoading()
            if (it.isNullOrEmpty()) {
                dataHistoryChannelAdapter.setList(ArrayList())
            } else {
                dataHistoryChannelAdapter.setList(it)
            }
        }
        mViewModel.channelHistoryDataList.onError.observe(this) {
            mDatabind.srfDataChannel.isRefreshing = false
            dismissLoading()
            showToast(it.msg)
        }

    }
}