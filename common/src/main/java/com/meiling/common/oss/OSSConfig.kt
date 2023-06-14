package com.meiling.common.oss


/**
 * @Author : xf
 * @Time : On 2021/2/24 17:43
 * @Description : OSSConfig
 */
object OSSConfig {
    const val domain = "http://robmeta.oss-cn-hangzhou.aliyuncs.com/"
    const val endpoint = "https://oss-cn-hangzhou.aliyuncs.com/"
    const val authServerUrl = "http://47.97.158.192:8080/api/v1/app/oss/getToken"
    const val connectionTimeOut = 15 * 1000
    const val socketTimeOut = 15 * 1000
    const val maxConcurrentRequest = 5
    const val maxErrorRetry = 2
    const val bucketName = "robmeta"
    const val IMG = "media/img/"
    const val VIDEO = "media/video/"
    const val AUDIO = "media/audio/"
    const val OOS_IMG = 1
    const val OSS_VIDEO = 2
    const val OSS_AUDIO = 3
    const val VIDEO_SUFFIX = "?x-oss-process=video/snapshot,t_1000,f_jpg,m_fast,ar_auto"
}