package com.meiling.account.widget

import android.annotation.SuppressLint
import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.content.Intent
import android.net.Uri
import android.view.View
import android.widget.Checkable
import java.text.SimpleDateFormat
import java.util.*


fun androidx.fragment.app.Fragment.showToast(content: String, type: CustomToast.CustomType) {
    CustomToast(this.activity?.applicationContext, content, type).show()
}

fun androidx.fragment.app.Fragment.showToast(content: String) {
    CustomToast(this.activity?.applicationContext, content).show()
}


fun Context.showToast(content: String) {
    CustomToast(this, content).show()
}

fun Context.openBrowser(url: String) {
    Intent(Intent.ACTION_VIEW, Uri.parse(url)).run { startActivity(this) }
}

// 扩展点击事件属性(重复点击时长)
var <T : View> T.lastClickTime: Long
    set(value) = setTag(1766613352, value)
    get() = getTag(1766613352) as? Long ?: 0

// 重复点击事件绑定
inline fun <T : View> T.setSingleClickListener(time: Long = 300, crossinline block: (T) -> Unit) {
    setOnClickListener {
        val currentTimeMillis = System.currentTimeMillis()
        if (currentTimeMillis - lastClickTime > time || this is Checkable) {
            lastClickTime = currentTimeMillis
            block(this)
        }
    }
}




/**
 * 格式化当前日期
 */
fun formatCurrentDate(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd ")
    return sdf.format(Date())
}

/**
 * 格式化当前年
 */
fun formatCurrentDateYear(): String {
    val sdf = SimpleDateFormat("yyyy")
    return sdf.format(Date())
}


/**
 * 格式化当前月
 */
fun formatCurrentDataMM(): String {
    val sdf = SimpleDateFormat("MM")
    return sdf.format(Date())
}

/**
 * 格式化当前日
 */
fun formatCurrentDataDD(): String {
    val sdf = SimpleDateFormat("dd")
    return sdf.format(Date())
}

/**
 * 之前一周的时间
 */
fun formatCurrentDateBeforeWeek(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    var cal = Calendar.getInstance()
    cal.add(Calendar.DAY_OF_MONTH, -6)
    return sdf.format(cal.time)
}

/**
 * 之前一天的时间
 */
fun formatCurrentDateBeforeDay(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    var cal = Calendar.getInstance()
    cal.add(Calendar.DAY_OF_MONTH, -1)
    return sdf.format(cal.time)
}

/**
 * 之前15天的时间
 */
fun formatCurrentDateBefore15(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    var cal = Calendar.getInstance()
    cal.add(Calendar.DAY_OF_MONTH, -15)
    return sdf.format(cal.time)
}

/**
 * 之前30天的时间
 */
fun formatCurrentDateBeforeMouth(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    var cal = Calendar.getInstance()
    cal.add(Calendar.DAY_OF_MONTH, -29)
    return sdf.format(cal.time)
}

/**
 * 之前90天的时间
 */
fun formatCurrentDateBefore90(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    var cal = Calendar.getInstance()
    cal.add(Calendar.DAY_OF_MONTH, -89)
    return sdf.format(cal.time)
}

/**
 * 明天
 * */
fun getTomorrowDate(): String {
    var sdf = SimpleDateFormat("yyyy-MM-dd")
    var cal = Calendar.getInstance()
    cal.add(Calendar.DAY_OF_MONTH, 1)
    return sdf.format(cal.time)
}

fun getBeforeSevenDate(): String {
    var sdf = SimpleDateFormat("yyyy-MM-dd")
    var cal = Calendar.getInstance()
    cal.add(Calendar.DAY_OF_MONTH, -6)
    return sdf.format(cal.time)
}

fun getassignSevenDate(int: Int): String {
    var sdf = SimpleDateFormat("yyyy-MM-dd")
    var cal = Calendar.getInstance()
    cal.add(Calendar.DAY_OF_MONTH, int)

    return sdf.format(cal.time)
}

fun getBeforeMonthDate(): String {
    var sdf = SimpleDateFormat("yyyy-MM-dd")
    var cal = Calendar.getInstance()
    cal.add(Calendar.DAY_OF_MONTH, -30)
    return sdf.format(cal.time)
}

fun getSevenDate(): String {
    var sdf = SimpleDateFormat("yyyy-MM-dd")
    var cal = Calendar.getInstance()
    cal.add(Calendar.DAY_OF_MONTH, 6)
    return sdf.format(cal.time)
}

fun getMonthDate(): String {
    var sdf = SimpleDateFormat("yyyy-MM-dd")
    var cal = Calendar.getInstance()
    cal.add(Calendar.DAY_OF_MONTH, 30)
    return sdf.format(cal.time)
}


fun formatCurrentDate2(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return sdf.format(Date())
}

fun formatCurrentDate3(): String {
    val sdf = SimpleDateFormat("HH:mm:ss")
    return sdf.format(Date())
}

