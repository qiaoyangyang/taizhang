package com.meiling.oms.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.location.Location
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.appcompat.widget.AppCompatTextView
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.facade.annotation.Route
import com.amap.api.location.AMapLocation
import com.amap.api.location.AMapLocationClient
import com.amap.api.location.AMapLocationClientOption
import com.amap.api.location.AMapLocationListener
import com.amap.api.maps.*
import com.amap.api.maps.AMap.*
import com.amap.api.maps.LocationSource.OnLocationChangedListener
import com.amap.api.maps.model.*
import com.amap.api.services.core.LatLonPoint
import com.amap.api.services.core.PoiItem
import com.amap.api.services.geocoder.GeocodeQuery
import com.amap.api.services.geocoder.GeocodeResult
import com.amap.api.services.geocoder.GeocodeSearch
import com.amap.api.services.geocoder.RegeocodeResult
import com.amap.api.services.poisearch.PoiResult
import com.amap.api.services.poisearch.PoiSearch
import com.amap.api.services.poisearch.PoiSearch.OnPoiSearchListener
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.google.gson.Gson
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityOrderChengeAddredssMapBinding
import com.meiling.oms.dialog.OrderDistributionSelectLocalCityDialog
import com.meiling.oms.viewmodel.ChangeAddressModel
import com.meiling.oms.widget.KeyBoardUtil
import com.meiling.oms.widget.showToast

