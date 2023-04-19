package com.meiling.common

import android.app.Application
import android.content.Intent
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ToastUtils
import com.google.gson.Gson
import com.meiling.common.constant.ARouteConstants
import com.meiling.common.network.APIException
import com.meiling.common.network.ExceptionHandle
import com.meiling.common.network.ResultData
import com.meiling.common.utils.MMKVUtils
import com.orhanobut.logger.Logger
import kotlinx.coroutines.Dispatchers
import kotlinx.coroutines.Job
import kotlinx.coroutines.launch

open class BaseViewModel(application: Application) : AndroidViewModel(application) {

    val resultError = MutableLiveData<APIException>()


    fun <T : Any> request(
        block: suspend () -> ResultData<T>,
        resultState: BaseLiveData<T>,
    ): Job {
        return viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                resultState.onStart.postValue("")
                block()
            }.onSuccess {
                if (it.code == 200) {
                    resultState.onSuccess.postValue(it.data)
                } else {
                    resultState.onError.postValue(
                        ExceptionHandle.handleException(
                            APIException(
                                it.code,
                                it.msg
                            )
                        )
                    )
                }
            }.onFailure {
                if (it.message!!.isNotEmpty()) {
                    if (it.message!!.contains("403")) {
                        MMKVUtils.clear()
                        ARouter.getInstance().build(ARouteConstants.LOGIN_ACTIVITY)
                            .withFlags(Intent.FLAG_ACTIVITY_CLEAR_TOP).navigation()
                    }

                } else {
                    resultState.onError.postValue(ExceptionHandle.handleException(it))
                }
            }
        }
    }

    fun <T : Any> request(
        block: suspend () -> ResultData<T>,
        resultState: MutableLiveData<T>,
        onError: (exception: APIException) -> Unit,
    ): Job {
        return viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                block()
            }.onSuccess {
                if (it.code == 0) {
                    resultState.postValue(it.data)
                } else if (it.code == 403) {
                    ARouter.getInstance().build(ARouteConstants.LOGIN_ACTIVITY).navigation()
                } else {

                    onError(ExceptionHandle.handleException(APIException(it.code, it.msg)))
                }
            }.onFailure {
                onError(ExceptionHandle.handleException(it))
            }
        }
    }

    fun <T : Any> request(
        block: suspend () -> ResultData<T>,
        resultState: MutableLiveData<T>,
        onError: MutableLiveData<APIException>,
    ): Job {
        return viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                block()
            }.onSuccess {
                if (it.code == 0) {
                    resultState.postValue(it.data)
                } else if (it.code == 403) {
                    ARouter.getInstance().build(ARouteConstants.LOGIN_ACTIVITY).navigation()
                } else {
                    onError.postValue(APIException(it.code, it.msg))
                }
            }.onFailure {
                onError.postValue(ExceptionHandle.handleException(it))
            }
        }
    }

}