package com.meiling.oms.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.FragmentUtils
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityRechargeBinding
import com.meiling.oms.dialog.RechargeSelectTimeDialog
//import com.meiling.oms.dialog.RechargeDialog
import com.meiling.oms.eventBusData.MessageEventTime
import com.meiling.oms.eventBusData.MessageEventTimeShow
import com.meiling.oms.fragment.*
import com.meiling.oms.viewmodel.RechargeViewModel
import com.meiling.oms.widget.*
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * 充值
 * **/
@Route(path = "/app/MyRechargeActivity")
class MyRechargeActivity : BaseActivity<RechargeViewModel, ActivityRechargeBinding>() {
    private val fragmentList: MutableList<Fragment> = ArrayList()

    override fun initView(savedInstanceState: Bundle?) {
        EventBus.getDefault().register(this)
        var type=intent?.getStringExtra("type")
        if(type=="1"){
            var rechargeRecordFragment=RechargeRecordFragment.newInstance()
            FragmentUtils.add(supportFragmentManager,rechargeRecordFragment,R.id.view_pager)
            mDatabind.TitleBar.title = "充值记录"
        }else{
            var rechargeSettlementFragment=RechargeSettlementFragment.newInstance()
            FragmentUtils.add(supportFragmentManager,rechargeSettlementFragment,R.id.view_pager)
            mDatabind.TitleBar.title = "财务结算"
        }
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityRechargeBinding {
        return ActivityRechargeBinding.inflate(layoutInflater)
    }

    override fun initListener() {


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
    }

    @SuppressLint("SetTextI18n")
    override fun createObserver() {

//        mViewModel.rechargeDto.onSuccess.observe(this) {
//            disLoading()
//            val jsonObject = JSONObject(it)
//            var from = jsonObject.get("form")
//            PayUtils.aliPay(this,
//                from.toString(),
//                object : Observer<AliPayResp> {
//                    override fun onSubscribe(d: Disposable) {
//                    }
//
//                    override fun onError(e: Throwable) {
//                    }
//
//                    override fun onComplete() {
//                    }
//
//                    override fun onNext(t: AliPayResp) {
////                        if (t.isSuccess) {
//                        ARouter.getInstance().build("/app/RechargeFinishActivity")
//                            .navigation()
////                        } else {
//////                            showToast(t.message)
////                            ARouter.getInstance().build("/app/RechargeFinishActivity")
////                                .navigation()
////                        }
//                    }
//
//                }
//            )
//        }

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