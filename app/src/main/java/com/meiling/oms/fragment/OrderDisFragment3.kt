package com.meiling.oms.fragment

import android.os.Bundle
import android.view.LayoutInflater
import com.meiling.common.fragment.BaseFragment
import com.meiling.common.network.data.LogisticsConfirmDtoList
import com.meiling.common.network.data.LogisticsInsertDto
import com.meiling.common.network.data.OrderSendAddress
import com.meiling.oms.eventBusData.MessageEventUpDataTip
import com.meiling.oms.databinding.FragmentDis3Binding
import com.meiling.oms.viewmodel.OrderDisFragmentViewModel
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast
import org.greenrobot.eventbus.EventBus

class OrderDisFragment3 : BaseFragment<OrderDisFragmentViewModel, FragmentDis3Binding>() {

    companion object {
        fun newInstance(
            poid: String,
            orderId: String,
            orderPrice: Double,
        ): OrderDisFragment3 {
            val args = Bundle()
            args.putString("orderId", orderId)
            args.putString("orderPrice", orderPrice.toString())
            args.putString("poid", poid)
            val fragment = OrderDisFragment3()
            fragment.arguments = args
            return fragment
        }
    }

    var poid: String = ""
    var orderId: String = ""
    var orderPrice: String = ""
    lateinit var orderSendAddress: OrderSendAddress
    override fun initView(savedInstanceState: Bundle?) {

        poid = arguments?.getString("poid").toString()
        orderId = arguments?.getString("orderId").toString()
        orderPrice = arguments?.getString("orderPrice").toString()
        mViewModel.getOrderAndPoiDeliveryDate(
            poid,
            orderId,
            "SELF"
        )
        mDatabind.btnSendDis.setSingleClickListener {
            var insertOrderSendList = ArrayList<LogisticsInsertDto>()
            insertOrderSendList.add(
                LogisticsInsertDto(
                    deliveryType = "4",
                    orderId = orderId,
                    deliveryName = mDatabind.edtRecName.text.toString(),
                    deliveryPhone = mDatabind.edtRecPhone.text.toString(),
                    deliveryId = "",
                    channelType = "",
                    orderAndPoiDeliveryDateDto = orderSendAddress!!
                )
            )

            if (orderSendAddress!!.lon.isNullOrBlank() || orderSendAddress!!.lat.isNullOrBlank()) {
                showToast("经纬度无效，请重新修改地址")
                return@setSingleClickListener
            }

            if (orderSendAddress!!.recvName.isNullOrBlank() || orderSendAddress!!.recvAddr.isNullOrBlank() ||
                orderSendAddress!!.recvPhone.isNullOrBlank()
            ) {
                showToast("请补全收货信息")
                return@setSingleClickListener
            }
            if (!insertOrderSendList.isNullOrEmpty()) {
                mViewModel.insertOrderSend(LogisticsConfirmDtoList(logisticsConfirmDtoList = insertOrderSendList))
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getOrderAndPoiDeliveryDate(
            poid,
            orderId,
            "SELF"
        )
    }

    override fun getBind(inflater: LayoutInflater): FragmentDis3Binding {
        return FragmentDis3Binding.inflate(inflater)
    }

    override fun initListener() {

    }

    override fun createObserver() {
        mViewModel.orderSendAddress.onStart.observe(this) {}
        mViewModel.orderSendAddress.onSuccess.observe(this) {
            orderSendAddress = it
            mDatabind.edtRecName.setText(it.deliveryName)
            mDatabind.edtRecPhone.setText(it.deliveryPhone)
        }
        mViewModel.orderSendAddress.onError.observe(this) {
            showToast("${it.msg}")
        }

        mViewModel.sendSuccess.onStart.observe(this) {
            showLoading("正在请求")
        }
        mViewModel.sendSuccess.onSuccess.observe(this) {
            dismissLoading()
            EventBus.getDefault().post(MessageEventUpDataTip())
            showToast("发起配送成功")
            mActivity.finish()
        }
        mViewModel.sendSuccess.onSuccess.observe(this) {
            dismissLoading()
            showToast("发起配送失败 ")
        }
    }

}