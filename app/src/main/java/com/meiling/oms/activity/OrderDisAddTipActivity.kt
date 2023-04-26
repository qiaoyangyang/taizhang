package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.bumptech.glide.Glide
import com.bumptech.glide.load.engine.DiskCacheStrategy
import com.bumptech.glide.load.resource.drawable.DrawableTransitionOptions
import com.bumptech.glide.request.RequestOptions
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.*
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityOrderDisTipBinding
import com.meiling.oms.dialog.MineExitDialog
import com.meiling.oms.viewmodel.OrderDisFragmentViewModel
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast

@Route(path = "/app/OrderDisAddTipActivity")
class OrderDisAddTipActivity :
    BaseActivity<OrderDisFragmentViewModel, ActivityOrderDisTipBinding>() {

    lateinit var ryOrderDisAddTipAdapter: BaseQuickAdapter<OrderSendAddTips, BaseViewHolder>
    lateinit var content: OrderDto.Content
    override fun initView(savedInstanceState: Bundle?) {
        // 1 TC  2 自提 3 全国
        content = intent.getSerializableExtra("kk") as OrderDto.Content

        mViewModel.getOrderAndPoiDeliveryDate(
            content.order?.poiId!!,
            content.order!!.viewId!!,
            logisticsType = when (content.order?.deliveryType) {
                "1" -> "TC"
                "2" -> "EXPRESS"
                "3" -> "SELF"
                else -> {
                    "TC"
                }
            }
        )
        mViewModel.getLogisticsOrder(content.deliveryConsume?.viewId ?: "0")

        ryOrderDisAddTipAdapter =
            object :
                BaseQuickAdapter<OrderSendAddTips, BaseViewHolder>(R.layout.item_recy_distribution_add_tip) {
                override fun convert(holder: BaseViewHolder, item: OrderSendAddTips) {
                    holder.setText(R.id.txtSendName, item.channelName)
                    holder.setText(R.id.txtSendMoney, "¥ ${item.channelAmount}")
                    holder.setText(R.id.txtAddTipsMoney, "已加小费:${item.tip}元")
                    var editText = holder.getView<TextView>(R.id.edtAddTipShow)
                    var iconAddTip = holder.getView<ImageView>(R.id.iconAddTip)
//                    item.addtip = item.tip
                    editText.text = "${item.Addtip}"
                    Glide.with(context)
                        .load(item.iconUrl)
                        .transition(DrawableTransitionOptions().crossFade())
                        .apply(
                            RequestOptions()
                                .diskCacheStrategy(DiskCacheStrategy.DATA)
                        )
                        .into(iconAddTip)
                }
            }

        mDatabind.rvPlatform.adapter = ryOrderDisAddTipAdapter

        ryOrderDisAddTipAdapter.addChildClickViewIds(
            R.id.txtAddTipMinus,
            R.id.txtAddTipPlus,
            R.id.txt_add
        )
        ryOrderDisAddTipAdapter.setOnItemChildClickListener { adapter, view, position ->
            var addTipShow = ryOrderDisAddTipAdapter.data[position].Addtip.toInt()
            when (view.id) {
                R.id.txtAddTipMinus -> {
                    if (addTipShow == 1) {
//                        showToast("不能在减了")
                    } else {
                        ryOrderDisAddTipAdapter.data[position].Addtip =
                            (addTipShow - 1).toString()
                    }
                    ryOrderDisAddTipAdapter.notifyItemChanged(position)
                }
                R.id.txtAddTipPlus -> {
//                    if (addTipShow+ryOrderDisAddTipAdapter.data[position].tip > ryOrderDisAddTipAdapter.data[position].channelAmount.toInt()) {
//                        showToast("加小费金额不能大于配送金额")
//                    }
                    ryOrderDisAddTipAdapter.data[position].Addtip = (addTipShow + 1).toString()
                    ryOrderDisAddTipAdapter.notifyItemChanged(position)
                }
                R.id.txt_add -> {
                    if (ryOrderDisAddTipAdapter.data[position].tip.toInt() + ryOrderDisAddTipAdapter.data[position].Addtip.toInt() > 30) {
                        showToast("您已添加${ryOrderDisAddTipAdapter.data[position].tip}元小费，最多还能添加${30 - ryOrderDisAddTipAdapter.data[position].Addtip.toInt()}元")
                        return@setOnItemChildClickListener
                    }
                    val dialog: MineExitDialog =
                        MineExitDialog().newInstance("温馨提示", "确定加  ${ryOrderDisAddTipAdapter.data[position].Addtip}元 小费吗？", "取消", "确认", false)
                    dialog.setOkClickLister {
                        dialog.dismiss()
                        mViewModel.setAddTips(
                            OrderSendAddTipRequest(
                                content.deliveryConsume?.id ?: "0",
                                content.order?.poiId ?: "0",
                                ryOrderDisAddTipAdapter.data[position].channelType,
                                ryOrderDisAddTipAdapter.data[position].Addtip
                            )
                        )
                    }
                    dialog.show(supportFragmentManager)


                }
            }
        }
        mDatabind.btnCancelOrder.setSingleClickListener {
            val dialog: MineExitDialog =
                MineExitDialog().newInstance("温馨提示", "确定取消配送吗？", "取消", "确认", false)
            dialog.setOkClickLister {
                mViewModel.cancelOrder(
                    CancelOrderSend(
                        deliveryConsumerId = content.deliveryConsume!!.id ?: "0",
                        poiId = content.order!!.poiId ?: "0",
                        stationChannelId = content.deliveryConsume!!.stationChannelId ?: "0"
                    )
                )
            }
            dialog.show(supportFragmentManager)

        }
    }

    override fun createObserver() {
        mViewModel.orderSendAddress.onStart.observe(this) {}
        mViewModel.orderSendAddress.onSuccess.observe(this) {
            mDatabind.txtOrderDisName.text = it.poiName
            mDatabind.txtOrderDisPhone.text = it.poiPhone
            mDatabind.txtOrderDisAddress.text = it.poiAddr
            mDatabind.txtOrderDisRecName.text = it.recvName
            mDatabind.txtOrderDisRecPhone.text = it.recvPhone
            mDatabind.txtOrderDisRecAddress.text =  it?.recvAddr?.replace("@@", "")
        }
        mViewModel.orderSendAddress.onError.observe(this) {
            showToast("${it.msg}")
        }

        mViewModel.addDto.onStart.observe(this) {}
        mViewModel.addDto.onSuccess.observe(this) {
            showToast("加小费成功")
            mViewModel.getLogisticsOrder(content.deliveryConsume?.viewId ?: "0")
        }
        mViewModel.addDto.onError.observe(this) {
            showToast(it.msg)
        }

        mViewModel.addDtoPat.onStart.observe(this) {
        }
        mViewModel.addDtoPat.onSuccess.observe(this) {
            it.forEach { orderTip ->
                orderTip.Addtip = "5"
            }
            ryOrderDisAddTipAdapter.setList(it as MutableList<OrderSendAddTips>)
            ryOrderDisAddTipAdapter.notifyDataSetChanged()
        }
        mViewModel.addDtoPat.onError.observe(this) {
            showToast(it.msg)
        }

        mViewModel.cancelOrderDto.onStart.observe(this) {
            showLoading("请求中")
        }
        mViewModel.cancelOrderDto.onSuccess.observe(this) {
            disLoading()
            showToast("订单已取消")
            finish()
        }
        mViewModel.cancelOrderDto.onError.observe(this) {
            disLoading()
            showToast(it.msg)
        }

    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityOrderDisTipBinding {
        return ActivityOrderDisTipBinding.inflate(layoutInflater)
    }


    override fun initListener() {
    }

}