class NewScanOrderChangeAddressMapActivity() :
    BaseActivity<ChangeAddressModel, ActivityOrderChengeAddredssMapBinding>(),
    OnMyLocationChangeListener, LocationSource,
    OnMapTouchListener, AMapLocationListener, OnPoiSearchListener {
    var mMapView: MapView? = null

    var myLocationStyle: MyLocationStyle? = null
    var aMap: AMap? = null
    var useMoveToLocationWithMapMode = true

    var txtMapLocalCity: TextView? = null
    var txtOrderAddressTitle: TextView? = null
    var ryOrderDisSearchLocal: RecyclerView? = null
    var edtLocalSearch: EditText? = null
    var imgClearLocalSearch: ImageView? = null
    var back: ImageView? = null
    lateinit var ryOrderDisMapAdapter: BaseQuickAdapter<PoiItem, BaseViewHolder>
    var cityCode = ""
    var lat = "0"
    var lon = "0"
    var type: String = "1"
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.activity_order_chenge_addredss_map)

        mMapView = findViewById<View>(R.id.mapView) as MapView
        txtMapLocalCity = findViewById<View>(R.id.txtMapLocalCity) as TextView
        edtLocalSearch = findViewById<EditText>(R.id.edtLocalSearch) as EditText
        imgClearLocalSearch = findViewById<ImageView>(R.id.imgClearLocalSearch) as ImageView
        back = findViewById<ImageView>(R.id.img_search_back) as ImageView
        txtOrderAddressTitle = findViewById<TextView>(R.id.txt_address_title) as TextView

        back?.setOnClickListener {
            finish()
        }
        ryOrderDisSearchLocal =
            findViewById<RecyclerView>(R.id.ryOrderDisSearchLocal) as RecyclerView
        //在activity执行onCreate时执行mMapView.onCreate(savedInstanceState)，创建地图
        mMapView!!.onCreate(savedInstanceState)
        if (aMap == null) {
            aMap = mMapView!!.map
//            setUpMap()
        }

        txtOrderAddressTitle?.text = intent.getStringExtra("title").toString() ?: "选择地址"
        if (intent.getStringExtra("type").toString() == "2") {
            getLatlon(intent.getStringExtra("cityName").toString())
            txtMapLocalCity?.text = intent.getStringExtra("cityName").toString()
            edtLocalSearch?.setText(intent.getStringExtra("address").toString())
        } else {
            getLatlon(intent.getStringExtra("cityName").toString())
            txtMapLocalCity?.text = intent.getStringExtra("cityName").toString()
            edtLocalSearch?.setText("")
        }
        initViewAdapter()
    }

    private fun initViewAdapter() {
        ryOrderDisMapAdapter = object :
            BaseQuickAdapter<PoiItem, BaseViewHolder>(R.layout.item_recy_distribution_local_map) {
            override fun convert(holder: BaseViewHolder, item: PoiItem) {
                holder.setText(R.id.txtLocalCityName, item.title)
//                holder.setText(R.id.txtKm, "${item.distance}")
                holder.setText(
                    R.id.txtMapAddress,
                    "${item.provinceName}${item.cityName}${item.adName}${item.snippet}"
                )
            }
        }

        ryOrderDisSearchLocal?.adapter = ryOrderDisMapAdapter




        ryOrderDisMapAdapter?.setOnItemClickListener { adapter, view, position ->
            var it = adapter!!.data[position] as PoiItem
//            mViewModel.recvAddr.onSuccess.value =
//                "${it.provinceName}${it.cityName}${it.adName}${it.snippet}${it.title}"
            lon = it.latLonPoint.longitude.toString()
            lat = it.latLonPoint.latitude.toString()
//            mViewModel.lat.onSuccess.value = lat
            val data = Intent()
            data.putExtra("lon", lon)
            data.putExtra("lat", lat)
            data.putExtra("poiItem", it)
            data.putExtra(
                "address",
                "${it.provinceName}${it.cityName}${it.adName}${it.snippet}${it.title}"
            )
            setResult(RESULT_OK, data)
            finish()
//            onSelectMapAddress?.invoke(adapter?.data[position] as PoiItem)
        }
        edtLocalSearch?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(
                s: CharSequence?,
                start: Int,
                count: Int,
                after: Int
            ) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNotEmpty()) {
                    imgClearLocalSearch?.visibility = View.VISIBLE
                } else {
                    imgClearLocalSearch?.visibility = View.GONE
                    if (!lat.isNullOrBlank() && !lon.isNullOrBlank()) {
                        getGeocodeSearch(
                            LatLng(lat.toDouble(), lon.toDouble()),
                            cityCode
                        )
                    }
                }
            }
        })

        edtLocalSearch?.setOnEditorActionListener { v, actionId, event ->
            ryOrderDisMapAdapter.setList(arrayListOf())
            ryOrderDisMapAdapter.notifyDataSetChanged()
            if (v.text.toString().isNullOrEmpty()) {
                if (!lat.isNullOrBlank() && !lon.isNullOrBlank()) {
                    getGeocodeSearch(
                        LatLng(lat.toDouble(), lon.toDouble()),
                        cityCode
                    )
                }
            } else {
                searchLocationName(v.text.toString(), cityCode)
            }
            KeyBoardUtil.closeKeyBord(
                edtLocalSearch!!,
                this
            )
            return@setOnEditorActionListener false
        }
        imgClearLocalSearch?.setOnClickListener {
            edtLocalSearch?.setText("")
            ryOrderDisMapAdapter.setList(arrayListOf())
            ryOrderDisMapAdapter.notifyDataSetChanged()
            if (!lat.isNullOrBlank() && !lon.isNullOrBlank()) {
                getGeocodeSearch(
                    LatLng(lat.toDouble(), lon.toDouble()),
                    cityCode
                )
            }
        }

        txtMapLocalCity?.setOnClickListener {
            if (txtMapLocalCity?.text.toString() == "定位失败") {
                showToast("定位失败，请检查网络或者权限")
            } else {
                var bas = OrderDistributionSelectLocalCityDialog().newInstance()
                bas.show(supportFragmentManager)
                bas.setOnSelectAddressClickLister {
                    getLatlon(it)
                    txtMapLocalCity?.text = it
                }
            }
        }

    }

    fun searchLocationName(keyWork: String, cityCode: String) {
        var queryQuery = PoiSearch.Query(keyWork, "", cityCode)
        var poiSearch = PoiSearch(this, queryQuery)
        queryQuery.pageSize = 20
        poiSearch.searchPOIAsyn();
        poiSearch.setOnPoiSearchListener(object : PoiSearch.OnPoiSearchListener {
            override fun onPoiSearched(p0: PoiResult?, p1: Int) {
                if (p0?.pois.isNullOrEmpty()) {
                    ryOrderDisMapAdapter.setList(arrayListOf())
                    ryOrderDisMapAdapter.notifyDataSetChanged()
                } else {
                    ryOrderDisMapAdapter.setList(p0?.pois)
                    val lng =
                        LatLng(
                            p0?.pois!![0].latLonPoint.latitude,
                            p0?.pois!![0].latLonPoint.longitude
                        )
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
                        lon = geocodeAddress.latLonPoint.longitude.toString()
                        lat = geocodeAddress.latLonPoint.latitude.toString()
                        cityCode = geocodeAddress.adcode
                        val lng = LatLng(latitude, longititude)
                        aMap!!.moveCamera(CameraUpdateFactory.changeLatLng(lng))
                        if (intent.getStringExtra("type").toString() == "2") {
                            if (edtLocalSearch?.text.toString().isNullOrEmpty()) {
                                getGeocodeSearch(lng, cityCode)
                            } else {
                                searchLocationName(
                                    edtLocalSearch?.text.toString(),
                                    cityCode
                                )
                            }
                        } else {
                            txtMapLocalCity?.text = intent.getStringExtra("cityName").toString()
                            if (intent.getStringExtra("address").toString().isNullOrEmpty()) {
                                getGeocodeSearch(lng, cityCode)
                            } else {
                                searchLocationName(
                                    intent.getStringExtra("address").toString(),
                                    cityCode
                                )
                            }
                        }
                    } else {
                    }
                }
            }
        })
        val geocodeQuery = GeocodeQuery(cityName.toString().trim(), "30000")
        geocodeSearch.getFromLocationNameAsyn(geocodeQuery)
    }

    /**
     * 设置一些amap的属性
     */
    private fun setUpMap() {
        aMap!!.setLocationSource(this) // 设置定位监听
        aMap!!.uiSettings.isMyLocationButtonEnabled = true // 设置默认定位按钮是否显示
        aMap!!.isMyLocationEnabled = true // 设置为true表示显示定位层并可触发定位，false表示隐藏定位层并不可触发定位，默认是false
        aMap!!.setOnMapTouchListener(this)
    }

    /**
     * 方法必须重写
     */
    override fun onDestroy() {
        super.onDestroy()
        mMapView!!.onDestroy()
        if (null != mlocationClient) {
            mlocationClient!!.onDestroy()
        }
    }

    override fun onResume() {
        super.onResume()
        mMapView!!.onResume()
        useMoveToLocationWithMapMode = true
    }

    override fun onPause() {
        super.onPause()
        mMapView!!.onPause()
        deactivate()
        useMoveToLocationWithMapMode = false
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mMapView!!.onSaveInstanceState(outState)
    }

    override fun onMyLocationChange(location: Location) {}
    private var mListener: OnLocationChangedListener? = null
    private var mlocationClient: AMapLocationClient? = null
    private var mLocationOption: AMapLocationClientOption? = null

    /**
     * 激活定位
     */
    override fun activate(listener: OnLocationChangedListener) {
        mListener = listener
        if (mlocationClient == null) {
            try {
                mlocationClient = AMapLocationClient(this)
                mLocationOption = AMapLocationClientOption()
                //设置定位监听
                mlocationClient!!.setLocationListener(this)
                //设置为高精度定位模式
                mLocationOption!!.locationMode =
                    AMapLocationClientOption.AMapLocationMode.Hight_Accuracy
                //是指定位间隔
                //   mLocationOption.setInterval(2000);
                mLocationOption!!.isOnceLocation = true
                //设置定位参数
                mlocationClient!!.setLocationOption(mLocationOption)
                // 此方法为每隔固定时间会发起一次定位请求，为了减少电量消耗或网络流量消耗，
                // 注意设置合适的定位时间的间隔（最小间隔支持为2000ms），并且在合适时间调用stopLocation()方法来取消定位请求
                // 在定位结束后，在合适的生命周期调用onDestroy()方法
                // 在单次定位情况下，定位无论成功与否，都无需调用stopLocation()方法移除请求，定位sdk内部会移除
                mlocationClient!!.startLocation()
            } catch (e: Exception) {
                e.printStackTrace()
            }
        }
    }

    /**
     * 停止定位
     */
    override fun deactivate() {
        mListener = null
        if (mlocationClient != null) {
            mlocationClient!!.stopLocation()
            mlocationClient!!.onDestroy()
        }
        mlocationClient = null
    }

    override fun onTouch(motionEvent: MotionEvent) {
        useMoveToLocationWithMapMode = false
    }

    //自定义定位小蓝点的Marker
    var locationMarker: Marker? = null
    override fun onLocationChanged(amapLocation: AMapLocation) {
        if (mListener != null && amapLocation != null) {
            if (amapLocation != null
                && amapLocation.errorCode == 0
            ) {
                val latLng = LatLng(amapLocation.latitude, amapLocation.longitude)
                //展示自定义定位小蓝点
                if (locationMarker == null) {
                    //首次定位
                    locationMarker = aMap!!.addMarker(
                        MarkerOptions().position(latLng)
                            .icon(BitmapDescriptorFactory.fromResource(R.drawable.bg_guanbi))
                            .anchor(0.5f, 0.5f)
                    )

                    //首次定位,选择移动到地图中心点并修改级别到15级
                    aMap!!.moveCamera(CameraUpdateFactory.newLatLngZoom(latLng, 15f))
                } else {
                    if (useMoveToLocationWithMapMode) {
                        //二次以后定位，使用sdk中没有的模式，让地图和小蓝点一起移动到中心点（类似导航锁车时的效果）
                        startMoveLocationAndMap(latLng)
                    } else {
                        startChangeLocation(latLng)
                    }
                }
                Log.d("yjl", "onLocationChanged: " + Gson().toJson(amapLocation))
                Log.d("yjl", "onLocationChanged: " + amapLocation.latitude)
                Log.d("yjl", "onLocationChanged: " + amapLocation.latitude)
                cityCode = amapLocation.cityCode
                lat = amapLocation.latitude.toString()
                lon = amapLocation.longitude.toString()
                txtMapLocalCity?.text = amapLocation.city
                getGeocodeSearch(latLng, amapLocation.city)

            } else {
                cityCode = ""
                txtMapLocalCity?.text = "定位失败"
                val errText = "定位失败," + amapLocation.errorCode + ": " + amapLocation.errorInfo
                Log.e("AmapErr", errText)
            }
        }
    }

    //坐标和经纬度转换工具
    var projection: Projection? = null

    /**
     * 同时修改自定义定位小蓝点和地图的位置
     *
     * @param latLng
     */
    private fun startMoveLocationAndMap(latLng: LatLng) {

        //将小蓝点提取到屏幕上
        if (projection == null) {
            projection = aMap!!.projection
        }
        if (locationMarker != null && projection != null) {
            val markerLocation = locationMarker!!.position
            val screenPosition = aMap!!.projection.toScreenLocation(markerLocation)
            locationMarker!!.setPositionByPixels(screenPosition.x, screenPosition.y)
        }

        //移动地图，移动结束后，将小蓝点放到放到地图上
        myCancelCallback.setTargetLatlng(latLng)
        //动画移动的时间，最好不要比定位间隔长，如果定位间隔2000ms 动画移动时间最好小于2000ms，可以使用1000ms
        //如果超过了，需要在myCancelCallback中进行处理被打断的情况
        aMap!!.animateCamera(CameraUpdateFactory.changeLatLng(latLng), 1000, myCancelCallback)
    }

    var myCancelCallback = MyCancelCallback()

    override fun onPoiSearched(poiResult: PoiResult, i: Int) {
    }

    override fun onPoiItemSearched(poiItem: PoiItem, i: Int) {}

    /**
     * 监控地图动画移动情况，如果结束或者被打断，都需要执行响应的操作
     */
    inner class MyCancelCallback : CancelableCallback {
        var targetLatlng = null
        fun setTargetLatlng(latlng: LatLng?) {
            targetLatlng = latlng as Nothing?
        }

        override fun onFinish() {
            if (locationMarker != null && targetLatlng != null) {
                locationMarker!!.position = targetLatlng
            }
        }

        override fun onCancel() {
            if (locationMarker != null && targetLatlng != null) {
                locationMarker!!.position = targetLatlng
            }
        }
    }

    /**
     * 修改自定义定位小蓝点的位置
     *
     * @param latLng
     */
    private fun startChangeLocation(latLng: LatLng) {
        if (locationMarker != null) {
            val curLatlng = locationMarker!!.position
            if (curLatlng == null || curLatlng != latLng) {
                locationMarker!!.position = latLng
            }
        }
    }

    fun getGeocodeSearch(targe: LatLng, code: String?) {
        var queryQuery = PoiSearch.Query("住宿|商场|学校|住宅区|楼宇", "", cityCode)
        var poiSearch = PoiSearch(this, queryQuery)
        queryQuery.pageSize = 10
        poiSearch.bound = PoiSearch.SearchBound(
            LatLonPoint(targe.latitude, targe.longitude), 1000
        ) //设置周边搜索的中心点以及半径

        poiSearch.searchPOIAsyn();
        poiSearch.setOnPoiSearchListener(object : PoiSearch.OnPoiSearchListener {
            override fun onPoiSearched(p0: PoiResult?, p1: Int) {
                ryOrderDisMapAdapter.setList(p0?.pois)
                ryOrderDisMapAdapter.notifyDataSetChanged()
            }

            override fun onPoiItemSearched(p0: PoiItem?, p1: Int) {
            }
        });

    }

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityOrderChengeAddredssMapBinding {
        return ActivityOrderChengeAddredssMapBinding.inflate(layoutInflater)
    }

}