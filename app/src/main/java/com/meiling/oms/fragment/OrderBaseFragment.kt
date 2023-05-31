package com.meiling.oms.fragment

import android.Manifest
import android.content.Intent
import android.content.pm.PackageManager
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatImageView
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hjq.shape.layout.ShapeRelativeLayout
import com.hjq.shape.view.ShapeTextView
import com.meiling.common.fragment.BaseFragment
import com.meiling.common.network.data.CancelOrderSend
import com.meiling.common.network.data.OrderDto
import com.meiling.common.utils.GlideAppUtils
import com.meiling.oms.R
import com.meiling.oms.activity.ChannelActivity
import com.meiling.oms.activity.MainActivity
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


class OrderBaseFragment : BaseFragment<BaseOrderFragmentViewModel, FragmentBaseOrderBinding>() {


    private lateinit var orderDisAdapter: BaseQuickAdapter<OrderDto.Content, BaseViewHolder>
    var pageIndex = 1

    companion object {
        fun newInstance(type: String, isSelect: Boolean): Fragment {
            val orderBaseFragment = OrderBaseFragment()
            val bundle = Bundle()
            bundle.putString("type", type)
            bundle.putBoolean("isSelect", isSelect)
            orderBaseFragment.arguments = bundle
            return orderBaseFragment
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
            object :
                BaseQuickAdapter<OrderDto.Content, BaseViewHolder>(R.layout.item_home_base_order),
                LoadMoreModule {
                override fun convert(holder: BaseViewHolder, item: OrderDto.Content) {
                    val imgPrint = holder.getView<TextView>(R.id.img_order_print)
                    val checkMap = holder.getView<TextView>(R.id.txt_check_map)
                    val orderDelivery = holder.getView<TextView>(R.id.txt_order_delivery_1)
                    val btnSendDis =
                        holder.getView<ShapeTextView>(R.id.txt_base_order_dis)//发起配送或查看配送详情
                    val btnCancelDis =
                        holder.getView<TextView>(R.id.txt_base_order_dis_cancel)//取消配送
                    val btnOrderDisIgnore = holder.getView<TextView>(R.id.txt_order_ignore)//忽略配送
                    val btnShopDetail =
                        holder.getView<ShapeRelativeLayout>(R.id.srl_check_shop)//商品详情
                    val changeOrder =
                        holder.getView<ShapeRelativeLayout>(R.id.srl_check_order_detail)//修改订单
                    val phone = holder.getView<TextView>(R.id.txt_base_order_delivery_phone)
                    val orderAddress =
                        holder.getView<TextView>(R.id.txt_base_order_delivery_address)
                    val callPhone = holder.getView<ImageView>(R.id.iv_call_phone)
                    val channelLogoImg =
                        holder.getView<AppCompatImageView>(R.id.img_order_channel_icon)
                    val imsDeliveryWay = holder.getView<AppCompatImageView>(R.id.ims_delivery_way)
                    holder.setText(R.id.txt_order_delivery_name, item.order?.recvName)
                    phone.text = item.order?.recvPhone
                    checkMap.text = "1.2km"
                    telPhone = item.order?.recvPhone ?: ""
                    orderAddress.text = item.order?.recvAddr!!.replace("@@", "")
                    holder.setText(
                        R.id.txt_base_order_shop_msg, "共${item.goodsVoList?.size}种商品，共100元"
                    )
                    holder.setText(
                        R.id.txt_base_order_shop_name, "商品名称"
                    )
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
//                    if (item.order?.channelCreateTime.isNullOrBlank()) {
//                        holder.setText(
//                            R.id.txt_order_store,
//                            "${transToString(item.order?.createTime!!)}下单  ${item.channelName} ${item.shopName}"
//                        )
//                    } else {
//                        holder.setText(
//                            R.id.txt_order_store,
//                            "${item.order?.channelCreateTime}下单  ${item.channelName}"
//                        )
//                    }
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
                                requireActivity(),
                                arrayOf(Manifest.permission.CALL_PHONE),
                                REQUEST_CALL_PHONE_PERMISSION
                            )
                        }
                    }
                    btnShopDetail.setSingleClickListener {
                        showToast("商品详情")
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
                    changeOrder.setSingleClickListener {

                        if (item.order!!.deliveryType != "2") {
                            ARouter.getInstance().build("/app/OrderChangeAddressActivity")
                                .withString("receiveTime", item.order?.arriveTimeDate)
                                .withString("receiveName", item.order?.recvName)
                                .withString("receivePhone", item.order?.recvPhone)
                                .withString("receiveAddress", item.order?.recvAddr)
                                .withString("receiveRemark", item.order?.remark)
                                .withString("lat", item.order?.lat)
                                .withString("lon", item.order?.lon)
                                .withString("orderId", item.order?.viewId)
                                .withInt("index", holder.adapterPosition).navigation()
                        }

                    }
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
                        dialog.show(childFragmentManager)
                    }
                    btnOrderDisIgnore.setSingleClickListener {
                        val dialog: MineExitDialog =
                            MineExitDialog().newInstance("温馨提示", "确定忽略订单？", "取消", "确认", false)
                        dialog.setOkClickLister {
                            showToast("订单已经忽略")
                            dialog.dismiss()
                        }
                        dialog.show(childFragmentManager)
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
                                orderDisDialog.show(childFragmentManager)
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
                        orderDelivery.text = "送达"
                        imsDeliveryWay.visibility = View.INVISIBLE
                        checkMap.visibility = View.VISIBLE
                        orderAddress.visibility = View.GONE
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
        mDatabind.rvOrderList.adapter = orderDisAdapter
        orderDisAdapter.setOnItemClickListener { adapter, view, position ->
            showToast("订单详情")
        }
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

        vm.getByTenantId.observe(this) {
            if (it.poi == -1 || it.shop == -1) {
                val view =
                    LayoutInflater.from(activity).inflate(R.layout.order_store_empty, null, false)
                view.findViewById<TextView>(R.id.tv_bind).setOnClickListener {
                    startActivity(Intent(requireActivity(), ChannelActivity::class.java))

                }
                orderDisAdapter.setEmptyView(view)
            } else {
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
            if (it.content!!.size < 20) {
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
            showLoading("请求中")
        }
        mViewModel.cancelOrderDto.onSuccess.observe(this) {
            dismissLoading()
            mDatabind.sflLayout.autoRefresh()
            EventBus.getDefault().post(MessageEventUpDataTip())
            showToast("配送已取消")
        }
        mViewModel.cancelOrderDto.onError.observe(this) {
            dismissLoading()
//            mDatabind.sflLayout.autoRefresh()
            showToast(it.msg)
        }
        mViewModel.orderFinish.onStart.observe(this) {
            showLoading("请求中")
        }
        mViewModel.orderFinish.onSuccess.observe(this) {
            dismissLoading()
            mDatabind.sflLayout.autoRefresh()
            EventBus.getDefault().post(MessageEventUpDataTip())
            showToast("出货成功")
        }
        mViewModel.orderFinish.onError.observe(this) {
            dismissLoading()
//            mDatabind.sflLayout.autoRefresh()
            showToast(it.msg)
        }
        mViewModel.printDto.onStart.observe(this) {
        }
        mViewModel.printDto.onSuccess.observe(this) {
            showToast("已发送打印任务")
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