package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.facade.annotation.Route
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.databinding.ActivityModifyPasswordBinding

@Route(path = "/app/ModifyPassWordActivity")
class ModifyPassWordActivity : BaseActivity<BaseViewModel, ActivityModifyPasswordBinding>() {
    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityModifyPasswordBinding {
        return ActivityModifyPasswordBinding.inflate(layoutInflater)
    }


    override fun initListener() {
//        mDatabind.aivBack.setOnClickListener { finish() }
    }

}