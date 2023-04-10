package com.meiling.oms.fragment

import android.graphics.drawable.PictureDrawable
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.alibaba.android.arouter.launcher.ARouter
import com.bumptech.glide.Glide
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.fragment.BaseFragment
import com.meiling.common.network.data.OrderDto
import com.meiling.common.utils.svg.SvgSoftwareLayerSetter
import com.meiling.oms.R
import com.meiling.oms.databinding.FragmentBaseOrderBinding
import com.meiling.oms.viewmodel.BaseOrderFragmentViewModel
import com.meiling.oms.widget.copyText
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast


class BaseOrderFragment : BaseFragment<BaseOrderFragmentViewModel, FragmentBaseOrderBinding>() {


    private lateinit var orderDisAdapter: BaseQuickAdapter<OrderDto.Content, BaseViewHolder>
    lateinit var orderGoodsListAdapter: BaseQuickAdapter<OrderDto.Content.GoodsVo, BaseViewHolder>

    var pageIndex = 1;

    companion object {
        fun newInstance(type: String, isSelect: Boolean): Fragment {
            val baseOrderFragment = BaseOrderFragment()
            val bundle = Bundle()
            bundle.putString("type", type)
            bundle.putBoolean("isSelect", isSelect)
            baseOrderFragment.arguments = bundle
            return baseOrderFragment
        }
    }


    override fun initView(savedInstanceState: Bundle?) {
        requireArguments().getString("type").toString()
        orderDisAdapter =
            object : BaseQuickAdapter<OrderDto.Content, BaseViewHolder>(R.layout.item_home_order),LoadMoreModule {
                override fun convert(holder: BaseViewHolder, item: OrderDto.Content) {
                    holder.setText(R.id.txt_order_delivery_type, item.orderName)
                    val imgShopCopy = holder.getView<ImageView>(R.id.img_shop_copy)
                    val changeOrder = holder.getView<TextView>(R.id.txt_change_order)
                    val btnSendDis = holder.getView<TextView>(R.id.txt_order_dis)
                    val showMsg = holder.getView<TextView>(R.id.txt_shop_show_all)
                    val hideMsg = holder.getView<TextView>(R.id.txt_show_hide)
                    val copyOrderId = holder.getView<TextView>(R.id.txt_copy_order)
                    val orderId = holder.getView<TextView>(R.id.txt_order_id)
                    val channelLogoImg = holder.getView<ImageView>(R.id.img_order_icon)
                    holder.setText(R.id.txt_order_delivery_name, item.order?.recvName)
                    holder.setText(R.id.txt_order_delivery_phone, item.order?.recvPhone)
                    holder.setText(R.id.txt_order_delivery_address, item.order?.recvAddr)
                    holder.setText(R.id.txt_order_num, "#${item.order?.channelDaySn}")
                    holder.setText(R.id.txt_shop_actual_money, "${item.order?.actualIncome}")
                    holder.setText(R.id.txt_order_delivery_time, "${item.order?.arriveTimeDate}")
                    holder.setText(R.id.txt_pay_money, "¥${item.order?.payPrice}")
                    holder.setText(R.id.txt_pay_fee, "¥${item.order?.platformServiceFee}")
                    holder.setText(R.id.txt_order_total_money, "¥${item.order?.actualIncome}")

                    //加载svg图片
                    Glide.with(context).`as`(PictureDrawable::class.java)
                        .listener(SvgSoftwareLayerSetter())
                        .load(item.channelLogo)
                        .into(channelLogoImg)

                    holder.setText(
                        R.id.txt_order_delivery_state,
                        "${item.order?.deliveryStatusName}"
                    )
                    orderId.text = "${item.order?.viewId}"
                    holder.setText(R.id.txt_order_remark, "${item.order?.remark}")
                    holder.setText(
                        R.id.txt_time_shop,
                        "${item.order?.createTime}下单${item.channelName}店铺"
                    )
                    holder.setText(R.id.txt_shop_name, "${item.shopName}")

                    if (item.order?.deliveryType == 1 || item.order?.deliveryType == 3) {
                        changeOrder.visibility = View.VISIBLE
                        holder.setText(R.id.txt_order_delivery_type, "配送")
                        holder.setGone(R.id.txt_order_dis, false)
                    } else {
                        holder.setText(R.id.txt_order_delivery_type, "自提")
                        holder.setGone(R.id.txt_order_dis, true)
                        changeOrder.visibility = View.GONE
                    }
                    showMsg.setSingleClickListener {
                        holder.setGone(R.id.cos_hide, true)
                        holder.setGone(R.id.cos_shop_show, false)
                    }
                    hideMsg.setSingleClickListener {
                        holder.setGone(R.id.cos_hide, false)
                        holder.setGone(R.id.cos_shop_show, true)
                    }
                    copyOrderId.setSingleClickListener {
                        copyText(context, orderId.text.toString())
                        showToast("复制成功")
                    }

                    changeOrder.setOnClickListener {
                        ARouter.getInstance().build("/app/OrderChangeAddressActivity").navigation()
                    }



                    imgShopCopy.setOnClickListener {
                        copyText(
                            context,
                            "订单来源：" + "${item.channelName} \n" +
                                    "门店名称${item.shopName}\n" +
                                    "订单编号${item.order?.viewId}\n" +
                                    "-------\n" +
                                    "商品信息${item.goodsVoList.toString()}\n" +
                                    "-------\n" +
                                    "收货时间${item.order?.arriveTimeDate}\n" +
                                    "收货人${item.order?.recvName}${item.order?.recvPhone}\n" +
                                    "收货地址${item.order?.recvAddr}\n" +
                                    "-------\n" +
                                    "备注${item.order?.remark}\n"
                        )
//                        ToastUtils.showLong("复制成功")
                        showToast("复制成功")
                    }
                    btnSendDis.setOnClickListener {
                        if (item.order?.deliveryType == 1 || item.order?.deliveryType == 3) {
                            ARouter.getInstance().build("/app/OrderDisActivity").withSerializable("kk",item).navigation()
                        } else {
                            ARouter.getInstance().build("/app/OrderDisAddTipActivity")
                                .navigation()
                        }
                    }

                    var ryOrderSendDisDetail =
                        holder.getView<RecyclerView>(R.id.rv_shop)
                    if (ryOrderSendDisDetail.layoutManager == null) {
                        ryOrderSendDisDetail.layoutManager =
                            LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                    }
                    orderGoodsListAdapter =
                        object :
                            BaseQuickAdapter<OrderDto.Content.GoodsVo, BaseViewHolder>(R.layout.item_home_order_shop),
                            LoadMoreModule {
                            override fun convert(
                                holder: BaseViewHolder,
                                item: OrderDto.Content.GoodsVo
                            ) {
                                holder.setText(R.id.txt_order_shop_name, item.gname)
                                holder.setText(R.id.txt_order_shop_spec, item.specs)
                                holder.setText(R.id.txt_order_shop_num, "X" + item.number)
                                holder.setText(R.id.txt_order_shop_price, "¥" + item.price)
                            }
                        }
                    ryOrderSendDisDetail!!.adapter = orderGoodsListAdapter
                    if (item.goodsVoList?.isNotEmpty() == true) {
                        orderGoodsListAdapter.setList(item.goodsVoList as MutableList<OrderDto.Content.GoodsVo>)
                        var sum: Double = 0.0
                        var sumNumber: Int = 0
                        for (ne in item.goodsVoList!!) {
                            sum += ne?.totalPrice!!
                            sumNumber += ne?.number!!
                        }
                        holder.setText(
                            R.id.txt_order_shop_msg,
                            "${item.goodsVoList?.size}种商品，共${sumNumber}件"
                        )
                        holder.setText(R.id.txt_total_money, "¥${sum}")
                    }
                }
            }
        mDatabind.rvOrderList.adapter = orderDisAdapter

    }

