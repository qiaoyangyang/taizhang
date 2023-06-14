package com.meiling.account.activity

import android.content.Intent
import android.net.Uri
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.hjq.permissions.OnPermissionCallback
import com.hjq.permissions.XXPermissions
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.OrderDetailDto
import com.meiling.common.network.data.OrderGoodsVo
import com.meiling.common.utils.GlideAppUtils
import com.meiling.common.utils.PermissionUtilis
import com.meiling.common.utils.TextDrawableUtils
import com.meiling.account.R
import com.meiling.account.adapter.OrderBaseShopListAdapter
import com.meiling.account.databinding.ActivityOrderZitiDetailBinding
import com.meiling.account.dialog.DataTipDialog
import com.meiling.account.dialog.MineExitDialog
import com.meiling.account.viewmodel.BaseOrderFragmentViewModel
import com.meiling.account.widget.copyText
import com.meiling.account.widget.setSingleClickListener
import com.meiling.account.widget.showToast

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
    var deliveryConsumeType = 2

    override fun initListener() {

        mDatabind.included.tvRevocation.setSingleClickListener {
            if (orderDetailDto?.order?.isValid == 1) {
                val dialog: MineExitDialog =
                    MineExitDialog().newInstance(
                        "温馨提示",
                        "您确认要忽略该订单吗？ \n忽略后可去「订单查询」中查找到该订单？",
                        "取消",
                        "确认",
                        false
                    )
                dialog.setOkClickLister {
                    mViewModel.invalid(
                        orderDetailDto?.order!!.viewId.toString(),
                        "0"
                    )
                    dialog.dismiss()
                }
                dialog.show(supportFragmentManager)
            } else {
                mViewModel.invalid(orderDetailDto?.order!!.viewId.toString(), "1")
            }
        }
        mDatabind.included.tvGoOn.setSingleClickListener { mViewModel.orderFinish(orderDetailDto?.order!!.viewId.toString()) }
        mDatabind.included.ivCallPhone.setSingleClickListener {
            XXPermissions.with(this).permission(
                PermissionUtilis.Group.PHONE_CALL
            )
                .request(object : OnPermissionCallback {
                    override fun onGranted(
                        permissions: MutableList<String>,
                        allGranted: Boolean
                    ) {
                        if (!allGranted) {
                            showToast("获取部分权限成功，但部分权限未正常授予")
                            return
                        }
                        dialPhoneNumber(orderDetailDto.order?.recvPhone!!)
                    }

                    override fun onDenied(
                        permissions: MutableList<String>,
                        doNotAskAgain: Boolean
                    ) {
                        if (doNotAskAgain) {
                            // 如果是被永久拒绝就跳转到应用权限系统设置页面
                            XXPermissions.startPermissionActivity(
                                this@OrderDetail1Activity,
                                permissions
                            )
                        } else {
                            showToast("授权失败，请检查权限")
                        }

                    }

                })
        }
        mDatabind.included.txtOrderPlatformAccount.setSingleClickListener {
            DataTipDialog().newInstance("部分外卖平台存在服务费，仅记录展示，非小喵来客收取", "平台服务费")
                .show(supportFragmentManager)
        }
        mDatabind.included.txtOrderAccount.setSingleClickListener {
            DataTipDialog().newInstance("本单支付入账=顾客实际支付-平台服务费", "本单支付入账")
                .show(supportFragmentManager)
        }
        mDatabind.included.txtOrderID.setSingleClickListener {
            copyText(
                this, mDatabind.included.txtOrderID.text.toString()
            )
            showToast("复制成功")
        }
        mDatabind.included.txtOrderId1.setSingleClickListener {
            copyText(this, mDatabind.included.txtOrderId1.text.toString())

            showToast("复制成功")
        }
        mDatabind.included.btnPrintReceipt.setSingleClickListener {
            when (orderDetailDto?.order!!.logisticsStatus) {
                "70" -> {
                    mViewModel.getPrint(
                        orderDetailDto?.order?.viewId.toString(),
                        orderDetailDto?.order?.shopId.toString(),
                        "3"
                    )
                }
                else -> {
                    mViewModel.getPrint(
                        orderDetailDto?.order?.viewId.toString(),
                        orderDetailDto?.order?.shopId.toString(),
                        "1"
                    )
                }

            }

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
            Log.d("TAG", "createObserver:${it.goodsVoList.toString()} ")
            orderDisAdapter.setList(it.goodsVoList as MutableList<OrderGoodsVo>)
            orderDisAdapter.notifyDataSetChanged()
            orderDetailDto = it
            // logisticsStatus 待配送0 待抢单20 待取货30 配送中50 已取消70 已送达80
            var deliveryStatusName = ""

            when (it.order!!.logisticsStatus) {
                "0" -> {
                    TextDrawableUtils.setLeftDrawable(
                        mDatabind.included.tvStatusTitle,
                        R.drawable.to_be_delivered
                    )
                    deliveryStatusName = "待配送"
                    mDatabind.included.tvRevocation.visibility = View.VISIBLE

                    mDatabind.included.btnChangeAddress.visibility = View.GONE
                    if (it.order?.isValid == 0) {
                        mDatabind.included.tvRevocation.text = "取消忽略"
                        mDatabind.included.tvGoOn.visibility = View.GONE
                    } else {
                        mDatabind.included.tvGoOn.visibility = View.VISIBLE
                        mDatabind.included.tvRevocation.text = "忽略订单"
                    }
                }
                "20" -> {

                }

                "70" -> {

                }
                "30", "50", "80" -> {
                    TextDrawableUtils.setLeftDrawable(
                        mDatabind.included.tvStatusTitle,
                        R.drawable.daito_be_delivered_80
                    )
                    deliveryStatusName = "已完成"
                    mDatabind.included.tvRevocation.visibility = View.GONE
                    mDatabind.included.tvGoOn.visibility = View.GONE
                    mDatabind.included.btnChangeAddress.visibility = View.GONE
                }

            }

            mDatabind.included.tvStatusTitle.text = deliveryStatusName
            mDatabind.included.txtActualMoney.text = "¥" + it?.order?.actualPayPrice//顾客实际支付
            mDatabind.included.txtPlatformMoney.text = "¥" + it?.order?.platformServiceFee//平台服务费
            mDatabind.included.txtOrderAccountMoney.text = "¥" + it?.order?.actualIncome//本单支付入账
            mDatabind.included.txtArriveTime.text = it?.order?.arriveTimeDate//送达时间
            mDatabind.included.txtInsertOrderTime.text = it?.order?.channelCreateTime//下单时间
            mDatabind.included.txtOrderDeliverStore.text = it?.poi?.name//发货门店
            mDatabind.included.txtOrderChannelStore.text = it?.shop?.name//渠道店铺
            mDatabind.included.txtOrderID.text = it?.order?.viewId//订单编号
            mDatabind.included.txtOrderId1.text = it?.order?.channelOrderNum//三方编号
            mDatabind.included.txtBaseOrderRemark.text = it?.order?.remark//备注
            mDatabind.included.txtBaseOrderDeliveryPhone.text = it.order?.recvPhone//电话
            mDatabind.included.txtOrderDeliveryName.text = it.order?.recvName//电话
            mDatabind.included.txtCheckMap.visibility = View.GONE//电话
            mDatabind.included.txtBaseOrderDeliveryAddress.visibility = View.GONE
            mDatabind.included.txtOrderStore.text = "${it.channelName} "//渠道
            mDatabind.included.txtBaseOrderNo.text = "${it.order?.channelDaySn} "//单号
            mDatabind.included.txtBaseOrderDeliveryTime.text = "${it.order?.arriveTimeDate} "//时间
            var sumNumber: Int = it.goodsTotalNum ?: 0
//            for (ne in it.goodsVoList!!) {
//                sumNumber += ne?.number!!
//            }
            mDatabind.included.tvCommon.text = "商品${sumNumber}件，共${it.order?.totalPrice}元"//时间
            GlideAppUtils.loadUrl(
                mDatabind.included.imgOrderChannelIcon,
                it.channelLogo ?: "https://static.igoodsale.com/%E7%BA%BF%E4%B8%8B.svg"
            )
            //deliveryType == "1" ,"3" 待配送 2:自提
            if (it.order!!.deliveryType == "2") {

                mDatabind.included.txtBaseOrderDelivery1.text = "前自提"
            } else {
                mDatabind.included.txtBaseOrderDelivery1.text = "前送达"
            }
        }
        mViewModel.orderDetailDto.onError.observe(this) {}

        mViewModel.orderFinish.onSuccess.observe(this) {
            disLoading()
             showToast("订单自提完成")
            finish()
        }
        mViewModel.orderFinish.onError.observe(this) {
            disLoading()
            showToast(it.msg)
        }
        mViewModel.invalidDto.onSuccess.observe(this) {
            if (orderDetailDto?.order?.isValid == 1) {
                showToast("订单忽略成功")
            } else {
                showToast("取消忽略成功")
            }
            finish()
        }
        mViewModel.invalidDto.onError.observe(this) {
            showToast(it.msg)
        }
    }

    private fun dialPhoneNumber(phoneNumber: String) {
        val dialIntent = Intent(Intent.ACTION_CALL, Uri.parse("tel:${phoneNumber}"))
        startActivity(dialIntent)
    }
}