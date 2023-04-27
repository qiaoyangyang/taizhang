package com.meiling.oms.jpush

import android.annotation.SuppressLint
import android.content.Context
import android.content.res.Resources
import android.os.Build
import android.view.View
import android.widget.Toast
import androidx.annotation.RequiresApi
import androidx.viewbinding.ViewBinding
import com.blankj.utilcode.util.ToastUtils
import com.blankj.utilcode.util.Utils
import com.google.gson.Gson
import com.meiling.common.BaseApplication
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.jpush.jpushPlay.MessageData
import com.meiling.oms.jpush.jpushPlay.MessageManagement
import com.meiling.common.network.data.PushMsgJson
import com.meiling.oms.OmsApplication
import com.meiling.oms.R
import com.meiling.oms.activity.MainActivity
import com.meiling.oms.dialog.OrderDialog
import com.umeng.message.entity.UMessage
import java.util.*


var count = 0
var xsize = 0
var ysize = 60
var listView = ArrayList<View>()

@RequiresApi(Build.VERSION_CODES.O)
@SuppressLint("SetTextI18n", "RtlHardcoded", "InflateParams", "WrongConstant")
fun createAndStart(context: Context, uMessage: UMessage, x: Int, y: Int) {

    if (AppConfig.getActivity() != null) {
        val childFragmentManager = OrderDialog().newInstance()
        childFragmentManager.show((context as MainActivity).supportFragmentManager)
    }
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


//        }

//        MainRetrofit.service.getNetOrdersV4(map).ss(null, null, onSuccess = {
//            if (it.data != null) {
//                if (!it.data.pageData.isNullOrEmpty()) {
//                    printOrderReceipt(context, it.data.pageData[0], pushCode)
//                }
//            }
//        }, onError = {})
        }

    } catch (e: Exception) {
        ToastUtils.showShort("推送消息格式异常")
    }

}

private fun dp2px(dp: Int): Int {
    return (dp * Resources.getSystem().displayMetrics.density).toInt()
}

