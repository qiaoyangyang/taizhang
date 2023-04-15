package com.meiling.oms.jpush

import android.app.NotificationManager
import android.app.PendingIntent
import android.content.BroadcastReceiver
import android.content.Context
import android.content.Intent
import androidx.core.app.NotificationCompat
import com.meiling.oms.R
import com.meiling.oms.activity.MainActivity

// 在广播接收器或者服务中处理友盟推送的自定义消息
class MyPushReceiver : BroadcastReceiver() {
    override fun onReceive(context: Context, intent: Intent) {
        // 获取友盟推送的自定义消息内容
        val title = intent.getStringExtra("title")
        val content = intent.getStringExtra("content")

        // 创建通知
        val notificationManager =
            context.getSystemService(Context.NOTIFICATION_SERVICE) as NotificationManager
        val builder = NotificationCompat.Builder(context, "channelId")
            .setSmallIcon(R.mipmap.logo)
            .setContentTitle(title)
            .setContentText(content)
            .setPriority(NotificationCompat.PRIORITY_DEFAULT)
            .setAutoCancel(true)

        // 点击通知时的跳转动作
        val clickIntent = Intent(context, MainActivity::class.java)
        val pendingIntent =
            PendingIntent.getActivity(context, 0, clickIntent, PendingIntent.FLAG_UPDATE_CURRENT)
        builder.setContentIntent(pendingIntent)

        // 显示通知
        notificationManager.notify(0, builder.build())
    }
}
