package com.meiling.account.viewmodel

import android.app.Application
import com.meiling.account.bean.*
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


    //商品入库
    val storageGoodssave = BaseLiveData<String>()
    fun storageGood(goosClassify: StorageGoods) {


        request({ commodityService.storageGoodssave(goosClassify) }, storageGoodssave)
    }
    //获取时间分段列表
    val dateSplitlist = BaseLiveData<ArrayList<DateSplitList>>()
    fun dateSplit(dateSplit: DateSplit) {


        request({ commodityService.dateSplit(dateSplit) }, dateSplitlist)
    }
    //获取时间分段列表
    val goodsSplitlist = BaseLiveData<ArrayList<GoodsSplit>>()
    fun goodsSplit(dateSplit: DateSplit) {


        request({ commodityService.goodsSplit(dateSplit) }, goodsSplitlist)
    }

    //获取时间分段列表
    val storageGoodsdata = BaseLiveData<String>()
    fun goodsSplit(viewId: String) {


        request({ commodityService.storageGoods(viewId) }, storageGoodsdata)
    }

    //入库统计
    val statisticsdata = BaseLiveData<FormStatistics>()
    fun statistics(statistics: Statistics) {

        var statistics1 = Statistics();

        statistics1.startTime=statistics.startTime+" 00:00:00"
        statistics1.endTime=statistics.endTime+" 00:00:00"
        statistics1.storeViewId=statistics.storeViewId

        request({ commodityService.statistics(statistics1) }, statisticsdata)
    }
    //入库统计
    val rankingdata = BaseLiveData<ArrayList<Ranking>>()
    fun ranking(statistics: Statistics) {

        var statistics1 = Statistics();

        statistics1.startTime=statistics.startTime+" 00:00:00"
        statistics1.endTime=statistics.endTime+" 00:00:00"
        statistics1.storeViewId=statistics.storeViewId

        request({ commodityService.ranking(statistics1) }, rankingdata)
    }

    //入库统计图
    val periodTimedata = BaseLiveData<ArrayList<PeriodTimeItem>>()
    fun periodTime(statistics: Statistics) {

        var statistics1 = Statistics();

        statistics1.startTime=statistics.startTime+" 00:00:00"
        statistics1.endTime=statistics.endTime+" 00:00:00"
        statistics1.storeViewId=statistics.storeViewId

        request({ commodityService.periodTime(statistics1) }, periodTimedata)
    }




}