package com.meiling.oms.activity

import android.content.res.Resources
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
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.GoodsListVo
import com.meiling.oms.R
import com.meiling.oms.R.id.iv_arrow
import com.meiling.oms.adapter.OrderBaseShopListAdapter
import com.meiling.oms.databinding.ActivityOrderDetailBinding
import com.meiling.oms.viewmodel.OrderCreateViewModel
import io.reactivex.annotations.NonNull


//配送订单详情
class OrderDetailActivity : BaseActivity<OrderCreateViewModel, ActivityOrderDetailBinding>() {
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
            addGrowMarker(latLng,1)
            val latLng1 = LatLng(34.242593, 108.903436)
            addGrowMarker(latLng1,2)
        }



        behavior = BottomSheetBehavior.from(mDatabind.bottomSheet)
        behavior?.addBottomSheetCallback(bottomSheetCallback())
        behavior?.peekHeight =dp2px( 100)
        behavior?.state = BottomSheetBehavior.STATE_HALF_EXPANDED

        orderDisAdapter = OrderBaseShopListAdapter()
        var goods = ArrayList<GoodsListVo>()
        goods.add(GoodsListVo(gname = "张三"))
        goods.add(GoodsListVo(gname = "张三1"))
        goods.add(GoodsListVo(gname = "张三2"))

        orderDisAdapter.setList(goods)
        mDatabind.included.recyShopList.adapter = orderDisAdapter


    }

    override fun initData() {
        super.initData()
        var orderid = intent.getStringExtra("orderId").toString()


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
    private fun addGrowMarker(latLng: LatLng,int: Int) {
        val options = MarkerOptions()
        options.position(latLng)
        val view = LayoutInflater.from(this).inflate(R.layout.addimg, null, false)
        var iv_icon = view.findViewById<ImageView>(R.id.iv_icon)
        var tv_distance = view.findViewById<TextView>(R.id.tv_distance)
        if (int==1){
            iv_icon.setBackgroundResource(R.drawable.add_1)
            tv_distance.visibility=View.INVISIBLE
        }else{
            iv_icon.setBackgroundResource(R.drawable.collected)
            tv_distance.visibility=View.VISIBLE
        }

        options.icon(BitmapDescriptorFactory.fromView(view)
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


}