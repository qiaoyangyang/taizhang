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
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.CancelOrderSend
import com.meiling.common.network.data.OrderDto
import com.meiling.common.utils.GlideAppUtils
import com.meiling.common.utils.SaveDecimalUtils
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivitySearch1Binding
import com.meiling.oms.dialog.MineExitDialog
import com.meiling.oms.dialog.OrderDistributionDetailDialog
import com.meiling.oms.viewmodel.BaseOrderFragmentViewModel
import com.meiling.oms.widget.*

@Route(path = "/app/Search1Activity")
class Search1Activity : BaseActivity<BaseOrderFragmentViewModel, ActivitySearch1Binding>() {

    lateinit var orderDisAdapter: BaseQuickAdapter<OrderDto.Content, BaseViewHolder>
    lateinit var orderGoodsListAdapter: BaseQuickAdapter<OrderDto.Content.GoodsVo, BaseViewHolder>

    var telPhone = ""
    override fun initView(savedInstanceState: Bundle?) {
        orderDisAdapter =
            object : BaseQuickAdapter<OrderDto.Content, BaseViewHolder>(R.layout.item_home_order),
                LoadMoreModule {
                override fun convert(holder: BaseViewHolder, item: OrderDto.Content) {
                    holder.setText(R.id.txt_order_delivery_type, item.orderName)
                    val imgPrint = holder.getView<ImageView>(R.id.img_shop_print)
                    val imgShopCopy = holder.getView<ImageView>(R.id.img_shop_copy)
                    val changeOrder = holder.getView<TextView>(R.id.txt_change_order)
                    val btnSendDis = holder.getView<TextView>(R.id.txt_order_dis)//发起配送或查看配送详情
                    val btnCancelDis = holder.getView<TextView>(R.id.txt_order_dis_cancel)//取消配送
                    val showMsg = holder.getView<TextView>(R.id.txt_shop_show_all)
                    val hideMsg = holder.getView<TextView>(R.id.txt_show_hide)
                    val copyOrderId = holder.getView<TextView>(R.id.txt_copy_order)
                    val orderId = holder.getView<TextView>(R.id.txt_order_id)
                    val channelLogoImg = holder.getView<ImageView>(R.id.img_order_icon)
                    val phone = holder.getView<TextView>(R.id.txt_order_delivery_phone)
                    holder.setText(R.id.txt_order_delivery_name, item.order?.recvName)
                    phone.text = item.order?.recvPhone
                    telPhone = item.order?.recvPhone ?: ""
                    holder.setText(
                        R.id.txt_order_delivery_address,
                        item.order?.recvAddr?.replace("@@", "")
                    )
                    holder.setText(R.id.txt_order_num, "#${item.order?.channelDaySn}")
                    holder.setText(R.id.txt_shop_actual_money, "${item.order?.actualIncome}")
                    holder.setText(R.id.txt_order_delivery_time, "${item.order?.arriveTimeDate}")
                    holder.setText(R.id.txt_pay_money, "¥${item.order?.payPrice}")
                    holder.setText(R.id.txt_pay_fee, "¥${item.order?.platformServiceFee}")
                    holder.setText(R.id.txt_order_total_money, "¥${item.order?.actualIncome}")
                    val options = RequestOptions().format(DecodeFormat.PREFER_ARGB_8888)
                    //加载svg图片
//                    Glide.with(context).`as`(PictureDrawable::class.java).load(item.channelLogo)
//                        .apply(options).listener(SvgSoftwareLayerSetter()).into(channelLogoImg)
                    GlideAppUtils.loadUrl(channelLogoImg,item.channelLogo?:"https://static.igoodsale.com/default-goods.png")
                    orderId.text = "${item.order?.viewId}"
                    holder.setText(R.id.txt_order_remark, "${item.order?.remark}")
                    if(item.order?.channelCreateTime.isNullOrBlank()){
                        holder.setText(
                            R.id.txt_time_shop,
                            "${transToString(item.order?.createTime!!)}下单  ${item.channelName}店铺"
                        )
                    }else{
                        holder.setText(
                            R.id.txt_time_shop,
                            "${item.order?.channelCreateTime}下单  ${item.channelName}店铺"
                        )
                    }
                    holder.setText(R.id.txt_shop_name, "${item.shopName}")
                    showMsg.setSingleClickListener {
                        holder.setGone(R.id.cos_hide, true)
                        holder.setGone(R.id.cos_shop_show, false)
                    }
                    hideMsg.setSingleClickListener {
                        holder.setGone(R.id.cos_hide, false)
                        holder.setGone(R.id.cos_shop_show, true)
                    }
                    copyOrderId.setSingleClickListener {
                        copyText(context, orderId.text.toString())
                        showToast("复制成功")
                    }

                    if (item.order!!.type == 1) {
                        holder?.setGone(R.id.txt_order_delivery_yu, false)
                    } else {
                        holder?.setGone(R.id.txt_order_delivery_yu, true)
                    }

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
                    phone.setSingleClickListener {
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
                                this@Search1Activity,
                                arrayOf(Manifest.permission.CALL_PHONE),
                                REQUEST_CALL_PHONE_PERMISSION
                            )
                        }
                    }
                    var listGoods = item.goodsVoList
                    var x = ""
                    for (goods in listGoods!!) {
                        x += "名称:" + goods?.gname + "\n数量:" + goods?.number + "\n价格:" + goods?.price
                    }
                    imgShopCopy.setSingleClickListener {
                        copyText(
                            context,
                            "订单来源:" + "${item.channelName} \n" + "门店名称:${item.shopName}\n" + "订单编号:${item.order?.viewId}\n" + "-------\n" + "商品信息:${x}\n" +
//                                    "商品信息${Gson().fromJson(filteredData.toString(),Array<NewGoodsVo>::class.java).toList()}\n" +
                                    "-------\n" + "收货时间:${item.order?.arriveTimeDate}\n" + "收货人:${item.order?.recvName}${item.order?.recvPhone}\n" + "收货地址:${item.order?.recvAddr}\n" + "-------\n" + "备注${item.order?.remark}\n"
                        )
//                        ToastUtils.showLong("复制成功")
                        showToast("复制成功")
                    }

                    var ryOrderSendDisDetail = holder.getView<RecyclerView>(R.id.rv_shop)
                    if (ryOrderSendDisDetail.layoutManager == null) {
                        ryOrderSendDisDetail.layoutManager = LinearLayoutManager(
                            context, LinearLayoutManager.VERTICAL, false
                        )
                    }
                    orderGoodsListAdapter = object :
                        BaseQuickAdapter<OrderDto.Content.GoodsVo, BaseViewHolder>(R.layout.item_home_order_shop),
                        LoadMoreModule {
                        override fun convert(
                            holder: BaseViewHolder, item: OrderDto.Content.GoodsVo
                        ) {
                            val view = holder.getView<ImageView>(R.id.img_order_shop_icon)
                            holder.setText(R.id.txt_order_shop_name, item.gname)
                            holder.setText(R.id.txt_order_shop_spec, item.sku)
                            holder.setText(R.id.txt_order_shop_num, "X" + item.number)
                            holder.setText(R.id.txt_order_shop_price, "¥" + item.price)
                            val txtRefund = holder.getView<TextView>(R.id.txt_order_refund)

                            if (item.refundNum == item.number) {
                                txtRefund.visibility = View.VISIBLE
                            } else {
                                txtRefund.visibility = View.GONE
                            }
                            GlideAppUtils.loadUrl(view,item.avater?:"https://static.igoodsale.com/default-goods.png")
//                            Glide.with(context).load(item.avater).into(view)
                        }
                    }
                    ryOrderSendDisDetail!!.adapter = orderGoodsListAdapter
                    if (item.goodsVoList?.isNotEmpty() == true) {
                        orderGoodsListAdapter.setList(item.goodsVoList as MutableList<OrderDto.Content.GoodsVo>)
                        var sum: Double = 0.0
                        var sumNumber: Int = 0
                        for (ne in item.goodsVoList!!) {
                            sum += ne?.totalPrice!!
                            sumNumber += ne?.number!!
                        }
                        holder.setText(
                            R.id.txt_order_shop_msg, "${item.goodsVoList?.size}种商品，共${sumNumber}件"
                        )
                        holder.setText(R.id.txt_total_money, "¥${SaveDecimalUtils.decimalUtils(sum)}")
                    }

                    changeOrder.setSingleClickListener {
                        b = true
                        ARouter.getInstance().build("/app/OrderChangeAddressActivity")
                            .withString("receiveTime", item.order?.arriveTimeDate)
                            .withString("receiveName", item.order?.recvName)
                            .withString("receivePhone", item.order?.recvPhone)
                            .withString("receiveAddress", item.order?.recvAddr)
                            .withString("receiveRemark", item.order?.remark)
                            .withString("lat", item.order?.lat).withString("lon", item.order?.lon)
                            .withString("orderId", item.order?.viewId)
                            .withInt("index", holder.adapterPosition).navigation()
                    }
                    btnCancelDis.setSingleClickListener {
                        b = true
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
                    var orderDisDialog =
                        OrderDistributionDetailDialog().newInstance(false, item.order?.viewId!!)
                    btnSendDis.setSingleClickListener {
                        b = true
                        when (item.order!!.logisticsStatus) {
                            "0" -> {
                                if (item.order!!.deliveryType == "2") {
//                                    val dialog: MineExitDialog =
//                                        MineExitDialog().newInstance("温馨提示", "确定确认出货吗？", "取消", "确认", false)
//                                    dialog.setOkClickLister {
                                        mViewModel.orderFinish(item.order!!.viewId!!)
//                                        dialog.dismiss()
//                                    }
//                                    dialog.show(supportFragmentManager)
                                }else{
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
                    //0.待配送  20.待抢单 30.待取货 50.配送中 70.取消 80.已送达
                    //deliveryType == "1" ,"3" 待配送 2:自提
                    if (item.order!!.deliveryType == "2") {
                        holder.setText(R.id.txt_order_delivery_type, "自提")
                        holder.setText(R.id.txt_order_delivery_1, "自提")
                        holder.setGone(R.id.txt_order_dis, true)
                        changeOrder.visibility = View.INVISIBLE
                        btnSendDis.text = "确认出货"
                        btnCancelDis.visibility = View.GONE
                        btnSendDis.visibility = View.VISIBLE
                        holder.setText(R.id.txt_order_delivery_state, "自提")
                        holder.setGone(R.id.txt_order_delivery_address, true)
                    } else {
                        holder.setText(R.id.txt_order_delivery_type, "配送")
                        holder.setText(R.id.txt_order_delivery_1, "前送达")
                        holder.setGone(R.id.txt_order_dis, false)
                        holder.setText(R.id.txt_order_delivery_state, "待配送")
                        holder.setGone(R.id.txt_order_delivery_address, false)
                        btnCancelDis.visibility = View.GONE
                        changeOrder.visibility = View.VISIBLE
                    }
                    when (item.order!!.logisticsStatus) {
                        "0" -> {
                            //deliveryType == "1" ,"3" 待配送 2:自提
                            if (item.order!!.deliveryType == "2") {
                                holder.setText(R.id.txt_order_delivery_type, "自提")
                                holder.setText(R.id.txt_order_delivery_1, "自提")
                                holder.setGone(R.id.txt_order_dis, true)
                                changeOrder.visibility = View.INVISIBLE
                                btnSendDis.text = "确认出货"
                                btnSendDis.visibility = View.VISIBLE
                                btnCancelDis.visibility = View.GONE
                                holder.setText(R.id.txt_order_delivery_state, "自提")
                                holder.setGone(R.id.txt_order_delivery_address, true)
                            } else {
                                holder.setText(R.id.txt_order_delivery_type, "配送")
                                holder.setText(R.id.txt_order_delivery_1, "前送达")
                                holder.setGone(R.id.txt_order_dis, false)
                                holder.setText(R.id.txt_order_delivery_state, "待配送")
                                holder.setGone(R.id.txt_order_delivery_address, false)
                                btnCancelDis.visibility = View.GONE
                                changeOrder.visibility = View.VISIBLE
                                btnSendDis.text = "发起配送"
                            }
                        }
                        "20" -> {
                            holder.setText(
                                R.id.txt_order_delivery_state, "待抢单"
                            )
                            btnCancelDis.visibility = View.VISIBLE
                            changeOrder.visibility = View.INVISIBLE
                            btnSendDis.text = "加小费"
                        }
                        "30" -> {
                            holder.setText(
                                R.id.txt_order_delivery_state, "待取货"
                            )
                            btnCancelDis.visibility = View.VISIBLE
                            changeOrder.visibility = View.INVISIBLE
                            btnSendDis.text = "配送详情"
                        }
                        "50" -> {
                            holder.setText(
                                R.id.txt_order_delivery_state, "取消"
                            )
                            btnCancelDis.visibility = View.GONE
                            changeOrder.visibility = View.INVISIBLE
                            btnSendDis.text = "配送详情"
                        }
                        "70" -> {
                            holder.setText(
                                R.id.txt_order_delivery_state, "已取消"
                            )
                            btnCancelDis.visibility = View.INVISIBLE
                            changeOrder.visibility = View.VISIBLE
                            btnSendDis.text = "重新配送"
                        }
                        "80" -> {
                            holder.setText(
                                R.id.txt_order_delivery_state, "已送达"
                            )
                            btnCancelDis.visibility = View.GONE
                            changeOrder.visibility = View.INVISIBLE
                            if (item.order!!.deliveryType == "2") {
                                holder.setText(
                                    R.id.txt_order_delivery_state, "已完成"
                                )
                                btnSendDis.visibility = View.GONE
                            } else {
                                holder.setText(
                                    R.id.txt_order_delivery_state, "已送达"
                                )
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