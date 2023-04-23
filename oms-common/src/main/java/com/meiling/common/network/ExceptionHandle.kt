package com.meiling.common.network

import android.net.ParseException
import com.google.gson.JsonParseException
import com.google.gson.stream.MalformedJsonException
import org.apache.http.conn.ConnectTimeoutException
import org.json.JSONException
import retrofit2.HttpException
import java.net.ConnectException
import java.net.SocketTimeoutException

object ExceptionHandle {
    fun handleException(e: Throwable?): APIException {
        val ex: APIException
        e?.let {
            when (it) {
                is HttpException -> {
                    ex = APIException(Error.SERVER_ERROR,e)
                    return ex
                }
                is JsonParseException, is JSONException, is ParseException, is MalformedJsonException -> {
                    ex = APIException(Error.PARSE_ERROR,e)
                    return ex
                }
                is ConnectException -> {
                    ex = APIException(Error.NETWORK_ERROR,e)
                    return ex
                }
                is javax.net.ssl.SSLException -> {
                    ex = APIException(Error.SSL_ERROR,e)
                    return ex
                }
                is ConnectTimeoutException -> {
                    ex = APIException(Error.TIMEOUT_ERROR,e)
                    return ex
                }
                is SocketTimeoutException -> {
                    ex = APIException(Error.TIMEOUT_ERROR,e)
                    return ex
                }
                is java.net.UnknownHostException -> {
                    ex = APIException(Error.TIMEOUT_ERROR,e)
                    return ex
                }
                is APIException -> return it

                else -> {
                    ex = APIException(Error.UNKNOWN,e)
                    return ex
                }
            }
        }
        ex = APIException(Error.UNKNOWN,e)
        return ex
    }
}