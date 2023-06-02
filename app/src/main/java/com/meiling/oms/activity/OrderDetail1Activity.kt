package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.OrderDetailDto
import com.meiling.common.network.data.OrderGoodsVo
import com.meiling.oms.adapter.OrderBaseShopListAdapter
import com.meiling.oms.databinding.ActivityOrderZitiDetailBinding
import com.meiling.oms.viewmodel.BaseOrderFragmentViewModel
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast

//自提订单详情
class OrderDetail1Activity :
    BaseActivity<BaseOrderFragmentViewModel, ActivityOrderZitiDetailBinding>() {

    private lateinit var orderDisAdapter: OrderBaseShopListAdapter
    lateinit var orderDetailDto: OrderDetailDto
    override fun initView(savedInstanceState: Bundle?) {
        mViewModel.getOrderDetail(intent.getStringExtra("orderViewId").toString())
        orderDisAdapter = OrderBaseShopListAdapter()
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

    override fun createObserver() {
        mViewModel.printDto.onStart.observe(this) {
        }
        mViewModel.printDto.onSuccess.observe(this) {
            disLoading()
            showToast("已发送打印任务")
        }
        mViewModel.printDto.onError.observe(this) {
            showToast(it.msg)
        }

        mViewModel.orderDetailDto.onStart.observe(this) {}
        mViewModel.orderDetailDto.onSuccess.observe(this) {
            orderDetailDto = it
            orderDisAdapter.setList(it.goodsVoList as MutableList<OrderGoodsVo>)
        }
        mViewModel.orderDetailDto.onError.observe(this) {}

    }

}