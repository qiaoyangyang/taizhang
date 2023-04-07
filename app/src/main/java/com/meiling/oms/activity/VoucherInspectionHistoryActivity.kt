package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.WriteoffhistoryPageData
import com.meiling.common.utils.RecyclerViewDivider
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

    override fun initData() {
        super.initData()
        mViewModel.coupon()
        initRecycleyView()
    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.writeoffhistory.onSuccess.observe(this) {

            orderLeftRecyAdapter.setList(it?.pageData)
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
                holder.setText(R.id.tv_couponBuyPrice,item?.coupon?.couponBuyPrice)
                holder.setText(R.id.tv_dealTitle,item?.coupon?.dealTitle)
                holder.setText(R.id.tv_couponCode,"券码号:"+item?.coupon?.couponCode)
                //if (item?.coupon?.tv_undoType==)

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


    }
}


