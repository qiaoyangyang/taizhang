package com.meiling.oms.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.graphics.drawable.PictureDrawable
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.bumptech.glide.load.DecodeFormat
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.fragment.BaseFragment
import com.meiling.common.network.data.CancelOrderSend
import com.meiling.common.network.data.OrderDto
import com.meiling.common.utils.svg.SvgSoftwareLayerSetter
import com.meiling.oms.R
import com.meiling.oms.activity.MainActivity
import com.meiling.oms.activity.NewlyBuiltStoreActivity
import com.meiling.oms.activity.NoStoreActivity
import com.meiling.oms.databinding.FragmentBaseOrderBinding
import com.meiling.oms.dialog.MineExitDialog
import com.meiling.oms.dialog.OrderDistributionDetailDialog
import com.meiling.oms.eventBusData.MessageEvent
import com.meiling.oms.eventBusData.MessageEventUpDataTip
import com.meiling.oms.viewmodel.BaseOrderFragmentViewModel
import com.meiling.oms.viewmodel.MainViewModel2
import com.meiling.oms.widget.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class BaseOrderFragment : BaseFragment<BaseOrderFragmentViewModel, FragmentBaseOrderBinding>() {


    private lateinit var orderDisAdapter: BaseQuickAdapter<OrderDto.Content, BaseViewHolder>
    lateinit var orderGoodsListAdapter: BaseQuickAdapter<OrderDto.Content.GoodsVo, BaseViewHolder>

    var pageIndex = 1;

    companion object {
        fun newInstance(type: String, isSelect: Boolean): Fragment {
            val baseOrderFragment = BaseOrderFragment()
            val bundle = Bundle()
            bundle.putString("type", type)
            bundle.putBoolean("isSelect", isSelect)
            baseOrderFragment.arguments = bundle
            return baseOrderFragment
        }
    }

    override fun onResume() {
        super.onResume()
        initViewData()
        EventBus.getDefault().post(MessageEventUpDataTip())
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    var telPhone = ""
    lateinit var vm: MainViewModel2
    override fun initView(savedInstanceState: Bundle?) {
        vm = ViewModelProvider(
            MainActivity.mainActivity!!,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel2::class.java)
        requireArguments().getString("type").toString()
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
                    val phone = holder.getView<TextView>(R.id.txt_order_delivery_phone)
                    val channelLogoImg = holder.getView<ImageView>(R.id.img_order_icon)

                    holder.setText(R.id.txt_order_delivery_name, item.order?.recvName)
                    phone.text = item.order?.recvPhone
                    telPhone = item.order?.recvPhone ?: ""
                    holder.setText(
                        R.id.txt_order_delivery_address,
                        item.order?.recvAddr!!.replace("@@", "")
                    )
                    holder.setText(R.id.txt_order_num, "#${item.order?.channelDaySn}")
                    holder.setText(R.id.txt_shop_actual_money, "¥${item.order?.actualIncome}")
                    holder.setText(R.id.txt_order_delivery_time, "${item.order?.arriveTimeDate}")
                    holder.setText(R.id.txt_pay_money, "¥${item.order?.payPrice}")
                    holder.setText(R.id.txt_pay_fee, "¥${item.order?.platformServiceFee}")
                    holder.setText(R.id.txt_order_total_money, "¥${item.order?.actualIncome}")
                    val options = RequestOptions().format(DecodeFormat.PREFER_ARGB_8888)
                    //加载svg图片
                    Glide.with(context).`as`(PictureDrawable::class.java).load(item.channelLogo)
                        .apply(options).listener(SvgSoftwareLayerSetter()).into(channelLogoImg)


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
                        changeOrder.visibility = View.INVISIBLE
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
                                requireActivity(),
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
                            requireContext(), LinearLayoutManager.VERTICAL, false
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
                            val txtRefund = holder.getView<TextView>(R.id.txt_order_refund)

                            if (item.refundNum == item.number) {
                                txtRefund.visibility = View.VISIBLE
                            } else {
                                txtRefund.visibility = View.GONE
                            }

//                            Glide.with(context).load(item.avater).into(view)
                            Glide.with(context).load(item.avater)
                                .apply(options).into(view)

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
//                            if (ne.refundNum == ne.number){
//                                ne.isRefund = true
//                            }else{
//                                ne.isRefund = false
//                            }
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
                        dialog.show(childFragmentManager)

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
                                orderDisDialog.show(childFragmentManager)
                            }

                        }
                    }
                    //0.待配送  20.待抢单 30.待取货 50.配送中 70.取消 80.已送达
                    when (item.order!!.logisticsStatus) {
                        "0" -> {
                            holder.setText(
                                R.id.txt_order_delivery_state, "待配送"
                            )
                            btnCancelDis.visibility = View.GONE
                            changeOrder.visibility = View.VISIBLE
                            btnSendDis.text = "发起配送"
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
                                R.id.txt_order_delivery_state, "配送中"
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
                            btnSendDis.text = "配送详情"
                        }
                    }

                }


            }
        mDatabind.rvOrderList.adapter = orderDisAdapter
        mDatabind.sflLayout.setOnRefreshListener {
            pageIndex = 1
            initViewData()
            EventBus.getDefault().post(MessageEventUpDataTip())
        }
    }

    var list = ArrayList<String>()

    private fun initViewData() {
        mViewModel.orderList(
            logisticsStatus = requireArguments().getString("type").toString(),
            startTime = formatCurrentDateBeforeWeek(),
            endTime = formatCurrentDate(),
            businessNumberType = "1",
            pageIndex = pageIndex,
            pageSize = "20",
            orderTime = "1",
            deliverySelect = "0",
            isValid = "",
            businessNumber = "",
        )
        orderDisAdapter.loadMoreModule.loadMoreView = SS()
        orderDisAdapter.loadMoreModule.setOnLoadMoreListener {
            pageIndex++
            mViewModel.orderList(
                logisticsStatus = requireArguments().getString("type").toString(),
                startTime = formatCurrentDateBeforeWeek(),
                endTime = formatCurrentDate(),
                businessNumberType = "1",
                pageIndex = pageIndex,
                pageSize = "20",
                orderTime = "1",
                deliverySelect = "0",
                isValid = "",
                businessNumber = "",
                selectText = ""
            )
        }
    }

    override fun createObserver() {

        vm.getByTenantId.observe(this){
            if(it.poi==-1){
                val view = LayoutInflater.from(activity).inflate(R.layout.order_store_empty, null, false)
                view.findViewById<TextView>(R.id.tv_bind).setOnClickListener {
                    startActivity(Intent(requireActivity(),NewlyBuiltStoreActivity::class.java))

                }
                orderDisAdapter.setEmptyView(view)
            }else{
                orderDisAdapter.setEmptyView(R.layout.order_search_empty)
            }
        }

        mViewModel.orderList.onStart.observe(this) {
            showLoading("加载中")
        }
        mViewModel.orderList.onSuccess.observe(this) {
            dismissLoading()
            EventBus.getDefault().post(MessageEventUpDataTip())
            mDatabind.sflLayout.finishRefresh()
            if (it.pageIndex == 1) {
                if (it.content.isNullOrEmpty()) {
                    orderDisAdapter.setList(null)
                } else {
                    orderDisAdapter.setList(it.content as MutableList<OrderDto.Content>)
                }
            } else {
                orderDisAdapter.addData(it.content as MutableList<OrderDto.Content>)
            }
            if (it.content.size < 20) {
                dismissLoading()
                orderDisAdapter.footerWithEmptyEnable = false
                orderDisAdapter.footerLayout?.visibility = View.GONE
                orderDisAdapter.loadMoreModule.loadMoreEnd()
            } else {
                orderDisAdapter.loadMoreModule.loadMoreComplete()
            }
        }
        mViewModel.orderList.onError.observe(this) {
            dismissLoading()
            mDatabind.sflLayout.finishRefresh()
            showToast("${it.msg}")
        }

        mViewModel.cancelOrderDto.onStart.observe(this) {
            showLoading("正在请求")
        }
        mViewModel.cancelOrderDto.onSuccess.observe(this) {
            dismissLoading()
            mDatabind.sflLayout.autoRefresh()
            EventBus.getDefault().post(MessageEventUpDataTip())
            showToast("订单已取消")
        }
        mViewModel.cancelOrderDto.onError.observe(this) {
            dismissLoading()
//            mDatabind.sflLayout.autoRefresh()
            showToast(it.msg)
        }
        mViewModel.printDto.onStart.observe(this) {
        }
        mViewModel.printDto.onSuccess.observe(this) {
        }
        mViewModel.printDto.onError.observe(this) {
            dismissLoading()
            showToast(it.msg)
        }

    }


    override fun getBind(inflater: LayoutInflater): FragmentBaseOrderBinding {
        return FragmentBaseOrderBinding.inflate(inflater)
    }


    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        // 在这里处理事件
        val message: Int = event.message
        orderDisAdapter.notifyItemChanged(message)
    }

    var REQUEST_CALL_PHONE_PERMISSION = 1
    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
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