package com.meiling.oms.activity

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
import com.amap.api.maps.model.BitmapDescriptorFactory
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.Marker
import com.amap.api.maps.model.MarkerOptions
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.OrderDetail
import com.meiling.common.network.data.OrderPoi
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityOrderCheckMapBinding
import com.meiling.oms.viewmodel.BaseOrderFragmentViewModel


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
        var order = intent.getSerializableExtra("order") as OrderDetail
        var poi = intent.getSerializableExtra("poi") as OrderPoi
//        mViewModel.getOrderDetail(orderid)
        val latLng = LatLng(order?.lat?.toDouble()!!, order?.lon?.toDouble()!!)//客户
        addGrowMarker(latLng, 1, 0)
        val latLng1 = LatLng(poi?.lat?.toDouble()!!, poi?.lon?.toDouble()!!)
        addGrowMarker(latLng1, 2, 0)
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
                tv_distance.text = "顾客距离门店${1}km"
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

    private fun dialPhoneNumber(phoneNumber: String) {
        val dialIntent = Intent(Intent.ACTION_CALL, Uri.parse("tel:${phoneNumber}"))
        startActivity(dialIntent)
    }

}