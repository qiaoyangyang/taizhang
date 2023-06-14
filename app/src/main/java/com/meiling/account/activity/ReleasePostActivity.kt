package com.meiling.account.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.facade.annotation.Route
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.account.databinding.ActivityReleasePostBinding

@Route(path = "/app/ReleasePostActivity")
class ReleasePostActivity : BaseActivity<BaseViewModel, ActivityReleasePostBinding>() {
    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityReleasePostBinding {
        return ActivityReleasePostBinding.inflate(layoutInflater)
    }


    override fun initListener() {
        mDatabind.aivBack.setOnClickListener { finish() }
    }

}