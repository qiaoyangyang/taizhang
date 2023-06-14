package com.meiling.common.utils

import android.graphics.Bitmap
import android.graphics.BitmapFactory
import android.graphics.Canvas
import android.graphics.Color
import android.text.TextUtils
import android.util.Base64
import android.view.View
import com.meiling.common.utils.StorageUtils.getCacheImgDirPath
import java.io.BufferedOutputStream
import java.io.File
import java.io.FileOutputStream
import java.util.*

/**
 * @Author : xf
 * @Time : On 2020/11/25 18:41
 * @Description : BitmapUtils
 */
object BitmapUtils {
    /**
     * base64转为bitmap
     *
     * @param base64Data
     * @return
     */
    fun base64ToBitmap(base64Data: String?): Bitmap {
        val bytes = Base64.decode(base64Data, Base64.DEFAULT)
        return BitmapFactory.decodeByteArray(bytes, 0, bytes.size)
    }

    fun getBitmapFromView(v: View): Bitmap {
        val b = Bitmap.createBitmap(v.width, v.height, Bitmap.Config.RGB_565)
        val c = Canvas(b)
        v.layout(v.left, v.top, v.right, v.bottom)
        // Draw background
        val bgDrawable = v.background
        if (bgDrawable != null) {
            bgDrawable.draw(c)
        } else {
            c.drawColor(Color.WHITE)
        }
        // Draw view to canvas
        v.draw(c)
        return b
    }

    fun saveFile(bm: Bitmap, fileName: String, fileExt: String): String {
        try {
            val path = getCacheImgDirPath()
            val dirFile = File(path)
            if (!dirFile.exists()) {
                dirFile.mkdirs()
            }
            var pathName = path + UUID.randomUUID() + ".png"
            if (!TextUtils.isEmpty(fileName)) {
                pathName = if (!TextUtils.isEmpty(fileExt)) {
                    "$path$fileName.$fileExt"
                } else {
                    "$path$fileName.png"
                }
            }
            val myCaptureFile = File(pathName)
            val bos = BufferedOutputStream(FileOutputStream(myCaptureFile))
            bm.compress(Bitmap.CompressFormat.JPEG, 100, bos)
            bos.flush()
            bos.close()
            return myCaptureFile.path
        } catch (e: Exception) {
            e.printStackTrace()
        }
        return ""
    }
}