package com.meiling.common.utils

import com.tencent.mmkv.MMKV

object MMKVUtils {
    private val mmkv: MMKV = MMKV.defaultMMKV()

    fun putInt(key: String, value: Int) {
        mmkv.putInt(key, value)
    }

    fun getInt(key: String, defaultValue: Int = 0): Int {
        return mmkv.getInt(key, defaultValue)
    }

    fun putString(key: String, value: String) {
        mmkv.putString(key, value)
    }

    fun getString(key: String, defaultValue: String = ""): String {
        return mmkv.getString(key, defaultValue) ?: defaultValue
    }

    fun putBoolean(key: String, value: Boolean) {
        mmkv.putBoolean(key, value)
    }

    fun getBoolean(key: String, defaultValue: Boolean = false): Boolean {
        return mmkv.getBoolean(key, defaultValue)
    }

    fun putFloat(key: String, value: Float) {
        mmkv.putFloat(key, value)
    }

    fun getFloat(key: String, defaultValue: Float = 0f): Float {
        return mmkv.getFloat(key, defaultValue)
    }

    fun putLong(key: String, value: Long) {
        mmkv.putLong(key, value)
    }

    fun getLong(key: String, defaultValue: Long = 0L): Long {
        return mmkv.getLong(key, defaultValue)
    }

    fun remove(key: String) {
        mmkv.remove(key)
    }

    fun clear() {
        mmkv.clearAll()
    }
}