    var list = ArrayList<String>()

    override fun initData() {

        mDatabind.sflLayout.setOnRefreshListener {
            mViewModel.orderList(
                logisticsStatus = requireArguments().getString("type").toString(),
                startTime = "2023-04-06",
                endTime = "2023-04-06",
                businessNumberType = "1",
                pageIndex = 1,
                pageSize = "1",
                orderTime = "1",
                deliverySelect = "0",
                isValid = "0",
                businessNumber = ""
            )
            mViewModel.statusCount(
                logisticsStatus = "",
                startTime = "2023-04-06",
                endTime = "2023-04-06",
                businessNumberType = "1",
                pageIndex = "1",
                pageSize = "1",
                orderTime = "1",
                deliverySelect = "0",
                isValid = "0",
                businessNumber = ""
            )
        }

        mViewModel.orderList(
            logisticsStatus = requireArguments().getString("type").toString(),
            startTime = "2023-04-06",
            endTime = "2023-04-06",
            businessNumberType = "1",
            pageIndex = 1,
            pageSize = "1",
            orderTime = "1",
            deliverySelect = "0",
            isValid = "0",
            businessNumber = "",
        )
        mViewModel.statusCount(
            logisticsStatus = "",
            startTime = "2023-04-06",
            endTime = "2023-04-06",
            businessNumberType = "1",
            pageIndex = "1",
            pageSize = "1",
            orderTime = "1",
            deliverySelect = "0",
            isValid = "0",
            businessNumber = ""
        )
        orderDisAdapter.loadMoreModule.setOnLoadMoreListener {
            pageIndex++
            mViewModel.orderList(
                logisticsStatus = requireArguments().getString("type").toString(),
                startTime = "2023-04-06",
                endTime = "2023-04-06",
                businessNumberType = "1",
                pageIndex = pageIndex,
                pageSize = "1",
                orderTime = "1",
                deliverySelect = "0",
                isValid = "0",
                businessNumber = "",
                selectText = ""
            )
        }


    }

    override fun createObserver() {
        mViewModel.orderList.onStart.observe(this) {
            showLoading("请求中。。。")
        }
        mViewModel.orderList.onSuccess.observe(this) {
            dismissLoading()
            mDatabind.sflLayout.finishRefresh()
            if (it.pageIndex == 1){
                if (it.content.isNullOrEmpty()) {
                    orderDisAdapter.setEmptyView(R.layout.order_search_empty)
                } else {
                    orderDisAdapter.setList(it.content as MutableList<OrderDto.Content>)
                }
            }else{
                orderDisAdapter.addData(it.content as MutableList<OrderDto.Content>)
            }
            if (it.content.size < 1) {
                orderDisAdapter.footerWithEmptyEnable = false
                orderDisAdapter.loadMoreModule.loadMoreEnd()
            }else{
                orderDisAdapter.loadMoreModule.loadMoreComplete()
            }



            Log.e("order", "createObserver: " + it)
        }
        mViewModel.orderList.onError.observe(this) {
            dismissLoading()
            mDatabind.sflLayout.finishRefresh()
            showToast("${it.message}")
        }

        mViewModel.statusCountDto.onSuccess.observe(this) {
            dismissLoading()


            Log.e("order", "createObserver: " + it)
        }
        mViewModel.statusCountDto.onError.observe(this) {
            dismissLoading()
            showToast("${it.message}")
        }

    }


    override fun getBind(inflater: LayoutInflater): FragmentBaseOrderBinding {
        return FragmentBaseOrderBinding.inflate(inflater)
    }

}