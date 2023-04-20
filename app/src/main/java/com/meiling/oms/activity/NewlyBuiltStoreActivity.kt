package com.meiling.oms.activity

import android.Manifest
import android.annotation.SuppressLint
import android.content.Intent
import android.content.pm.PackageManager
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Nullable
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.amap.api.services.core.PoiItem
import com.hjq.permissions.XXPermissions
import com.meiling.common.activity.BaseVmActivity
import com.meiling.oms.bean.PoiVo
import com.meiling.oms.databinding.ActivityNewlyBuiltStoreBinding
import com.meiling.oms.viewmodel.StoreManagementViewModel
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast
import kotlinx.coroutines.*

//新建门店
@Route(path = "/app/NewlyBuiltStoreActivity")
class NewlyBuiltStoreActivity :
    BaseVmActivity<StoreManagementViewModel>() {
    private val REQUEST_CODE = 1000
    lateinit var mDatabind: ActivityNewlyBuiltStoreBinding

    override fun initView(savedInstanceState: Bundle?) {

    }

    private val ACCESS_FINE_LOCATION = 1
    var address = ""
    var lat = ""
    var lon = ""
    var poiItem: PoiItem? = null
    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // 处理返回的结果
            lon = data.getStringExtra("lon").toString()
            lat = data.getStringExtra("lat").toString()
            address = data.getStringExtra("address").toString()
            poiItem = data.getParcelableExtra("poiItem")
            mDatabind.etStoreAddress.text = address
        }
    }

    override fun isStatusBarDarkFont(): Boolean {
        return true
    }

    override fun initDataBind() {
        mDatabind = ActivityNewlyBuiltStoreBinding.inflate(layoutInflater)
        setContentView(mDatabind.root)


    }

    var id = ""
    override fun initData() {
        super.initData()
        mDatabind.viewModel = mViewModel
        mDatabind.lifecycleOwner = this
        id = intent.getStringExtra("id").toString()
        if (!TextUtils.isEmpty(id) && id != "null") {

            mDatabind.tvGoOn.visibility = View.GONE
            mDatabind.etStoreName.keyListener = null
            mDatabind.etStoreTelephone.keyListener = null
            mDatabind.etStoreNumber.keyListener = null
            mDatabind.etStoreAddress.keyListener = null
            mDatabind.etDetailedAddress.keyListener = null
            mDatabind.etStoreContact.keyListener = null
            mDatabind.etContactPhoner.keyListener = null
            mViewModel.poi(id)
        } else {
            mDatabind.tvGoOn.visibility = View.VISIBLE
        }
        mDatabind.tvGoOn.setSingleClickListener {
            if (TextUtils.isEmpty(mViewModel.PoiVoBean.value?.poiVo?.name)) {
                showToast("请输入门店名称")
            }
            if (TextUtils.isEmpty(mViewModel.PoiVoBean.value?.poiVo?.name)) {
                showToast("请输入门店名称")
            }
            mViewModel.poiadd(
                lat,
                lon,
                poiItem?.provinceCode!!,
                "",
                poiItem?.adCode!!,
                poiItem?.cityName!!.replace("市", "")
            )


        }
        mDatabind.etStoreAddress.setOnClickListener {
//            var intent = Intent(this, OrderChangeAddressMapActivity::class.java)
//            requestDataLauncher.launch(intent)
            if (ContextCompat.checkSelfPermission(
                    this,
                    Manifest.permission.ACCESS_FINE_LOCATION
                ) == PackageManager.PERMISSION_GRANTED
            ) {
                // 如果有权限，拨打电话
                ARouter.getInstance().build("/app/OrderChangeAddressMapActivity")
                    .navigation(this, REQUEST_CODE)
            } else {
                // 如果没有权限，申请权限
                ActivityCompat.requestPermissions(
                    this,
                    arrayOf(Manifest.permission.ACCESS_FINE_LOCATION),
                    ACCESS_FINE_LOCATION
                )
            }
        }

    }

    var poiVo = PoiVo()

    @SuppressLint("SuspiciousIndentation")
    override fun createObserver() {
        mViewModel.poidata.onSuccess.observe(this) {

            val x = it.poiVo?.address!!.split(" ")
            if (x.size != 0) {
                it?.poiVo?.storeaddress = x[0]
                it?.poiVo?.etdetailedaddress = x[1]
            }

            mViewModel.PoiVoBean.value = it
        }
    }

    override fun isLightMode(): Boolean {
        return false
    }

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ACCESS_FINE_LOCATION) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_GRANTED) {
                // 如果有权限跳转
                ARouter.getInstance().build("/app/OrderChangeAddressMapActivity")
                    .navigation(this, REQUEST_CODE)
            } else {
                showToast("您已经禁止权限，请手动开启")
                // 如果用户拒绝了权限，可以在这里处理相应的逻辑
            }
        }
    }


}