package com.meiling.oms.activity

import android.graphics.drawable.PictureDrawable
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ToastUtils
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.CancelOrderSend
import com.meiling.common.network.data.OrderDto
import com.meiling.common.utils.svg.SvgSoftwareLayerSetter
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivitySearch1Binding
import com.meiling.oms.dialog.OrderDistributionDetailDialog
import com.meiling.oms.viewmodel.BaseOrderFragmentViewModel
import com.meiling.oms.widget.*

@Route(path = "/app/Search1Activity")
class Search1Activity : BaseActivity<BaseOrderFragmentViewModel, ActivitySearch1Binding>() {

    lateinit var orderDisAdapter: BaseQuickAdapter<OrderDto.Content, BaseViewHolder>
    lateinit var orderGoodsListAdapter: BaseQuickAdapter<OrderDto.Content.GoodsVo, BaseViewHolder>

    override fun initView(savedInstanceState: Bundle?) {
        setBar(this, mDatabind.cosTitle)
        orderDisAdapter =
            object : BaseQuickAdapter<OrderDto.Content, BaseViewHolder>(R.layout.item_home_order),
                LoadMoreModule {
                override fun convert(holder: BaseViewHolder, item: OrderDto.Content) {
                    holder.setText(R.id.txt_order_delivery_type, item.orderName)
                    val imgShopCopy = holder.getView<ImageView>(R.id.img_shop_copy)
                    val changeOrder = holder.getView<TextView>(R.id.txt_change_order)
                    val btnSendDis = holder.getView<TextView>(R.id.txt_order_dis)//发起配送或查看配送详情
                    val btnCancelDis = holder.getView<TextView>(R.id.txt_order_dis_cancel)//取消配送
                    val showMsg = holder.getView<TextView>(R.id.txt_shop_show_all)
                    val hideMsg = holder.getView<TextView>(R.id.txt_show_hide)
                    val copyOrderId = holder.getView<TextView>(R.id.txt_copy_order)
                    val orderId = holder.getView<TextView>(R.id.txt_order_id)
                    val channelLogoImg = holder.getView<ImageView>(R.id.img_order_icon)
                    holder.setText(R.id.txt_order_delivery_name, item.order?.recvName)
                    holder.setText(R.id.txt_order_delivery_phone, item.order?.recvPhone)
                    holder.setText(R.id.txt_order_delivery_address, item.order?.recvAddr)
                    holder.setText(R.id.txt_order_num, "#${item.order?.channelDaySn}")
                    holder.setText(R.id.txt_shop_actual_money, "${item.order?.actualIncome}")
                    holder.setText(R.id.txt_order_delivery_time, "${item.order?.arriveTimeDate}")
                    holder.setText(R.id.txt_pay_money, "¥${item.order?.payPrice}")
                    holder.setText(R.id.txt_pay_fee, "¥${item.order?.platformServiceFee}")
                    holder.setText(R.id.txt_order_total_money, "¥${item.order?.actualIncome}")
                    val options = RequestOptions().format(DecodeFormat.PREFER_ARGB_8888)
                    //加载svg图片
                    Glide.with(context).`as`(PictureDrawable::class.java).load(item.channelLogo)
                        .apply(options).listener(SvgSoftwareLayerSetter()).into(channelLogoImg)

                    holder.setText(
                        R.id.txt_order_delivery_state, "${item.order?.deliveryStatusName}"
                    )
                    orderId.text = "${item.order?.viewId}"
                    holder.setText(R.id.txt_order_remark, "${item.order?.remark}")
                    holder.setText(
                        R.id.txt_time_shop,
                        "${transToString(item.order?.createTime!!)}下单${item.channelName}店铺"
                    )
                    holder.setText(R.id.txt_shop_name, "${item.shopName}")
                    if (item.order?.deliveryType == "1" || item.order?.deliveryType == "3") {
                        changeOrder.visibility = View.VISIBLE
                        holder.setText(R.id.txt_order_delivery_type, "配送")
                        holder.setGone(R.id.txt_order_dis, false)
                    } else {
                        holder.setText(R.id.txt_order_delivery_type, "自提")
                        holder.setGone(R.id.txt_order_dis, true)
                        changeOrder.visibility = View.GONE
                    }
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

                    var listGoods = item.goodsVoList
                    var x = ""
                    for (goods in listGoods!!) {
                        x += "名称" + goods?.gname + "\n数量" + goods?.number + "\n价格" + goods?.price
                    }
                    imgShopCopy.setSingleClickListener {
                        copyText(
                            context,
                            "订单来源：" + "${item.channelName} \n" + "门店名称${item.shopName}\n" + "订单编号${item.order?.viewId}\n" + "-------\n" + "商品信息${x}\n" +
//                                    "商品信息${Gson().fromJson(filteredData.toString(),Array<NewGoodsVo>::class.java).toList()}\n" +
                                    "-------\n" + "收货时间${item.order?.arriveTimeDate}\n" + "收货人${item.order?.recvName}${item.order?.recvPhone}\n" + "收货地址${item.order?.recvAddr}\n" + "-------\n" + "备注${item.order?.remark}\n"
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
                            holder.setText(R.id.txt_order_shop_spec, item.specs)
                            holder.setText(R.id.txt_order_shop_num, "X" + item.number)
                            holder.setText(R.id.txt_order_shop_price, "¥" + item.price)
                            Glide.with(context).load(item.avater).into(view)
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
                        holder.setText(R.id.txt_total_money, "¥${sum}")
                    }

                    changeOrder.setSingleClickListener {
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
                        mViewModel.cancelOrder(
                            CancelOrderSend(
                                deliveryConsumerId = item.deliveryConsume!!.id ?: "0",
                                poiId = item.order!!.poiId ?: "0",
                                stationChannelId = item.deliveryConsume!!.stationChannelId ?: "0"
                            )
                        )
                    }
                    var orderDisDialog =
                        OrderDistributionDetailDialog().newInstance(false, item.order?.viewId!!)
                    btnSendDis.setSingleClickListener {
                        when (item.order!!.logisticsStatus) {
                            "0" -> {
                                ARouter.getInstance().build("/app/OrderDisActivity")
                                    .withSerializable("kk", item).navigation()
                            }
                            "20" -> {
                                ARouter.getInstance().build("/app/OrderDisAddTipActivity")
                                    .withSerializable("kk", item).navigation()
                            }

                            "70" -> {
                                ARouter.getInstance().build("/app/OrderDisActivity")
                                    .withSerializable("kk", item).navigation()
                            }
                            "30", "50", "80" -> {
                                orderDisDialog.show(supportFragmentManager)
                            }

                        }
                    }
                    //0.待配送  20.待抢单 30.待取货 50.配送中 70.取消 80.已送达
                    when (item.order!!.logisticsStatus) {
                        "0" -> {
                            btnCancelDis.visibility = View.GONE
                            changeOrder.visibility = View.VISIBLE
                            btnSendDis.text = "发起配送"
                        }
                        "20" -> {
                            btnCancelDis.visibility = View.VISIBLE
                            changeOrder.visibility = View.GONE
                            btnSendDis.text = "加小费"
                        }
                        "30" -> {
                            btnCancelDis.visibility = View.VISIBLE
                            changeOrder.visibility = View.GONE
                            btnSendDis.text = "配送详情"
                        }
                        "50" -> {
                            btnCancelDis.visibility = View.GONE
                            changeOrder.visibility = View.GONE
                            btnSendDis.text = "配送详情"
                        }
                        "70" -> {
                            btnCancelDis.visibility = View.GONE
                            changeOrder.visibility = View.GONE
                            btnSendDis.text = "重新配送"
                        }
                        "80" -> {
                            btnCancelDis.visibility = View.GONE
                            changeOrder.visibility = View.GONE
                            btnSendDis.text = "配送详情"
                        }
                    }

                }


            }
        mDatabind.rvHistoryOrderList.adapter = orderDisAdapter
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivitySearch1Binding {
        return ActivitySearch1Binding.inflate(layoutInflater)
    }

    private var b = true

    override fun initListener() {
        mDatabind.imgSearchBack.setOnClickListener {
            finish()
        }

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

    override fun createObserver() {
        mViewModel.orderList.onStart.observe(this) {
            showLoading("请求中。。。")
        }
        mViewModel.orderList.onSuccess.observe(this) {
            disLoading()

            if (it.content.isNullOrEmpty()) {
                mDatabind.edtSearch.setText("")
                orderDisAdapter.setList(null)
                mDatabind.rlOrderEmpty.visibility = View.VISIBLE
                mDatabind.txtErrorMsg.text = "未查询到订单"
            } else {
                mDatabind.rlOrderEmpty.visibility = View.GONE
                orderDisAdapter.setList(it.content as MutableList<OrderDto.Content>)
            }
            Log.e("order", "createObserver: " + it)
        }
        mViewModel.orderList.onError.observe(this) {
            disLoading()
            mDatabind.edtSearch.setText("")
            orderDisAdapter.setList(null)
            mDatabind.rlOrderEmpty.visibility = View.VISIBLE
            mDatabind.txtErrorMsg.text = "支持通过订单编号、收货人姓名、手机号进行搜索"
            showToast("${it.message}")
        }

    }

}