package com.meiling.oms.pay;

import android.app.Activity;

import com.alipay.sdk.app.PayTask;
import com.blankj.utilcode.util.Utils;
import com.meiling.common.utils.SchedulerTransformer;
import com.tencent.mm.opensdk.modelpay.PayReq;
import com.tencent.mm.opensdk.openapi.IWXAPI;
import com.tencent.mm.opensdk.openapi.WXAPIFactory;


import org.jetbrains.annotations.NotNull;

import java.util.Map;

import io.reactivex.rxjava3.core.Observable;
import io.reactivex.rxjava3.core.ObservableEmitter;
import io.reactivex.rxjava3.core.ObservableOnSubscribe;
import io.reactivex.rxjava3.core.Observer;


/**
 * 支付工具类
 *
 * @author xiaofan
 */
public class PayUtils {

    private static WXPayListener wxPayListener;

    /**
     * 微信支付
     *
     * @param partnerId    partnerId
     * @param prepayId     prepayId
     * @param nonceStr     nonceStr
     * @param timeStamp    timeStamp
     * @param packageValue packageValue
     * @param sign         sign
     * @param observable   observable 是否成功
     */
    public static void WxPay(final String appId, final String partnerId, final String prepayId, final String nonceStr,
                             final String timeStamp, final String packageValue, final String sign,
                             Observer<String> observable) {
        Observable.create(new ObservableOnSubscribe<String>() {
            @Override
            public void subscribe(@NotNull ObservableEmitter<String> emitter) throws Exception {
                IWXAPI api = WXAPIFactory.createWXAPI(Utils.getApp(), "WE_CHAT_APP_ID", false);
                PayReq req = new PayReq();
                req.appId = appId;
                req.partnerId = partnerId;
                req.prepayId = prepayId;
                req.nonceStr = nonceStr;
                req.timeStamp = timeStamp;
                req.packageValue = packageValue;
                req.sign = sign;
                api.sendReq(req);
                setWxPayListener(new WXPayListener() {
                    @Override
                    public void onWxPaySuccess(int errCode, String errStr) {
                        emitter.onNext(errStr);
                        emitter.onComplete();
                    }

                    @Override
                    public void onWxPayFail(int errCode, String errStr) {
                        emitter.onError(new Exception(errStr));
                        emitter.onComplete();
                    }
                });
            }
        }).compose(new SchedulerTransformer()).subscribe(observable);
    }

    /**
     * 微信支付设置回调
     *
     * @param wxPayListener
     */
    public static void setWxPayListener(WXPayListener wxPayListener) {
        PayUtils.wxPayListener = wxPayListener;
    }

    /**
     * 微信支付成功
     *
     * @param errCode
     * @param errStr
     */
    public static void setWxPaySuccess(int errCode, String errStr) {
        if (wxPayListener != null) {
            wxPayListener.onWxPaySuccess(errCode, errStr);
        }
    }

    /**
     * 微信支付失败
     *
     * @param errCode
     * @param errStr
     */
    public static void setWxPayFail(int errCode, String errStr) {
        if (wxPayListener != null) {
            wxPayListener.onWxPayFail(errCode, errStr);
        }
    }


    /**
     * @param activity  上下文
     * @param orderInfo 支付串
     * @param observer  判断resultStatus 为“9000”则代表支付成功，具体状态码代表参考：https://docs.open.alipay.com/204/105301
     */
    public static void aliPay(final Activity activity, final String orderInfo, Observer<AliPayResp> observer) {
        Observable.create((ObservableOnSubscribe<AliPayResp>) emitter -> {
            PayTask payTask = new PayTask(activity);
            Map<String, String> result = payTask.payV2(orderInfo, true);
            emitter.onNext(new AliPayResp(result));
            emitter.onComplete();
        }).compose(new SchedulerTransformer()).subscribe(observer);
    }
}
