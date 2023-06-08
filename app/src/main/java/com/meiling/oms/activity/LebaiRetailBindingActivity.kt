package com.meiling.oms.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import com.meiling.common.GlideApp
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.databinding.ActivityLebaiRetailBindingBinding
import com.meiling.oms.viewmodel.StoreManagementViewModel
import com.meiling.oms.widget.copyText
import com.meiling.oms.widget.showToast

//
class LebaiRetailBindingActivity : BaseActivity<StoreManagementViewModel, ActivityLebaiRetailBindingBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        GlideApp.with(this)
            .load("https://static.igoodsale.com/img_shangou.png")
            .into(mDatabind.ivArrow3)
        mDatabind.ivArrow3.setOnClickListener {
            ImageActivity().start(this,"https://static.igoodsale.com/img_shangou.png")

        }


    }

    override fun initData() {
        super.initData()
       var sinceCode=intent.getStringExtra("sinceCode").toString()

       var url=intent.getStringExtra("url").toString()

        mDatabind.tvShopid.text=sinceCode

        mDatabind.tvOk.setOnClickListener {
            startActivity(Intent(this, BaseWebActivity::class.java).putExtra("url", url))
        }
        mDatabind.tvCopy.setOnClickListener {
            copyText(this,sinceCode )
            showToast("复制成功")
        }
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityLebaiRetailBindingBinding {
        return ActivityLebaiRetailBindingBinding.inflate(layoutInflater)
    }
}