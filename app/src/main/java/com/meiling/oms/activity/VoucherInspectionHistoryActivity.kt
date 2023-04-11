package com.meiling.oms.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hjq.shape.view.ShapeTextView
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.OrderDto
import com.meiling.common.network.data.Shop
import com.meiling.common.network.data.WriteoffhistoryPageData
import com.meiling.common.utils.Constant
import com.meiling.common.utils.RecyclerViewDivider
import com.meiling.common.utils.SpannableUtils
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityHistoryBinding
import com.meiling.oms.viewmodel.VoucherInspectionHistoryViewModel
import com.meiling.oms.widget.showToast
import okhttp3.internal.notify
import java.util.ArrayList

//验券历史
class VoucherInspectionHistoryActivity :
    BaseActivity<VoucherInspectionHistoryViewModel, ActivityHistoryBinding>() {
    lateinit var orderLeftRecyAdapter: BaseQuickAdapter<WriteoffhistoryPageData?, BaseViewHolder>
    var isSearch=false
    override fun initView(savedInstanceState: Bundle?) {
        initRecycleyView()
        mDatabind.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (s.toString().isNotEmpty()) {
                    mDatabind.imgSearchEditClear.visibility = View.VISIBLE
                } else {
                    mDatabind.imgSearchEditClear.visibility = View.GONE
                }
            }

        })
        mDatabind.btnSearch.setOnClickListener {
            orderLeftRecyAdapter.setList(null)
            setcoupon()

        }
        mDatabind.imgSearchEditClear.setOnClickListener {
            orderLeftRecyAdapter.setList(null)
            mDatabind.edtSearch.setText("")
            setcoupon()

        }
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityHistoryBinding {
        return ActivityHistoryBinding.inflate(layoutInflater)
    }

    var shop: Shop? = null
    override fun initData() {
        super.initData()
        shop = intent.getSerializableExtra("shop") as Shop
//


        orderLeftRecyAdapter.loadMoreModule.setOnLoadMoreListener {
            pageIndex++
            setcoupon()
        }
        mDatabind.refeshLayout.setOnRefreshListener {
            pageIndex = 1
            setcoupon()
        }
        setcoupon()
    }

    override fun createObserver() {
        super.createObserver()

        mViewModel.writeoffhistory.onSuccess.observe(this) {
            if (it.pageData != null) {


                mDatabind.refeshLayout.finishRefresh()
                if (it.pageNum == 1) {
                    if (it.pageData.isNullOrEmpty()) {
                        orderLeftRecyAdapter.setEmptyView(R.layout.order_search_empty1)
                    } else {
                        orderLeftRecyAdapter.setList(it?.pageData as MutableList<WriteoffhistoryPageData>)
                    }
                } else {
                    orderLeftRecyAdapter.addData(it?.pageData as MutableList<WriteoffhistoryPageData>)
                }
                if (it?.pageData?.size!! < Constant.size.toInt()) {
                    orderLeftRecyAdapter.footerWithEmptyEnable = false
                    orderLeftRecyAdapter.loadMoreModule.loadMoreEnd()
                } else {
                    orderLeftRecyAdapter.loadMoreModule.loadMoreComplete()
                }
            }else{
                if (orderLeftRecyAdapter.data.size==0){
                    orderLeftRecyAdapter.setEmptyView(R.layout.order_search_empty1)
                }
            }


        }
        mViewModel.writeoffhistory.onError.observe(this) {
            mDatabind.refeshLayout.finishRefresh()
            showToast("${it.message}")
        }
    }

    override fun isStatusBarDarkFont(): Boolean {
        return true
    }

    private fun initRecycleyView() {

        orderLeftRecyAdapter = object :
            BaseQuickAdapter<WriteoffhistoryPageData?, BaseViewHolder>(R.layout.item_recy_stock_list),
            LoadMoreModule {
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

    var startDate: String = "2023-04-10"
    var endDate: String = "2023-04-10"
    var pageIndex: Int = 1
    fun setcoupon() {


        if (!TextUtils.isEmpty(shop?.poiId)) {
            mViewModel.coupon(
                shop?.poiId!!,
                startDate,
                endDate,
                mDatabind.edtSearch.text.toString(),
                pageIndex,
                Constant.size
            )
        } else {
            mViewModel.coupon(
                "",
                startDate,
                endDate,
                mDatabind.edtSearch.text.toString(),
                pageIndex,
                Constant.size
            )

        }

    }
}


