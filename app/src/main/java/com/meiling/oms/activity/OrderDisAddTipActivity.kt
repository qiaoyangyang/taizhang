package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.facade.annotation.Route
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.databinding.ActivityOrderDisTipBinding

@Route(path = "/app/OrderDisAddTipActivity")
class OrderDisAddTipActivity : BaseActivity<BaseViewModel, ActivityOrderDisTipBinding>() {


    override fun initView(savedInstanceState: Bundle?) {
      setBar(this,mDatabind.txtOrderDisAddTip)
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityOrderDisTipBinding {
        return ActivityOrderDisTipBinding.inflate(layoutInflater)
    }


    override fun initListener() {
        mDatabind.inSendDisBack.setOnClickListener { finish() }
    }

}