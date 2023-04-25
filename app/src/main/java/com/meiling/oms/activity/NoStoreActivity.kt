package com.meiling.oms.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import androidx.lifecycle.ViewModelProvider
import com.meiling.common.activity.BaseVmDbActivity
import com.meiling.oms.databinding.ActivityNoStoreBinding
import com.meiling.oms.viewmodel.MainViewModel2
import com.meiling.oms.viewmodel.RegisterViewModel

class NoStoreActivity : BaseVmDbActivity<RegisterViewModel,ActivityNoStoreBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun createObserver() {

    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityNoStoreBinding {
        return ActivityNoStoreBinding.inflate(layoutInflater)
    }

    override fun initData() {
        super.initData()
        var mainViewModel=ViewModelProvider(MainActivity.mainActivity!!).get(MainViewModel2::class.java)
//        mainViewModel.getByTenantId.value=mainViewModel.getByTenantId.value?.copy(shop = 1, poi = -1)
        mDatabind.creatStoreBtn.setOnClickListener {
            startActivity(Intent(this,NewlyBuiltStoreActivity::class.java))
            finish()
        }
    }

}
