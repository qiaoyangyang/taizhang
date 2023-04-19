package com.meiling.common

import android.app.Application
import android.util.Log
import androidx.lifecycle.AndroidViewModel
import androidx.lifecycle.MutableLiveData
import androidx.lifecycle.viewModelScope
import com.alibaba.android.arouter.launcher.ARouter
import com.google.gson.Gson
import com.meiling.common.constant.ARouteConstants
import com.meiling.common.network.APIException
import com.meiling.common.network.ExceptionHandle
import com.meiling.common.network.ResultData
import com.orhanobut.logger.Logger
import kotlinx.coroutines.*

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
                } else if (it.code == 403) {
                    ARouter.getInstance().build(ARouteConstants.LOGIN_ACTIVITY).navigation()
                } else {
                    resultState.onError.postValue(ExceptionHandle.handleException(APIException(it.code, it.msg)))
                }

            }.onFailure {
                resultState.onError.postValue(ExceptionHandle.handleException(it))
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
                if (it.code == 200) {
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
                if (it.code == 200) {
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

    fun <T : Any> launchRequest(
        block: suspend () -> ResultData<T>,
        isShowLoading:Boolean?=true,
        onSuccess:(T?)->Unit,
        onError:((String?)->Unit) ?= null
    ): Job {
        Log.e("当前线程",""+Thread.currentThread().name)

        return viewModelScope.launch(Dispatchers.IO) {
            runCatching {
                block()
            }.onSuccess {
                Log.e("当前线程2",""+Thread.currentThread().name)
                withContext(Dispatchers.Main){
                    Log.e("当前线程3",""+Thread.currentThread().name)
                    if (it.code == 200) {
                        if(it.data!=null){
                            onSuccess.invoke(it.data)
                        }else{
                            onSuccess.invoke(null)
                        }
                    } else if (it.code == 403) {
                        ARouter.getInstance().build(ARouteConstants.LOGIN_ACTIVITY).navigation()
                    } else {
                        if(onError!=null){
                            onError?.invoke(it.msg)
                        }
                    }
                }

            }.onFailure {
                withContext(Dispatchers.Main){
                    Log.e("当前线程4",""+Thread.currentThread().name)

                    onError?.invoke(ExceptionHandle.handleException(it).msg)
                }

            }
        }
    }



}