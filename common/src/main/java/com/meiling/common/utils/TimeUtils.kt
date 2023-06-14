package com.friendwing.universe.common.utils

import android.os.Build
import androidx.annotation.RequiresApi
import java.time.LocalDateTime
import java.time.ZoneOffset
import java.time.format.DateTimeFormatter


object TimeUtils {

    @RequiresApi(Build.VERSION_CODES.O)
    var dateTimeFormatter = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss")

    @RequiresApi(Build.VERSION_CODES.O)
    var YYYYMMDD = DateTimeFormatter.ofPattern("yyyy-MM-dd")


    @RequiresApi(Build.VERSION_CODES.O)
    var MMddHHmm = DateTimeFormatter.ofPattern("MM-dd HH:mm")


    val day = 86400000L

    val currentTime: Long
        get() = System.currentTimeMillis()


    @RequiresApi(Build.VERSION_CODES.O)
    fun toEpochMilli(time: String): Long {
        return LocalDateTime.parse(time, dateTimeFormatter)
            .toInstant(ZoneOffset.of("+8")).toEpochMilli()
    }

    @RequiresApi(Build.VERSION_CODES.O)
    fun formatter(time: String, formatter: DateTimeFormatter): String {
        return LocalDateTime.parse(time, dateTimeFormatter).format(formatter).toString()
    }


    @RequiresApi(Build.VERSION_CODES.O)
    fun getTime(time: String): Long {
        return LocalDateTime.parse(time, dateTimeFormatter).toInstant(ZoneOffset.of("+8"))
            .toEpochMilli()
    }



}