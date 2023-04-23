package com.meiling.oms.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.util.Log
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
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityOrderChengeAddredssMapBinding
import com.meiling.oms.dialog.OrderDistributionSelectLocalCityDialog
import com.meiling.oms.viewmodel.ChangeAddressModel
import com.meiling.oms.widget.KeyBoardUtil


/**
 * 地图选择
 * **/
@Route(path = "/app/OrderChangeAddressMapActivity")
class OrderChangeAddressMapActivity :
    BaseActivity<ChangeAddressModel, ActivityOrderChengeAddredssMapBinding>() {

    lateinit var mapView: MapView
    var aMap: AMap? = null
    var mLocationOption: AMapLocationClientOption? = null
    var mLocationClient: AMapLocationClient? = null

    lateinit var ryOrderDisMapAdapter: BaseQuickAdapter<PoiItem, BaseViewHolder>


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        mapView = mDatabind.mapView
        mapView.onCreate(savedInstanceState)
        aMap = mDatabind.mapView.map

    }

    var cityCode = ""
    var lat = "0"
    var lon = "0"
    override fun initView(savedInstanceState: Bundle?) {
        var mLocationListener = AMapLocationListener { amapLocation ->
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
                    cityCode = amapLocation.cityCode
                    mLocationClient?.stopLocation()
                    lat = amapLocation.latitude.toString()
                    lon = amapLocation.longitude.toString()
                    if (!lat.isNullOrBlank() && !lon.isNullOrBlank()) {
                        getGeocodeSearch(
                            LatLng(amapLocation.latitude, amapLocation.longitude),
                            cityCode
                        )
                    }
                    aMap!!.animateCamera(
                        CameraUpdateFactory.newLatLng(
                            LatLng(
                                amapLocation.latitude,
                                amapLocation.longitude
                            )
                        )
                    )
//                    rootView.findViewById<LinearLayout>(R.id.llError).visibility = View.GONE
                    mapView.visibility = View.VISIBLE
//                    rootView.findViewById<RecyclerView>(R.id.ryOrderDisSearchLocal).visibility = View.VISIBLE
                } else {
//                    rootView.findViewById<LinearLayout>(R.id.llError).visibility = View.VISIBLE
                    mapView.visibility = View.GONE
                    mDatabind.txtMapLocalCity?.text = "定位中"
//                    rootView.findViewById<RecyclerView>(R.id.ryOrderDisSearchLocal).visibility = View.GONE
                    Log.d("lwq", "错误3============errorCode${amapLocation?.errorCode}")
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
        //设置是否只定位一次,默认为false
        mLocationOption?.isOnceLocation = true
        //初始化定位
        mLocationClient = AMapLocationClient(this);
        //设置定位回调监听
        mLocationClient?.setLocationListener(mLocationListener);
        //初始化AMapLocationClientOption对象
        mLocationOption = AMapLocationClientOption();
        //设置定位模式为AMapLocationMode.Hight_Accuracy，高精度模式。
        mLocationOption?.locationMode = AMapLocationClientOption.AMapLocationMode.Hight_Accuracy;
        //设置定位间隔,单位毫秒,默认为2000ms，最低1000ms。
        mLocationOption?.interval = 100000;
        //设置是否返回地址信息（默认返回地址信息）
        mLocationOption?.isNeedAddress = true;
        //单位是毫秒，默认30000毫秒，建议超时时间不要低于8000毫秒。
        mLocationOption?.httpTimeOut = 800000;
        //给定位客户端对象设置定位参数
        mLocationClient?.setLocationOption(mLocationOption);
        //启动定位
        mLocationClient?.startLocation();
//        rootView.findViewById<ImageView>(R.id.imgOrderMapClose).setOnClickListener {
//            dismiss()
//        }


    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityOrderChengeAddredssMapBinding {
        return ActivityOrderChengeAddredssMapBinding.inflate(layoutInflater)
    }


    private var type = true

    override fun initData() {
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
        mDatabind.ryOrderDisSearchLocal.adapter = ryOrderDisMapAdapter

        ryOrderDisMapAdapter?.setOnItemClickListener { adapter, view, position ->
            var it = adapter!!.data[position] as PoiItem
//            mViewModel.recvAddr.onSuccess.value =
//                "${it.provinceName}${it.cityName}${it.adName}${it.snippet}${it.title}"
            lon = it.latLonPoint.longitude.toString()
            lat = it.latLonPoint.latitude.toString()
//            mViewModel.lon.onSuccess.value = lon
//            mViewModel.lat.onSuccess.value = lat

            val data = Intent()
            data.putExtra("lon", lon)
            data.putExtra("lat", lat)
            data.putExtra(
                "address",
                "${it.provinceName}${it.cityName}${it.adName}${it.snippet}${it.title}"
            )
            setResult(RESULT_OK, data)

            finish()
//            onSelectMapAddress?.invoke(adapter?.data[position] as PoiItem)
        }
        mDatabind.edtLocalSearch.addTextChangedListener(object : TextWatcher {
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
                    mDatabind.imgClearLocalSearch.visibility = View.VISIBLE
                } else {
                    mDatabind.imgClearLocalSearch.visibility = View.GONE
                    if (!lat.isNullOrBlank() && !lon.isNullOrBlank()) {
                        getGeocodeSearch(
                            LatLng(lat.toDouble(), lon.toDouble()),
                            cityCode
                        )
                    }
                }
            }
        })


//        aMap?.setOnCameraChangeListener(object : AMap.OnCameraChangeListener {
//            override fun onCameraChange(p0: CameraPosition?) {
//
//                Log.d("lwq", "=12121212===========${p0!!.target}==1212==${cityCode}")
//
//            }
//
//            override fun onCameraChangeFinish(p0: CameraPosition?) {
//                Log.d("lwq", "============${p0!!.target}==1212==${cityCode}")
//                getGeocodeSearch(p0!!.target, cityCode);
//            }
//        })

        mDatabind.edtLocalSearch.setOnEditorActionListener { v, actionId, event ->
            ryOrderDisMapAdapter.setList(arrayListOf())
            ryOrderDisMapAdapter.notifyDataSetChanged()
            searchLocationName(v.text.toString(), cityCode)
            KeyBoardUtil.closeKeyBord(
                mDatabind.edtLocalSearch,
                this
            )
            return@setOnEditorActionListener false
        }
        mDatabind.imgClearLocalSearch.setOnClickListener {
            mDatabind.edtLocalSearch.setText("")
            ryOrderDisMapAdapter.setList(arrayListOf())
            ryOrderDisMapAdapter.notifyDataSetChanged()
            if (!lat.isNullOrBlank() && !lon.isNullOrBlank()) {
                getGeocodeSearch(
                    LatLng(lat.toDouble(), lon.toDouble()),
                    cityCode
                )
            }
        }

        mDatabind.txtMapLocalCity.setOnClickListener {
            var bas = OrderDistributionSelectLocalCityDialog().newInstance()
            bas.show(supportFragmentManager)
            bas.setOnSelectAddressClickLister {
                getLatlon(it)
                mDatabind.txtMapLocalCity?.text = it
            }
        }

    }

    override fun initListener() {
//        mDatabind.aivBack.setOnClickListener { finish() }
    }

    //逆地理编码获取当前位置信息
    fun getGeocodeSearch(targe: LatLng, cityCode: String) {
        Log.d("lwq", "============12121")
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
                Log.d("lwq", "============12121p0")
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
                        getGeocodeSearch(lng, cityCode)
                    } else {
                        Log.d("lwq", "错误============errorCode${121212}")
                    }
                }
            }
        })
        val geocodeQuery = GeocodeQuery(cityName.toString().trim(), "30000")
        geocodeSearch.getFromLocationNameAsyn(geocodeQuery)
    }

    override fun onSaveInstanceState(outState: Bundle) {
        super.onSaveInstanceState(outState)
        mapView.onSaveInstanceState(outState);
    }

    override fun onResume() {
        super.onResume()
        mapView.onResume()
    }

    override fun onDestroy() {
        super.onDestroy()
        mapView.onDestroy()
        mLocationClient?.onDestroy();//停止定位后，本地定位服务并不会被销毁
    }
}