package com.meiling.oms.activity

import android.content.Intent
import android.content.res.Resources
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.alibaba.android.arouter.launcher.ARouter
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.UiSettings
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.Marker
import com.amap.api.maps.model.MarkerOptions
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.CancelOrderSend
import com.meiling.common.network.data.OrderDetailDto
import com.meiling.common.network.data.OrderGoodsVo
import com.meiling.common.utils.DoubleClickHelper
import com.meiling.common.utils.GlideAppUtils
import com.meiling.common.utils.PermissionUtilis
import com.meiling.common.utils.TextDrawableUtils
import com.meiling.oms.R
import com.meiling.oms.adapter.OrderBaseShopListAdapter
import com.meiling.oms.databinding.ActivityOrderDetailBinding
import com.meiling.oms.dialog.DataTipDialog
import com.meiling.oms.dialog.MineExitDialog
import com.meiling.oms.eventBusData.MessageEvent
import com.meiling.oms.viewmodel.BaseOrderFragmentViewModel
import com.meiling.oms.widget.copyText
import com.meiling.oms.widget.showToast
import io.reactivex.annotations.NonNull
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


//配送订单详情
class OrderDetailActivity : BaseActivity<BaseOrderFragmentViewModel, ActivityOrderDetailBinding>(),
    View.OnClickListener {
    //初始化地图控制器对象
    var aMap: AMap? = null
    var uiSettings: UiSettings? = null
    private var behavior: BottomSheetBehavior<View>? = null

    //商品
    private lateinit var orderDisAdapter: OrderBaseShopListAdapter
    override fun initView(savedInstanceState: Bundle?) {

        mDatabind.map?.onCreate(savedInstanceState)
        if (aMap == null) {

            aMap = mDatabind.map.map

            uiSettings = aMap?.uiSettings;//实例化UiSettings类对象
            uiSettings?.isZoomControlsEnabled = false

            //aMap?.moveCamera(CameraUpdateFactory.zoomTo(12f))

        }
        EventBus.getDefault().register(this)


        behavior = BottomSheetBehavior.from(mDatabind.bottomSheet)


        orderDisAdapter = OrderBaseShopListAdapter()
        mDatabind.included.recyShopList.adapter = orderDisAdapter
        mDatabind.included.tvRevocation.setOnClickListener(this)
        mDatabind.included.tvGoOn.setOnClickListener(this)
        mDatabind.included.btnPrintReceipt.setOnClickListener(this)
        mDatabind.included.btnChangeAddress.setOnClickListener(this)
        mDatabind.included.ivCallPhone.setOnClickListener(this)
        mDatabind.included.txtOrderPlatformAccount.setOnClickListener(this)
        mDatabind.included.txtOrderAccount.setOnClickListener(this)
        mDatabind.included.txtOrderID.setOnClickListener(this)
        mDatabind.included.txtOrderId1.setOnClickListener(this)
        mDatabind.included.ivPlayBack.visibility = View.GONE


    }

    override fun initData() {
        super.initData()
        var orderid = intent.getStringExtra("orderViewId").toString()
        mViewModel.getOrderDetail(orderid)


    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityOrderDetailBinding {
        return ActivityOrderDetailBinding.inflate(layoutInflater)
    }

    override fun onDestroy() {
        super.onDestroy()
        mDatabind.map.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        mDatabind.map.onResume()
    }

    override fun onPause() {
        super.onPause()
        mDatabind.map.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mDatabind.map.onSaveInstanceState(outState)
    }

    private val ZOOM = 10f

    /**
     * 添加带生长效果marker
     */
    private fun addGrowMarker(latLng: LatLng, int: Int, type: Int) {
        val options = MarkerOptions()
        options.position(latLng)
        val view = LayoutInflater.from(this).inflate(R.layout.addimg, null, false)
        var iv_icon = view.findViewById<ImageView>(R.id.iv_icon)
        var tv_distance = view.findViewById<TextView>(R.id.tv_distance)
        if (type == 0) {
            if (int == 1) {
                iv_icon.setBackgroundResource(R.drawable.add_1)
                tv_distance.visibility = View.VISIBLE
                tv_distance.text = "顾客距离门店${orderDetailDto?.distance}km"
            } else {
                iv_icon.setBackgroundResource(R.drawable.add_shop_02)
                tv_distance.visibility = View.GONE
            }
        } else if (type == 1) {
            if (int == 1) {
                iv_icon.setBackgroundResource(R.drawable.add_shop_02)
                tv_distance.visibility = View.GONE
            } else {
                iv_icon.setBackgroundResource(R.drawable.collected)
                tv_distance.visibility = View.GONE
            }
        } else if (type == 2) {
            if (int == 1) {
                iv_icon.setBackgroundResource(R.drawable.add_1)
                tv_distance.visibility = View.GONE
            } else {
                iv_icon.setBackgroundResource(R.drawable.add_shop_02)
                tv_distance.visibility = View.GONE
            }
        }

        options.icon(
            BitmapDescriptorFactory.fromView(view)
        )
        val marker: Marker = aMap!!.addMarker(options)
        marker.startAnimation()
        aMap!!.moveCamera(CameraUpdateFactory.zoomTo(ZOOM))
        aMap!!.moveCamera(CameraUpdateFactory.changeLatLng(latLng))
    }

    private fun bottomSheetCallback(): BottomSheetCallback {
        return object : BottomSheetCallback() {
            override fun onStateChanged(@NonNull bottomSheet: View, newState: Int) {
                Log.d("yjk", "bottomSheet---$bottomSheet")
                Log.d("yjk", "newState---$newState")
                when (newState) {
                    BottomSheetBehavior.STATE_COLLAPSED -> {
                        if (mDatabind.bottomSheet.scrollY !== 0) {
                            mDatabind.bottomSheet.scrollY = 0 //列表滑动到顶端
                        }
                    }
                    BottomSheetBehavior.STATE_HALF_EXPANDED -> //列表滑动到顶端
                    {
                        if (
                            mDatabind.TitleBar?.translationY !== 0f) {
                            mDatabind.TitleBar?.translationY = 0f
                        }
                    }
                }

            }

            override fun onSlide(@NonNull bottomSheet: View, slideOffset: Float) {
                val distance: Float
                if (slideOffset > 0) { //在peekHeight位置以上 滑动(向上、向下) slideOffset bottomSheet.getHeight() 是展开后的高度的比例
                    val height = bottomSheet.height
                    distance = height * slideOffset
                    //地图跟随滑动，将我的位置移动到中心
                    aMap!!.moveCamera(CameraUpdateFactory.zoomTo(ZOOM))
                    var latLng = LatLng(39.906901, 116.397972)
                    if (orderDetailDto != null) {
                        Log.d("yjk", "onSlide: 99999")
                        latLng = LatLng(
                            orderDetailDto?.order?.lat?.toDouble()!!,
                            orderDetailDto?.order?.lon?.toDouble()!!
                        )

                    }
                    Log.d("yjk", "onSlide: ")
                    aMap!!.moveCamera(CameraUpdateFactory.changeLatLng(latLng))
                    mDatabind.map.scrollTo(0, -(distance / 2f).toInt())
                    mDatabind.map.translationY = -distance

                } else { //在peekHeight位置以下 滑动(向上、向下)  slideOffset 是PeekHeight的高度的比例
                    // distance = behavior?.peekHeight * slideOffset
                }

            }
        }
    }

    private fun dp2px(dp: Int): Int {
        return (dp * Resources.getSystem().displayMetrics.density).toInt()
    }

    override fun onClick(v: View?) {
        if (!DoubleClickHelper.isOnDoubleClick()) {
            when (v?.id) {

                //忽略订单
                R.id.tv_revocation -> {
                    if (orderDetailDto != null) {
                        when (orderDetailDto?.order?.logisticsStatus?.toInt()) {
                            0 -> {
                                if (orderDetailDto?.order?.isValid == 1) {
                                    setinvalid()
                                }
                            }
                            20 -> {
                                setcancelOrder()
                            }
                            50 -> {
                                setgetPrint()
                            }
                        }

                    }
                }
                //自提完成
                R.id.tv_go_on -> {
                    when (orderDetailDto?.order?.logisticsStatus?.toInt()) {

                        0 -> {
                            // setgetPrint()
                            showToast("发起配送")
                        }
                        20 -> {
                            showToast("加小费")
                        }
                        30, 50, 80 -> {
                            showToast("配送详情")
                        }
                    }
                }
                //打印小票
                R.id.btn_Print_receipt -> {

                    when (orderDetailDto?.order?.logisticsStatus?.toInt()) {

                        0, 30, 50, 70, 80 -> {
                            setgetPrint()
                        }
                    }

                }
                //修改订单
                R.id.btn_change_address -> {
                    when (orderDetailDto?.order?.logisticsStatus?.toInt()) {
                        0 -> {
                            if (orderDetailDto?.order?.isValid == 0) {
                                mViewModel.invalid(
                                    orderDetailDto?.order!!.viewId.toString(),
                                    "0"
                                )
                            } else {
                                //startActivity(Intent(this, OrderChangeAddressActivity::class.java))
                                ARouter.getInstance().build("/app/OrderChangeAddressActivity")
                                    .withString(
                                        "receiveTime",
                                        orderDetailDto?.order?.arriveTimeDate
                                    )
                                    .withString("receiveName", orderDetailDto?.order?.recvName)
                                    .withString("receivePhone", orderDetailDto?.order?.recvPhone)
                                    .withString("receiveAddress", orderDetailDto?.order?.recvAddr)
                                    .withString("receiveRemark", orderDetailDto?.order?.remark)
                                    .withString("lat", orderDetailDto?.order?.lat)
                                    .withString("lon", orderDetailDto?.order?.lon)
                                    .withString("orderId", orderDetailDto?.order?.viewId)
                                    .withInt("index", 0).navigation()
                            }
                        }

                    }


                }
                //打电话
                R.id.iv_call_phone -> {
                    XXPermissions.with(this).permission(PermissionUtilis.Group.PHONE_CALL)
                        .request(object : OnPermissionCallback {
                            override fun onGranted(
                                permissions: MutableList<String>,
                                allGranted: Boolean
                            ) {
                                if (!allGranted) {
                                    showToast("获取部分权限成功，但部分权限未正常授予")
                                    return
                                }
                                dialPhoneNumber(orderDetailDto?.order?.recvPhone.toString())
                            }

                            override fun onDenied(
                                permissions: MutableList<String>,
                                doNotAskAgain: Boolean
                            ) {
                                if (doNotAskAgain) {
                                    // 如果是被永久拒绝就跳转到应用权限系统设置页面
                                    XXPermissions.startPermissionActivity(
                                        this@OrderDetailActivity,
                                        permissions
                                    )
                                } else {
                                    showToast("授权失败，请检查权限")
                                }

                            }

                        })

                }
                //平台服务费
                R.id.txt_order_platform_account -> {
                    DataTipDialog().newInstance("部分外卖平台存在服务费，仅记录展示，非小喵来客收取", "平台服务费")
                        .show(supportFragmentManager)
                }
                //本单支付入账
                R.id.txt_order_account -> {
                    DataTipDialog().newInstance("本单支付入账=顾客实际支付-平台服务费", "本单支付入账")
                        .show(supportFragmentManager)

                }
                //订单编号
                R.id.txt_order_ID -> {
                    copyText(this, orderDetailDto?.order?.viewId.toString())
                    showToast("复制成功")

                }
                //三方编号
                R.id.txt_order_id_1 -> {
                    copyText(this, orderDetailDto?.order?.channelOrderNum.toString())
                    showToast("复制成功")

                }
            }
        } else {
            Log.d("yjk", "请勿重复点击")
        }
    }

    var orderDetailDto: OrderDetailDto? = null

    override fun createObserver() {

        super.createObserver()
        mViewModel.invalidDto.onStart.observe(this) {
            showLoading("")
        }
        mViewModel.invalidDto.onSuccess.observe(this) {
            disLoading()
            finish()
        }
        mViewModel.invalidDto.onError.observe(this) {
            disLoading()
            showToast(it.msg)
        }
        mViewModel.printDto.onStart.observe(this) {
        }
        mViewModel.printDto.onSuccess.observe(this) {
            disLoading()
            showToast("已发送打印任务")
        }
        mViewModel.printDto.onError.observe(this) {
            disLoading()
            showToast(it.msg)
        }

        mViewModel.orderDetailDto.onStart.observe(this) {}
        mViewModel.orderDetailDto.onSuccess.observe(this) {
            Log.d("TAG", "createObserver:${it.goodsVoList.toString()} ")
            orderDisAdapter.setList(it.goodsVoList as MutableList<OrderGoodsVo>)
            orderDisAdapter.notifyDataSetChanged()
            orderDetailDto = it
            // logisticsStatus 待配送0 待抢单20 待取货30 配送中50 已取消70 已送达80
            var deliveryStatusName = ""
            if (it.order?.logisticsStatus?.toInt() == 0) {
                TextDrawableUtils.setLeftDrawable(
                    mDatabind.included.tvStatusTitle,
                    R.drawable.to_be_delivered,

                    )
                deliveryStatusName = "待配送"

                behavior?.peekHeight = dp2px(160)

                if (it.order?.isValid == 1) {
                    mDatabind.included.tvGoOn.text = "发起配送"
                    mDatabind.included.tvRevocation.text = "忽略订单"
                    mDatabind.included.btnPrintReceipt.text = "打印小票"
                    mDatabind.included.btnChangeAddress.text = "修改订单"
                } else if (it.order?.isValid == 0) {
                    mDatabind.included.tvGoOn.visibility = View.GONE
                    mDatabind.included.tvRevocation.visibility = View.GONE

                    mDatabind.included.btnPrintReceipt.text = "打印小票"
                    mDatabind.included.btnChangeAddress.text = "取消忽略"
                }

                val latLng = LatLng(it.order?.lat?.toDouble()!!, it?.order?.lon?.toDouble()!!)//客户
                addGrowMarker(latLng, 1, 0)
                val latLng1 = LatLng(it.poi?.lat?.toDouble()!!, it?.poi?.lon?.toDouble()!!)
                addGrowMarker(latLng1, 2, 0)

            } else if (it.order?.logisticsStatus?.toInt() == 20) {
                TextDrawableUtils.setLeftDrawable(
                    mDatabind.included.tvStatusTitle,
                    R.drawable.daito_be_delivered_20
                )
                deliveryStatusName = "待抢单"
                behavior?.peekHeight = dp2px(160)

                mDatabind.included.tvGoOn.text = "加小费"
                mDatabind.included.tvRevocation.text = "取消配送"
                mDatabind.included.btnPrintReceipt.text = "打印小票"
                mDatabind.included.btnChangeAddress.visibility = View.GONE
                val latLng = LatLng(it.order?.lat?.toDouble()!!, it?.order?.lon?.toDouble()!!)//客户
                addGrowMarker(latLng, 1, 0)
                val latLng1 = LatLng(it.poi?.lat?.toDouble()!!, it?.poi?.lon?.toDouble()!!)
                addGrowMarker(latLng1, 2, 0)


            } else if (it.order?.logisticsStatus?.toInt() == 30) {
                TextDrawableUtils.setLeftDrawable(
                    mDatabind.included.tvStatusTitle,
                    R.drawable.daito_be_delivered_30
                )
                behavior?.peekHeight = dp2px(160)
                deliveryStatusName = "待取货"

                mDatabind.included.tvGoOn.text = "配送详情"
                mDatabind.included.tvRevocation.text = "取消配送"
                mDatabind.included.btnPrintReceipt.text = "打印小票"
                mDatabind.included.btnChangeAddress.visibility = View.GONE

                val latLng = LatLng(it.order?.lat?.toDouble()!!, it?.order?.lon?.toDouble()!!)//客户
                addGrowMarker(latLng, 1, 1)
                val latLng1 = LatLng(it.poi?.lat?.toDouble()!!, it?.poi?.lon?.toDouble()!!)
                addGrowMarker(latLng1, 2, 1)


            } else if (it.order?.logisticsStatus?.toInt() == 50) {
                TextDrawableUtils.setLeftDrawable(
                    mDatabind.included.tvStatusTitle,
                    R.drawable.daito_be_delivered_50
                )
                deliveryStatusName = "配送中"

                mDatabind.included.tvGoOn.text = "配送详情"
                mDatabind.included.tvRevocation.text = "打印小票"
                mDatabind.included.btnPrintReceipt.visibility = View.GONE
                mDatabind.included.btnChangeAddress.visibility = View.GONE

                behavior?.peekHeight = dp2px(120)
            } else if (it.order?.logisticsStatus?.toInt() == 70) {
                TextDrawableUtils.setLeftDrawable(
                    mDatabind.included.tvStatusTitle,
                    R.drawable.daito_be_delivered_70
                )
                deliveryStatusName = "已取消"
                mDatabind.included.tvGoOn.visibility = View.GONE
                mDatabind.included.tvRevocation.text = "打印小票"
                mDatabind.included.btnPrintReceipt.visibility = View.GONE
                mDatabind.included.btnChangeAddress.visibility = View.GONE
                behavior?.peekHeight = dp2px(120)

                val latLng = LatLng(it.order?.lat?.toDouble()!!, it?.order?.lon?.toDouble()!!)//客户
                addGrowMarker(latLng, 1, 1)
                val latLng1 = LatLng(it.poi?.lat?.toDouble()!!, it?.poi?.lon?.toDouble()!!)
                addGrowMarker(latLng1, 2, 1)

            } else if (it.order?.logisticsStatus?.toInt() == 80) {
                TextDrawableUtils.setLeftDrawable(
                    mDatabind.included.tvStatusTitle,
                    R.drawable.daito_be_delivered_80
                )
                val latLng = LatLng(it.order?.lat?.toDouble()!!, it?.order?.lon?.toDouble()!!)//客户
                addGrowMarker(latLng, 1, 2)
                val latLng1 = LatLng(it.poi?.lat?.toDouble()!!, it?.poi?.lon?.toDouble()!!)
                addGrowMarker(latLng1, 2, 2)

                deliveryStatusName = "已送达"
                mDatabind.included.tvRevocation.text = "打印小票"
                mDatabind.included.tvGoOn.text = "配送详情"
                mDatabind.included.btnPrintReceipt.visibility = View.GONE
                mDatabind.included.btnChangeAddress.visibility = View.GONE
                behavior?.peekHeight = dp2px(120)
            }
            mDatabind.included.tvStatusTitle.text = deliveryStatusName
            mDatabind.included.txtActualMoney.text = "¥" + it?.order?.actualPayPrice//顾客实际支付
            mDatabind.included.txtPlatformMoney.text = "¥" + it?.order?.platformServiceFee//平台服务费
            mDatabind.included.txtOrderAccountMoney.text = "¥" + it?.order?.actualIncome//本单支付入账
            mDatabind.included.txtArriveTime.text = it?.order?.arriveTimeDate//送达时间
            mDatabind.included.txtInsertOrderTime.text = it?.order?.channelCreateTime//下单时间
            mDatabind.included.txtOrderDeliverStore.text = it?.poi?.name//发货门店
            mDatabind.included.txtOrderChannelStore.text = it?.shop?.name//渠道店铺
            mDatabind.included.txtOrderID.text = it?.order?.viewId//订单编号
            mDatabind.included.txtOrderId1.text = it?.order?.channelOrderNum//三方编号
            mDatabind.included.txtBaseOrderRemark.text = it?.order?.remark//备注
            mDatabind.included.txtBaseOrderDeliveryPhone.text = it.order?.recvPhone//电话
            mDatabind.included.txtOrderDeliveryName.text = it.order?.recvName//电话
            mDatabind.included.txtCheckMap.text = "${it.distance}km"//电话
            mDatabind.included.txtBaseOrderDeliveryAddress.text =
                it.order?.recvAddr?.replace("@@", "")//地址
            mDatabind.included.txtOrderStore.text = "${it.channelName} "//渠道
            mDatabind.included.txtBaseOrderNo.text = "${it.order?.channelDaySn} "//单号
            mDatabind.included.txtBaseOrderDeliveryTime.text = "${it.order?.arriveTimeDate} "//时间
            var sumNumber: Int = 0
            for (ne in it.goodsVoList!!) {
                sumNumber += ne?.number!!
            }
            mDatabind.included.tvCommon.text = "商品${sumNumber}件，共${it.order?.totalPrice}元"//时间
            GlideAppUtils.loadUrl(
                mDatabind.included.imgOrderChannelIcon,
                it.channelLogo ?: "https://static.igoodsale.com/%E7%BA%BF%E4%B8%8B.svg"
            )
            //deliveryType == "1" ,"3" 待配送 2:自提
            if (it.order!!.deliveryType == "2") {

                mDatabind.included.txtBaseOrderDelivery1.text = "前自提"
            } else {

                mDatabind.included.txtBaseOrderDelivery1.text = "前送达"
            }

            behavior?.addBottomSheetCallback(bottomSheetCallback())

            behavior?.state = BottomSheetBehavior.STATE_HALF_EXPANDED


        }
        mViewModel.orderDetailDto.onError.observe(this) {

        }
    }

    private fun dialPhoneNumber(phoneNumber: String) {
        val dialIntent = Intent(Intent.ACTION_CALL, Uri.parse("tel:${phoneNumber}"))
        startActivity(dialIntent)
    }

    //确定忽悠订单
    fun setinvalid() {
        val dialog: MineExitDialog =
            MineExitDialog().newInstance(
                "温馨提示",
                "您确认要忽略该订单吗？\n" +
                        "忽略后可去「订单查询」中查找到该订单",
                "取消",
                "确认",
                false
            )
        dialog.setOkClickLister {
            mViewModel.invalid(
                orderDetailDto?.order!!.viewId.toString(),
                "0"
            )
            showToast("订单已经忽略")
            dialog.dismiss()
        }
        dialog.show(supportFragmentManager)

    }

    //取消配送
    fun setcancelOrder() {
        val dialog: MineExitDialog =
            MineExitDialog().newInstance("温馨提示", "确定取消配送吗？", "取消", "确认", false)
        dialog.setOkClickLister {
            mViewModel.cancelOrder(
                CancelOrderSend(
                    deliveryConsumerId = orderDetailDto?.deliveryConsume!!.id ?: "0",
                    poiId = orderDetailDto?.order!!.poiId ?: "0",
                    stationChannelId = orderDetailDto?.deliveryConsume!!.stationChannelId
                        ?: "0"
                )
            )
            dialog.dismiss()
        }
        dialog.show(supportFragmentManager)
    }

    //收银小票
    fun setgetPrint() {
        //收银小票:1 退款小票:3
        when (orderDetailDto?.order!!.logisticsStatus) {
            "70" -> {
                mViewModel.getPrint(
                    orderDetailDto?.order?.viewId.toString(),
                    orderDetailDto?.order?.shopId.toString(),
                    "3"
                )
            }
            else -> {
                mViewModel.getPrint(
                    orderDetailDto?.order?.viewId.toString(),
                    orderDetailDto?.order?.shopId.toString(),
                    "1"
                )
            }

        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        Log.d("yjk", "onMessageEvent: .")
        initData()
    }






}