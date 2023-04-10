package com.meiling.oms.activity

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.TimeUtils
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.WriteoffhistoryPageData
import com.meiling.common.utils.MMKVUtils
import com.meiling.common.utils.SpannableUtils
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityWriteOffDetailsBinding
import com.meiling.oms.dialog.MineExitDialog
import com.meiling.oms.viewmodel.VoucherInspectionHistoryViewModel

//核销详情
@Route(path = "/app/WriteOffDetailsActivity")
class WriteOffDetailsActivity :
    BaseActivity<VoucherInspectionHistoryViewModel, ActivityWriteOffDetailsBinding>() {
    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityWriteOffDetailsBinding {
        return ActivityWriteOffDetailsBinding.inflate(layoutInflater)
    }


    override fun initData() {
        var serializableExtra =
            intent.getSerializableExtra("writeoffhistoryPageData") as WriteoffhistoryPageData?
        var shopId = intent.getStringExtra("shopId")
        Log.d("yjk", "initData: $shopId")
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
            mDatabind.tvCouponUseTime.text = serializableExtra?.coupon?.couponUseTime


            if (serializableExtra?.coupon?.undoType == 1) {
                mDatabind.tvStatus.setTextColor(Color.parseColor("#31D288"))
                mDatabind.tvStatus.shapeDrawableBuilder.setSolidColor(Color.parseColor("#EDFFF4"))
                    .intoBackground()
                mDatabind.tvStatus.text = "已撤销"

            } else if (serializableExtra?.coupon?.undoType == 0) {
                mDatabind.tvStatus.setTextColor(Color.parseColor("#FB9716"))
                //  tv_status.setBackgroundColor(Color.parseColor("#FFF1DF"))
                mDatabind.tvStatus.shapeDrawableBuilder.setSolidColor(Color.parseColor("#FFF1DF"))
                    .intoBackground()
                mDatabind.tvStatus.text = "已核销"

            } else {

            }

            if (serializableExtra?.coupon?.type == 2) {//美团

                var conet = "由${serializableExtra.shopName}店验证"
                SpannableUtils.setTextcolor(
                    this,
                    conet,
                    mDatabind.tvShopName,
                    1,
                    conet.length - 2,
                    R.color.pwd_1180FF
                )

            } else if (serializableExtra?.coupon?.type == 5) {//抖音
                //  holder.setText(R.id.tv_shopName, "由"+item?.coupon?.shopName+"验证")
                var conet = "由${serializableExtra?.coupon?.shopName}店验证"
                SpannableUtils.setTextcolor(
                    this,
                    conet,
                    mDatabind.tvShopName,
                    1,
                    conet.length - 2,
                    R.color.pwd_1180FF
                )


            }
            //1200000
            var string2Millis = TimeUtils.string2Millis("2023-04-07 19:43:01")
            var nowMills = TimeUtils.getNowMills()
            val b: Boolean = (nowMills - string2Millis) / 60000 > 20
            if (b) {
                mDatabind.tvOk.visibility = View.GONE

            } else {

                mDatabind.tvOk.visibility = View.VISIBLE
                mDatabind.tvOk.setOnClickListener {

                    val dialog: MineExitDialog =
                        MineExitDialog().newInstance("温馨提示", "注销后，该账号将不可用。\n 请确认操作～", false)
                    dialog.setOkClickLister {
                        mViewModel.cancel(
                            serializableExtra.coupon?.couponCode!!,
                            serializableExtra.coupon?.shopId!!
                        )

                    }
                    dialog.show(supportFragmentManager)
//
//
                }
            }




            Log.d("yjk", "initData: ${(b)}")


        }

    }

    override fun createObserver() {
        mViewModel.cancelstring.onStart.observe(this) {

        }

    }

    override fun isStatusBarDarkFont(): Boolean {
        return true
    }

}