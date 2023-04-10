package com.meiling.oms.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hjq.shape.view.ShapeTextView
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.Shop
import com.meiling.common.network.data.Test
import com.meiling.common.network.data.WriteoffhistoryPageData
import com.meiling.common.utils.RecyclerViewDivider
import com.meiling.common.utils.SpannableUtils
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityHistoryBinding
import com.meiling.oms.viewmodel.VoucherInspectionHistoryViewModel
import java.util.ArrayList

//验券历史
class VoucherInspectionHistoryActivity :
    BaseActivity<VoucherInspectionHistoryViewModel, ActivityHistoryBinding>() {
    lateinit var orderLeftRecyAdapter: BaseQuickAdapter<WriteoffhistoryPageData?, BaseViewHolder>
    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityHistoryBinding {
        return ActivityHistoryBinding.inflate(layoutInflater)
    }

    var shop: Shop? = null
    override fun initData() {
        super.initData()
        shop = intent.getSerializableExtra("shop") as Shop
        if (shop != null) {
            if (!TextUtils.isEmpty(shop?.poiId)){
                mViewModel.coupon(shop?.poiId!!)
            }else{
                mViewModel.coupon("")
            }

        }
        initRecycleyView()

    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.writeoffhistory.onSuccess.observe(this) {
            if (it.pageData!=null){
                orderLeftRecyAdapter.setList(it?.pageData)
            }


        }
    }

    override fun isStatusBarDarkFont(): Boolean {
        return true
    }

    private fun initRecycleyView() {

        orderLeftRecyAdapter = object :
            BaseQuickAdapter<WriteoffhistoryPageData?, BaseViewHolder>(R.layout.item_recy_stock_list) {
            override fun convert(
                holder: BaseViewHolder,
                item: WriteoffhistoryPageData?

            ) {
                holder.setText(R.id.tv_couponBuyPrice, item?.coupon?.couponBuyPrice)
                holder.setText(R.id.tv_dealTitle, item?.coupon?.dealTitle)
                holder.setText(R.id.tv_couponCode, "券码号:" + item?.coupon?.couponCode)
                var tv_status = holder.getView<ShapeTextView>(R.id.tv_status)
                if (item?.coupon?.undoType == 1) {
                    tv_status.setTextColor(Color.parseColor("#31D288"))
                    tv_status.shapeDrawableBuilder.setSolidColor(Color.parseColor("#EDFFF4"))
                        .intoBackground()
                    holder.setText(R.id.tv_status, "已撤销")
                } else if (item?.coupon?.undoType == 0) {
                    tv_status.setTextColor(Color.parseColor("#FB9716"))
                    //  tv_status.setBackgroundColor(Color.parseColor("#FFF1DF"))
                    tv_status.shapeDrawableBuilder.setSolidColor(Color.parseColor("#FFF1DF"))
                        .intoBackground()
                    holder.setText(R.id.tv_status, "已核销")
                }

                if (item?.coupon?.type == 2) {//美团
                    var conet = "由${item.shopName}店验证"
                    SpannableUtils.setTextcolor(
                        holder.itemView.context,
                        conet,
                        holder.getView(R.id.tv_shopName),
                        1,
                        conet.length - 2,
                        R.color.pwd_1180FF
                    )

                } else if (item?.coupon?.type == 5) {//抖音
                    //  holder.setText(R.id.tv_shopName, "由"+item?.coupon?.shopName+"验证")
                    var conet = "由${item?.coupon?.shopName}店验证"
                    SpannableUtils.setTextcolor(
                        holder.itemView.context,
                        conet,
                        holder.getView(R.id.tv_shopName),
                        1,
                        conet.length - 2,
                        R.color.pwd_1180FF
                    )

                }

//
//

            }
        }
        mDatabind.ryOrderLeft.layoutManager = LinearLayoutManager(this)
        var recyclerViewDivider = RecyclerViewDivider()
        recyclerViewDivider.setDividerHeight(10)
        mDatabind.ryOrderLeft.addItemDecoration(recyclerViewDivider)
        mDatabind.ryOrderLeft.adapter = orderLeftRecyAdapter
        orderLeftRecyAdapter.setList(arrayListOf())
        orderLeftRecyAdapter.setEmptyView(R.layout.empty_want_goods_recycler)
        orderLeftRecyAdapter.setOnItemClickListener { adapter, view, position ->
            var writeoffhistoryPageData = orderLeftRecyAdapter.data[position]
            ARouter.getInstance().build("/app/WriteOffDetailsActivity")
                .withSerializable(
                    "writeoffhistoryPageData",
                    writeoffhistoryPageData
                ).withString("shopId", shop?.id).navigation()

        }


    }
}


