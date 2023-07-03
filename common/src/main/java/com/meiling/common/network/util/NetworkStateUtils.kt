package com.meiling.common.network.util

import android.content.Context
import android.net.ConnectivityManager
import android.net.Network
import android.net.NetworkCapabilities
import android.net.NetworkInfo
import android.os.Build
import com.meiling.common.network.enums.NetworkState
import java.io.IOException
import java.net.InetSocketAddress
import java.net.Socket

object NetworkStateUtils {

    /**
     * 判断当前设备是否有网络连接
     */
    fun hasNetworkCapability(context: Context): Boolean {
        try {
            val connectivityManager = context.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager?
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val activeNetwork = connectivityManager?.activeNetwork ?: return false
                val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return false
                val hasNet=networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_VALIDATED) &&
                        networkCapabilities.hasCapability(NetworkCapabilities.NET_CAPABILITY_INTERNET)
                if(!hasNet){
                    return isNetworkAvailable(context)
                }else{
                    return hasNet
                }

            } else {
                val networkInfo = connectivityManager?.activeNetworkInfo ?: return false
                return networkInfo.isAvailable && networkInfo.isConnected
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return false
    }
    //
    fun isNetworkAvailable(context: Context): Boolean {
        val cm = context.getSystemService(Context.CONNECTIVITY_SERVICE) as ConnectivityManager
        if (cm == null) {
//            UiUtils.showLongCenterToast("网络正在休息")

        } else {
            //如果仅仅是用来判断网络连接
            //则可以使用 cm.getActiveNetworkInfo().isAvailable();
            val info: Array<NetworkInfo>? = cm.allNetworkInfo
            if (info != null) {
                for (i in info.indices) {
                    if (info[i].getState() === NetworkInfo.State.CONNECTED) {
                        return true
                    }
                }
            }
        }
        return false
    }
    /**
     * 获取当前网络状态
     */
    fun getNetworkState(context: Context?): NetworkState {
        try {
            val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager?

            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val activeNetwork = connectivityManager?.activeNetwork ?: return NetworkState.NONE
                val networkCapabilities = connectivityManager.getNetworkCapabilities(activeNetwork) ?: return NetworkState.NONE

                return when {
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> NetworkState.WIFI
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> NetworkState.CELLULAR
                    else -> NetworkState.NONE
                }
            } else {
                return when (connectivityManager?.activeNetworkInfo?.type) {
                    ConnectivityManager.TYPE_MOBILE -> NetworkState.CELLULAR
                    ConnectivityManager.TYPE_WIFI -> NetworkState.WIFI
                    else -> NetworkState.NONE
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return NetworkState.NONE
    }

    fun isOnline(path:String):Boolean{
        try {
            var timeoutMs = 1000;
            var sock=Socket()
            var sockaddr=InetSocketAddress(path,80)
            sock.connect(sockaddr,timeoutMs)
            sock.close()
            return true

        }catch (e:IOException){
            return false
        }
    }

    internal fun getNetworkState(context: Context?, network: Network?): NetworkState {
        try {
            val connectivityManager = context?.getSystemService(Context.CONNECTIVITY_SERVICE) as? ConnectivityManager?
            if (Build.VERSION.SDK_INT >= Build.VERSION_CODES.M) {
                val networkCapabilities = connectivityManager?.getNetworkCapabilities(network) ?: return NetworkState.NONE
                return when {
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_WIFI) -> NetworkState.WIFI
                    networkCapabilities.hasTransport(NetworkCapabilities.TRANSPORT_CELLULAR) -> NetworkState.CELLULAR
                    else -> NetworkState.NONE
                }
            } else {
                return when (connectivityManager?.activeNetworkInfo?.type) {
                    ConnectivityManager.TYPE_MOBILE -> NetworkState.CELLULAR
                    ConnectivityManager.TYPE_WIFI -> NetworkState.WIFI
                    else -> NetworkState.NONE
                }
            }
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return NetworkState.NONE
    }
}