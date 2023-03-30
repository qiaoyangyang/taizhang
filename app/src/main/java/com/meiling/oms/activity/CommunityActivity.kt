package com.meiling.oms.activity

import android.os.Build
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.annotation.RequiresApi
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.google.android.material.appbar.AppBarLayout
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.AppBarStateChangeListener
import com.meiling.oms.R
import com.meiling.oms.adapter.BaseFragmentPagerAdapter
import com.meiling.oms.databinding.ActivityCommunityBinding
import com.meiling.oms.fragment.CommunityFragment
import com.meiling.oms.viewmodel.CommunityViewModel


@Route(path = "/app/CommunityActivity")
class CommunityActivity : BaseActivity<CommunityViewModel, ActivityCommunityBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        val fragments: MutableList<Fragment> = arrayListOf()
        fragments.add(
            CommunityFragment.newInstance()
        )

    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityCommunityBinding {
        return ActivityCommunityBinding.inflate(layoutInflater)
    }


    override fun initListener() {
    }

}