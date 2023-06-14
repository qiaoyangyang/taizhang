package com.meiling.account.activity

import android.app.Activity
import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.widget.TextView
import com.meiling.account.R
import com.umeng.message.UmengNotifyClick
import com.umeng.message.entity.UMessage


class MfrMessageActivity : Activity() {
    private val mNotificationClick: UmengNotifyClick = object : UmengNotifyClick() {
        public override fun onMessage(msg: UMessage) {
            val body = msg.raw.toString()
//            Log.d(TAG, "body: $body")
            if (!TextUtils.isEmpty(body)) {
                runOnUiThread { (findViewById<View>(R.id.tvOrderMsg) as TextView).text = body }
            }
        }
    }

    override fun onCreate(bundle: Bundle?) {
        super.onCreate(bundle)
        setContentView(R.layout.dialog_order_jpush)
        mNotificationClick.onCreate(this, intent)
    }

    override fun onNewIntent(intent: Intent) {
        super.onNewIntent(intent)
        mNotificationClick.onNewIntent(intent)
    }

    companion object {
        private const val TAG = "MfrMessageActivity"
    }
}