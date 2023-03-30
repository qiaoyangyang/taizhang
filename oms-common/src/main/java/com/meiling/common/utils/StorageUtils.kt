package com.meiling.common.utils

import com.blankj.utilcode.util.PathUtils
import java.io.File

object StorageUtils {

    fun getExternalCacheDirPath(): String {
        return PathUtils.getExternalAppDataPath()
    }


    fun getCacheImgDirPath(): String {
        val path = PathUtils.getExternalAppDataPath() + File.separator + "img" + File.separator
        val file = File(path)
        if (!file.exists()) {
            file.mkdirs()
        }
        return path
    }

    fun getCache3dDirPath(): String {
        val path = getExternalCacheDirPath() + File.separator + "3d" + File.separator
        val file = File(path)
        if (!file.exists()) {
            file.mkdirs()
        }
        return path
    }

    fun getCacheApkPath(): String {
        val path = getExternalCacheDirPath() + File.separator + "apk" + File.separator
        val file = File(path)
        if (!file.exists()) {
            file.mkdirs()
        }
        return path
    }


}