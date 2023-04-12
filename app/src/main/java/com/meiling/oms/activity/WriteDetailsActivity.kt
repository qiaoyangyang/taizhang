package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.facade.annotation.Route
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.databinding.ActivityWriteDetailsBinding
import com.meiling.oms.viewmodel.VoucherInspectionHistoryViewModel

//核销详情
@Route(path = "/app/WriteDetailsActivity")
class WriteDetailsActivity :
    BaseActivity<VoucherInspectionHistoryViewModel, ActivityWriteDetailsBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.tvOk.setOnClickListener {
            finish()
        }

    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityWriteDetailsBinding {
        return ActivityWriteDetailsBinding.inflate(layoutInflater)
    }


    override fun isStatusBarDarkFont(): Boolean {
        return true
    }

}