/**
 * String 转 Calendar
 */
fun String.stringToCalendar(): Calendar {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    val date = sdf.parse(this)
    val calendar = Calendar.getInstance()
    calendar.time = date
    return calendar
}

fun formatCurrentDateMM(): String {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    return sdf.format(Date())
}

@SuppressLint("SimpleDateFormat")
fun formatCurrentDateMM(str: String?): String {
    return try {
        val sdf2 = SimpleDateFormat("yyyy-MM-dd")
        val sdf1 = SimpleDateFormat("MM")
        var d: Date = sdf2.parse(str)
        sdf1.format(d)
    } catch (e: Exception) {
        "--"
    }
}

@SuppressLint("SimpleDateFormat")
fun formatCurrentDateMYY(str: String?): String {
    return try {
        val sdf2 = SimpleDateFormat("yyyy-MM-dd")
        val sdf1 = SimpleDateFormat("MM")
        var d: Date = sdf2.parse(str)
        sdf1.format(d)
    } catch (e: Exception) {
        "--"
    }
}

fun formatCurrentDateDay(str: String?): String {
    return try {
        val sdf2 = SimpleDateFormat("yyyy-MM-dd")
        val sdf1 = SimpleDateFormat("dd")
        var d: Date = sdf2.parse(str)
        return sdf1.format(d)
    } catch (e: Exception) {
        "--"
    }
}

fun formatCurrentDateGeshiMM(str: String): String {
    val sdf2 = SimpleDateFormat("MM-dd HH:mm")
    val sdf1 = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    var d: Date = sdf1.parse(str)
    return sdf2.format(d)
}

fun transToString(time: Long): String {
    return SimpleDateFormat("yyyy-MM-dd HH:mm:ss").format(time)
}

fun formatCurrentDateToString(time: Date): String {
    val sdf1 = SimpleDateFormat("yyyy-MM-dd HH:mm")
    return sdf1.format(time)
}

/**
 * 对比传入时间和当前时间比较
 * 传入时间大于当前时间 返回true
 * **/
@SuppressLint("SimpleDateFormat")
fun isSelectTimeCompare(time: String): Boolean {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    var checkTime = sdf.parse(time).time
    var checkCurrentTime = sdf.parse(formatCurrentDate2()).time
    return checkTime > checkCurrentTime
}

/**
 * 传入时间和当前时间比较
 * time1
 * time2 比较
 * time2时间大于time1 返回true
 * **/
@SuppressLint("SimpleDateFormat")
fun compareTimeCompare(time1: String, time2: String): Boolean {
    val sdf = SimpleDateFormat("yyyy-MM-dd")
    var checkTime1 = sdf.parse(time1).time
    var checkTime2 = sdf.parse(time2).time
    return checkTime2 >= checkTime1
}


@SuppressLint("SimpleDateFormat")
fun calculationWorkLongTime(starTime: String?): String {
    if (starTime.isNullOrEmpty()) {
        return "--"
    }
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    var longStart = sdf.parse(starTime).time
    var longEndTime = sdf.parse(formatCurrentDate2()).time
    var longTime = longEndTime - longStart
    var longHH = longTime / (60 * 60 * 1000); //根据时间差来计算小时数
    val longMM = (longTime - longHH * (60 * 60 * 1000)) / (60 * 1000); //根据时间差来计算小时数

    return "${longHH}小时${longMM}分钟"
}


/**
 * 计算时间
 * **/
@SuppressLint("SimpleDateFormat")
fun calculationClickable(): Boolean {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm:ss")
    val startTime = formatCurrentDate() + " 16:00:00"
    var longStart = sdf.parse(startTime).time
    var longEndTime = sdf.parse(formatCurrentDate2()).time
    return longStart > longEndTime

}

/**
 * 计算时间
 * **/
var xxxxx = true

@SuppressLint("SimpleDateFormat")
fun calculationClickableTime(): Boolean {
    val sdf = SimpleDateFormat("yyyy-MM-dd HH:mm")
    val startTime = formatCurrentDate() + " 20:07"
    var longStart = sdf.parse(startTime).time
    var longEndTime = sdf.parse(formatCurrentDate2()).time

//    if (longStart == longEndTime) {
//        if (xxxxx) {
//            NLog.d("lwq", "执行退出")
//            ARouter.getInstance()
//                .build("/login/activity")
//                .navigation()
//            xxxxx = false
//        } else {
//        }
//    }
    return false
}


fun copyText(context: Context, str: String) {
    // 获取剪贴板管理器
    val clipboard =
        context.getSystemService(Context.CLIPBOARD_SERVICE) as ClipboardManager
    // 创建剪贴板数据对象
    val clip = ClipData.newPlainText("label", str)
    // 将剪贴板数据设置到剪贴板管理器中
    clipboard.setPrimaryClip(clip)
}
