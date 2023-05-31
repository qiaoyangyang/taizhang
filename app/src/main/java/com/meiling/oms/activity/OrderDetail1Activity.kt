package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.launcher.ARouter
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.GoodsListVo
import com.meiling.oms.adapter.OrderBaseShopListAdapter
import com.meiling.oms.databinding.ActivityOrderZitiDetailBinding
import com.meiling.oms.viewmodel.OrderCreateViewModel
import com.meiling.oms.widget.setSingleClickListener

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
        mDatabind.included.btnChangeAddress.setSingleClickListener {

//            if (item.order!!.deliveryType != "2") {
//                ARouter.getInstance().build("/app/OrderChangeAddressActivity")
//                    .withString("receiveTime", item.order?.arriveTimeDate)
//                    .withString("receiveName", item.order?.recvName)
//                    .withString("receivePhone", item.order?.recvPhone)
//                    .withString("receiveAddress", item.order?.recvAddr)
//                    .withString("receiveRemark", item.order?.remark)
//                    .withString("lat", item.order?.lat)
//                    .withString("lon", item.order?.lon)
//                    .withString("orderId", item.order?.viewId)
//                    .withInt("index", holder.adapterPosition).navigation()
//
//            }
        }
    }

}