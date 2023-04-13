package com.meiling.oms.fragment

import android.os.Bundle
import android.view.LayoutInflater
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.fragment.BaseFragment
import com.meiling.common.network.data.DataDisDto
import com.meiling.common.network.data.DataListDto
import com.meiling.common.network.data.DataShop
import com.meiling.common.network.data.DataShopList
import com.meiling.oms.R
import com.meiling.oms.databinding.FragmentDataShopBinding
import com.meiling.oms.dialog.DataSelectTimeDialog
import com.meiling.oms.viewmodel.DataFragmentViewModel
import com.meiling.oms.widget.formatCurrentDate
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast

class DataShopFragment : BaseFragment<DataFragmentViewModel, FragmentDataShopBinding>() {

    companion object {
        fun newInstance() = DataShopFragment()
    }

    private lateinit var dataShopAdapter: BaseQuickAdapter<DataShopList.OrderStaticsGroupByHour, BaseViewHolder>

    override fun initView(savedInstanceState: Bundle?) {

        dataShopAdapter =
            object :
                BaseQuickAdapter<DataShopList.OrderStaticsGroupByHour, BaseViewHolder>(R.layout.item_data_shop) {
                override fun convert(
                    holder: BaseViewHolder,
                    item: DataShopList.OrderStaticsGroupByHour
                ) {
                    holder.setText(R.id.txt_data_time, item.hour)
                    holder.setText(R.id.txt_all_data_order_num, item.count)
                    holder.setText(R.id.txt_data_receive_money, item.price)
                }
            }
        mDatabind.rvDataShop.adapter = dataShopAdapter

        mDatabind.refDataShop.setOnRefreshListener {
            initViewData()
        }

    }

    override fun getBind(inflater: LayoutInflater): FragmentDataShopBinding {
        return FragmentDataShopBinding.inflate(inflater)
    }


    fun initViewData() {
        mViewModel.shopDataList(
            DataListDto(
                startTime = formatCurrentDate() + " 00:00:00",
                endTime = formatCurrentDate() + " 23:59:59",
                ArrayList<Long>()
            )
        )

        mViewModel.shopHistoryDataList(
            DataShop(
                timeType = 1,
                orderStatus = ArrayList(),
                refundStatus = ArrayList(),
                channelIds = ArrayList(),
                adminUserIds = ArrayList(),
                deliveryType = ArrayList(),
                goodsType = ArrayList(),
                orderType = ArrayList(),
                classificationViewIds = ArrayList(),
                poiIds = ArrayList(),
                startTime = formatCurrentDate() + " 00:00:00",
                endTime = formatCurrentDate() + " 23:59:59",
                isValid = -1,
                sortType = 0,
                pageIndex = 1,
                pageSize = 50,
            )
        )
        mViewModel.shopData(
            DataShop(
                timeType = 1,
                orderStatus = ArrayList(),
                refundStatus = ArrayList(),
                channelIds = ArrayList(),
                adminUserIds = ArrayList(),
                deliveryType = ArrayList(),
                goodsType = ArrayList(),
                orderType = ArrayList(),
                classificationViewIds = ArrayList(),
                poiIds = ArrayList(),
                startTime = formatCurrentDate() + " 00:00:00",
                endTime = formatCurrentDate() + " 23:59:59",
                isValid = -1,
                sortType = 0,
                pageIndex = 1,
                pageSize = 50,
            )
        )
    }

    override fun onResume() {
        super.onResume()
        initViewData()
    }

    override fun initListener() {
        mDatabind.txtDataHistoryShopTime.setSingleClickListener {
            var dataSelectTimeDialog = DataSelectTimeDialog().newInstance()
            dataSelectTimeDialog.show(childFragmentManager)
            dataSelectTimeDialog.setSelectTime { it, name ->
                mDatabind.txtDataHistoryShopTime.text = name
                mViewModel.shopHistoryDataList(
                    DataShop(
                        timeType = 1,
                        orderStatus = ArrayList(),
                        refundStatus = ArrayList(),
                        channelIds = ArrayList(),
                        adminUserIds = ArrayList(),
                        deliveryType = ArrayList(),
                        goodsType = ArrayList(),
                        orderType = ArrayList(),
                        classificationViewIds = ArrayList(),
                        poiIds = ArrayList(),
                        startTime = "$it 00:00:00",
                        endTime = formatCurrentDate() + " 23:59:59",
                        isValid = -1,
                        sortType = 0,
                        pageIndex = 1,
                        pageSize = 50,
                    )
                )
            }
        }
    }

    override fun createObserver() {
        mViewModel.dataShopList.onStart.observe(this) {
            showLoading("正在请求")
        }
        mViewModel.dataShopList.onSuccess.observe(this) {
            mDatabind.refDataShop.isRefreshing = false
            dismissLoading()
            mDatabind.textDataShopCount.text = it.orderCountTotal//减去
            mDatabind.textDataInsertOrderCount.text = it.orderCountTotal
            mDatabind.textDataRefundOrderCount.text = it.orderRefundTotal
            mDatabind.textDataAvgOrderCount.text = it.costTotal//加减乘除
            mDatabind.textDataValidOrderCount.text = it.validPriceTotal
            mDatabind.textDataActualOrderCount.text = it.actualPayPriceTotal
            mDatabind.textDataRefundOrderMoney.text = it.refundPriceTotal
        }
        mViewModel.dataShopList.onError.observe(this) {
            mDatabind.refDataShop.isRefreshing = false
            dismissLoading()
            showToast(it.msg)
        }
        mViewModel.shopDataList.onStart.observe(this) {
            showLoading("正在请求")
        }
        mViewModel.shopDataList.onSuccess.observe(this) {
            mDatabind.refDataShop.isRefreshing = false
            dismissLoading()
            dataShopAdapter.setList(it.orderStaticsGroupByHours as MutableList<DataShopList.OrderStaticsGroupByHour>)
        }

        mViewModel.shopDataList.onError.observe(this) {
            mDatabind.refDataShop.isRefreshing = false
            dismissLoading()
            showToast(it.msg)
        }


        mViewModel.shopHistoryDataList.onStart.observe(this) {
            showLoading("正在请求")
        }
        mViewModel.shopHistoryDataList.onSuccess.observe(this) {
            mDatabind.refDataShop.isRefreshing = false
            dismissLoading()
            mDatabind.textDataHistoryShopCount.text = it.orderCountTotal//减去
            mDatabind.textDataHistoryInsertOrderCount.text = it.orderCountTotal
            mDatabind.textDataHistoryRefundOrderCount.text = it.orderRefundTotal
            mDatabind.textDataHistoryAvgOrderCount.text = it.costTotal//加减乘除
            mDatabind.textDataHistoryValidOrderCount.text = it.validPriceTotal
            mDatabind.textDataHistoryActualOrderCount.text = it.actualPayPriceTotal
            mDatabind.textDataHistoryRefundOrderMoney.text = it.refundPriceTotal
        }
        mViewModel.shopHistoryDataList.onError.observe(this) {
            mDatabind.refDataShop.isRefreshing = false
            dismissLoading()
            showToast(it.msg)
        }
    }

}