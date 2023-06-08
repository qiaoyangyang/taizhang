package com.meiling.oms.wxapi;


/**
 * 微信支付回调
 *
 * @author xiaofan
 */
public interface WXPayListener {


    void onWxPaySuccess(int errCode, String errStr);

    void onWxPayFail(int errCode, String errStr);

}
