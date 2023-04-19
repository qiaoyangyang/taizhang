package com.meiling.oms.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.meiling.common.activity.BaseActivity
import com.meiling.common.activity.BaseVmActivity
import com.meiling.oms.bean.PoiVo
import com.meiling.oms.bean.PoiVoBean
import com.meiling.oms.databinding.ActivityNewlyBuiltStoreBinding
import com.meiling.oms.databinding.ActivityStoreManagementBinding
import com.meiling.oms.viewmodel.StoreManagementViewModel

//新建门店
class NewlyBuiltStoreActivity :
    BaseVmActivity<StoreManagementViewModel>() {
    lateinit var mDatabind:ActivityNewlyBuiltStoreBinding
    override fun initView(savedInstanceState: Bundle?) {
    }

//    override fun getBind(layoutInflater: LayoutInflater): ActivityNewlyBuiltStoreBinding {
//        return ActivityNewlyBuiltStoreBinding.inflate(layoutInflater)
//    }
//
//    override fun isStatusBarDarkFont(): Boolean {
//        return true
//    }

    override fun initDataBind() {
        super.initDataBind()
        mDatabind=ActivityNewlyBuiltStoreBinding.inflate(layoutInflater)
        setContentView(mDatabind.root)
    }
    var id = ""
    override fun initData() {
        super.initData()
        mDatabind.viewModel=mViewModel
        id = intent.getStringExtra("id").toString()
        mViewModel.poi(id)
    }

    @SuppressLint("SuspiciousIndentation")
    override fun createObserver() {
        mViewModel.poidata.onSuccess.observe(this) {
        mViewModel.PoiVoBean.value=it
            Log.e("","mViewModel.PoiVoBean.value=="+mViewModel.PoiVoBean.value.toString())
        }
//        mViewModel.PoiVoBean.value= PoiVoBean(poiVo = PoiVo(name = "1234"), test = "6666")
    }

    override fun isLightMode(): Boolean {
        return false
    }

}