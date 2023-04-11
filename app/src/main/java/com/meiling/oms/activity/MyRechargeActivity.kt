package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.RadioGroup
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.angcyo.tablayout.delegate2.ViewPager2Delegate
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.RechargeRequest
import com.meiling.oms.EventBusData.MessageEvent
import com.meiling.oms.EventBusData.MessageEventTime
import com.meiling.oms.R
import com.meiling.oms.adapter.BaseFragmentPagerAdapter
import com.meiling.oms.databinding.ActivityRechargeBinding
import com.meiling.oms.dialog.RechargeDialog
import com.meiling.oms.fragment.*
import com.meiling.oms.pay.AliPayResp
import com.meiling.oms.pay.PayUtils
import com.meiling.oms.viewmodel.RechargeViewModel
import com.meiling.oms.widget.*
import io.reactivex.rxjava3.core.Observer
import org.greenrobot.eventbus.EventBus

/**
 * 充值
 * **/
@Route(path = "/app/MyRechargeActivity")
class MyRechargeActivity : BaseActivity<RechargeViewModel, ActivityRechargeBinding>() {
    private val fragmentList: MutableList<Fragment> = ArrayList()

    override fun initView(savedInstanceState: Bundle?) {
        EventBus.getDefault().register(this)
        mDatabind.viewPager.isUserInputEnabled = false
        setBar(this, mDatabind.cosTitle)
        fragmentList.add(RechargeSettlementFragment.newInstance())
        fragmentList.add(RechargeRecordFragment.newInstance())
        mDatabind.viewPager.setCurrentItem(0, false)
        mDatabind.viewPager.adapter =
            BaseFragmentPagerAdapter(supportFragmentManager, lifecycle, fragmentList)
        ViewPager2Delegate.install(mDatabind.viewPager, mDatabind.tabLayout)
        mViewModel.getBalance()
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityRechargeBinding {
        return ActivityRechargeBinding.inflate(layoutInflater)
    }

    override fun initListener() {
        mDatabind.imgRechargeBack.setOnClickListener { finish() }

        mDatabind.btnRecharge.setSingleClickListener {
            var rechargeDialog = RechargeDialog().newInstance()
            rechargeDialog.setOkClickLister { money, channel ->
                mViewModel.rechargeRequest(RechargeRequest(money, "3", channel, ""))
//                ARouter.getInstance().build("/app/RechargeFinishActivity").navigation()
            }
            rechargeDialog.show(supportFragmentManager)
        }

        mDatabind.radioGroup.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.radio_button1 -> {
                        EventBus.getDefault().post(MessageEventTime(formatCurrentDateBeforeWeek()));
                    }
                    R.id.radio_button2 -> {
                        EventBus.getDefault()
                            .post(MessageEventTime(formatCurrentDateBeforeMouth()));
                    }
                    R.id.radio_button3 -> {
                        EventBus.getDefault().post(MessageEventTime(formatCurrentDateBefore90()));
                    }
                    R.id.radio_button4 -> {
                        EventBus.getDefault().post(MessageEventTime(formatCurrentDateBefore90()));
                    }
                }
            }
    }

    override fun createObserver() {
        mViewModel.rechargeDto.onStart.observe(this) {}
        mViewModel.rechargeDto.onSuccess.observe(this) {
//            PayUtils.aliPay(this,
//                it,
//            )
        }
        mViewModel.rechargeDto.onError.observe(this) {}
        mViewModel.balance.onSuccess.observe(this) {
            mDatabind.txtBalance.text = it.unitPrice
            mDatabind.txtServiceFee.text = it.availableAmount
        }
    }

}