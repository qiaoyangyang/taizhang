package com.meiling.account.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.UiSettings
import com.amap.api.maps.model.*
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.OrderDetailDto
import com.meiling.account.R
import com.meiling.account.databinding.ActivityOrderCheckMapBinding
import com.meiling.account.viewmodel.BaseOrderFragmentViewModel


//查看地图
class OrderMapCheActivity :
    BaseActivity<BaseOrderFragmentViewModel, ActivityOrderCheckMapBinding>() {
    //初始化地图控制器对象
    var aMap: AMap? = null
    var uiSettings: UiSettings? = null

    //商品
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.map?.onCreate(savedInstanceState)
        if (aMap == null) {
            aMap = mDatabind.map.map
            uiSettings = aMap?.uiSettings;//实例化UiSettings类对象
            uiSettings?.isZoomControlsEnabled = false
            //aMap?.moveCamera(CameraUpdateFactory.zoomTo(12f))
        }
    }

    override fun initData() {
        super.initData()
        var orderDetailDto = intent.getSerializableExtra("orderDetailDto") as OrderDetailDto
        val latLng = LatLng(orderDetailDto.order?.lat?.toDouble()!!, orderDetailDto.order?.lon?.toDouble()!!)//客户
        addGrowMarker(latLng, 1, 0,orderDetailDto.distance.toString())
        val latLng1 = LatLng(orderDetailDto.poi?.lat?.toDouble()!!, orderDetailDto.poi?.lon?.toDouble()!!)
        addGrowMarker(latLng1, 2, 0,orderDetailDto.distance.toString())
           var mAllLatLng = ArrayList<LatLng>()
        // 添加我的位置
        mAllLatLng.add(latLng)
        mAllLatLng.add(latLng1)
        // 将所有的点显示到地图界面上
        setMapBounds(mAllLatLng);
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityOrderCheckMapBinding {
        return ActivityOrderCheckMapBinding.inflate(layoutInflater)
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
    private fun addGrowMarker(latLng: LatLng, int: Int, type: Int,distance :String) {
        val options = MarkerOptions()
        options.position(latLng)
        val view = LayoutInflater.from(this).inflate(R.layout.addimg, null, false)
        var iv_icon = view.findViewById<ImageView>(R.id.iv_icon)
        var tv_distance = view.findViewById<TextView>(R.id.tv_distance)
        if (type == 0) {
            if (int == 1) {
                iv_icon.setBackgroundResource(R.drawable.add_1)
                tv_distance.visibility = View.VISIBLE
                tv_distance.text = "顾客距离门店${distance}km"
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
        ).position(latLng)
        val marker: Marker = aMap!!.addMarker(options)
        marker.startAnimation()
       // aMap!!.moveCamera(CameraUpdateFactory.zoomTo(ZOOM))
       // aMap!!.moveCamera(CameraUpdateFactory.changeLatLng(latLng))

//        aMap?.moveCamera(CameraUpdateFactory.zoomTo(17.0f))
//        var movecity = CameraUpdateFactory.newLatLngZoom(latLng,17f)
//        aMap?.moveCamera(movecity)
//        options.position(latLng)
//        aMap?.addMarker(options)
    }

    /**
     * include marker show zoom
     */
    private fun setMapBounds(LatLngs: List<LatLng>) {
        val latlngBuilder = LatLngBounds.builder()
        for (latLng in LatLngs) { // 将所有的点都放到latlngBuilder中
            latlngBuilder.include(latLng)
        }
        val bounds = latlngBuilder.build()
        aMap?.animateCamera(
            CameraUpdateFactory.newLatLngBounds(
                bounds,
                300
            )
        ) // 地图显示包含全部的点 40 表示padding=40，如果你想让你的marker布局全部显示出来就需要考虑到marker的高度来设置padding值
    }

    private fun dialPhoneNumber(phoneNumber: String) {
        val dialIntent = Intent(Intent.ACTION_CALL, Uri.parse("tel:${phoneNumber}"))
        startActivity(dialIntent)
    }

}