package com.meiling.account.wxapi

object MyApiUtil {


    //微信登录AppSecret
        const   val WeChatAppSecret = ""

    //微信登录AppID
    const  val WeChatAppID = ""

    //微信登录-First，获取 Access token
     const val WeChatLogin =
         "https://api.weixin.qq.com/cgi-bin/token?grant_type=client_credential&appid=$WeChatAppID&secret=$WeChatAppSecret"

    //微信登录-Second，获取 sdk_ticket
    const val WeChatLoginSecond = "https://api.weixin.qq.com/cgi-bin/ticket/getticket?"

    //微信登录-Third
    const val WeChatLoginThird =
        "https://api.weixin.qq.com/sns/oauth2/access_token?appid=&secret=&code="

    //微信登录-Fourth
    const  val WeChatLoginFourth = "https://api.weixin.qq.com/sns/userinfo?access_token="


}