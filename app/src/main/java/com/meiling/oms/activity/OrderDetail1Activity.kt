package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.GoodsListVo
import com.meiling.oms.adapter.OrderBaseShopListAdapter
import com.meiling.oms.databinding.ActivityOrderZitiDetailBinding
import com.meiling.oms.viewmodel.OrderCreateViewModel

//自提订单详情
class OrderDetail1Activity : BaseActivity<OrderCreateViewModel, ActivityOrderZitiDetailBinding>() {

    private lateinit var orderDisAdapter: OrderBaseShopListAdapter

    override fun initView(savedInstanceState: Bundle?) {
        orderDisAdapter = OrderBaseShopListAdapter()
        var goods = ArrayList<GoodsListVo>()
        goods.add(GoodsListVo(gname = "张三"))
        goods.add(GoodsListVo(gname = "张三1"))
        goods.add(GoodsListVo(gname = "张三2"))
        goods.add(GoodsListVo(gname = "张三3"))
        goods.add(GoodsListVo(gname = "张三3"))
        goods.add(GoodsListVo(gname = "张三3"))
        goods.add(GoodsListVo(gname = "张三3"))
        goods.add(GoodsListVo(gname = "张三3"))
        goods.add(GoodsListVo(gname = "张三3"))
        goods.add(GoodsListVo(gname = "张三3"))
        goods.add(GoodsListVo(gname = "张三3"))
        goods.add(GoodsListVo(gname = "张三3"))
        goods.add(GoodsListVo(gname = "张三3"))
        goods.add(GoodsListVo(gname = "张三3"))
        goods.add(GoodsListVo(gname = "张三3"))
        goods.add(GoodsListVo(gname = "张三3"))
        orderDisAdapter.setList(goods)
        mDatabind.included.recyShopList.adapter = orderDisAdapter
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityOrderZitiDetailBinding {
        return ActivityOrderZitiDetailBinding.inflate(layoutInflater)
    }


    private var b = true

    override fun initListener() {

    }

}