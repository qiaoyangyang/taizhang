package com.meiling.oms.activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.view.WindowManager
import com.amap.api.maps.AMap
import com.google.android.material.bottomsheet.BottomSheetBehavior
import com.google.android.material.bottomsheet.BottomSheetBehavior.BottomSheetCallback
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.databinding.ActivityOrderDetailBinding
import com.meiling.oms.viewmodel.OrderCreateViewModel


//订单详情
class OrderDetailActivity : BaseActivity<OrderCreateViewModel, ActivityOrderDetailBinding>() {
    //初始化地图控制器对象
    var aMap: AMap? = null
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.map?.onCreate(savedInstanceState)
        if (aMap == null) {
            aMap = mDatabind.map.map
        }
       BottomSheetBehavior.from(mDatabind.bottomSheet).setBottomSheetCallback(object :BottomSheetCallback(){
           override fun onStateChanged(bottomSheet: View, newState: Int) {
               val linearParams = mDatabind.map.layoutParams //取控件textView当前的布局参数

               if (newState == BottomSheetBehavior.STATE_EXPANDED || newState == BottomSheetBehavior.STATE_HALF_EXPANDED ) {
                   linearParams.height=800
                   Log.d("yjk","0000")
               }else if (newState == BottomSheetBehavior.STATE_COLLAPSED) {
                   linearParams.height= WindowManager.LayoutParams.MATCH_PARENT
                   Log.d("yjk","2222")
               }
               mDatabind.map.layoutParams=linearParams

           }

           override fun onSlide(bottomSheet: View, slideOffset: Float) {
           }

       })
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityOrderDetailBinding {
        return ActivityOrderDetailBinding.inflate(layoutInflater)
    }
    override fun onDestroy() {
        super.onDestroy()
        //在activity执行onDestroy时执行mMapView.onDestroy()，销毁地图
       // mDatabind.mMapView.onDestroy()
        mDatabind.map.onDestroy()
    }

    override fun onResume() {
        super.onResume()
        //在activity执行onResume时执行mMapView.onResume ()，重新绘制加载地图
        mDatabind.map.onResume()
    }

    override fun onPause() {
        super.onPause()
        //在activity执行onPause时执行mMapView.onPause ()，暂停地图的绘制
        mDatabind.map.onPause()
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mDatabind.map.onSaveInstanceState(outState)
    }

}