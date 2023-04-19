package com.meiling.oms.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.MutableLiveData
import com.meiling.common.activity.BaseActivity
import com.meiling.common.activity.BaseVmActivity
import com.meiling.common.network.service.loginService
import com.meiling.oms.bean.PoiVo
import com.meiling.oms.bean.PoiVoBean
import com.meiling.oms.databinding.ActivityNewlyBuiltStoreBinding
import com.meiling.oms.databinding.ActivityStoreManagementBinding
import com.meiling.oms.service.branchInformationService
import com.meiling.oms.viewmodel.StoreManagementViewModel
import kotlinx.coroutines.*

//新建门店
class NewlyBuiltStoreActivity :
    BaseVmActivity<StoreManagementViewModel>() {
    lateinit var mDatabind: ActivityNewlyBuiltStoreBinding
    override fun initView(savedInstanceState: Bundle?) {
    }

//    override fun getBind(layoutInflater: LayoutInflater): ActivityNewlyBuiltStoreBinding {
//        return ActivityNewlyBuiltStoreBinding.inflate(layoutInflater)
//    }
//
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