package com.meiling.oms.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import androidx.activity.result.contract.ActivityResultContracts
import androidx.annotation.Nullable
import com.alibaba.android.arouter.facade.annotation.Route
import com.meiling.common.activity.BaseVmActivity
import com.meiling.oms.bean.PoiVo
import com.meiling.oms.databinding.ActivityNewlyBuiltStoreBinding
import com.meiling.oms.viewmodel.StoreManagementViewModel
import kotlinx.coroutines.*

//新建门店
@Route(path = "/app/NewlyBuiltStoreActivity")
class NewlyBuiltStoreActivity :
    BaseVmActivity<StoreManagementViewModel>() {
    private val REQUEST_CODE = 1000
    lateinit var mDatabind: ActivityNewlyBuiltStoreBinding
    private val requestDataLauncher =
        registerForActivityResult(ActivityResultContracts.StartActivityForResult()) { result ->
            if (result.resultCode == RESULT_OK) {
                val data = result.data?.getStringExtra("data")
                Log.d("FirstActivity", "data =${data}")
            }
        }

    override fun initView(savedInstanceState: Bundle?) {

    }
    var address = ""
    var lat = ""
    var lon = ""
    override fun onActivityResult(requestCode: Int, resultCode: Int, @Nullable data: Intent?) {
        super.onActivityResult(requestCode, resultCode, data)
        if (requestCode == REQUEST_CODE && resultCode == RESULT_OK && data != null) {
            // 处理返回的结果
            lon = data.getStringExtra("lon").toString()
            lat = data.getStringExtra("lat").toString()
            address = data.getStringExtra("address").toString()
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
        if (!TextUtils.isEmpty(id)) {

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

        }
        mDatabind.etStoreAddress.setOnClickListener {
//            ARouter.getInstance().build("/app/OrderChangeAddressMapActivity")
//                .navigation(this, REQUEST_CODE)
            var intent = Intent(this, OrderChangeAddressMapActivity::class.java)
            requestDataLauncher.launch(intent)
          //  startActivityForResult(intent,REQUEST_CODE)
        }

    }

    var poiVo = PoiVo()

    @SuppressLint("SuspiciousIndentation")
    override fun createObserver() {
        mViewModel.poidata.onSuccess.observe(this) {
            mViewModel.PoiVoBean.value = it
        }
    }

    override fun isLightMode(): Boolean {
        return false
    }


}