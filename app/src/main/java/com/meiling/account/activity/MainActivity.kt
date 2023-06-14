package com.meiling.account.activity

import android.Manifest
import android.content.pm.PackageManager
import android.os.Bundle
import android.os.Handler
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.Toast
import androidx.core.app.ActivityCompat
import androidx.core.content.ContextCompat
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import androidx.lifecycle.ViewModelStoreOwner
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.gyf.immersionbar.ImmersionBar
import com.meiling.common.activity.BaseActivity
import com.meiling.common.utils.MMKVUtils
import com.meiling.account.adapter.BaseFragmentPagerAdapter
import com.meiling.account.databinding.ActivityMainBinding
import com.meiling.account.eventBusData.MessageEvent
import com.meiling.account.eventBusData.MessageEventTabChange
import com.meiling.account.fragment.DataFragment
import com.meiling.account.fragment.HomeNowOrderFragment
import com.meiling.account.fragment.MyFragment
import com.meiling.account.fragment.ScanFragment
import com.meiling.account.viewmodel.MainViewModel
import com.meiling.account.viewmodel.MainViewModel2
import com.meiling.account.widget.UpdateVersion
import com.meiling.account.widget.showToast
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


@Route(path = "/app/MainActivity")
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    companion object {
        var mainActivity: ViewModelStoreOwner? = null
    }

    private val fragmentList: MutableList<Fragment> = ArrayList()

    lateinit var mainViewModel2: MainViewModel2
    private val ACCESS_NOTIFICATION_POLICY = 1

    override fun isStatusBarEnabled(): Boolean {
        return false
    }

    override fun initView(savedInstanceState: Bundle?) {
//        EventBus.getDefault().register(this)
        mainActivity = this
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


//        XXPermissions.with(this).permission(PermissionUtilis.Group.NOTIFICATION)
//            .request(object : OnPermissionCallback {
//                override fun onGranted(permissions: MutableList<String>, allGranted: Boolean) {
//                    if (!allGranted) {
//                        showToast("获取部分权限成功，但部分权限未正常授予")
//                        return
//                    }
//                }
//
//                override fun onDenied(
//                    permissions: MutableList<String>,
//                    doNotAskAgain: Boolean
//                ) {
//                    if (doNotAskAgain) {
//                        // 如果是被永久拒绝就跳转到应用权限系统设置页面
//                        XXPermissions.startPermissionActivity(
//                            this@MainActivity,
//                            permissions
//                        )
//                    } else {
//                        showToast("授权失败，请检查权限")
//                    }
//                }
//            })
        MMKVUtils.putBoolean("isUpdate", true)
    }

    override fun onResume() {
        super.onResume()
        if (MMKVUtils.getBoolean("isUpdate")) {
            UpdateVersion.getUpdateVersion(this, "0")
        }
    }

    override fun initData() {
        mainViewModel2 = ViewModelProvider(
            MainActivity.mainActivity!!,
            ViewModelProvider.NewInstanceFactory()
        ).get(MainViewModel2::class.java)


        fragmentList.add(HomeNowOrderFragment.newInstance())
        fragmentList.add(ScanFragment.newInstance())
        fragmentList.add(DataFragment.newInstance())
        fragmentList.add(MyFragment.newInstance())
        mDatabind.viewPager.adapter =
            BaseFragmentPagerAdapter(supportFragmentManager, lifecycle, fragmentList)
        mDatabind.viewPager.setCurrentItem(0, false)
        mDatabind.viewPager.offscreenPageLimit = 1
        mDatabind.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                ImmersionBar.with(this@MainActivity).statusBarDarkFont(true)
                        .autoDarkModeEnable(true, 0.2f).init()

                if (position == 1) {
                    Log.d("yjk","-----2-------")
//
                } else {
                    Log.d("yjk","-----1-------")
//                    ImmersionBar.with(this@MainActivity).statusBarDarkFont(true)
//                        .autoDarkModeEnable(true, 0.2f).init()

                }

            }

        })
//        mDatabind.aivHome.isSelected = true
//        mDatabind.atvHome.isSelected = true
        mDatabind.aivHomeSelect.visibility = View.VISIBLE
        mDatabind.aivHome.visibility = View.GONE
        mDatabind.atvHome.visibility = View.GONE
        mViewModel.getByTenantId()

    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }


    override fun initListener() {
        mDatabind.llHome.setOnClickListener {

            resetting()
            mDatabind.aivHomeSelect.visibility = View.VISIBLE
            mDatabind.aivHome.visibility = View.GONE
            mDatabind.atvHome.visibility = View.GONE
            mDatabind.aivHome.isSelected = true
            mDatabind.atvHome.isSelected = true
            EventBus.getDefault().post(MessageEventTabChange())
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
        mViewModel.setUmTokenDto.onStart.observe(this) {
        }
        mViewModel.setUmTokenDto.onSuccess.observe(this) {
        }
        mViewModel.setUmTokenDto.onError.observe(this) {
            showToast(it.msg)
        }
        mViewModel.getByTenantId.onSuccess.observe(this) {
            SaveUserBean(it)
            mainViewModel2.getByTenantId.value = it

        }
    }


    private fun resetting() {
        mDatabind.aivHome.visibility = View.VISIBLE
        mDatabind.atvHome.visibility = View.VISIBLE
        mDatabind.aivHomeSelect.visibility = View.GONE
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

    override fun onDestroy() {
        super.onDestroy()
//        mainActivity=null
//            EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
    }

}