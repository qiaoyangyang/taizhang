package com.meiling.oms.activity

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.blankj.utilcode.util.TimeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.DealMenu
import com.meiling.common.network.data.WriteoffhistoryPageData
import com.meiling.common.utils.GsonUtils
import com.meiling.common.utils.RecyclerViewDivider
import com.meiling.common.utils.SpannableUtils
import com.meiling.oms.eventBusData.MessageEventVoucherInspectionHistory
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityWriteOffDetailsBinding
import com.meiling.oms.dialog.MineExitDialog
import com.meiling.oms.viewmodel.VoucherInspectionHistoryViewModel
import com.meiling.oms.widget.showToast
import org.greenrobot.eventbus.EventBus

//核销详情
@Route(path = "/app/WriteOffDetailsActivity")
class WriteOffDetailsActivity :
    BaseActivity<VoucherInspectionHistoryViewModel, ActivityWriteOffDetailsBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        initRecycleyView()

    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityWriteOffDetailsBinding {
        return ActivityWriteOffDetailsBinding.inflate(layoutInflater)
    }
    var id =0

    override fun initData() {
        var serializableExtra =
            intent.getSerializableExtra("writeoffhistoryPageData") as WriteoffhistoryPageData?
        var shopId = intent.getStringExtra("shopId")
         id = intent.getIntExtra("id",0)

        var persons =
            GsonUtils.getPersons1(serializableExtra?.coupon?.dealMenu, DealMenu::class.java)
        orderLeftRecyAdapter.setList(persons)

        if (serializableExtra != null) {

            mDatabind.tvName.text = serializableExtra?.coupon?.dealTitle
            //原价
            mDatabind.tvCouponBuyPrice.text = "¥" + serializableExtra?.coupon?.couponBuyPrice
            //
            mDatabind.tvDealValue.text = "¥" + serializableExtra?.coupon?.dealValue

            mDatabind.tvDealValue?.paint?.flags = Paint.STRIKE_THRU_TEXT_FLAG
            //券码编号
            mDatabind.tvCouponCode.text = serializableExtra?.coupon?.couponCode
            //1。团购 2。代金
            if (serializableExtra.coupon?.isVoucher == 1) {
                mDatabind.tvType.text = "团购券"//券码类型
            } else if (serializableExtra.coupon?.isVoucher == 2) {
                mDatabind.tvType.text = "代金券"//券码类型
            }

            //核销时间
            mDatabind.tvCouponUseTime.text = serializableExtra?.coupon?.couponUseTime
            //订单编号
            if (TextUtils.isEmpty(serializableExtra?.coupon?.orderId) || serializableExtra?.coupon?.orderId == "0") {
                mDatabind.tvOrderId.text = "暂无"
            } else {
                mDatabind.tvOrderId.text = serializableExtra?.coupon?.orderId
            }


            if (serializableExtra?.coupon?.undoType == 1) {
                mDatabind.tvStatus.setTextColor(Color.parseColor("#FB9716"))
                mDatabind.tvStatus.shapeDrawableBuilder.setSolidColor(Color.parseColor("#FFF1DF"))
                    .intoBackground()
                mDatabind.tvStatus.text = "已撤销"

            } else if (serializableExtra?.coupon?.undoType == 0) {

                mDatabind.tvStatus.setTextColor(Color.parseColor("#31D288"))
                mDatabind.tvStatus.shapeDrawableBuilder.setSolidColor(Color.parseColor("#EDFFF4"))
                    .intoBackground()


                mDatabind.tvStatus.text = "已核销"

                var string2Millis =
                    TimeUtils.string2Millis(serializableExtra?.coupon?.couponUseTime)
                var nowMills = TimeUtils.getNowMills()
                val b: Boolean = (nowMills - string2Millis) / 60000 > 20
                if (b) {
                    mDatabind.tvOk.visibility = View.GONE

                } else {

                    mDatabind.tvOk.visibility = View.VISIBLE

                }


            } else {

            }
            var shopname=serializableExtra?.shopName
            if (serializableExtra?.shopName.toString().length>12){
                shopname=serializableExtra?.shopName.toString().substring(0,12)+"...."
            }
            if (serializableExtra?.coupon?.type == 2) {//美团
                mDatabind.meiRecyclerView.visibility = View.VISIBLE
                var conet = "由 ${shopname} 验证"
                SpannableUtils.setTextcolor(
                    this,
                    conet,
                    mDatabind.tvShopName,
                    1,
                    conet.length - 2,
                    R.color.pwd_1180FF
                )

            } else if (serializableExtra?.coupon?.type == 5) {//抖音
                mDatabind.meiRecyclerView.visibility = View.GONE
                //  holder.setText(R.id.tv_shopName, "由"+item?.coupon?.shopName+"验证")
                var conet = "由 ${shopname} 验证"
                SpannableUtils.setTextcolor(
                    this,
                    conet,
                    mDatabind.tvShopName,
                    1,
                    conet.length - 2,
                    R.color.pwd_1180FF
                )


            }

            mDatabind.tvOk.setOnClickListener {

                val dialog: MineExitDialog =
                    MineExitDialog().newInstance(
                        "温馨提示", "确认撤销核销吗？",
                        "我再想想", "确定撤销", false
                    )
                dialog.setOkClickLister {
                    dialog.dismiss()

                    if (serializableExtra?.coupon?.type == 5) {
                        mViewModel.cancel(
                            serializableExtra.coupon?.couponCode!!,
                            serializableExtra.coupon?.shopId!!
                        )
                    } else if (serializableExtra?.coupon?.type == 2) {
                        mViewModel.meituancancel(
                            serializableExtra.coupon?.couponCode!!,
                            serializableExtra.coupon?.shopId!!
                        )
                    }

                }
                dialog.show(supportFragmentManager)
//
//
            }


        }

    }

    lateinit var orderLeftRecyAdapter: BaseQuickAdapter<DealMenu?, BaseViewHolder>
    private fun initRecycleyView() {

        orderLeftRecyAdapter = object :
            BaseQuickAdapter<DealMenu?, BaseViewHolder>(R.layout.item_recy_meituan),
            LoadMoreModule {
            override fun convert(
                holder: BaseViewHolder,
                item: DealMenu?

            ) {
                holder.setText(R.id.tv_name, item?.content)
                holder.setText(R.id.tv_price, "¥" + item?.price)
                holder.setText(R.id.tv_total, item?.specification)

//
//

            }
        }
        mDatabind.meiRecyclerView.layoutManager = LinearLayoutManager(this)
        var recyclerViewDivider = RecyclerViewDivider()
        recyclerViewDivider.setDividerHeight(10)
        mDatabind.meiRecyclerView.addItemDecoration(recyclerViewDivider)
        mDatabind.meiRecyclerView.adapter = orderLeftRecyAdapter
        orderLeftRecyAdapter.setList(arrayListOf())


    }

    override fun createObserver() {

        mViewModel.cancelstring.onStart.observe(this) {
            showLoading("")

        }
        mViewModel.cancelstring.onSuccess.observe(this){
            disLoading()
            EventBus.getDefault().post(MessageEventVoucherInspectionHistory(id))
            finish()
        }

        mViewModel.cancelstring.onError.observe(this){
            disLoading()
            showToast("${it.msg}")
        }

        mViewModel.cancelmeituanstring.onStart.observe(this) {
            showLoading("")

        }
        mViewModel.cancelmeituanstring.onSuccess.observe(this) {
            disLoading()
            EventBus.getDefault().post(MessageEventVoucherInspectionHistory(id))
            finish()
        }
        mViewModel.cancelmeituanstring.onError.observe(this){
            disLoading()
            showToast("${it.msg}")
        }

    }

    override fun isStatusBarDarkFont(): Boolean {
        return true
    }

}