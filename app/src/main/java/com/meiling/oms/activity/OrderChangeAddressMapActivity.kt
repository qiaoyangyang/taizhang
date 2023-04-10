package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.AMap
import com.amap.api.maps.CameraUpdateFactory
import com.amap.api.maps.MapView
import com.amap.api.maps.model.CameraPosition
import com.amap.api.maps.model.LatLng
import com.amap.api.maps.model.MyLocationStyle
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.core.PoiItem
import com.amap.api.services.geocoder.GeocodeQuery
import com.amap.api.services.geocoder.GeocodeResult
import com.amap.api.services.geocoder.GeocodeSearch
import com.amap.api.services.geocoder.RegeocodeResult
import com.amap.api.services.poisearch.PoiResult
import com.amap.api.services.poisearch.PoiSearch
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.databinding.ActivityOrderChengeAddredssBinding
import com.meiling.oms.databinding.ActivityOrderChengeAddredssMapBinding

/**
 * 地图选择
 * **/
@Route(path = "/app/OrderChangeAddressMapActivity")
class OrderChangeAddressMapActivity : BaseActivity<BaseViewModel,ActivityOrderChengeAddredssMapBinding>() {

    lateinit var mapView: MapView
    var aMap: AMap? = null
    var mLocationOption: AMapLocationClientOption? = null
    var mLocationClient: AMapLocationClient? = null


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapView =  mDatabind.mapView
        mapView.onCreate(savedInstanceState)
        aMap =   mDatabind.mapView.map

    }

    override fun initView(savedInstanceState: Bundle?) {
        var mLocationListener: AMapLocationListener = AMapLocationListener { amapLocation ->
            if (amapLocation != null) {
                if (amapLocation.errorCode == 0) {
                    amapLocation.locationType;//获取当前定位结果来源，如网络定位结果，详见定位类型表
                    amapLocation.latitude;//获取纬度
                    amapLocation.longitude;//获取经度
                    amapLocation.accuracy;//获取精度信息
                    mDatabind.txtMapLocalCity?.text = amapLocation.city;
//                    clearSearch = amapLocation.address
                    amapLocation.address;//地址，如果option中设置isNeedAddress为false，则没有此结果，网络定位结果中会有地址信息，GPS定位不返回地址信息。
                    amapLocation.country;//国家信息
                    amapLocation.province;//省信息
                    amapLocation.city;//城市信息
                    amapLocation.district;//城区信息
                    amapLocation.street;//街道信息
                    amapLocation.streetNum;//街道门牌号信息
                    amapLocation.cityCode;//城市编码
                    amapLocation.adCode;//地区编码
                    amapLocation.aoiName;//获取当前定位点的AOI信息
                    amapLocation.buildingId;//获取当前室内定位的建筑物Id
                    amapLocation.floor;//获取当前室内定位的楼层
                    amapLocation.gpsAccuracyStatus;//获取GPS的当前状态
//                    cityCode = amapLocation.cityCode
                    mLocationClient?.stopLocation()
//                    lat = amapLocation.latitude.toString()
//                    lon = amapLocation.longitude.toString()
//                    if(!lat.isNullOrBlank()&&!lon.isNullOrBlank()) {
//                        getGeocodeSearch(
//                            LatLng(amapLocation.latitude, amapLocation.longitude),
//                            cityCode
//                        )
//                    }
//                    aMap!!.animateCamera(CameraUpdateFactory.newLatLng(LatLng(amapLocation.latitude, amapLocation.longitude)))
//                    rootView.findViewById<LinearLayout>(R.id.llError).visibility = View.GONE
//                    mapView.visibility = View.VISIBLE
//                    rootView.findViewById<RecyclerView>(R.id.ryOrderDisSearchLocal).visibility = View.VISIBLE
                } else {
//                    rootView.findViewById<LinearLayout>(R.id.llError).visibility = View.VISIBLE
                    mapView.visibility = View.GONE
                    mDatabind.txtMapLocalCity?.text = "定位中"
//                    rootView.findViewById<RecyclerView>(R.id.ryOrderDisSearchLocal).visibility = View.GONE
//                    NLog.d("lwq", "错误3============errorCode${amapLocation?.errorCode}")
                }
            }
        }
        var myLocationStyle = MyLocationStyle()
        myLocationStyle.interval(2000); //设置连续定位模式下的定位间隔，只在连续定位模式下生效，单次定位模式下不会生效。单位为毫秒。
        myLocationStyle.myLocationType(MyLocationStyle.LOCATION_TYPE_LOCATE);
        aMap?.myLocationStyle = myLocationStyle;//设置定位蓝点的Style
        aMap?.uiSettings?.isMyLocationButtonEnabled = false;//设置默认定位按钮是否显示，非必需设置。
        aMap?.isMyLocationEnabled = true;// 设置为true表示启动显示定位蓝点，false表示隐藏定位蓝点并不进行定位，默认是false。
        //设置地图的放缩级别
        aMap?.moveCamera(CameraUpdateFactory.zoomTo(15f));
        //初始化定位
        mLocationClient = AMapLocationClient(this);
        //设置定位回调监听
        mLocationClient?.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption?.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy;
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption?.interval = 1000;
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption?.isNeedAddress = true;
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption?.httpTimeOut = 20000;
        //给定位客户端对象设置定位参数
        mLocationClient?.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient?.startLocation();
//        rootView.findViewById<ImageView>(R.id.imgOrderMapClose).setOnClickListener {
//            dismiss()
//        }

        aMap?.setOnCameraChangeListener(object : AMap.OnCameraChangeListener {
            override fun onCameraChange(p0: CameraPosition?) {

            }

            override fun onCameraChangeFinish(p0: CameraPosition?) {
//                getGeocodeSearch(p0!!.target, cityCode);
            }
        })
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityOrderChengeAddredssMapBinding {
        return ActivityOrderChengeAddredssMapBinding.inflate(layoutInflater)
    }


    private var type = true

    override fun initListener() {
//        mDatabind.aivBack.setOnClickListener { finish() }
    }
    //逆地理编码获取当前位置信息
    fun getGeocodeSearch(targe: LatLng, cityCode: String) {
//        if (geocodeSearch == null) geocodeSearch = GeocodeSearch(context);
//        // 第一个参数表示一个Latlng，第二参数表示范围多少米，第三个参数表示是火系坐标系还是GPS原生坐标系
//        var query: RegeocodeQuery =
//            RegeocodeQuery(LatLonPoint(targe.latitude, targe.longitude), 1000f, GeocodeSearch.AMAP);
//        geocodeSearch?.getFromLocationAsyn(query)
//        geocodeSearch?.setOnGeocodeSearchListener(object : GeocodeSearch.OnGeocodeSearchListener {
//            override fun onRegeocodeSearched(p0: RegeocodeResult?, p1: Int) {
//                NLog.d("lwq","=====PoiResult====${Gson().toJson(p0?.regeocodeAddress!!.pois)}")
//                ryOrderDisMapAdapter.setList(p0?.regeocodeAddress!!.pois)
//            }
//
//            override fun onGeocodeSearched(p0: GeocodeResult?, p1: Int) {
//            }
//        });

        var queryQuery = PoiSearch.Query("住宿|商场|学校|住宅区|楼宇", "", cityCode)
        var poiSearch = PoiSearch(this, queryQuery)
        queryQuery.pageSize = 10
        poiSearch.bound = PoiSearch.SearchBound(
            LatLonPoint(targe.latitude, targe.longitude), 1000
        ) //设置周边搜索的中心点以及半径

        poiSearch.searchPOIAsyn();
        poiSearch.setOnPoiSearchListener(object : PoiSearch.OnPoiSearchListener {
            override fun onPoiSearched(p0: PoiResult?, p1: Int) {
//                ryOrderDisMapAdapter.setList(p0?.pois)
//                ryOrderDisMapAdapterOrderDisMapAdapter.notifyDataSetChanged()
            }

            override fun onPoiItemSearched(p0: PoiItem?, p1: Int) {
            }
        });
    }


    fun searchLocationName(keyWork: String, cityCode: String) {
        var queryQuery = PoiSearch.Query(keyWork, "", cityCode)
        var poiSearch = PoiSearch(this, queryQuery)
        queryQuery.pageSize = 20
        poiSearch.searchPOIAsyn();
        poiSearch.setOnPoiSearchListener(object : PoiSearch.OnPoiSearchListener {
            override fun onPoiSearched(p0: PoiResult?, p1: Int) {
                if (p0?.pois.isNullOrEmpty()){
//                    ryOrderDisMapAdapter.setList(arrayListOf())
//                    ryOrderDisMapAdapter.notifyDataSetChanged()
                }else{
//                    ryOrderDisMapAdapter.setList(p0?.pois)
                    val lng =
                        LatLng(p0?.pois!![0].latLonPoint.latitude, p0?.pois!![0].latLonPoint.longitude)
                    aMap!!.moveCamera(CameraUpdateFactory.changeLatLng(lng))
                }
            }
            override fun onPoiItemSearched(p0: PoiItem?, p1: Int) {
            }
        });

    }


    //切换城市
    private fun getLatlon(cityName: String) {
        //构造 GeocodeSearch 对象，并设置监听。
        val geocodeSearch = GeocodeSearch(this)
        geocodeSearch.setOnGeocodeSearchListener(object : GeocodeSearch.OnGeocodeSearchListener {
            //------------------------坐标转地址/坐标转地址的监听回调-----------------------
            override fun onRegeocodeSearched(regeocodeResult: RegeocodeResult, i: Int) {}
            override fun onGeocodeSearched(geocodeResult: GeocodeResult, i: Int) {
                if (i == 1000) {
                    if (geocodeResult != null && geocodeResult.geocodeAddressList != null && geocodeResult.geocodeAddressList.size > 0) {
                        val geocodeAddress = geocodeResult.geocodeAddressList[0]

                        val latitude = geocodeAddress.latLonPoint.latitude //纬度
                        val longititude = geocodeAddress.latLonPoint.longitude //经度
//                        lon = geocodeAddress.latLonPoint.longitude.toString()
//                        lat = geocodeAddress.latLonPoint.latitude.toString()
//                        cityCode  = geocodeAddress.adcode
                        val adcode = geocodeAddress.adcode //区域编码
                        val lng = LatLng(latitude, longititude)
                        aMap!!.moveCamera(CameraUpdateFactory.changeLatLng(lng))
                    } else {

                    }
                }
            }
        })
        val geocodeQuery = GeocodeQuery(cityName.toString().trim(), "30000")
        geocodeSearch.getFromLocationNameAsyn(geocodeQuery)
    }

}