package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.meiling.common.activity.BaseActivity
import com.meiling.common.utils.TextDrawableUtils
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityChannelBinding
import com.meiling.oms.viewmodel.StoreManagementViewModel

//渠道店铺管理
class ChannelActivity:  BaseActivity<StoreManagementViewModel, ActivityChannelBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        TextDrawableUtils.setRightDrawable(mDatabind.TitleBar.titleView, R.drawable.xia1)
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityChannelBinding {
        return ActivityChannelBinding.inflate(layoutInflater)
    }

    override fun onRightClick(view: View) {
        super.onRightClick(view)

    }
    override fun isStatusBarDarkFont(): Boolean {
        return true
    }

}