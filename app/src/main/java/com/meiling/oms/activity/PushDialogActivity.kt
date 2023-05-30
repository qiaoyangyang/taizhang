package com.meiling.oms.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.GestureDetector
import android.view.GestureDetector.SimpleOnGestureListener
import android.view.Gravity
import android.view.MotionEvent
import android.widget.TextView
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.gyf.immersionbar.ImmersionBar
import com.hjq.bar.TitleBar
import com.hjq.shape.layout.ShapeRelativeLayout
import com.meiling.oms.R
import com.meiling.oms.widget.formatCurrentDate3


//在线消息弹出
@Route(path = "/app/PushDialogActivity")
class PushDialogActivity : AppCompatActivity() {

    @SuppressLint("MissingInflatedId", "ClickableViewAccessibility")
    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(R.layout.dialog_order_jpush)
        val lp = window.attributes
        lp.gravity = Gravity.TOP; // 屏幕左边显示，可设置Gravity其他参数
        lp.y = 80;
        window.attributes = lp // 设置参数给window
        val intentTitle = intent.getStringExtra("pushTitle")
        val intentDetail = intent.getStringExtra("pushDetail")
        val intentOrderId = intent.getStringExtra("pushOrderId")

        findViewById<TextView>(R.id.tvMsgTime).text = formatCurrentDate3()
        findViewById<TextView>(R.id.tvOrderMsg).text = intentTitle
        findViewById<TextView>(R.id.tvMsgShow).text = intentDetail
        var gestureDetector = GestureDetector(this, object : SimpleOnGestureListener() {
                override fun onFling(
                    event1: MotionEvent,
                    event2: MotionEvent,
                    velocityX: Float,
                    velocityY: Float
                ): Boolean {
                    if (event2.y < event1.y) {
                        // 上滑退出
                        onBackPressed()
                        return true
                    }else{
                        ARouter.getInstance().build("/app/Search1Activity")
                            .withString("pushOrderId", intentOrderId).navigation()
                    }
                    return false
                }
            })


        findViewById<ShapeRelativeLayout>(R.id.bg_close).setOnClickListener {
            onBackPressed()
        }

        findViewById<TextView>(R.id.tvMsgShow).setOnClickListener {
            ARouter.getInstance().build("/app/Search1Activity")
                .withString("pushOrderId", intentOrderId).navigation()
            onBackPressed()
        }
        findViewById<ShapeRelativeLayout>(R.id.bg_set).setOnTouchListener { v, event ->
            gestureDetector.onTouchEvent(event)
            true
        }




        ImmersionBar.with(this).init()
        ImmersionBar.setTitleBar(this, findViewById<TitleBar>(R.id.TitleBar))

    }

    override fun onBackPressed() {
        super.onBackPressed()
        overridePendingTransition(R.anim.activity_top_in, R.anim.activity_top_out)
    }

}