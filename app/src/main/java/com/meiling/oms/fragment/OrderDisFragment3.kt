package com.meiling.oms.fragment

import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.launcher.ARouter
import com.meiling.common.fragment.BaseFragment
import com.meiling.common.network.data.LogisticsConfirmDtoList
import com.meiling.common.network.data.LogisticsInsertDto
import com.meiling.common.network.data.OrderSendAddress
import com.meiling.common.network.data.SelectLabel
import com.meiling.oms.databinding.FragmentDis3Binding
import com.meiling.oms.dialog.MineExitDialog
import com.meiling.oms.dialog.OrderDisPlatformDialog
import com.meiling.oms.eventBusData.MessageEventUpDataTip
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
    var channelType: String = ""
    var logisticsMenuList = ArrayList<SelectLabel>()
    lateinit var orderSendAddress: OrderSendAddress
    override fun initView(savedInstanceState: Bundle?) {

        mDatabind.txtRecPlatform.setSingleClickListener {
            if (logisticsMenuList.size > 0) {
                var orderDisGoodsSelectDialog = OrderDisPlatformDialog().newInstance(logisticsMenuList)
                orderDisGoodsSelectDialog.show(childFragmentManager)
                orderDisGoodsSelectDialog.setOkClickLister { id, name ->
                    mDatabind.txtRecPlatform.text = name
                    channelType = id
                }
            } else {
                showToast("请重试")
            }

        }
        poid = arguments?.getString("poid").toString()
        orderId = arguments?.getString("orderId").toString()
        orderPrice = arguments?.getString("orderPrice").toString()
        mViewModel.getOrderAndPoiDeliveryDate(
            poid,
            orderId,
            "SELF"
        )
        mViewModel.logisticsMenu()
        mDatabind.btnSendDis.setSingleClickListener {

            if (mDatabind.edtRecName.text.toString().isNullOrEmpty()) {
                showToast("请输入配送员姓名")
                return@setSingleClickListener
            }
            if (mDatabind.edtRecPhone.text.toString().isNullOrEmpty()) {
                showToast("请输入配送员手机号")
                return@setSingleClickListener
            }
            if (!isPhoneNumber(mDatabind.edtRecPhone.text.toString())) {
                showToast("请输入正确手机号")
                return@setSingleClickListener
            }

            if (mDatabind.edtRecPlatformOrder.text.toString().isNullOrEmpty()) {
                showToast("请输物流单号")
                return@setSingleClickListener
            }

            var insertOrderSendList = ArrayList<LogisticsInsertDto>()
            insertOrderSendList.add(
                LogisticsInsertDto(
                    deliveryType = "4",
                    orderId = orderId,
                    deliveryName = mDatabind.edtRecName.text.toString(),
                    deliveryPhone = mDatabind.edtRecPhone.text.toString(),
                    deliveryId = mDatabind.edtRecPlatformOrder.text.toString(),
                    channelType = channelType,
                    orderAndPoiDeliveryDateDto = orderSendAddress!!
                )
            )

//            if (orderSendAddress!!.lon.isNullOrBlank() || orderSendAddress!!.lat.isNullOrBlank()) {
//                showToast("经纬度无效，请重新修改地址")
//                return@setSingleClickListener
//            }
//            if (orderSendAddress!!.recvName.isNullOrBlank() || orderSendAddress!!.recvAddr.isNullOrBlank() ||
//                orderSendAddress!!.recvPhone.isNullOrBlank()
//            ) {
//                showToast("请补全收货信息")
//                return@setSingleClickListener
//            }
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
        mViewModel.orderSendAddress.onStart.observe(this) {
            showLoading("请求中")
        }
        mViewModel.orderSendAddress.onSuccess.observe(this) {
            dismissLoading()
            orderSendAddress = it
            mDatabind.edtRecName.setText(it.deliveryName)
            mDatabind.edtRecPhone.setText(it.deliveryPhone)
        }
        mViewModel.orderSendAddress.onError.observe(this) {
            dismissLoading()
            showToast("${it.msg}")
        }

        mViewModel.sendSuccess.onStart.observe(this) {
            showLoading("请求中")
        }
        mViewModel.sendSuccess.onSuccess.observe(this) {
            dismissLoading()
            if (it == 760) {
                val dialog: MineExitDialog =
                    MineExitDialog().newInstance(
                        "温馨提示",
                        "可用余额（账户余额-冻结余额）不足，无法发起配送，请去充值中心进行余额充值！",
                        "知道了",
                        "去充值",
                        false
                    )
                dialog.setOkClickLister {

                    ARouter.getInstance().build("/app/MyRechargeActivity").navigation()
                }
                dialog.show(childFragmentManager)
            } else {
                EventBus.getDefault().post(MessageEventUpDataTip())
                showToast("发起配送成功")
                mActivity.finish()
            }
        }
        mViewModel.sendSuccess.onError.observe(this) {
            dismissLoading()
//            if (it.msg == null || it.msg == "") {
//                showToast("操作失败")
//            } else {
                showToast(it.msg)
//            }

        }

        mViewModel.logisticsMenu.onStart.observe(this) {
        }
        mViewModel.logisticsMenu.onSuccess.observe(this) {
            dismissLoading()
            mDatabind.txtRecPlatform.text = it[0].label
            channelType = it[0].value
            logisticsMenuList.addAll(it)
        }
        mViewModel.logisticsMenu.onError.observe(this) {
            dismissLoading()
            showToast(it.msg)

        }
    }

    private fun isPhoneNumber(input: String): Boolean {
        val regex = Regex("^1[3-9]\\d{9}$")
        return regex.matches(input)
    }
}