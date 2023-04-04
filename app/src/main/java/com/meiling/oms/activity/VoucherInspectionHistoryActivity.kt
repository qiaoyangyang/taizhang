package com.meiling.oms.activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.meiling.common.activity.BaseActivity
import com.meiling.common.utils.TextDrawableUtils
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityHistoryBinding
import com.meiling.oms.databinding.ActivityVoucherinspectionBinding
import com.meiling.oms.viewmodel.VoucherInspectionHistoryViewModel
import com.meiling.oms.viewmodel.VoucherinspectionViewModel

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