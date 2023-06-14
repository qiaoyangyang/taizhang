package com.meiling.account.activity

import android.os.Bundle
import android.os.Handler
import android.view.LayoutInflater
import android.widget.Toast
import androidx.lifecycle.ViewModelStoreOwner
import com.alibaba.android.arouter.facade.annotation.Route
import com.meiling.account.databinding.ActivityMainBinding
import com.meiling.account.eventBusData.MessageEvent
import com.meiling.account.viewmodel.MainViewModel
import com.meiling.common.activity.BaseActivity
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


@Route(path = "/app/MainActivity")
class MainActivity : BaseActivity<MainViewModel, ActivityMainBinding>() {
    companion object {
        var mainActivity: ViewModelStoreOwner? = null
    }



    override fun isStatusBarEnabled(): Boolean {
        return false
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun onResume() {
        super.onResume()

    }

    override fun initData() {


    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityMainBinding {
        return ActivityMainBinding.inflate(layoutInflater)
    }


    override fun initListener() {

    }

    override fun createObserver() {

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


    override fun onDestroy() {
        super.onDestroy()
//        mainActivity=null
//            EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
    }

}