package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.meiling.common.activity.BaseActivity
import com.meiling.common.utils.SpannableUtils
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityStoreManagementBinding
import com.meiling.oms.databinding.ActivityTiktoKnindingBinding
import com.meiling.oms.viewmodel.StoreManagementViewModel

class TiktokBindingActvity :
    BaseActivity<StoreManagementViewModel, ActivityTiktoKnindingBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        SpannableUtils.setTiktokBindingTextcolor(
            this,
            getString(R.string.first_step),
            mDatabind.tvFirstStep,
            getString(R.string.first_step).length - 2,
            getString(R.string.first_step).length,
            R.color.pwd_1180FF,0
        )
        SpannableUtils.setTiktokBindingTextcolor(
            this,
            getString(R.string.service),
            mDatabind.tvService,
            6,
            10,
            R.color.pwd_1180FF,1
        )
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityTiktoKnindingBinding {
        return ActivityTiktoKnindingBinding.inflate(layoutInflater)
    }
}