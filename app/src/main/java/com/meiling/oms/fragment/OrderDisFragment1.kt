package com.meiling.oms.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.fragment.BaseFragment
import com.meiling.common.network.data.*
import com.meiling.oms.EventBusData.MessageEventUpDataTip
import com.meiling.oms.R
import com.meiling.oms.databinding.FragmentDis1Binding
import com.meiling.oms.dialog.OrderDisGoodsSelectDialog
import com.meiling.oms.viewmodel.OrderDisFragmentViewModel
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast
import org.greenrobot.eventbus.EventBus

class OrderDisFragment1 : BaseFragment<OrderDisFragmentViewModel, FragmentDis1Binding>() {

    companion object {
        fun newInstance(
            bean: OrderSendAddress,
            poid: String,
            orderId: String,
            orderPrice: Double,
        ): OrderDisFragment1 {
            val args = Bundle()
            args.putSerializable("bean", bean)
            args.putString("orderId", orderId)
            args.putString("orderPrice", orderPrice.toString())
            args.putString("poid", poid)
            val fragment = OrderDisFragment1()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var shopSelectDisWayAdapter: BaseQuickAdapter<OrderSendChannel, BaseViewHolder>

    var orderId: String = ""
    var selectShop: String = "30"
    var orderPrice: String = ""
    lateinit var orderSendAddress: OrderSendAddress
    override fun initView(savedInstanceState: Bundle?) {

        var orderDisGoodsSelectDialog = OrderDisGoodsSelectDialog().newInstance()
        mDatabind.txtRecShop.setSingleClickListener {
            orderDisGoodsSelectDialog.show(childFragmentManager)
            orderDisGoodsSelectDialog.setOkClickLister { id, name ->
                mDatabind.txtRecShop.text = name
                selectShop = id
            }
        }
        //deliveryType 同城2 物流 3
        orderSendAddress = arguments?.getSerializable("bean") as OrderSendAddress
//        EventBus.getDefault().post(orderSendAddress)
        orderId = arguments?.getString("orderId").toString()
        orderPrice = arguments?.getString("orderPrice").toString()
        addressChange(EventBusChangeAddress(orderSendAddress, "TC"))
        shopSelectDisWayAdapter =
            object : BaseQuickAdapter<OrderSendChannel, BaseViewHolder>(R.layout.item_dis_send) {
                override fun convert(holder: BaseViewHolder, item: OrderSendChannel) {
                    var imgSelect = holder.getView<ImageView>(R.id.imgSelectIcon)
                    var money = holder.getView<TextView>(R.id.txt_money)
                    var viewPrice = holder.getView<TextView>(R.id.txtTip)
                    var name = holder.getView<TextView>(R.id.txt_name)
                    Glide.with(context)
                        .load(item.iconUrl)
                        .transition(DrawableTransitionOptions().crossFade())
                        .apply(
                            RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.DATA)
                        )
                        .into(imgSelect)
                    money.text = item.payMoney
                    name.text = item.typeName
                    if (item.select) {
                        holder.setBackgroundResource(R.id.rlSelect, R.drawable.order_dis_channel_bg)
//                        holder.setTextColor(R.id.selectAll, Color.parseColor("#FE4B48"))
                    } else {
                        holder.setBackgroundResource(R.id.rlSelect, R.drawable.order_bg_dis_rv)
//                        holder.setTextColor(R.id.selectAll, Color.parseColor("#535C7A"))
                    }
                    if (item.errMsg == null) {
//                        viewPrice.textSize = 14f
                        viewPrice.visibility = View.GONE
                    } else {
                        viewPrice.visibility = View.VISIBLE
                        viewPrice.textSize = 11f
                        viewPrice.setTextColor(resources.getColor(R.color.red))
                        viewPrice.text = item.errMsg
                    }
                }
            }
        mDatabind.rvRecType.adapter = shopSelectDisWayAdapter
        var text = orderSendAddress.goodsWeight?.toInt()

        mDatabind.btnSendDis.setSingleClickListener {
            var insertOrderSendList = ArrayList<LogisticsInsertDto>()
            for (shopSelectDis in shopSelectDisWayAdapter.data) {
                if (shopSelectDis.select) {
                    insertOrderSendList.add(
                        LogisticsInsertDto(
                            amount = shopSelectDis.payMoney,
                            cargoPrice = shopSelectDis.payMoney,
                            cargoType = selectShop,
                            channelType = shopSelectDis.channelType,
                            deliveryTime = "",
                            deliveryType = "2",
                            distance = shopSelectDis.distance ?: "0",
                            orderId = orderId,
                            orgId = shopSelectDis.originId ?: "0",
                            wight = text.toString(),
                            orderAndPoiDeliveryDateDto = orderSendAddress.copy(
                                originId = shopSelectDis.originId ?: "0",
                                deliverId = shopSelectDis.deliverId ?: "0",
                                stationChannelId = shopSelectDis.stationChannelId ?: "0",
                            )
                        )
                    )
                }
            }
            if (orderSendAddress.lon.isNullOrBlank() || orderSendAddress.lat.isNullOrBlank()) {
                showToast("经纬度无效，请重新修改地址")
                return@setSingleClickListener
            }

            if (orderSendAddress.recvName.isNullOrBlank() || orderSendAddress.recvAddr.isNullOrBlank() ||
                orderSendAddress.recvPhone.isNullOrBlank()
            ) {
                showToast("请补全收货信息")
                return@setSingleClickListener
            }
            if (!insertOrderSendList.isNullOrEmpty()) {
                mViewModel.insertOrderSend(LogisticsConfirmDtoList(logisticsConfirmDtoList = insertOrderSendList))
            } else {
                showToast("请选择配送方式")
            }
        }
        shopSelectDisWayAdapter.setOnItemClickListener { adapter, view, position ->
            var data = adapter.data[position] as OrderSendChannel
            if (data.errMsg != null) {
//                shopSelectDisWayAdapter.getItem(position).select = false
//                OrderDistributionCheckRuleDialog().newInstance().show(this.childFragmentManager)
            } else {
                data.select = !shopSelectDisWayAdapter.getItem(position).select
            }
            shopSelectDisWayAdapter.notifyDataSetChanged()
        }

        var weight = orderSendAddress.goodsWeight!!.toInt()
        mDatabind.txtAddTipPlus.setSingleClickListener {
            mDatabind.edtAddTipShow.text = "${weight++}"
            var orderSendRequest = OrderSendRequest(
                cargoPrice = orderPrice!!,
                cargoType = selectShop,
                deliveryTime = "",
                deliveryType = "2",
                orderId = orderId!!,
                wight = mDatabind.edtAddTipShow.text.toString(),
                orderSendAddress
            )
            mViewModel.orderSendConfirm(orderSendRequest)
        }
        mDatabind.txtAddTipMinus.setSingleClickListener {
            if (weight <= 1) {
                showToast("不能在减啦")
                return@setSingleClickListener
            }
            mDatabind.edtAddTipShow.text = "${weight--}"
            var orderSendRequest = OrderSendRequest(
                cargoPrice = orderPrice,
                cargoType = selectShop,
                deliveryTime = "",
                deliveryType = "2",
                orderId = orderId!!,
                wight = mDatabind.edtAddTipShow.text.toString(),
                orderSendAddress
            )
            mViewModel.orderSendConfirm(orderSendRequest)
        }

    }

    override fun getBind(inflater: LayoutInflater): FragmentDis1Binding {
        return FragmentDis1Binding.inflate(inflater)
    }


    override fun initListener() {

    }


    override fun createObserver() {
        mViewModel.sendSuccess.onStart.observe(this) {
            showLoading("正在请求。。。")
        }
        mViewModel.sendSuccess.onSuccess.observe(this) {
            dismissLoading()
            EventBus.getDefault().post(MessageEventUpDataTip())
            showToast("已成功发起配送 请在订单页面，查看配送详情")
            mActivity.finish()
        }
        mViewModel.sendSuccess.onError.observe(this) {
            dismissLoading()
            showToast("发起配送失败 , 失败原因：${it.toString()}")
        }
        mViewModel.orderSendConfirmList.onStart.observe(this) {

        }
        mViewModel.orderSendConfirmList.onSuccess.observe(this) {
            if (!it.isNullOrEmpty()) {
                shopSelectDisWayAdapter.setList(it)
                for (bean in it) {
                    if (orderSendAddress.method == "all") {
                        shopSelectDisWayAdapter.data.forEach { bean ->
                            bean.select = bean.errMsg == null
                        }
                    }
//                    else {
//                        shopSelectDisWayAdapter.data[0].select = true
//                    }
                    shopSelectDisWayAdapter.notifyDataSetChanged()
                }
            }
        }
        mViewModel.orderSendConfirmList.onError.observe(this) {
            showToast("发起配送失败 , 失败原因：${it.toString()}")
        }
    }


    private fun addressChange(eventBusChangeAddress: EventBusChangeAddress) {
        orderSendAddress = eventBusChangeAddress.orderSendAddress
        mDatabind.edtAddTipShow.text = orderSendAddress.goodsWeight
        var orderSendRequest = OrderSendRequest(
            cargoPrice = orderPrice!!,
            cargoType = orderSendAddress.cargoType ?: "0",
            deliveryTime = "",
            deliveryType = "2",
            orderId = orderId!!,
            wight = if (orderSendAddress.goodsWeight!! != null) {
                orderSendAddress.goodsWeight!!
            } else {
                "1"
            },
            orderAndPoiDeliveryDateDto = orderSendAddress
        )
        if (orderSendAddress.lon.isNullOrBlank()) {
            showToast("请填写完整的收货地址")
        } else {
            mViewModel.orderSendConfirm(orderSendRequest)
        }
    }

    private fun checkString(str: String?): String {
        return str ?: "0"
    }
}