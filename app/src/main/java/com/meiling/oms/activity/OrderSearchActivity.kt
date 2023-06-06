package com.meiling.oms.activity

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hjq.shape.layout.ShapeRelativeLayout
import com.hjq.shape.view.ShapeTextView
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.CancelOrderSend
import com.meiling.common.network.data.OrderDetailDto
import com.meiling.common.utils.GlideAppUtils
import com.meiling.common.utils.SaveDecimalUtils
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivitySearch1Binding
import com.meiling.oms.dialog.MineExitDialog
import com.meiling.oms.dialog.OrderDistributionDetailDialog
import com.meiling.oms.dialog.OrderGoodsListDetailDialog
import com.meiling.oms.viewmodel.BaseOrderFragmentViewModel
import com.meiling.oms.widget.KeyBoardUtil
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast

@Route(path = "/app/Search1Activity")
class OrderSearchActivity : BaseActivity<BaseOrderFragmentViewModel, ActivitySearch1Binding>() {

    lateinit var orderDisAdapter: BaseQuickAdapter<OrderDetailDto, BaseViewHolder>

    var telPhone = ""
    var intentIsValid: String = ""
    var deliveryConsumeType = 2

    override fun initView(savedInstanceState: Bundle?) {
        orderDisAdapter =
            object :
                BaseQuickAdapter<OrderDetailDto, BaseViewHolder>(R.layout.item_home_base_order),
                LoadMoreModule {
                override fun convert(holder: BaseViewHolder, item: OrderDetailDto) {
                    val imgPrint = holder.getView<TextView>(R.id.img_order_print)
                    val checkMap = holder.getView<TextView>(R.id.txt_check_map)
                    val orderDelivery = holder.getView<TextView>(R.id.txt_base_order_delivery_1)
                    val btnSendDis =
                        holder.getView<ShapeTextView>(R.id.txt_base_order_dis)//发起配送或查看配送详情
                    val btnCancelDis =
                        holder.getView<TextView>(R.id.txt_base_order_dis_cancel)//取消配送
                    val btnOrderDisIgnore = holder.getView<TextView>(R.id.txt_order_ignore)//忽略配送
                    val btnOrderCncelIgnore =
                        holder.getView<TextView>(R.id.txt_order_cancel_ignore)//取消忽略配送
                    val btnShopDetail =
                        holder.getView<ShapeRelativeLayout>(R.id.srl_check_shop)//商品详情

                    val phone = holder.getView<TextView>(R.id.txt_base_order_delivery_phone)
                    val orderAddress =
                        holder.getView<TextView>(R.id.txt_base_order_delivery_address)
                    val callPhone = holder.getView<ImageView>(R.id.iv_call_phone)
                    val imgIgnore = holder.getView<ImageView>(R.id.img_ignore)
                    val channelLogoImg =
                        holder.getView<AppCompatImageView>(R.id.img_order_channel_icon)
                    val imsDeliveryWay = holder.getView<AppCompatImageView>(R.id.ims_delivery_way)
                    holder.setText(R.id.txt_order_delivery_name, item.order?.recvName)
                    phone.text = item.order?.recvPhone
                    checkMap.text = "${item.distance}km"
                    telPhone = item.order?.recvPhone ?: ""
                    orderAddress.text = item.order?.recvAddr!!.replace("@@", "")
                    val sumNumber= item.goodsTotalNum ?: 0
                        holder.setText(
                            R.id.txt_base_order_shop_msg,
                            "共${item.goodsTotalNum}件，共${SaveDecimalUtils.decimalUtils(item.order!!.totalPrice!!)}元"
                        )
                    if (!item.goodsVoList.isNullOrEmpty()){
                        holder.setText(R.id.txt_base_order_shop_name, "${item.goodsVoList!![0]?.gname}")
                    }

                    holder.setText(R.id.txt_base_order_No, "${item.order?.channelDaySn}")
                    holder.setText(
                        R.id.txt_base_order_delivery_time,
                        "${item.arriveTime}"
                    )
                    holder.setText(R.id.txt_base_order_remark, "${item.order?.remark}")
                    holder.setText(
                        R.id.txt_order_store,
                        "${item.channelName} ${item.shopName}"
                    )
                    GlideAppUtils.loadUrl(
                        channelLogoImg,
                        item.channelLogo ?: "https://static.igoodsale.com/%E7%BA%BF%E4%B8%8B.svg"
                    )
                    callPhone.setSingleClickListener {
                        if (ContextCompat.checkSelfPermission(
                                context,
                                Manifest.permission.CALL_PHONE
                            )
                            == PackageManager.PERMISSION_GRANTED
                        ) {
                            // 如果有权限，拨打电话
                            dialPhoneNumber(telPhone)
                        } else {
                            // 如果没有权限，申请权限
                            ActivityCompat.requestPermissions(
                                this@OrderSearchActivity,
                                arrayOf(Manifest.permission.CALL_PHONE),
                                REQUEST_CALL_PHONE_PERMISSION
                            )
                        }
                    }
                    btnShopDetail.setSingleClickListener {
                        val orderGoodsListDetailDialog =
                            OrderGoodsListDetailDialog().newInstance(
                                sumNumber,
                                SaveDecimalUtils.decimalUtils(item.order!!.totalPrice!!).toString(),
                                item.goodsVoList!!
                            )
                        orderGoodsListDetailDialog.show(supportFragmentManager)
                    }
//
//                    if (item.order!!.type == 1) {
//                        holder?.setGone(R.id.txt_base_order_delivery_yu, false)
//                    } else {
//                        holder?.setGone(R.id.txt_base_order_delivery_yu, true)
//                    }
//
                    imgPrint.setSingleClickListener {
                        //收银小票:1 退款小票:3
                        when (item.order!!.logisticsStatus) {
                            "70" -> {
                                mViewModel.getPrint(
                                    item.order?.viewId.toString(),
                                    item.order?.shopId.toString(),
                                    "3"
                                )
                            }
                            else -> {
                                mViewModel.getPrint(
                                    item.order?.viewId.toString(),
                                    item.order?.shopId.toString(),
                                    "1"
                                )
                            }

                        }
                    }

//

                    checkMap.setSingleClickListener {
//                        showToast("查看地图")
                        startActivity(
                            Intent(
                               this@OrderSearchActivity,
                                OrderMapCheActivity::class.java
                            ).putExtra("orderDetailDto", item)
                        )
                    }
                    btnCancelDis.setSingleClickListener {
                        val dialog: MineExitDialog =
                            MineExitDialog().newInstance("温馨提示", "确定取消配送吗？", "取消", "确认", false)
                        dialog.setOkClickLister {
                            mViewModel.cancelOrder(
                                CancelOrderSend(
                                    deliveryConsumerId = item.deliveryConsume!!.id ?: "0",
                                    poiId = item.order!!.poiId ?: "0",
                                    stationChannelId = item.deliveryConsume!!.stationChannelId
                                        ?: "0"
                                )
                            )
                            dialog.dismiss()
                        }
                        dialog.show(supportFragmentManager)
                    }
                    btnOrderDisIgnore.setSingleClickListener {
                        val dialog: MineExitDialog =
                            MineExitDialog().newInstance(
                                "温馨提示",
                                "您确认要忽略该订单吗?\n忽略后可去「订单查询」中查找到该订单",
                                "取消",
                                "确认",
                                false
                            )
                        dialog.setOkClickLister {
                            mViewModel.invalid(item.order!!.viewId.toString(), "0")
                            dialog.dismiss()
                        }
                        dialog.show(supportFragmentManager)
                        intentIsValid = ""
                    }
                    btnOrderCncelIgnore.setSingleClickListener {
                        mViewModel.invalid(item.order!!.viewId.toString(), "1")
                        intentIsValid = "1"
                    }
                    var orderDisDialog =
                        OrderDistributionDetailDialog().newInstance(false, item.order?.viewId!!)
                    btnSendDis.setSingleClickListener {
                        when (item.order!!.logisticsStatus) {
                            "0" -> {
                                if (item.order!!.deliveryType == "2") {
                                    mViewModel.orderFinish(item.order!!.viewId!!)
                                    deliveryConsumeType = 2
                                } else {
                                    ARouter.getInstance().build("/app/OrderDisActivity")
                                        .withSerializable("kk", item.order).navigation()
                                }
                            }
                            "20" -> {
                                ARouter.getInstance().build("/app/OrderDisAddTipActivity")
                                    .withSerializable("kk", item).navigation()
                            }

                            "70" -> {
                                ARouter.getInstance().build("/app/OrderDisActivity")
                                    .withSerializable("kk", item.order).navigation()
                            }
                            "50" -> {
                                if(item.deliveryConsume?.type == 30){
                                    mViewModel.orderFinish(item.order!!.viewId!!)
                                    deliveryConsumeType = 30
                                }else{
                                    orderDisDialog.show(supportFragmentManager)
                                }
                            }
                            "30", "80" -> {
                                orderDisDialog.show(supportFragmentManager)
                            }

                        }
                    }
                    //deliveryType == "1" ,"3" 待配送 2:自提 isValid//1 有效 0无效
                    if (item.order!!.deliveryType == "2") {
                        imsDeliveryWay.visibility = View.VISIBLE
                        checkMap.visibility = View.INVISIBLE
                        orderAddress.visibility = View.GONE
                        orderDelivery.text = "前自提"
                    } else {
                        imsDeliveryWay.visibility = View.INVISIBLE
                        checkMap.visibility = View.VISIBLE
                        orderAddress.visibility = View.VISIBLE
                        orderDelivery.text = "前送达"
                    }
                    //0.待配送  20.待抢单 30.待取货 50.配送中 70.取消 80.已送达
                    when (item.order!!.logisticsStatus) {
                        "0" -> {
                            //deliveryType == "1" ,"3" 待配送 2:自提
                            if (item.order!!.deliveryType == "2") {
                                btnSendDis.text = "自提完成"
                            } else {
                                btnSendDis.text = "发起配送"
                            }
                            btnSendDis.visibility = View.VISIBLE
                            btnOrderDisIgnore.visibility = View.VISIBLE
                            btnCancelDis.visibility = View.GONE
                        }
                        "20" -> {
                            btnOrderDisIgnore.visibility = View.GONE
                            btnOrderCncelIgnore.visibility = View.GONE
                            btnCancelDis.visibility = View.VISIBLE
                            btnSendDis.visibility = View.VISIBLE
                            btnSendDis.text = "加小费"
                        }
                        "30" -> {
                            btnOrderDisIgnore.visibility = View.GONE
                            btnOrderCncelIgnore.visibility = View.GONE
                            btnCancelDis.visibility = View.VISIBLE
                            btnSendDis.visibility = View.VISIBLE
                            btnSendDis.text = "配送详情"

                        }
                        "50" -> {
                            btnOrderDisIgnore.visibility = View.GONE
                            btnOrderCncelIgnore.visibility = View.GONE
                            btnCancelDis.visibility = View.GONE
                            btnSendDis.visibility = View.VISIBLE
                            if (item.deliveryConsume!!.type == 30) {
                                btnSendDis.text = "配送完成"
                            } else {
                                btnSendDis.text = "配送详情"
                            }

                        }
                        "70" -> {
                            btnOrderDisIgnore.visibility = View.GONE
                            btnOrderCncelIgnore.visibility = View.GONE
                            btnCancelDis.visibility = View.GONE
                            btnSendDis.visibility = View.VISIBLE
                            btnSendDis.text = "重新配送"
                        }
                        "80" -> {
                            btnOrderDisIgnore.visibility = View.GONE
                            btnOrderDisIgnore.visibility = View.GONE
                            btnCancelDis.visibility = View.GONE
                            btnSendDis.visibility = View.VISIBLE
                            if (item.order!!.deliveryType == "2") {
                                btnSendDis.visibility = View.GONE
                            } else {
                                btnOrderDisIgnore.visibility = View.GONE
                                btnSendDis.visibility = View.VISIBLE
                                btnSendDis.text = "配送详情"
                            }
                        }
                    }



                    if (item.order!!.isValid == 0) {
                        imgIgnore.visibility = View.VISIBLE
                        btnCancelDis.visibility = View.GONE
                        btnSendDis.visibility = View.INVISIBLE
                        btnOrderDisIgnore.visibility = View.GONE
                        btnOrderCncelIgnore.visibility = View.VISIBLE
                    } else {
                        imgIgnore.visibility = View.GONE
                        btnOrderCncelIgnore.visibility = View.GONE
                    }
                }
            }
        mDatabind.rvHistoryOrderList.adapter = orderDisAdapter
        orderDisAdapter.setOnItemClickListener { adapter, view, position ->

            if (orderDisAdapter.data.get(position).order!!.deliveryType.toString() == "2") {
                startActivity(
                    Intent(
                        this,
                        OrderDetail1Activity::class.java
                    ).putExtra("orderViewId", orderDisAdapter.data.get(position).order!!.viewId)
                )
            } else {
                startActivity(
                    Intent(
                        this,
                        OrderDetailActivity::class.java
                    ).putExtra("orderViewId", orderDisAdapter.data.get(position).order!!.viewId)
                )
            }

        }
        var intentOrderId = intent.getStringExtra("pushOrderId")
        intentIsValid = intent.getStringExtra("isValid").toString()
        if (intentIsValid.isNotBlank()) {
            intentIsValid = ""
        }
        if (intentOrderId != null) {
            b = true
            intentIsValid = ""
            mDatabind.edtSearch.setText(intentOrderId)
        } else {
            b = false
        }
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivitySearch1Binding {
        return ActivitySearch1Binding.inflate(layoutInflater)
    }

    private var b = false

    override fun initListener() {

        mDatabind.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().isNotEmpty()) {
                    mDatabind.imgSearchEditClear.visibility = View.VISIBLE
                } else {
                    mDatabind.imgSearchEditClear.visibility = View.GONE
                }
            }
        })

        mDatabind.imgSearchEditClear.setSingleClickListener {
            mDatabind.edtSearch.setText("")
            orderDisAdapter.setList(null)
            orderDisAdapter.notifyDataSetChanged()
            mDatabind.rlOrderEmpty.visibility = View.VISIBLE
            mDatabind.rvHistoryOrderList.visibility = View.GONE
            mDatabind.txtErrorMsg.text = "支持通过订单编号、收货人姓名、手机号进行搜索"
        }

        mDatabind.btnSearch.setSingleClickListener {
            KeyBoardUtil.closeKeyBord(mDatabind.edtSearch, this)
            mViewModel.orderList(
                logisticsStatus = "",
                startTime = "",
                endTime = "",
                businessNumberType = "1",
                pageIndex = 1,
                pageSize = "50",
                orderTime = "1",
                deliverySelect = "0",
                isValid = intentIsValid,
                businessNumber = "",
                selectText = mDatabind.edtSearch.text.trim().toString(),
                sort = "4"
            )

        }


