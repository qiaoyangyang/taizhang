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
import com.meiling.oms.viewmodel.DataFragmentViewModel
import com.meiling.oms.widget.formatCurrentDate
import com.meiling.oms.widget.formatCurrentDateBeforeDay
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast

class DataOrderDisFragment : BaseFragment<DataFragmentViewModel, FragmentDataDisBinding>() {
    lateinit var dataDisAdapter: BaseQuickAdapter<DataDisDto.DeliveryConsumeLists, BaseViewHolder>

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
        mDatabind.srfDataDis.setOnRefreshListener {
            initData()
        }
    }


    override fun initData() {
        mViewModel.dataDisList(DataListDto(startTime = "", endTime = "", ArrayList<Long>()))
        mViewModel.dataHistoryDisList(
            DataListDto(
                startTime = formatCurrentDateBeforeDay() + " 00:00:00",
                endTime = formatCurrentDateBeforeDay() + " 23:59:59",
                ArrayList<Long>()
            )
        )
    }

    override fun getBind(inflater: LayoutInflater): FragmentDataDisBinding {
        return FragmentDataDisBinding.inflate(inflater)
    }

    override fun onResume() {
        super.onResume()
        initData()
    }

    override fun initListener() {
        mDatabind.txtDataHistoryChannelTime.setSingleClickListener {
            var dataSelectTimeDialog = DataSelectTimeDialog().newInstance()
            dataSelectTimeDialog.show(childFragmentManager)
            dataSelectTimeDialog.setSelectTime { it, name ->
                mDatabind.txtDataHistoryChannelTime.text = name
                mViewModel.dataHistoryDisList(
                    DataListDto(
                        startTime = "$it 00:00:00",
                        endTime = "$it 23:59:59",
                        ArrayList<Long>()
                    )
                )
            }
        }
    }

    override fun createObserver() {
        mViewModel.dataList.onStart.observe(this) {
            showLoading("正在请求")
        }
        mViewModel.dataList.onSuccess.observe(this) {
            dismissLoading()
            mDatabind.srfDataDis.isRefreshing = false
            dataDisAdapter.setList(it.deliveryConsumeLists as MutableList<DataDisDto.DeliveryConsumeLists>)
            mDatabind.txtSumOrderNum.text = it.sumOrderNum
            mDatabind.txtSumTips.text = it.sumTips
            mDatabind.txtSunAmount.text = it.sumAmount
            mDatabind.txtSumAmountAndTips.text = it.sumAmountAndTips
            mDatabind.txtHisAvgAmount.text = it.sumAvg

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
            mDatabind.txtSumOrderNum.text = it.sumOrderNum
            mDatabind.txtHisSumOrderNum.text = it.sumOrderNum
            mDatabind.txtHisSumTips.text = it.sumTips
            mDatabind.txtHisSunAmount.text = it.sumAmount
            mDatabind.txtHisSumAmountAndTips.text = it.sumAmountAndTips
        }
        mViewModel.dataHistoryList.onError.observe(this) {
            mDatabind.srfDataDis.isRefreshing = false
            dismissLoading()
            showToast(it.msg)
        }
    }

}