package com.meiling.common.network

import android.text.TextUtils
import com.orhanobut.logger.Logger
import okhttp3.*
import java.lang.Exception
import okhttp3.ResponseBody
import okio.Buffer
import java.io.IOException


class LoggerInterceptor : Interceptor {


    val TAG = "HttpLog"
    private var tag: String? = null
    private var showResponse = true

    fun LoggerInterceptor(tag: String?, showResponse: Boolean) {
        var tag = tag
        if (TextUtils.isEmpty(tag)) {
            tag = TAG
        }
        this.showResponse = showResponse
        this.tag = tag
    }


    override fun intercept(chain: Interceptor.Chain): Response {
        val request: Request = chain.request()
        logForRequest(request)
        val response = chain.proceed(request)
        return logForResponse(response)
    }

    private fun logForRequest(request: Request) {
        try {
            val url = request.url.toString()
            val headers: Headers? = request.headers
            Logger.d("method : " + request.method + "  ║  url : " + url)
            if (headers != null && headers.size > 0) {
                Logger.d("headers : " + headers.toString())
            }
            val requestBody = request.body
            if (requestBody != null) {
                val mediaType: MediaType? = requestBody.contentType()
                if (mediaType != null) {
                    Logger.d("requestBody's contentType : " + mediaType.toString())
                    if (isText(mediaType)) {
                        Logger.d("requestBody's content : " + bodyToString(request))
                    } else {
                        Logger.e("requestBody's content : " + " maybe [file part] , too large too print , ignored!")
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
    }

    private fun logForResponse(response: Response): Response {
        try {
            val builder = response.newBuilder()
            val clone = builder.build()
            Logger.d(
                "url : " + clone.request.url.toString() + "  ║  code : " + clone.code
                    .toString() + "  ║  protocol : " + clone.protocol
            )
            if (!TextUtils.isEmpty(clone.message)) {
                Logger.d("message : " + clone.message)
            }
            if (showResponse) {
                var body = clone.body
                if (body != null) {
                    val mediaType = body.contentType()
                    if (mediaType != null) {
                        Logger.d("responseBody's contentType : $mediaType")
                        if (isText(mediaType)) {
                            val resp = body.string()
                            when (mediaType.subtype) {
                                "xml" -> Logger.xml(resp)
                                "json" -> Logger.json(resp)
                                else -> Logger.d(resp)
                            }
                            body = ResponseBody.create(mediaType, resp)
                            return response.newBuilder().body(body).build()
                        } else {
                            Logger.e("responseBody's content : " + " maybe [file part] , too large too print , ignored!")
                        }
                    }
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return response
    }


    private fun isText(mediaType: MediaType): Boolean {
        if (mediaType.type != null && mediaType.type.equals("text")) {
            return true
        }
        if (mediaType.subtype != null) {
            if (mediaType.subtype.equals("json") ||
                mediaType.subtype.equals("xml") ||
                mediaType.subtype.equals("html") ||
                mediaType.subtype.equals("webviewhtml") || mediaType.subtype
                    .equals("x-www-form-urlencoded")
            ) {
                return true
            }
        }
        return false
    }

    private fun bodyToString(request: Request): String? {
        return try {
            val copy = request.newBuilder().build()
            val buffer = Buffer()
            copy.body!!.writeTo(buffer)
            buffer.readUtf8()
        } catch (e: IOException) {
            "something error when show requestBody."
        }
    }
}