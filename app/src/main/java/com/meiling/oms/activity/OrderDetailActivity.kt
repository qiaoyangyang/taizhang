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
import com.meiling.common.network.data.OrderDetailDto
import com.meiling.common.network.data.OrderGoodsVo
import com.meiling.common.utils.DoubleClickHelper
import com.meiling.common.utils.PermissionUtilis
import com.meiling.oms.R
import com.meiling.oms.adapter.OrderBaseShopListAdapter
import com.meiling.oms.databinding.ActivityOrderDetailBinding
import com.meiling.oms.dialog.DataTipDialog
import com.meiling.oms.dialog.MineExitDialog
import com.meiling.oms.viewmodel.BaseOrderFragmentViewModel
import com.meiling.oms.widget.copyText
import com.meiling.oms.widget.showToast
import io.reactivex.annotations.NonNull


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

            aMap?.moveCamera(CameraUpdateFactory.zoomTo(12f))
            val latLng = LatLng(39.906901, 116.397972)
            addGrowMarker(latLng, 1)
            val latLng1 = LatLng(34.242593, 108.903436)
            addGrowMarker(latLng1, 2)
        }



        behavior = BottomSheetBehavior.from(mDatabind.bottomSheet)
        behavior?.addBottomSheetCallback(bottomSheetCallback())
        behavior?.peekHeight = dp2px(120)
        behavior?.state = BottomSheetBehavior.STATE_HALF_EXPANDED

        orderDisAdapter = OrderBaseShopListAdapter()
//        var goods = ArrayList<GoodsListVo>()
//        goods.add(GoodsListVo(gname = "张三"))
//        goods.add(GoodsListVo(gname = "张三1"))
//        goods.add(GoodsListVo(gname = "张三2"))

//        orderDisAdapter.setList(goods)
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
        mDatabind.included.ivPlayBack.visibility=View.GONE


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

    private val ZOOM = 15f

    /**
     * 添加带生长效果marker
     */
    private fun addGrowMarker(latLng: LatLng, int: Int) {
        val options = MarkerOptions()
        options.position(latLng)
        val view = LayoutInflater.from(this).inflate(R.layout.addimg, null, false)
        var iv_icon = view.findViewById<ImageView>(R.id.iv_icon)
        var tv_distance = view.findViewById<TextView>(R.id.tv_distance)
        if (int == 1) {
            iv_icon.setBackgroundResource(R.drawable.add_1)
            tv_distance.visibility = View.INVISIBLE
        } else {
            iv_icon.setBackgroundResource(R.drawable.collected)
            tv_distance.visibility = View.VISIBLE
        }

        options.icon(
            BitmapDescriptorFactory.fromView(view)
        )
        val marker: Marker = aMap!!.addMarker(options)
        marker.startAnimation()
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
                    val latLng = LatLng(39.906901, 116.397972)
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
                    if (orderDetailDto!=null){
                        val dialog: MineExitDialog =
                            MineExitDialog().newInstance("温馨提示", "确定忽略订单？", "取消", "确认", false)
                        dialog.setOkClickLister {
                            mViewModel.invalid(orderDetailDto?.order!!.viewId.toString(), "0")
                            showToast("订单已经忽略")
                            dialog.dismiss()
                        }
                        dialog.show(supportFragmentManager)
                    }
                }
                //自提完成
                R.id.tv_go_on -> {

                }
                //打印小票
                R.id.btn_Print_receipt -> {
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
                //修改订单
                R.id.btn_change_address -> {
                    startActivity(Intent(this, OrderChangeAddressActivity::class.java))

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
                                dialPhoneNumber("12345678")
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
                    copyText(this, "")
                    showToast("复制成功")

                }
                //三方编号
                R.id.txt_order_id_1 -> {
                    copyText(this, "")
                    showToast("复制成功")

                }
            }
        }else{
            Log.d("yjk","请勿重复点击")
        }
    }
    var orderDetailDto: OrderDetailDto?=null

    override fun createObserver() {

        super.createObserver()
        mViewModel.printDto.onStart.observe(this) {
        }
        mViewModel.printDto.onSuccess.observe(this) {
            disLoading()
            showToast("已发送打印任务")
        }
        mViewModel.printDto.onError.observe(this) {
            showToast(it.msg)
        }

        mViewModel.orderDetailDto.onStart.observe(this) {}
        mViewModel.orderDetailDto.onSuccess.observe(this) {
            Log.d("TAG", "createObserver:${it.goodsVoList.toString()} ")
            orderDisAdapter.setList(it.goodsVoList as MutableList<OrderGoodsVo>)
            orderDisAdapter.notifyDataSetChanged()
            orderDetailDto=it
            mDatabind.included.txtActualMoney.text=it?.order?.actualPayPrice//顾客实际支付
            mDatabind.included.txtPlatformMoney.text=it?.order?.actualPayPrice//平台服务费
        }
        mViewModel.orderDetailDto.onError.observe(this) {}
    }

    private fun dialPhoneNumber(phoneNumber: String) {
        val dialIntent = Intent(Intent.ACTION_CALL, Uri.parse("tel:${phoneNumber}"))
        startActivity(dialIntent)
    }


}