package com.meihao.kotlin.cashier.widgets.orderv4dialog

import android.app.Application
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import androidx.core.view.DragAndDropPermissionsCompat.request
import androidx.fragment.app.FragmentManager
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.network.ResultData
import com.meiling.common.network.data.OrderSendDetail
import com.meiling.common.network.service.homeService
import com.meiling.common.network.service.orderDisService
import com.meiling.oms.R
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


/**
 * 订单配送详情
 */
class OrderDistributionDetailDialog() : BaseNiceDialog() {
    open var isshow = false

    init {
        setGravity(Gravity.BOTTOM)
        setWidth(600)
        setOutCancel(true)
    }

    lateinit var ryOrderDisDetailAdapter: BaseQuickAdapter<OrderSendDetail.DeliveryConsumeLog, BaseViewHolder>
    lateinit var ryOrderDisDetailAllAdapter: BaseQuickAdapter<OrderSendDetail, BaseViewHolder>


    fun newInstance(
        type: Boolean,
        orderId: String,
        orderSendDetail: ArrayList<OrderSendDetail>
    ): OrderDistributionDetailDialog {
        var bundle = Bundle()
        bundle.putBoolean("type", type)
        bundle.putString("orderId", orderId)
        val fragment = OrderDistributionDetailDialog()
        fragment.arguments = bundle
        return fragment
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_order_dis_detail
    }


    override fun show(manager: FragmentManager?): BaseNiceDialog {
        return super.show(manager)
    }

