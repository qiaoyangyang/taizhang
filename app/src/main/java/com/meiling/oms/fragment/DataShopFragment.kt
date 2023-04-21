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
import com.meiling.oms.dialog.DataTipDialog
import com.meiling.oms.eventBusData.MessageSelectShopPo
import com.meiling.oms.viewmodel.DataFragmentViewModel
import com.meiling.oms.widget.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

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
    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }
    override fun getBind(inflater: LayoutInflater): FragmentDataShopBinding {
        return FragmentDataShopBinding.inflate(inflater)
    }

    var startTime = formatCurrentDateBeforeDay()

    private fun initViewData() {
        mViewModel.shopDataList(
            DataListDto(
                startTime = formatCurrentDate() + " 00:00:00",
                endTime = formatCurrentDate() + " 23:59:59",
                poiId
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
                poiIds = poiId,
                startTime = "$startTime 00:00:00",
                endTime = formatCurrentDateBeforeDay() + " 23:59:59",
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
                poiIds = poiId,
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

    var poiId = ArrayList<String>()
    override fun initListener() {
        mDatabind.txtDataHistoryShopTime.setSingleClickListener {
            var dataSelectTimeDialog = DataSelectTimeDialog().newInstance()
            dataSelectTimeDialog.show(childFragmentManager)
            dataSelectTimeDialog.setSelectTime { it, name ->
                mDatabind.txtDataHistoryShopTime.text = name
                startTime = it
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
                        poiIds = poiId,
                        startTime = "$it 00:00:00",
                        endTime = formatCurrentDateBeforeDay() + " 23:59:59",
                        isValid = -1,
                        sortType = 0,
                        pageIndex = 1,
                        pageSize = 50,
                    )
                )
            }
        }

        mDatabind.txtDataOrderTip.setSingleClickListener {
            DataTipDialog().newInstance("订单小计=有效下单数-退单数").show(childFragmentManager)
        }
        mDatabind.txtDataOrderTip1.setSingleClickListener {
            DataTipDialog().newInstance("有效下单数").show(childFragmentManager)
        }
        mDatabind.txtDataOrderTip2.setSingleClickListener {
            DataTipDialog().newInstance("全部退款/取消的订单的数量").show(childFragmentManager)
        }
        mDatabind.txtDataOrderTip3.setSingleClickListener {
            DataTipDialog().newInstance("客单价=收款小计/订单数量小计")
           .show(childFragmentManager)
        }
        mDatabind.txtDataOrderTip4.setSingleClickListener {
            DataTipDialog().newInstance("收款小计=实付金额-退款金额")
                .show(childFragmentManager)
        }
        mDatabind.txtDataOrderTip5.setSingleClickListener {
            DataTipDialog().newInstance("用户实际支付的订单金额")
                .show(childFragmentManager)
        }

        mDatabind.txtDataOrderTip6.setSingleClickListener {
            DataTipDialog().newInstance("订单退款金额")
                .show(childFragmentManager)
        }
        mDatabind.txtHisDataOrderTip.setSingleClickListener {
            DataTipDialog().newInstance("订单小计=有效下单数-退单数")
                .show(childFragmentManager)
        }
        mDatabind.txtHisDataOrderTip1.setSingleClickListener {
            DataTipDialog().newInstance("有效下单数")
                .show(childFragmentManager)
        }
        mDatabind.txtHisDataOrderTip2.setSingleClickListener {
            DataTipDialog().newInstance("全部退款/取消的订单的数量")
                .show(childFragmentManager)
        }
        mDatabind.txtHisDataOrderTip3.setSingleClickListener {
            DataTipDialog().newInstance("客单价=收款小计/订单数量小计")
                .show(childFragmentManager)
        }
        mDatabind.txtHisDataOrderTip4.setSingleClickListener {
            DataTipDialog().newInstance("收款小计=实付金额-退款金额")
                .show(childFragmentManager)
        }
        mDatabind.txtHisDataOrderTip5.setSingleClickListener {
            DataTipDialog().newInstance("用户实际支付的订单金额")
                .show(childFragmentManager)
        }

        mDatabind.txtHisDataOrderTip6.setSingleClickListener {
            DataTipDialog().newInstance("订单退款金额")
                .show(childFragmentManager)
        }


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun eventSelectTime(messageSelectShopPo: MessageSelectShopPo) {
        poiId = messageSelectShopPo.idArrayList
        initViewData()
    }

    override fun createObserver() {
        mViewModel.dataShopList.onStart.observe(this) {
            showLoading("正在请求")
        }
        mViewModel.dataShopList.onSuccess.observe(this) {
            mDatabind.refDataShop.isRefreshing = false
            dismissLoading()
            mDatabind.textDataShopCount.text =
                calculationMinDataStr(it.orderCountTotal, it.orderRefundTotal)//减去
            mDatabind.textDataInsertOrderCount.text = it.orderCountTotal
            mDatabind.textDataRefundOrderCount.text = it.orderRefundTotal
            mDatabind.textDataAvgOrderCount.text = calculationDivDataStr(
                it.validPriceTotal,
                mDatabind.textDataShopCount.text.toString()
            )//加减乘除
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
            mDatabind.textDataHistoryShopCount.text =
                calculationMinDataStr(it.orderCountTotal, it.orderRefundTotal)//减去
            mDatabind.textDataHistoryInsertOrderCount.text = it.orderCountTotal ?: "0"
            mDatabind.textDataHistoryRefundOrderCount.text = it.orderRefundTotal ?: "0"
            mDatabind.textDataHistoryAvgOrderCount.text = calculationDivDataStr(
                it.validPriceTotal,
                mDatabind.textDataHistoryShopCount.text.toString()
            )
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