//private fun printOrderReceipt(context: Context, bean: OrederDetileItem, pushCode: String) {
//    var shopPhone: String by Preference(Constant.SHOPPHONE, "")
//    var shoAddress: String by Preference(Constant.SHOPADDRESS, "")
//    var tenantId: String by Preference(Constant.TENANTID, "")
//    var username: String by Preference(Constant.USERNAME, "")
//    var buttonIsShow: String by Preference(Constant.ButtonShow, "0")
//    var Print_A_Number: String by Preference(Constant.Print_A_Number, "1")
//    var LAbEL: Boolean by Preference(Constant.LAbEL, true)
//    var mobilePhone: String by Preference(Constant.mobilePhone, "")
//    if (bean.status == 300 && pushCode == "6") {
//        if (bean.morderPayPriceList.isNullOrEmpty()) {
//            bean.shopPhone = shopPhone
//            bean.name = username
//            bean.tenantId = tenantId
//            SunmiPrintHelper.getInstance()
//                .printTransMeiTuan(
//                    context,
//                    bean,
//                    username,
//                    false,
//                    shopPhone, false,
//                    0,
//                    buttonIsShow, shoAddress,
//                    null
//                )
//        } else {
//            bean.morderPayPriceList?.let {
//                var tempList = bean.morderPayPriceList!!.filter { it.type == "2" }
//                var depositPriceOrFinalPrice = 0
//                if (tempList.isEmpty()) {
//                    //商米内置打印机打印
//                    if (SunmiPrintHelper.getInstance().sunmiPrinter == 2) {
//                        bean.name = username
//                        bean.tenantId = tenantId
//                        if (bean.status == 760) {
//                            bean.morderPayPriceList = bean.morderPayPriceList?.filter {
//                                it.type == "2"
//                            } as java.util.ArrayList<MorderPayPrice>
//                        }
//                        if (bean.type == 3) {
//                            //                            if (bean.status == 111) {
//                            depositPriceOrFinalPrice = 2
//                            //                            }
//                        }
//                        SunmiPrintHelper.getInstance()
//                            .printTrans(
//                                context,
//                                bean,
//                                username,
//                                false,
//                                shopPhone, false,
//                                depositPriceOrFinalPrice,
//                                buttonIsShow,
//                                shoAddress,
//                                null
//                            )
//                    }
//                    bean.shopPhone = shopPhone
////            printTemplateFormatText(
////                bean = bean,
////                depositPriceOrFinalPrice = depositPriceOrFinalPrice
////            )
//
//                    //                    }
//                } else {
//                    MainRetrofit.service.getOrderREfundDetail(bean.viewId)
//                        .ss(null, null, false, onSuccess = {
//                            if (it.data.size > 0) {
//                                //                            var orderRefundDetailDialog= OrderRefundDetailDialog().newInstance(it.data as ArrayList<OrderRefundDetailBean.OrderRefundDetailBeanItem>)
//                                //                            orderRefundDetailDialog.show(supportFragmentManager)
//                                //有退款
//                                //                            if (!UsbPrintManager.getInstance().hasConnected()) {
//                                //商米内置打印机打印
//                                if (SunmiPrintHelper.getInstance().sunmiPrinter == 2) {
//                                    bean.name = username
//                                    bean.tenantId = tenantId
//                                    //                                    if (bean.status == 760) {
//                                    //                                        bean.morderPayPriceList = bean.morderPayPriceList?.filter {
//                                    //                                            it.type == "2"
//                                    //                                        } as ArrayList<MorderPayPrice>
//                                    //                                    }
//                                    SunmiPrintHelper.getInstance()
//                                        .printRefundTrans(
//                                            context,
//                                            bean,
//                                            username,
//                                            false,
//                                            shopPhone,
//                                            it.data as java.util.ArrayList<OrderRefundDetailBean.OrderRefundDetailBeanItem>,
//                                            false, shoAddress, null
//                                        )
//                                }
//                                //                            }
//                                var item =
//                                    (it.data as java.util.ArrayList<OrderRefundDetailBean.OrderRefundDetailBeanItem>)
//
//                                Collections.sort(item, SortByRefundSort())
//
//                                if (item.size > 0) {
//                                    var noRefundPrice = "0"
//                                    var tempNoRefundPrice = "0"
//                                    item.forEach {
//                                        tempNoRefundPrice =
//                                            XNumberUtils.StrAdd(
//                                                it.mOrderRefund.refundPrice,
//                                                tempNoRefundPrice
//                                            )
//                                        noRefundPrice =
//                                            XNumberUtils.Strsubtract(
//                                                bean.payPrice,
//                                                tempNoRefundPrice
//                                            )
//                                        it.noRefundPrice = noRefundPrice
//                                        it.payPrice = bean.payPrice
//                                        it.shopName = bean.shopName
//                                        it.shopPhone = shopPhone
//                                        it.channelDaySn = bean.channelDaySn
//                                        it.deliveryType = bean.deliveryType?.toString()
//                                        it.channelName = bean.channelName
//                                        it.viewId = bean.viewId
//                                        it.adminUserName = bean.adminUserName
//                                        it.tenantId = bean.tenantId
//                                        it.createTime =
//                                            XDateUtils.millis2String(it.mOrderRefund.updateTime.toLong())
//                                        val mHashMap = HashMap<String, Goods>()
//                                        if (!bean.goodsList.isNullOrEmpty()) {
//                                            var allNum = 0
//                                            for (item in bean.goodsList!!) {
//                                                allNum += item.num.toInt()
//                                                if (mHashMap.containsKey(item.id)) {
//
//                                                }
//                                                mHashMap[item.id] = item
//                                            }
//                                            it.allNum = allNum
//                                            it.allNum2 = mHashMap.size
//                                        }
//                                        if (it.goodsVoList.size > 0) {
//                                            it.goodsVoList.forEach {
//                                                //                                            sumPrice= XNumberUtils.StrAdd(sumPrice,
//                                                //                                                Strmultiply(it.num,
//                                                //                                                    it.price))
//                                                it.itemGoodsRefundPrice =
//                                                    XNumberUtils.Strmultiply(
//                                                        it.num,
//                                                        it.price
//                                                    )
//                                                it.num = XNumberUtils.FormatStrRemoveZero(it.num)
//
//                                            }
//                                            //                                        it.sumPrice=sumPrice
//                                        }
//                                    }
//
//                                }
////                        printRefundTemplateFormatText(bean = bean, refundBean = item)
//                            }
//                        })
//                }
//            }
//        }
//    }
//    bean.phone = mobilePhone
//    bean.shopPhone = shopPhone
//    var isprint = SPUtils.getBoolean("isprint", false)
//
//    if (LAbEL && isprint) {
//        NLog.d("yjk", "${bean.status}$pushCode")
//        if (bean.status == 300 && pushCode == "6") {
//            Labelprinting(context, bean, Print_A_Number)
//        }
//    }
//}





