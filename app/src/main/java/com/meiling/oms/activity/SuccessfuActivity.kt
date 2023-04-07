package com.meiling.oms.activity

import androidx.appcompat.app.AppCompatActivity
import android.os.Bundle
import android.view.LayoutInflater
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivitySuccessfuBinding
import com.meiling.oms.viewmodel.VoucherinspectionViewModel

class SuccessfuActivity : BaseActivity<VoucherinspectionViewModel, ActivitySuccessfuBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivitySuccessfuBinding {
        return ActivitySuccessfuBinding.inflate(layoutInflater)
    }
}