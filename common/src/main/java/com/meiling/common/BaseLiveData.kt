package com.meiling.common

import androidx.lifecycle.MutableLiveData
import com.meiling.common.network.APIException

class BaseLiveData<T : Any> {


    val onSuccess = MutableLiveData<T>()
    val onStart = MutableLiveData<String>()
    val onError = MutableLiveData<APIException>()


}