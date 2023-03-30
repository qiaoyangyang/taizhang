package com.meiling.common.oss

import android.content.Context
import com.alibaba.sdk.android.oss.*
import com.alibaba.sdk.android.oss.callback.OSSCompletedCallback
import com.alibaba.sdk.android.oss.model.PutObjectRequest
import com.alibaba.sdk.android.oss.model.PutObjectResult
import java.io.File
import java.io.FileInputStream
import java.io.IOException
import java.math.BigInteger
import java.security.MessageDigest
import java.security.NoSuchAlgorithmException

/**
 * @Author : xf
 * @Time : On 2021/2/24 17:57
 * @Description : OSSTool
 */
class OSSTool private constructor(context: Context) {

    private val oss: OSSClient

    companion object {
        private var INSTANCE: OSSTool? = null
        fun getInstance(context: Context): OSSTool {
            if (INSTANCE == null) {
                INSTANCE = OSSTool(context)
            }
            return INSTANCE!!
        }
    }

    init {
        oss = provideOSSClient(context)
    }


    private fun provideOSSClient(context: Context): OSSClient {
        val credentialProvider =
            CustOSSAuthCredentialsProvider(OSSConfig.authServerUrl)
        val conf = ClientConfiguration()
        conf.socketTimeout = OSSConfig.socketTimeOut
        conf.maxErrorRetry = OSSConfig.maxErrorRetry
        conf.connectionTimeout = OSSConfig.connectionTimeOut
        conf.maxConcurrentRequest = OSSConfig.maxConcurrentRequest
        return OSSClient(context, OSSConfig.endpoint, credentialProvider, conf)
    }

    fun putObjectMethodImg(file: File, callback: OnUploadCallback) {
        val uploadObject = OSSConfig.IMG + getMD5Three(file) + getFileSuffix(file.name)
        putObjectMethod(uploadObject, file, callback)
    }

    fun putObjectMethodVideo(file: File, callback: OnUploadCallback) {
        val uploadObject = OSSConfig.VIDEO + getMD5Three(file) + getFileSuffix(file.name)
        putObjectMethod(uploadObject, file, callback)
    }


    private fun putObjectMethod(
        uploadObject: String,
        file: File,
        callback: OnUploadCallback
    ) {
        val putObjectRequest = PutObjectRequest(OSSConfig.bucketName, uploadObject, file.path)
        oss.asyncPutObject(putObjectRequest,
            object : OSSCompletedCallback<PutObjectRequest, PutObjectResult> {
                override fun onSuccess(request: PutObjectRequest, result: PutObjectResult) {
                    callback.onSuccess(OSSConfig.domain + request.objectKey)
                }

                override fun onFailure(
                    request: PutObjectRequest,
                    clientException: ClientException,
                    serviceException: ServiceException
                ) {
                    callback.onFailure()
                }
            })
    }


    private fun getFileSuffix(fileName: String): String? {
        return fileName.substring(fileName.lastIndexOf("."))
    }


    interface OnUploadCallback {
        fun onSuccess(url: String)
        fun onFailure()
    }


    private fun getMD5Three(file: File): String {
        var bi: BigInteger? = null
        try {
            val buffer = ByteArray(8192)
            var len = 0
            val md = MessageDigest.getInstance("MD5")
            val fis = FileInputStream(file)
            while (fis.read(buffer).also { len = it } != -1) {
                md.update(buffer, 0, len)
            }
            fis.close()
            val b = md.digest()
            bi = BigInteger(1, b)
        } catch (e: NoSuchAlgorithmException) {
            e.printStackTrace()
        } catch (e: IOException) {
            e.printStackTrace()
        }
        return bi!!.toString(16)
    }

}