package com.meiling.account.viewmodel

import android.app.Application
import com.meiling.account.bean.Goods
import com.meiling.account.bean.GoodsBean
import com.meiling.account.bean.GoodsController
import com.meiling.account.bean.GoosClassify
import com.meiling.account.service.commodityService
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.network.data.ByTenantId
import com.meiling.common.network.data.userInfoBean
import com.meiling.common.network.service.loginService
import com.meiling.common.network.service.meService
import com.meiling.common.utils.MMKVUtils

class MainViewModel(application: Application) : BaseViewModel(application) {


    //用户信息详情
    val userBean = BaseLiveData<userInfoBean>()
    fun userInfo() {
        request({ loginService.userInfo() }, userBean)
    }


    //获取分类列表
    val goosClassify = BaseLiveData<ArrayList<GoosClassify>>()
    fun sorlistt() {

        request({ commodityService.sorlistt() }, goosClassify)
    }

    //商品管理
    val goods = BaseLiveData<GoodsBean>()
    fun goodslistt(goosClassify: GoodsController) {


        request({ commodityService.goodslistt(goosClassify) }, goods)
    }


}