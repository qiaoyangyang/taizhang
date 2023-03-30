package com.meiling.oms.activity

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.MyFlipOver
import com.meiling.oms.adapter.BaseFragmentPagerAdapter
import com.meiling.oms.databinding.ActivityTestBinding
import com.meiling.oms.fragment.OrderDisFragment1
import com.meiling.oms.fragment.DataFragment
import com.meiling.oms.viewmodel.TestViewModel

class TestActivity : BaseActivity<TestViewModel, ActivityTestBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        val fragments: MutableList<Fragment> = arrayListOf()
        fragments.add(
            OrderDisFragment1.newInstance()
        )
        fragments.add(
            DataFragment.newInstance()
        )
        mDatabind.ViewPager2.isUserInputEnabled = false
        mDatabind.ViewPager2.offscreenPageLimit = 1
        mDatabind.ViewPager2.adapter =
            BaseFragmentPagerAdapter(supportFragmentManager, lifecycle, fragments)
        mDatabind.MyFlipOver.setBgView(mDatabind.ViewPager2, object : MyFlipOver.OnPageListener {
            override fun OnPreviousPage() {
                mDatabind.MyFlipOver.clear()
                Log.e("上一页", "/*************")
                mDatabind.ViewPager2.setCurrentItem(0, false)
            }

            override fun OnNextPage() {
                mDatabind.MyFlipOver.clear()
                Log.e("上一页", "/000000000000")
                mDatabind.ViewPager2.setCurrentItem(1, false)
            }
        })

    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityTestBinding {
        return ActivityTestBinding.inflate(layoutInflater)

    }


}