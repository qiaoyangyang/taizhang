package com.meiling.oms.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.meiling.common.activity.BaseActivity
import com.meiling.common.utils.MMKVUtils
import com.meiling.oms.adapter.BaseFragmentPagerAdapter
import com.meiling.oms.databinding.ActivityMainBinding
import com.meiling.oms.fragment.DataFragment
import com.meiling.oms.fragment.HomeFragment
import com.meiling.oms.fragment.MyFragment
import com.meiling.oms.fragment.ScanFragment
import com.meiling.oms.viewmodel.MainViewModel
import com.meiling.oms.widget.showToast


@Route(path = "/app/MainActivity")
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {


    private val fragmentList: MutableList<Fragment> = ArrayList()

    private val ACCESS_NOTIFICATION_POLICY = 1
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.viewPager.isUserInputEnabled = false
        mViewModel.setUmToken()
        if (ContextCompat.checkSelfPermission(
                this,
                Manifest.permission.ACCESS_NOTIFICATION_POLICY
            ) == PackageManager.PERMISSION_DENIED
        ) {
            // 如果没有权限，申请权限
            ActivityCompat.requestPermissions(
                this,
                arrayOf(Manifest.permission.ACCESS_NOTIFICATION_POLICY),
                ACCESS_NOTIFICATION_POLICY
            )
        }
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

    override fun createObserver() {
        mViewModel.setUmTokenDto.onSuccess.observe(this) {

        }
        mViewModel.setUmTokenDto.onError.observe(this) {
            showToast(it.msg)
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

    override fun onRequestPermissionsResult(
        requestCode: Int,
        permissions: Array<String>,
        grantResults: IntArray
    ) {
        super.onRequestPermissionsResult(requestCode, permissions, grantResults)
        if (requestCode == ACCESS_NOTIFICATION_POLICY) {
            if (grantResults.isNotEmpty() && grantResults[0] == PackageManager.PERMISSION_DENIED) {
//                showToast("您拒绝了通知权限")
            }

        }
    }

}