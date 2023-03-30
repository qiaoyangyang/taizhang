package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.facade.annotation.Route
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.databinding.ActivityModifyInformationBinding

@Route(path = "/app/ModifyInformationActivity")
class ModifyInformationActivity : BaseActivity<BaseViewModel, ActivityModifyInformationBinding>() {
    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityModifyInformationBinding {
        return ActivityModifyInformationBinding.inflate(layoutInflater)
    }


    override fun initListener() {
        mDatabind.aivBack.setOnClickListener { finish() }
    }

}