    override fun dismiss() {
        super.dismiss()
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        EventBus.getDefault().register(this)
        val type = requireArguments().getBoolean("type")
        val orderId = requireArguments().getString("orderId")
        val imgOrderDisClose = holder?.getView<ImageView>(R.id.iv_detail_close)
        val txtOrderAddress = holder?.getView<TextView>(R.id.txt_order_dis_detail_address)
        val txtOrderName = holder?.getView<TextView>(R.id.txt_order_dis_detail_name)
        val txtOrderNameAddPhone = holder?.getView<TextView>(R.id.txt_order_dis_detail_phone)
        val ryOrderDisDetail = holder?.getView<RecyclerView>(R.id.rv_order_dis)
//        val rlOrderDisSendFinish = holder?.getView<RelativeLayout>(R.id.rlOrderDisSendFinish)
//        val txtSendReceive = holder?.getView<TextView>(R.id.txtSendReceive)
        ryOrderDisDetailAllAdapter =
            object :
                BaseQuickAdapter<OrderSendDetail, BaseViewHolder>(R.layout.item_order_send_detail) {
                override fun convert(holder: BaseViewHolder, orderSendDetail: OrderSendDetail) {
                    var ryOrderSendDisDetail =
                        holder.getView<RecyclerView>(R.id.ryOrderSendDisDetail)
                    if (ryOrderSendDisDetail.layoutManager == null) {
                        ryOrderSendDisDetail.layoutManager =
                            LinearLayoutManager(
                                requireContext(),
                                LinearLayoutManager.VERTICAL,
                                false
                            )
                    }
                    ryOrderDisDetailAdapter =
                        object :
                            BaseQuickAdapter<OrderSendDetail.DeliveryConsumeLog, BaseViewHolder>(R.layout.item_distribution) {
                            override fun convert(
                                holder: BaseViewHolder,
                                item: OrderSendDetail.DeliveryConsumeLog
                            ) {
                                holder.setText(R.id.txtOperateRemark, item.createTime)
                                holder.setText(R.id.txtOperateName, item.statusName)

                                var txtOperate = holder.getView<TextView>(R.id.txtOperate)
//                                var txtOrderDisCode = holder.getView<TextView>(R.id.txtOrderDisCode)
                                var txtOperateRemark =
                                    holder.getView<TextView>(R.id.txtOperateRemark)
//                                txtOperate.text = "骑手${item.deliveryName}/${item.deliveryPhone}"
                                //10 。发布订单（不做处理）
                                // 20//待骑手接单
                                //30 待骑手取货
                                //40 骑手已到店
                                //50 配送中
                                //70 已取消
                                //80 已收货
                                var view = holder.getView<ImageView>(R.id.imgView)
                                var viewLine = holder.getView<View>(R.id.viewLine)
                                var imgIcon = holder.getView<ImageView>(R.id.imgView)

                                when (item.status) {
                                    "20" -> {
                                        txtOperate.visibility = View.GONE
//                                        txtOrderDisCode.visibility = View.GONE
                                        view.setImageResource(R.mipmap.check_false)
                                        imgIcon.visibility = View.VISIBLE
                                        if (orderSendDetail.icon == "") {
                                            imgIcon.visibility = View.GONE
                                        } else {
                                            Glide.with(context)
                                                .load(orderSendDetail.icon)
                                                .transition(DrawableTransitionOptions().crossFade())
                                                .apply(
                                                    RequestOptions()
                                                        .diskCacheStrategy(DiskCacheStrategy.DATA)
                                                )
                                                .into(imgIcon)
                                        }
                                        if (orderSendDetail.channelDelNum != null || orderSendDetail.channelDelNum != "") {
                                            txtOperateRemark.visibility = View.VISIBLE
                                            txtOperateRemark.setText("物流单号: ${orderSendDetail.channelDelNum}")
                                        } else {
                                            txtOperateRemark.visibility = View.GONE
                                        }
                                    }
                                    "30", "40", "50", "60" -> {
//                                        txtOrderDisCode.visibility = View.VISIBLE
                                        txtOperate.visibility = View.VISIBLE
                                        txtOperate.visibility = View.VISIBLE
                                        txtOperate.text =
                                            "骑手${orderSendDetail.deliveryName}/${orderSendDetail.deliveryPhone}"
                                        if (orderSendDetail.pickupPassword == null || orderSendDetail.pickupPassword == "") {
//                                            txtOrderDisCode.visibility = View.GONE
                                        } else {
//                                            txtOrderDisCode.visibility = View.VISIBLE
//                                            txtOrderDisCode.text =
//                                                "取件码:${orderSendDetail.pickupPassword}"
                                        }
                                        view.setImageResource(R.mipmap.check_false)
                                        imgIcon.visibility = View.VISIBLE
                                        Glide.with(context)
                                            .load(orderSendDetail.icon)
                                            .transition(DrawableTransitionOptions().crossFade())
                                            .apply(
                                                RequestOptions()
                                                    .diskCacheStrategy(DiskCacheStrategy.DATA)
                                            )
                                            .into(imgIcon)
                                        if (orderSendDetail.channelDelNum != null || orderSendDetail.channelDelNum != "") {
                                            txtOperateRemark.visibility = View.VISIBLE
                                            txtOperateRemark.setText("物流单号: ${orderSendDetail.channelDelNum}")
                                        } else {
                                            txtOperateRemark.visibility = View.GONE
                                        }
                                    }
                                    "70" -> {
                                        txtOperate.visibility = View.VISIBLE
//                                        txtOrderDisCode.visibility = View.GONE
                                        txtOperateRemark.visibility = View.GONE
                                        txtOperateRemark.text = "配送取消，原因：您已成功取消配送"
                                        txtOperate.text = "配送取消"
                                        imgIcon.visibility = View.VISIBLE
                                        view.setImageResource(R.mipmap.icon_dis_cancel)
                                        imgIcon.setImageResource(R.mipmap.icon_dis_cancel)
                                    }
                                    "80" -> {
                                        txtOperate.visibility = View.GONE
//                                        txtOrderDisCode.visibility = View.GONE
                                        txtOperateRemark.visibility = View.GONE
                                        view.setImageResource(R.mipmap.icon_finish)
                                        imgIcon.visibility = View.GONE
                                    }


                                }

                                if (holder.adapterPosition == ryOrderDisDetailAdapter.data.size - 1) {
                                    viewLine.visibility = View.GONE
                                } else {
                                    viewLine.visibility = View.VISIBLE
                                }

//                viewLine.background = resources.getColor(R.color.Red,Resources.Theme)
                            }

                        }
                    ryOrderSendDisDetail!!.adapter = ryOrderDisDetailAdapter
                    ryOrderDisDetailAdapter.setList(orderSendDetail.deliveryConsumeLogs)
                }
            }
        ryOrderDisDetail!!.adapter = ryOrderDisDetailAllAdapter

        var bs = BaseLiveData<ArrayList<OrderSendDetail>>()
        BaseViewModel(Application()).request(
            { orderDisService.getLogisticsOrderDetail(orderId!!) },
            bs
        )

        bs.onSuccess.observe(this) {
            ryOrderDisDetailAllAdapter.setList(it)
        }


//        MainRetrofit.service.getLogisticsOrderDetail(orderId!!)
//            .ss(null, null, false, onSuccess = {
////                if (it.data[0].type == "30"&&it.data[0].status!="80") {
////                    rlOrderDisSendFinish?.visibility = View.VISIBLE
////                } else {
////                    rlOrderDisSendFinish?.visibility = View.GONE
////                }
//                if (!it.data.isNullOrEmpty()) {
//                    ryOrderDisDetailAllAdapter.setList(it.data)
//                    txtOrderAddress!!.text = it.data[0].recvAddr
//                    txtOrderNameAddPhone!!.text = "${it.data[0].recvName}/${it.data[0].recvPhone}"
//
//                }
//            }, onError = {
//            })
//
//        if (type) {
//            rlOrderDisSendFinish!!.visibility = View.VISIBLE
//        } else {
//            rlOrderDisSendFinish!!.visibility = View.GONE
//        }


//        txtSendReceive!!.setOnClickListener {
//            MainRetrofit.service.getFinishSendOrder(orderId!!).ss(null, null, onSuccess = {
//                showToast("完成配送", CustomToast.CustomType.SUCCESS)
//                EventBus.getDefault().post(EventBusRefreshOrder())
//                dismiss()
//            }, onError = {
//
//            })
//
//        }
        imgOrderDisClose!!.setOnClickListener {
            dismiss()
        }


    }

    override fun onDestroy() {
        super.onDestroy()
        EventBus.getDefault().unregister(this)
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun eventBusCloseOrderDistributionDialog(event: String) {
    }
}

