package com.meiling.oms.fragment

import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.fragment.BaseFragment
import com.meiling.common.network.data.*
import com.meiling.common.utils.SoftKeyBoardListener
import com.meiling.oms.eventBusData.MessageEventUpDataTip
import com.meiling.oms.R
import com.meiling.oms.activity.OrderDisActivity
import com.meiling.oms.databinding.FragmentDis2Binding
import com.meiling.oms.dialog.MineExitDialog
import com.meiling.oms.dialog.OrderDisGoodsSelectDialog
import com.meiling.oms.viewmodel.OrderDisFragmentViewModel
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast
import org.greenrobot.eventbus.EventBus
import java.util.regex.Pattern

class OrderDisFragment2 : BaseFragment<OrderDisFragmentViewModel, FragmentDis2Binding>() {

    companion object {
        fun newInstance(
            poid: String,
            orderId: String,
            orderPrice: Double,
        ): OrderDisFragment2 {
            val args = Bundle()
            args.putString("orderId", orderId)
            args.putString("orderPrice", orderPrice.toString())
            args.putString("poid", poid)
            val fragment = OrderDisFragment2()
            fragment.arguments = args
            return fragment
        }
    }

    private lateinit var shopSelectDisWayAdapter: BaseQuickAdapter<OrderSendChannel, BaseViewHolder>

    var poid: String = ""
    var orderId: String = ""
    var selectShop: String = "30"
    var orderPrice: String = ""
    lateinit var orderSendAddress: OrderSendAddress

