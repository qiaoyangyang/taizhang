package com.meiling.common.utils

import com.google.gson.Gson
import com.orhanobut.logger.Logger
import com.meiling.common.BuildConfig

object LogUtils {
    var isDebug = BuildConfig.DEBUG
    fun e(tag: String?, msg: String) {
        if (isDebug) {
            Logger.t(tag).e(msg + "")
        }
    }

    fun e(tag: String, title: String, msg: String) {
        if (isDebug) {
            Logger.t("$tag【$title】").e(msg + "")
        }
    }

    fun e(tag: String?, o: Any?) {
        if (isDebug) {
            Logger.t(tag).e(Gson().toJson(o))
        }
    }

    fun e(tag: String, title: String, o: Any?) {
        if (isDebug) {
            Logger.t("$tag【$title】").e(Gson().toJson(o))
        }
    }

    /**
     * 调试
     */
    fun d(tag: String?, msg: String) {
        if (isDebug) {
            Logger.t(tag).d(msg + "")
        }
    }

    fun d(tag: String?, msg: Any?) {
        if (isDebug) {
            Logger.t(tag).d(Gson().toJson(msg))
        }
    }

    fun i(tag: String?, msg: String) {
        if (isDebug) {
            Logger.t(tag).i(msg + "")
        }
    }

    fun i(tag: String?, msg: Any?) {
        if (isDebug) {
            Logger.t(tag).i(Gson().toJson(msg))
        }
    }
}