package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.launcher.ARouter
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.databinding.ActivityOrderZitiDetailBinding
import com.meiling.oms.databinding.ActivityPostDetailBinding
import com.meiling.oms.viewmodel.OrderCreateViewModel

//自提订单详情
class OrderDetail1Activity : BaseActivity<OrderCreateViewModel, ActivityOrderZitiDetailBinding>() {


    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityOrderZitiDetailBinding {
        return ActivityOrderZitiDetailBinding.inflate(layoutInflater)
    }


    private var b = true

    override fun initListener() {

    }

}