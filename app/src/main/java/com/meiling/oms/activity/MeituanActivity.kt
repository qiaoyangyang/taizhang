package com.meiling.oms.activity

import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hjq.shape.view.ShapeTextView
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.Meituan
import com.meiling.common.network.data.ThrillItem
import com.meiling.common.utils.RecyclerViewDivider
import com.meiling.common.utils.SpannableUtils
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityGiveBinding
import com.meiling.oms.databinding.ActivityMeituanBinding
import com.meiling.oms.dialog.MineExitDialog
import com.meiling.oms.viewmodel.VoucherInspectionHistoryViewModel

//核销成功
@Route(path = "/app/WriteOffActivity")
class MeituanActivity : BaseActivity<VoucherInspectionHistoryViewModel, ActivityMeituanBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.tvGoOn.setOnClickListener {
            finish()
        }
        mDatabind.tvRevocation.setOnClickListener {


        }

    }

    override fun createObserver() {


    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityMeituanBinding {
        return ActivityMeituanBinding.inflate(layoutInflater)
    }

    var meituan: Meituan? = null
    var shopId: String = ""
    override fun initData() {
        super.initData()
        shopId = intent.getStringExtra("shopId").toString()
        meituan = intent.getSerializableExtra("meituan") as Meituan
        if (meituan != null) {
            mDatabind.tvName.text = meituan?.dealTitle
            mDatabind.tvCouponBuyPrice.text = "¥" + meituan?.couponBuyPrice
            mDatabind.tvDealValue.text = "¥" + meituan?.dealValue
            mDatabind.tvDealValue?.paint?.flags = Paint.STRIKE_THRU_TEXT_FLAG
            mDatabind.tvCouponCode.text = meituan?.couponCode
            mDatabind.tvCouponUseTime.text = meituan!!.couponUseTime
            if (meituan?.isVoucher == 1) {
                // holder.setText(R.id.tv_type, "")
                mDatabind.tvType.text = "团购券"
            } else if (meituan?.isVoucher == 2) {
                mDatabind.tvType.text = "代金券"
            }
            var conet = "由${meituan?.shopName}店验证"
            SpannableUtils.setTextcolor(
                this,
                conet,
                mDatabind.tvShopName,
                1,
                conet.length - 2,
                R.color.pwd_1180FF
            )

            mDatabind.tvStatus.setTextColor( Color.parseColor("#FB9716"))

            mDatabind.tvStatus.shapeDrawableBuilder.setSolidColor(Color.parseColor("#FFF1DF"))
                .intoBackground()
            mDatabind.tvStatus.text = "已核销"


        }

    }


}