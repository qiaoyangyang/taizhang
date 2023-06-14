package com.meiling.account.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.lifecycle.ViewModelProvider
import com.meiling.common.activity.BaseVmDbActivity
import com.meiling.account.databinding.ActivityNoStoreBinding
import com.meiling.account.viewmodel.MainViewModel2
import com.meiling.account.viewmodel.RegisterViewModel

class NoStoreActivity : BaseVmDbActivity<RegisterViewModel,ActivityNoStoreBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun createObserver() {

    }

    override fun onLeftClick(view: View) {
        super.onLeftClick(view)
        finish()
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
