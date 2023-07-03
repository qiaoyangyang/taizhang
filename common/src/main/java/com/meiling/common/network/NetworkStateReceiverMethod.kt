package com.meiling.common.network

import com.meiling.common.network.enums.NetworkState
import java.lang.reflect.Method

internal class NetworkStateReceiverMethod(
    var any: Any? = null,
    var method: Method? = null,
    var monitorFilter: Array<NetworkState>? = null
)