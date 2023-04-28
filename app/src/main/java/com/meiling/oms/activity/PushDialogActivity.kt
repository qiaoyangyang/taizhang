package com.meiling.oms.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.os.Bundle
import android.view.Gravity
import android.view.LayoutInflater
import android.widget.RelativeLayout
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.hjq.shape.layout.ShapeRelativeLayout
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityRechargeSuccessBinding
import com.meiling.oms.databinding.DialogOrderJpushBinding
import com.meiling.oms.widget.formatCurrentDate
import com.meiling.oms.widget.formatCurrentDate3


//在线消息弹出
@Route(path = "/app/PushDialogActivity")
class PushDialogActivity : BaseActivity<BaseViewModel, DialogOrderJpushBinding>() {
    override fun initView(savedInstanceState: Bundle?) {

    }

    @SuppressLint("MissingInflatedId")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_order_jpush)
        val lp = window.attributes
        lp.gravity = Gravity.TOP; // 屏幕左边显示，可设置Gravity其他参数
        window.attributes = lp // 设置参数给window

        val intentTitle = intent.getStringExtra("pushTitle")
        val intentDetail = intent.getStringExtra("pushDetail")
        val intentOrderId = intent.getStringExtra("pushOrderId")

        findViewById<TextView>(R.id.tvMsgTime).text = formatCurrentDate3()
        findViewById<TextView>(R.id.tvOrderMsg).text = intentTitle
        findViewById<TextView>(R.id.tvMsgShow).text = intentDetail

        findViewById<ShapeRelativeLayout>(R.id.bg_close).setOnClickListener {
            finish()
        }
        findViewById<ShapeRelativeLayout>(R.id.bg_set).setOnClickListener {
            ARouter.getInstance().build("/app/Search1Activity")
                .withString("pushOrderId", intentOrderId).navigation()
            finish()
        }

    }

    override fun getBind(layoutInflater: LayoutInflater): DialogOrderJpushBinding {
        return DialogOrderJpushBinding.inflate(layoutInflater)
    }

}