package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.facade.annotation.Route
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.common.utils.Constant
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityRechargeSuccessBinding
import com.meiling.oms.databinding.ShareActivityBinding
import com.meiling.oms.eventBusData.MessageEventPayMoney
import com.meiling.oms.eventBusData.MessageEventVoucherInspectionHistory
import com.meiling.oms.widget.setSingleClickListener
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@Route(path = "/app/RechargeFinishActivity")
class RechargeFinishActivity : BaseActivity<BaseViewModel, ActivityRechargeSuccessBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        EventBus.getDefault().register(this)
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityRechargeSuccessBinding {
        return ActivityRechargeSuccessBinding.inflate(layoutInflater)
    }

    override fun initListener() {
        mDatabind.TitleBar.setLeftIcon(R.drawable.icon_close_x)
        mDatabind.TitleBar.setOnClickListener {
            finish()
        }
        mDatabind.btnAgainRecharge.setSingleClickListener {
            EventBus.getDefault().post(MessageEventPayMoney("0"))
            finish()
        }
        mDatabind.btnPayFinish.setSingleClickListener {
            EventBus.getDefault().post(MessageEventPayMoney("1"))
            finish()
        }
    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEventVoucherInspectionHistory) {

    }
}