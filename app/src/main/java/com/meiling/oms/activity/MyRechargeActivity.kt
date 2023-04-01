package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.angcyo.tablayout.delegate2.ViewPager2Delegate
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.adapter.BaseFragmentPagerAdapter
import com.meiling.oms.databinding.ActivityRechargeBinding
import com.meiling.oms.dialog.RechargeDialog
import com.meiling.oms.fragment.*
import com.meiling.oms.widget.setSingleClickListener

/**
 * 充值
 * **/
@Route(path = "/app/MyRechargeActivity")
class MyRechargeActivity : BaseActivity<BaseViewModel, ActivityRechargeBinding>() {
    private val fragmentList: MutableList<Fragment> = ArrayList()

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.viewPager.isUserInputEnabled = false
        setBar(this, mDatabind.cosTitle)
        fragmentList.add(RechargeSettlementFragment.newInstance())
        fragmentList.add(RechargeRecordFragment.newInstance())
        mDatabind.viewPager.setCurrentItem(0, false)
        mDatabind.viewPager.adapter =
            BaseFragmentPagerAdapter(supportFragmentManager, lifecycle, fragmentList)
        ViewPager2Delegate.install(mDatabind.viewPager, mDatabind.tabLayout)
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityRechargeBinding {
        return ActivityRechargeBinding.inflate(layoutInflater)
    }

    override fun initListener() {
        mDatabind.imgRechargeBack.setOnClickListener { finish() }

        mDatabind.btnRecharge.setSingleClickListener {
            var rechargeDialog = RechargeDialog().newInstance()
            rechargeDialog.setOkClickLister {
                ARouter.getInstance().build("/app/RechargeFinishActivity").navigation()
            }
            rechargeDialog.show(supportFragmentManager)
        }
    }

}