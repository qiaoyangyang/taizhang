package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.facade.annotation.Route
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.databinding.ShareActivityBinding
@Route(path = "/app/ShareActivity")

class ShareActivity : BaseActivity<BaseViewModel, ShareActivityBinding>() {
    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun getBind(layoutInflater: LayoutInflater): ShareActivityBinding {
        return ShareActivityBinding.inflate(layoutInflater)
    }

    override fun initListener() {
    }


}