package com.meiling.oms.activity

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.widget.Toast
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.adapter.BaseFragmentPagerAdapter
import com.meiling.oms.databinding.ActivityMainBinding
import com.meiling.oms.fragment.DataFragment
import com.meiling.oms.fragment.HomeFragment
import com.meiling.oms.fragment.MyFragment
import com.meiling.oms.fragment.ScanFragment
import com.meiling.oms.viewmodel.MainViewModel


@Suppress("DEPRECATION")
@Route(path = "/app/MainActivity")
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {


    private val fragmentList: MutableList<Fragment> = ArrayList()

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.viewPager.isUserInputEnabled = false

    }

    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)

    }

    override fun initData() {
        fragmentList.add(HomeFragment.newInstance())
        fragmentList.add(ScanFragment.newInstance())
        fragmentList.add(DataFragment.newInstance())
        fragmentList.add(MyFragment.newInstance())
        mDatabind.viewPager.adapter =
            BaseFragmentPagerAdapter(supportFragmentManager, lifecycle, fragmentList)
        mDatabind.viewPager.setCurrentItem(0, false)
        mDatabind.aivHome.isSelected = true
        mDatabind.atvHome.isSelected = true
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }


    override fun initListener() {
        mDatabind.llHome.setOnClickListener {
            resetting()
            mDatabind.aivHome.isSelected = true
            mDatabind.atvHome.isSelected = true
            mDatabind.viewPager.setCurrentItem(0, false)
        }
        mDatabind.llScan.setOnClickListener {
            resetting()
            mDatabind.aivFinds.isSelected = true
            mDatabind.atvFinds.isSelected = true
            mDatabind.viewPager.setCurrentItem(1, false)
        }
        mDatabind.llData.setOnClickListener {
            resetting()
            mDatabind.atvMessage.isSelected = true
            mDatabind.aivMessage.isSelected = true
            mDatabind.viewPager.setCurrentItem(2, false)
        }
        mDatabind.llMy.setOnClickListener {
            resetting()
            mDatabind.aivMy.isSelected = true
            mDatabind.atvMy.isSelected = true
            mDatabind.viewPager.setCurrentItem(3, false)
        }
    }


    private fun resetting() {
        mDatabind.aivHome.isSelected = false
        mDatabind.atvHome.isSelected = false
        mDatabind.aivFinds.isSelected = false
        mDatabind.atvFinds.isSelected = false
        mDatabind.atvMessage.isSelected = false
        mDatabind.aivMessage.isSelected = false
        mDatabind.aivMy.isSelected = false
        mDatabind.atvMy.isSelected = false
    }


    private var doubleBackToExitPressedOnce = false

    override fun onBackPressed() {
        if (doubleBackToExitPressedOnce) {
            super.onBackPressed()
            return
        }
        doubleBackToExitPressedOnce = true
        Toast.makeText(this, "再按一次返回键退出应用", Toast.LENGTH_SHORT).show()
        Handler().postDelayed(Runnable { doubleBackToExitPressedOnce = false }, 2000)
    }


}