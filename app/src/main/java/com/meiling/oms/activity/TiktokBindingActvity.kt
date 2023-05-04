package com.meiling.oms.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import com.meiling.common.activity.BaseActivity
import com.meiling.common.utils.SpannableUtils
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityStoreManagementBinding
import com.meiling.oms.databinding.ActivityTiktoKnindingBinding
import com.meiling.oms.viewmodel.StoreManagementViewModel
import com.meiling.oms.widget.setSingleClickListener

class TiktokBindingActvity :
    BaseActivity<StoreManagementViewModel, ActivityTiktoKnindingBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        SpannableUtils.setTiktokBindingTextcolor(
            this,
            getString(R.string.first_step),
            mDatabind.tvFirstStep,
            getString(R.string.first_step).length - 2,
            getString(R.string.first_step).length,
            R.color.pwd_1180FF, 0
        )
        SpannableUtils.setTiktokBindingTextcolor(
            this,
            getString(R.string.service),
            mDatabind.tvService,
            6,
            10,
            R.color.pwd_1180FF, 1
        )
        mDatabind.tvOk.setOnClickListener {
            startActivity(
                Intent(this, BindingTiktokListActivity::class.java).putExtra(
                    "channelId",
                    channelId
                )
                    .putExtra("poiId", poiId)
            )
        }
    }

    var channelId: String = ""
    var poiId: String = ""

    override fun initData() {
        channelId = intent.getStringExtra("channelId").toString()
        poiId = intent.getStringExtra("poiId").toString()


    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityTiktoKnindingBinding {
        return ActivityTiktoKnindingBinding.inflate(layoutInflater)
    }
}