package com.meiling.oms.jpush

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.util.Log
import android.view.View
import androidx.annotation.RequiresApi
import androidx.appcompat.app.AppCompatActivity
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils
import com.google.gson.Gson
import com.meiling.common.network.data.PushMsgJson
import com.meiling.oms.R
import com.meiling.oms.activity.MainActivity
import com.meiling.oms.dialog.OrderDialog
import com.meiling.oms.jpush.jpushPlay.MessageData
import com.meiling.oms.jpush.jpushPlay.MessageManagement
import com.umeng.message.entity.UMessage
import java.util.*


var count = 0
var xsize = 0
var ysize = 60
var listView = ArrayList<View>()

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("SetTextI18n", "RtlHardcoded", "InflateParams", "WrongConstant")
fun createAndStart(context: Context, uMessage: UMessage, x: Int, y: Int) {
    try {
        val pushCode = Gson().fromJson(
            uMessage.custom,
            PushMsgJson::class.java
        ).pushCode
//    新订单  1 取消   3 退款    7 超时提醒   11 骑手接单   301 配送取消   302 配送完成   303
        when (pushCode) {
            "1" -> {
                MessageManagement.get(context)?.addMessage(
                    MessageData(
                        R.raw.new_order,
                        ""
                    )
                )
            }
            "3" -> {
                MessageManagement.get(context)?.addMessage(
                    MessageData(
                        R.raw.cancel_order,
                        ""
                    )
                )
            }
            "7" -> {
                MessageManagement.get(context)?.addMessage(
                    MessageData(
                        R.raw.refund_order,
                        ""
                    )
                )
            }
            "11" -> {
                MessageManagement.get(context)?.addMessage(
                    MessageData(
                        R.raw.order_yuding,
                        ""
                    )
                )
            }
            "301" -> {
                MessageManagement.get(context)?.addMessage(
                    MessageData(
                        R.raw.order_dis_jiedan,
                        ""
                    )
                )
            }

            "303" -> {
                MessageManagement.get(context)?.addMessage(
                    MessageData(
                        R.raw.order_dis_finish,
                        ""
                    )
                )
            }
            "302" -> {
                MessageManagement.get(context)?.addMessage(
                    MessageData(
                        R.raw.order_dis_cancel,
                        ""
                    )
                )
            }
        }
//        val childFragmentManager = OrderDialog().newInstance()
//        childFragmentManager.show((AppConfig.getActivity() as AppCompatActivity).supportFragmentManager)
        ARouter.getInstance().build("/app/PushDialogActivity")
            .withString("pushTitle", uMessage.title.toString() ?: "小喵来客")
            .withString("pushDetail", uMessage.text.toString() ?: "小喵来客")
            .withString("pushOrderId", uMessage.extra["orderViewId"].toString() ?: null)
            .navigation()
        Log.i("lwq", "uMessage" + uMessage.extra.get("orderViewId"))
        Log.i("lwq", "uMessage" + "${uMessage.text.toString()}" + context)
//        ToastUtils.showShort("新消息${context}")
    } catch (e: Exception) {

        ToastUtils.showShort("推送消息格式异常")
    }

}

private fun dp2px(dp: Int): Int {
    return (dp * Resources.getSystem().displayMetrics.density).toInt()
}