    override fun initView(savedInstanceState: Bundle?) {
        //deliveryType 同城2 物流 3
        poid = arguments?.getString("poid").toString()
        orderId = arguments?.getString("orderId").toString()
        orderPrice = arguments?.getString("orderPrice").toString()
        mViewModel.getOrderAndPoiDeliveryDate(
            poid,
            orderId,
            "EXPRESS"
        )
        var orderDisGoodsSelectDialog = OrderDisGoodsSelectDialog().newInstance()
        mDatabind.txtRecShop.setSingleClickListener {
            orderDisGoodsSelectDialog.show(childFragmentManager)
            orderDisGoodsSelectDialog.setOkClickLister { id, name ->
                mDatabind.txtRecShop.text = name
                selectShop = id
            }
        }
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
                    } else {
                        holder.setBackgroundResource(R.id.rlSelect, R.drawable.order_bg_dis_rv)
                    }
                    if (item.errMsg == null) {
//                        viewPrice.textSize = 14f
                        viewPrice.visibility = View.INVISIBLE
                    } else {
                        viewPrice.visibility = View.VISIBLE
                        viewPrice.textSize = 11f
                        viewPrice.setTextColor(resources.getColor(R.color.red))
                        viewPrice.text = item.errMsg
                    }
                }
            }
        mDatabind.rvRecType.adapter = shopSelectDisWayAdapter
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getOrderAndPoiDeliveryDate(
            poid,
            orderId,
            "EXPRESS"
        )
    }

    private fun initViewData() {
        var text = orderSendAddress.goodsWeight!!.toInt()

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
                            deliveryType = "3",
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
            if (insertOrderSendList.isNullOrEmpty()) {
                showToast("请选择配送平台")
            } else {
                mViewModel.insertOrderSend(LogisticsConfirmDtoList(logisticsConfirmDtoList = insertOrderSendList))
            }
        }
        shopSelectDisWayAdapter.setOnItemClickListener { adapter, view, position ->
            var data = adapter.data[position] as OrderSendChannel
            if (data.errMsg != null) {
            } else {
                data.select = !shopSelectDisWayAdapter.getItem(position).select
            }
            shopSelectDisWayAdapter.notifyDataSetChanged()
        }
        var weight = orderSendAddress.goodsWeight!!.toInt()
        mDatabind.txtAddTipPlus.setSingleClickListener {
            mDatabind.edtAddTipShow.setText(
                "${
                    mDatabind.edtAddTipShow.text.toString().toInt() + 1
                }"
            )
            var orderSendRequest = OrderSendRequest(
                cargoPrice = orderPrice!!,
                cargoType = selectShop,
                deliveryTime = "",
                deliveryType = "3",
                orderId = orderId!!,
                wight = mDatabind.edtAddTipShow.text.toString(),
                orderSendAddress
            )
            mViewModel.orderSendConfirm(orderSendRequest)
        }
        mDatabind.txtAddTipMinus.setSingleClickListener {
            if (mDatabind.edtAddTipShow.text.toString().toInt() <= 1) {
                showToast("不能在减啦")
                return@setSingleClickListener
            }
            mDatabind.edtAddTipShow.setText(
                "${
                    mDatabind.edtAddTipShow.text.toString().toInt() - 1
                }"
            )
            var orderSendRequest = OrderSendRequest(
                cargoPrice = orderPrice,
                cargoType = selectShop,
                deliveryTime = "",
                deliveryType = "3",
                orderId = orderId!!,
                wight = mDatabind.edtAddTipShow.text.toString(),
                orderSendAddress
            )
            mViewModel.orderSendConfirm(orderSendRequest)
        }
        mDatabind.edtAddTipShow?.setOnEditorActionListener { v, actionId, event ->
            if (actionId == 0 || actionId == 3) {
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
            return@setOnEditorActionListener false
        }
        SoftKeyBoardListener.setListener(activity, object :
            SoftKeyBoardListener.OnSoftKeyBoardChangeListener {
            override fun keyBoardShow(height: Int) {
            }

            override fun keyBoardHide(height: Int) {
                if (!TextUtils.isEmpty(mDatabind.edtAddTipShow.text.toString())) {
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
        })

    }

    override fun getBind(inflater: LayoutInflater): FragmentDis2Binding {
        return FragmentDis2Binding.inflate(inflater)
    }

    override fun initListener() {

    }

    override fun createObserver() {
        mViewModel.orderSendAddress.onStart.observe(this) {}
        mViewModel.orderSendAddress.onSuccess.observe(this) {
            orderSendAddress = it
            addressChange(EventBusChangeAddress(orderSendAddress, "EXPRESS"))
            initViewData()
        }
        mViewModel.orderSendAddress.onError.observe(this) {
            showToast("${it.msg}")
        }

        mViewModel.sendSuccess.onStart.observe(this) {
            showLoading("请求中")
        }
        mViewModel.sendSuccess.onSuccess.observe(this) {
            dismissLoading()
            showToast("发起配送成功")
            EventBus.getDefault().post(MessageEventUpDataTip())
            mActivity.finish()
        }
        mViewModel.sendSuccess.onError.observe(this) {
            dismissLoading()
            showToast("发起配失败")
            if (it.errCode == 760) {
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
                showToast(it.msg)
            }
        }
        mViewModel.orderSendConfirmList.onStart.observe(this) {
            showLoading("请求中")
        }
        mViewModel.orderSendConfirmList.onStart.observe(this) {
            showLoading("加载中")
        }
        mViewModel.orderSendConfirmList.onSuccess.observe(this) {
            dismissLoading()
            if (!it.isNullOrEmpty()) {
                shopSelectDisWayAdapter.setList(it)
                it[0].select = true
                for (bean in it) {
                    if (orderSendAddress.method == "all") {
                        shopSelectDisWayAdapter.data.forEach { bean ->
                            bean.select = bean.errMsg == null
                        }
                    }
//                    else {
//                        shopSelectDisWayAdapter.it[0].select = true
//                    }
                    shopSelectDisWayAdapter.notifyDataSetChanged()
                }
            }
        }
        mViewModel.orderSendConfirmList.onError.observe(this) {
            dismissLoading()
            showToast("发起配送失败")
        }

        var outStr = "25";
        mDatabind.edtAddTipShow?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
                var edit = s.toString()

                if (edit?.length == 2 && Integer.parseInt(edit) >= 10) {
                    outStr = edit;
                }

            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                var words = s.toString()
                //首先内容进行非空判断，空内容（""和null）不处理
                if (!TextUtils.isEmpty(words)) {
                    //1-100的正则验证
                    var p = Pattern.compile("^(25|[1-9]\\d|\\d)$")
                    var m = p.matcher(words)
                    if (m.find() || ("").equals(words)) {
                        //这个时候输入的是合法范围内的值
                    } else {
                        if (words.length > 2) {
                            //若输入不合规，且长度超过2位，继续输入只显示之前存储的outStr
                            mDatabind.edtAddTipShow.setText(outStr)
                            // mDatabind.setText(outStr);
                            //重置输入框内容后默认光标位置会回到索引0的地方，要改变光标位置
                            mDatabind.edtAddTipShow.setSelection(2)
                        }
                    }
                }


            }

            override fun afterTextChanged(s: Editable?) {
                var  words = s.toString()
                //首先内容进行非空判断，空内容（""和null）不处理
                if (!TextUtils.isEmpty(words)) {
                    if (Integer.parseInt(s.toString()) > 25) {
                        mDatabind.edtAddTipShow.setText("25")
                    }
                }
            }

        })


    }


    private fun addressChange(eventBusChangeAddress: EventBusChangeAddress) {
        orderSendAddress = eventBusChangeAddress.orderSendAddress
        mDatabind.edtAddTipShow.setText(  orderSendAddress.goodsWeight)
        var orderSendRequest = OrderSendRequest(
            cargoPrice = orderPrice!!,
            cargoType = orderSendAddress.cargoType ?: "0",
            deliveryTime = "",
            deliveryType = "3",
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