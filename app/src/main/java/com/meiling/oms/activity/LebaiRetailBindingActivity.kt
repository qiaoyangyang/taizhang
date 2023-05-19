package com.meiling.oms.activity

import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import com.meiling.common.GlideApp
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.databinding.ActivityLebaiRetailBindingBinding
import com.meiling.oms.viewmodel.StoreManagementViewModel
import com.meiling.oms.widget.showToast

//
class LebaiRetailBindingActivity : BaseActivity<StoreManagementViewModel, ActivityLebaiRetailBindingBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        GlideApp.with(this)
            .load("https://static.igoodsale.com/elem-write.jpeg")
            .into(mDatabind.ivArrow)
        mDatabind.ivArrow.setOnClickListener {
            ImageActivity().start(this,"https://static.igoodsale.com/elem-write.jpeg")

        }
        mDatabind.tvOk.setOnClickListener {
            if (TextUtils.isEmpty(mDatabind.etBindTenant.text.toString())){
                showToast("请输入饿了么零售中应用的APP ID")
                return@setOnClickListener

            }
            if (TextUtils.isEmpty(mDatabind.etSecret.text.toString())){
                showToast("请输入饿了么零售中应用的APP Secret")
                return@setOnClickListener

            }

        }
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityLebaiRetailBindingBinding {
        return ActivityLebaiRetailBindingBinding.inflate(layoutInflater)
    }
}