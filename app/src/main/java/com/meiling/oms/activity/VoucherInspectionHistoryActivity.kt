package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.databinding.ActivityHistoryBinding
import com.meiling.oms.viewmodel.VoucherInspectionHistoryViewModel

//验券历史
class VoucherInspectionHistoryActivity :
    BaseActivity<VoucherInspectionHistoryViewModel, ActivityHistoryBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityHistoryBinding {
        return ActivityHistoryBinding.inflate(layoutInflater)
    }

    override fun initData() {
        super.initData()

    }

    override fun isStatusBarDarkFont(): Boolean {
        return true
    }
}