//        mDatabind.aivImg.setOnClickListener {
//            if (b) {
//                ARouter.getInstance().build("/app/SearchActivity").navigation()
//            } else {
//                finish()
//            }
//            b = !b
//        }
    }

    override fun onResume() {
        super.onResume()
        if (b) {
            KeyBoardUtil.closeKeyBord(mDatabind.edtSearch, this)
            mViewModel.orderList(
                logisticsStatus = "",
                startTime = "",
                endTime = "",
                businessNumberType = "1",
                pageIndex = 1,
                pageSize = "50",
                orderTime = "1",
                deliverySelect = "0",
                isValid = intentIsValid,
                businessNumber = "",
                selectText = mDatabind.edtSearch.text.trim().toString(),
                sort = "4"
            )

        }
    }

    override fun createObserver() {
        mViewModel.orderList.onStart.observe(this) {
            showLoading("请求中")
        }
        mViewModel.orderList.onSuccess.observe(this) {
            disLoading()
            if (it.content.isNullOrEmpty()) {
                orderDisAdapter.setList(null)
                mDatabind.rlOrderEmpty.visibility = View.VISIBLE
                mDatabind.rvHistoryOrderList.visibility = View.GONE
                mDatabind.txtErrorMsg.text = "未查询到订单"
            } else {
                mDatabind.rvHistoryOrderList.visibility = View.VISIBLE
                mDatabind.rlOrderEmpty.visibility = View.GONE
                orderDisAdapter.setList(it.content as MutableList<OrderDetailDto>)
            }
            orderDisAdapter.notifyDataSetChanged()
            Log.e("order", "createObserver: " + it)
        }
        mViewModel.orderList.onError.observe(this) {
            disLoading()
            orderDisAdapter.setList(null)
            mDatabind.rvHistoryOrderList.visibility = View.GONE
            mDatabind.rlOrderEmpty.visibility = View.VISIBLE
            mDatabind.txtErrorMsg.text = "支持通过订单编号、收货人姓名、手机号进行搜索"
            showToast("${it.msg}")
        }
        mViewModel.orderFinish.onStart.observe(this) {
            showLoading("请求中")
        }
        mViewModel.orderFinish.onSuccess.observe(this) {
            disLoading()
            if (deliveryConsumeType == 30) {
                showToast("配送完成")
            } else {
                showToast("自提完成")
            }
            mViewModel.orderList(
                logisticsStatus = "",
                startTime = "",
                endTime = "",
                businessNumberType = "1",
                pageIndex = 1,
                pageSize = "50",
                orderTime = "1",
                deliverySelect = "0",
                isValid = intentIsValid,
                businessNumber = "",
                selectText = mDatabind.edtSearch.text.trim().toString()
            )
//            showToast("出货成功")
        }
        mViewModel.orderFinish.onError.observe(this) {
            disLoading()
            showToast(it.msg)
        }
        mViewModel.printDto.onStart.observe(this) {
        }
        mViewModel.printDto.onSuccess.observe(this) {
            showToast("已发送打印任务")

        }
        mViewModel.printDto.onError.observe(this) {
            showToast(it.msg)
        }


        mViewModel.invalidDto.onSuccess.observe(this) {
            if (intentIsValid.isNullOrEmpty()) {
                showToast("订单忽略成功")
            } else {
                showToast("取消忽略成功")
            }
            KeyBoardUtil.closeKeyBord(mDatabind.edtSearch, this)
            mViewModel.orderList(
                logisticsStatus = "",
                startTime = "",
                endTime = "",
                businessNumberType = "1",
                pageIndex = 1,
                pageSize = "50",
                orderTime = "1",
                deliverySelect = "0",
                isValid = intentIsValid,
                businessNumber = "",
                selectText = mDatabind.edtSearch.text.trim().toString(),
                sort = "4"
            )
        }
        mViewModel.invalidDto.onError.observe(this) {
            showToast(it.msg)
        }
    }


    var REQUEST_CALL_PHONE_PERMISSION = 1
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == REQUEST_CALL_PHONE_PERMISSION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 如果用户授权了权限，拨打电话
                dialPhoneNumber(telPhone)
            } else {
                // 如果用户拒绝了权限，可以在这里处理相应的逻辑
                showToast("拒绝了打电话权限，请手动开启")
            }
        }
    }

    private fun dialPhoneNumber(phoneNumber: String) {
        val dialIntent = Intent(Intent.ACTION_CALL, Uri.parse("tel:${phoneNumber}"))
        startActivity(dialIntent)
    }
}