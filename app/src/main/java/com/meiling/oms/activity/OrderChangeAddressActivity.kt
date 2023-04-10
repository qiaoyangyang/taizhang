package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.databinding.ActivityOrderChengeAddredssBinding
import com.meiling.oms.widget.setSingleClickListener

/**
 * 此改地址
 * **/
@Route(path = "/app/OrderChangeAddressActivity")
class OrderChangeAddressActivity : BaseActivity<BaseViewModel, ActivityOrderChengeAddredssBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        setBar(this,mDatabind.cosChangeOrder)
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityOrderChengeAddredssBinding {
        return ActivityOrderChengeAddredssBinding.inflate(layoutInflater)
    }


    private var type = true

    override fun initListener() {
        mDatabind.aivBack.setOnClickListener { finish() }

        mDatabind.txtOrderChangeAddress.setSingleClickListener {
            ARouter.getInstance().build("/app/OrderChangeAddressMapActivity").navigation()
        }
    }

}