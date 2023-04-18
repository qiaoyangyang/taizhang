package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.databinding.ActivitySplashBinding
import com.meiling.oms.databinding.ActivityStoreManagementBinding
import com.meiling.oms.viewmodel.StoreManagementViewModel

//门店管理
class StoreManagementActivity :
    BaseActivity<StoreManagementViewModel, ActivityStoreManagementBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityStoreManagementBinding {
        return ActivityStoreManagementBinding.inflate(layoutInflater)
    }
}