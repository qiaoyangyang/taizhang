package com.meiling.oms.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.angcyo.tablayout.delegate2.ViewPager2Delegate
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.RechargeRequest
import com.meiling.oms.R
import com.meiling.oms.adapter.BaseFragmentPagerAdapter
import com.meiling.oms.databinding.ActivityRechargeBinding
import com.meiling.oms.dialog.RechargeSelectTimeDialog
import com.meiling.oms.dialog.RechargeDialog
import com.meiling.oms.eventBusData.MessageEventPayMoney
import com.meiling.oms.eventBusData.MessageEventTime
import com.meiling.oms.eventBusData.MessageEventTimeShow
import com.meiling.oms.fragment.*
import com.meiling.oms.pay.AliPayResp
import com.meiling.oms.pay.PayUtils
import com.meiling.oms.viewmodel.RechargeViewModel
import com.meiling.oms.widget.*
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode
import org.json.JSONObject


/**
 * 充值
 * **/
@Route(path = "/app/MyRechargeActivity")
class MyRechargeActivity : BaseActivity<RechargeViewModel, ActivityRechargeBinding>() {
    private val fragmentList: MutableList<Fragment> = ArrayList()

    override fun initView(savedInstanceState: Bundle?) {
        EventBus.getDefault().register(this)
        mDatabind.viewPager.isUserInputEnabled = false
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

        mDatabind.btnRecharge.setSingleClickListener {
            startActivity(Intent(this, MyRechargeToPayActivity::class.java))

//            var rechargeDialog = RechargeDialog().newInstance()
//            rechargeDialog.setOkClickLister { money, channel ->
//                mViewModel.rechargeRequest(RechargeRequest(money, "3", channel, ""))
//            }
//            rechargeDialog.show(supportFragmentManager)
        }

        mDatabind.radioGroup.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.radio_button1 -> {
                    EventBus.getDefault().post(
                        MessageEventTime(
                            formatCurrentDateBeforeWeek(),
                            formatCurrentDate()
                        )
                    );
                }
                R.id.radio_button2 -> {
                    EventBus.getDefault().post(
                        MessageEventTime(
                            formatCurrentDateBeforeMouth(),
                            formatCurrentDate()
                        )
                    );
                }
                R.id.radio_button3 -> {
                    EventBus.getDefault()
                        .post(MessageEventTime(formatCurrentDateBefore90(), formatCurrentDate()));
                }
                R.id.radio_button4 -> {

                }
            }
        }

        mDatabind.radioButton4.setSingleClickListener {
            var orderDisSelectTimeDialog = RechargeSelectTimeDialog().newInstance()
            orderDisSelectTimeDialog.show(supportFragmentManager)
            orderDisSelectTimeDialog.setSelectTime { startTime, endTime, type ->
                EventBus.getDefault()
                    .post(
                        MessageEventTime(
                            startTime,
                            endTime
                        )
                    )
                if (type == "1") {
                    mDatabind.radioButton1.isChecked = false
                }
            }
        }
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getBalance()
    }

    @SuppressLint("SetTextI18n")
    override fun createObserver() {
        mViewModel.rechargeDto.onStart.observe(this) {
            showLoading("加载中")
        }
        mViewModel.rechargeDto.onSuccess.observe(this) {
            disLoading()
            val jsonObject = JSONObject(it)
            var from = jsonObject.get("form")
            PayUtils.aliPay(this,
                from.toString(),
                object : Observer<AliPayResp> {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                    }

                    override fun onComplete() {
                    }

                    override fun onNext(t: AliPayResp) {
//                        if (t.isSuccess) {
                        ARouter.getInstance().build("/app/RechargeFinishActivity")
                            .navigation()
//                        } else {
////                            showToast(t.message)
//                            ARouter.getInstance().build("/app/RechargeFinishActivity")
//                                .navigation()
//                        }
                    }

                }
            )
        }
        mViewModel.rechargeDto.onError.observe(this) {
            disLoading()
        }
        mViewModel.balance.onSuccess.observe(this) {
            mDatabind.txtBalance.text = it.payAmount
            mDatabind.txtServiceFee.text = it.unitPrice
            mDatabind.txtFreezeAmount.text = "已冻结 ${it.freezeAmount}"
        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun eventDay(messageEventTime: MessageEventTimeShow) {
        mDatabind.radioButton1.isChecked = true
    }

//    @Subscribe(threadMode = ThreadMode.MAIN)
//    fun payMoney(messType: MessageEventPayMoney) {
//        if (messType.type == "1") {//完成支付
//           finish()
//        }
//
////        else {
////            var rechargeDialog = RechargeDialog().newInstance()
////            rechargeDialog.setOkClickLister { money, channel ->
//////                mViewModel.rechargeRequest(RechargeRequest(money, "3", channel, ""))
////            }
////            rechargeDialog.show(supportFragmentManager)
////        }
//    }
}