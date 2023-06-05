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
import com.meiling.common.network.data.OrderDto
import com.meiling.common.network.data.OrderGoodsVo
import com.meiling.common.utils.GlideAppUtils
import com.meiling.common.utils.SaveDecimalUtils
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivitySearch1Binding
import com.meiling.oms.dialog.MineExitDialog
import com.meiling.oms.dialog.OrderDistributionDetailDialog
import com.meiling.oms.dialog.OrderGoodsListDetailDialog
import com.meiling.oms.viewmodel.BaseOrderFragmentViewModel
import com.meiling.oms.widget.*

@Route(path = "/app/Search1Activity")
class OrderSearchActivity : BaseActivity<BaseOrderFragmentViewModel, ActivitySearch1Binding>() {

    lateinit var orderDisAdapter: BaseQuickAdapter<OrderDto.Content, BaseViewHolder>

    var telPhone = ""
    override fun initView(savedInstanceState: Bundle?) {
        orderDisAdapter =
            object :
                BaseQuickAdapter<OrderDto.Content, BaseViewHolder>(R.layout.item_home_base_order),
                LoadMoreModule {
                override fun convert(holder: BaseViewHolder, item: OrderDto.Content) {
                    val imgPrint = holder.getView<TextView>(R.id.img_order_print)
                    val checkMap = holder.getView<TextView>(R.id.txt_check_map)
                    val orderDelivery = holder.getView<TextView>(R.id.txt_base_order_delivery_1)
                    val btnSendDis =
                        holder.getView<ShapeTextView>(R.id.txt_base_order_dis)//发起配送或查看配送详情
                    val btnCancelDis =
                        holder.getView<TextView>(R.id.txt_base_order_dis_cancel)//取消配送
                    val btnOrderDisIgnore = holder.getView<TextView>(R.id.txt_order_ignore)//忽略配送
                    val btnShopDetail =
                        holder.getView<ShapeRelativeLayout>(R.id.srl_check_shop)//商品详情

                    val phone = holder.getView<TextView>(R.id.txt_base_order_delivery_phone)
                    val orderAddress =
                        holder.getView<TextView>(R.id.txt_base_order_delivery_address)
                    val callPhone = holder.getView<ImageView>(R.id.iv_call_phone)
                    val channelLogoImg =
                        holder.getView<AppCompatImageView>(R.id.img_order_channel_icon)
                    val imsDeliveryWay = holder.getView<AppCompatImageView>(R.id.ims_delivery_way)
                    holder.setText(R.id.txt_order_delivery_name, item.order?.recvName)
                    phone.text = item.order?.recvPhone
                    checkMap.text = "${item.distance}km"
                    telPhone = item.order?.recvPhone ?: ""
                    orderAddress.text = item.order?.recvAddr!!.replace("@@", "")
                    var sum: Double = 0.0
                    var sumNumber: Int = 0
                    if (item.goodsVoList?.isNotEmpty() == true) {

                        for (ne in item.goodsVoList!!) {
                            sum += ne?.totalPrice!!
                            sumNumber += ne?.number!!
                        }
                        holder.setText(
                            R.id.txt_base_order_shop_msg,
                            "共${sumNumber}件，共${SaveDecimalUtils.decimalUtils(sum)}元"
                        )
                        holder.setText(
                            R.id.txt_base_order_shop_name, "${item.goodsVoList!![0]?.gname}"
                        )
                    }

                    holder.setText(R.id.txt_base_order_No, "${item.order?.channelDaySn}")
                    holder.setText(
                        R.id.txt_base_order_delivery_time,
                        "${item.order?.arriveTimeDate}"
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
                            OrderGoodsListDetailDialog().newInstance(sumNumber,
                                SaveDecimalUtils.decimalUtils(sum).toString(),item.goodsVoList!!)
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
                        showToast("查看地图")
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
                            MineExitDialog().newInstance("温馨提示", "确定忽略订单？", "取消", "确认", false)
                        dialog.setOkClickLister {
                            mViewModel.invalid(item.order!!.viewId.toString(),"0")
                            dialog.dismiss()
                        }
                        dialog.show(supportFragmentManager)
                    }
                    var orderDisDialog =
                        OrderDistributionDetailDialog().newInstance(false, item.order?.viewId!!)
                    btnSendDis.setSingleClickListener {
                        when (item.order!!.logisticsStatus) {
                            "0" -> {
                                if (item.order!!.deliveryType == "2") {
                                    mViewModel.orderFinish(item.order!!.viewId!!)
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
                            "30", "50", "80" -> {
                                orderDisDialog.show(supportFragmentManager)
                            }

                        }
                    }
                    //deliveryType == "1" ,"3" 待配送 2:自提
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
                                btnSendDis.text = "确认出货"
                            } else {
                                btnSendDis.text = "发起配送"
                            }
                            btnOrderDisIgnore.visibility = View.VISIBLE
                            btnCancelDis.visibility = View.GONE
                        }
                        "20" -> {
                            btnCancelDis.visibility = View.VISIBLE
                            btnSendDis.text = "加小费"
                        }
                        "30" -> {
                            btnCancelDis.visibility = View.VISIBLE
                            btnSendDis.text = "配送详情"
                        }
                        "50" -> {
                            btnCancelDis.visibility = View.GONE
                            btnSendDis.text = "配送详情"
                        }
                        "70" -> {
                            btnCancelDis.visibility = View.GONE
                            btnSendDis.text = "重新配送"
                        }
                        "80" -> {
                            btnCancelDis.visibility = View.GONE
                            if (item.order!!.deliveryType == "2") {
                                btnSendDis.visibility = View.GONE
                            } else {

                                btnSendDis.visibility = View.VISIBLE
                                btnSendDis.text = "配送详情"
                            }
                        }
                    }
                }
            }
        mDatabind.rvHistoryOrderList.adapter = orderDisAdapter

        var intentOrderId = intent.getStringExtra("pushOrderId")
        if (intentOrderId != null) {
            b = true
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
                isValid = "",
                businessNumber = "",
                selectText = mDatabind.edtSearch.text.trim().toString()
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
                isValid = "",
                businessNumber = "",
                selectText = mDatabind.edtSearch.text.trim().toString()
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
                orderDisAdapter.setList(it.content as MutableList<OrderDto.Content>)
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
            mViewModel.orderList(
                logisticsStatus = "",
                startTime = "",
                endTime = "",
                businessNumberType = "1",
                pageIndex = 1,
                pageSize = "50",
                orderTime = "1",
                deliverySelect = "0",
                isValid = "",
                businessNumber = "",
                selectText = mDatabind.edtSearch.text.trim().toString()
            )
            showToast("出货成功")
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