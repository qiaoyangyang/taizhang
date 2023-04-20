package com.meiling.oms.fragment

import android.os.Bundle
import android.view.LayoutInflater
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.fragment.BaseFragment
import com.meiling.common.network.data.DataDisDto
import com.meiling.common.network.data.DataListDto
import com.meiling.oms.R
import com.meiling.oms.databinding.FragmentDataDisBinding
import com.meiling.oms.dialog.DataSelectTimeDialog
import com.meiling.oms.dialog.DataTipDialog
import com.meiling.oms.eventBusData.MessageSelectShopPo
import com.meiling.oms.viewmodel.DataFragmentViewModel
import com.meiling.oms.widget.formatCurrentDate
import com.meiling.oms.widget.formatCurrentDateBeforeDay
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class DataOrderDisFragment : BaseFragment<DataFragmentViewModel, FragmentDataDisBinding>() {
    lateinit var dataDisAdapter: BaseQuickAdapter<DataDisDto.DeliveryConsumeLists, BaseViewHolder>
    lateinit var dataHisDisAdapter: BaseQuickAdapter<DataDisDto.DeliveryConsumeLists, BaseViewHolder>

    companion object {
        fun newInstance() = DataOrderDisFragment()
    }

    override fun initView(savedInstanceState: Bundle?) {
        dataDisAdapter =
            object :
                BaseQuickAdapter<DataDisDto.DeliveryConsumeLists, BaseViewHolder>(R.layout.item_data_dis) {
                override fun convert(
                    holder: BaseViewHolder,
                    item: DataDisDto.DeliveryConsumeLists
                ) {
                    holder.setText(R.id.txt_data_platform, item.logisticsName)
                    holder.setText(R.id.txt_data_order_num, item.orderNum)
                    holder.setText(R.id.txt_data_order_dis_amount, item.amountAndTips)
                    holder.setText(R.id.txt_data_order_tips, item.tips)
                    holder.setText(R.id.txt_data_order_dis_avgAmount, item.avgAmount)
                }
            }
        mDatabind.rvDataDis.adapter = dataDisAdapter
        dataHisDisAdapter =
            object :
                BaseQuickAdapter<DataDisDto.DeliveryConsumeLists, BaseViewHolder>(R.layout.item_data_dis) {
                override fun convert(
                    holder: BaseViewHolder,
                    item: DataDisDto.DeliveryConsumeLists
                ) {
                    holder.setText(R.id.txt_data_platform, item.logisticsName)
                    holder.setText(R.id.txt_data_order_num, item.orderNum)
                    holder.setText(R.id.txt_data_order_dis_amount, item.amountAndTips)
                    holder.setText(R.id.txt_data_order_tips, item.tips)
                    holder.setText(R.id.txt_data_order_dis_avgAmount, item.avgAmount)
                }
            }
        mDatabind.rvHisDataDis.adapter = dataHisDisAdapter

        mDatabind.srfDataDis.setOnRefreshListener {
            startTime = formatCurrentDateBeforeDay()
            initViewData()
        }
    }

    var poiId = ArrayList<String>()

    private fun initViewData() {
        mViewModel.dataDisList(
            DataListDto(
                startTime = formatCurrentDate(),
                endTime = formatCurrentDate(),
                poiId
            )
        )
        mViewModel.dataHistoryDisList(
            DataListDto(
                startTime = "$startTime",
                endTime = formatCurrentDateBeforeDay(),
                poiId
            )
        )
    }


    override fun getBind(inflater: LayoutInflater): FragmentDataDisBinding {
        return FragmentDataDisBinding.inflate(inflater)
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

    var startTime = formatCurrentDateBeforeDay()
    override fun initListener() {
        mDatabind.txtDataHistoryChannelTime.setSingleClickListener {
            var dataSelectTimeDialog = DataSelectTimeDialog().newInstance()
            dataSelectTimeDialog.show(childFragmentManager)
            dataSelectTimeDialog.setSelectTime { it, name ->
                mDatabind.txtDataHistoryChannelTime.text = name
                startTime = it
                mViewModel.dataHistoryDisList(
                    DataListDto(
                        startTime = "$it",
                        endTime = "$it",
                        poiId
                    )
                )
            }
        }


        mDatabind.txtDataDisTip.setSingleClickListener {
            DataTipDialog().newInstance("只计算配送成功的订单数量，不包含配送中、配送失败和取消配送的订单")
                .show(childFragmentManager)
        }
        mDatabind.txtDataDisTip1.setSingleClickListener {
            DataTipDialog().newInstance("该配送费为系统预估的配送，实际配送费请以配送平台的为准")
                .show(childFragmentManager)
        }
        mDatabind.txtDataDisTip2.setSingleClickListener {
            DataTipDialog().newInstance("只计算配送成功的订单的加小费金额")
                .show(childFragmentManager)
        }
        mDatabind.txtDataDisTip3.setSingleClickListener {
            DataTipDialog().newInstance("小计=配送费+小费")
                .show(childFragmentManager)
        }
        mDatabind.txtHisDataDisTip.setSingleClickListener {
            DataTipDialog().newInstance("只计算配送成功的订单数量，不包含配送中、配送失败和取消配送的订单")
                .show(childFragmentManager)
        }
        mDatabind.txtHisDataDisTip1.setSingleClickListener {
            DataTipDialog().newInstance("该配送费为系统预估的配送，实际配送费请以配送平台的为准")
                .show(childFragmentManager)
        }
        mDatabind.txtHisDataDisTip2.setSingleClickListener {
            DataTipDialog().newInstance("只计算配送成功的订单的加小费金额")
                .show(childFragmentManager)
        }
        mDatabind.txtHisDataDisTip3.setSingleClickListener {
            DataTipDialog().newInstance("小计=配送费+小费")
                .show(childFragmentManager)
        }
        mDatabind.txtHisDataDisTip4.setSingleClickListener {
            DataTipDialog().newInstance("客单价=小计/发单数量")
                .show(childFragmentManager)
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

    override fun createObserver() {
        mViewModel.dataList.onStart.observe(this) {
            showLoading("正在请求")
        }
        mViewModel.dataList.onSuccess.observe(this) {
            dismissLoading()
            mDatabind.srfDataDis.isRefreshing = false
            if (it.deliveryConsumeLists.isNullOrEmpty()) {
                dataDisAdapter.setList(null)
            } else{
                dataDisAdapter.setList(it.deliveryConsumeLists as MutableList<DataDisDto.DeliveryConsumeLists>)
            }
            mDatabind.txtSumOrderNum.text = it.sumOrderNum
            mDatabind.txtSumTips.text = it.sumTips
            mDatabind.txtSunAmount.text = it.sumAmount
            mDatabind.txtSumAmountAndTips.text = it.sumAmountAndTips
        }
        mViewModel.dataList.onError.observe(this) {
            mDatabind.srfDataDis.isRefreshing = false
            dismissLoading()
            showToast(it.msg)
        }
        mViewModel.dataHistoryList.onStart.observe(this) {
            showLoading("正在请求")
        }
        mViewModel.dataHistoryList.onSuccess.observe(this) {
            dismissLoading()
            mDatabind.srfDataDis.isRefreshing = false
            mDatabind.txtHisSumOrderNum.text = it.sumOrderNum
            mDatabind.txtHisSumTips.text = it.sumTips
            if (it.deliveryConsumeLists.isNullOrEmpty()) {
                dataHisDisAdapter.setList(null)
            } else {
                dataHisDisAdapter.setList(it.deliveryConsumeLists as MutableList<DataDisDto.DeliveryConsumeLists>)
            }
            mDatabind.txtHisSunAmount.text = it.sumAmount
            mDatabind.txtHisSumAmountAndTips.text = it.sumAmountAndTips
            mDatabind.txtHisAvgAmount.text = it.sumAvg

        }
        mViewModel.dataHistoryList.onError.observe(this) {
            mDatabind.srfDataDis.isRefreshing = false
            dismissLoading()
            showToast(it.msg)
        }
    }

}