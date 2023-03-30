package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.facade.annotation.Route
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.databinding.ActivityGiveBinding

@Route(path = "/app/WriteOffActivity")
class WriteOffActivity : BaseActivity<BaseViewModel, ActivityGiveBinding>() {


    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityGiveBinding {
        return ActivityGiveBinding.inflate(layoutInflater)
    }


    override fun initListener() {
//        mDatabind.aivImg.setOnClickListener { finish() }
    }


}