package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.facade.annotation.Route
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityRechargeSuccessBinding
import com.meiling.oms.databinding.ShareActivityBinding
import com.meiling.oms.widget.setSingleClickListener

@Route(path = "/app/RechargeFinishActivity")

class RechargeFinishActivity : BaseActivity<BaseViewModel, ActivityRechargeSuccessBinding>() {
    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityRechargeSuccessBinding {
        return ActivityRechargeSuccessBinding.inflate(layoutInflater)
    }

    override fun initListener() {
        mDatabind.TitleBar.setLeftIcon(R.drawable.search_close_edt)
        mDatabind.TitleBar.setOnClickListener {
            finish()
        }
        mDatabind.btnAgainRecharge.setSingleClickListener {
            finish()
        }
        mDatabind.btnPayFinish.setSingleClickListener {
            finish()
        }
    }


}