package com.meiling.oms.activity

import android.content.Intent
import android.graphics.Color
import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hjq.shape.view.ShapeTextView
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.WriteoffhistoryPageData
import com.meiling.common.utils.RecyclerViewDivider
import com.meiling.common.utils.SpannableUtils
import com.meiling.oms.R
import com.meiling.oms.bean.BranchInformationContent
import com.meiling.oms.databinding.ActivitySplashBinding
import com.meiling.oms.databinding.ActivityStoreManagementBinding
import com.meiling.oms.viewmodel.StoreManagementViewModel

//门店管理
class StoreManagementActivity :
    BaseActivity<StoreManagementViewModel, ActivityStoreManagementBinding>() {
    lateinit var storeManagemenAdapter: BaseQuickAdapter<BranchInformationContent?, BaseViewHolder>
    override fun initView(savedInstanceState: Bundle?) {
        //新建门店
        mDatabind.tvNewlyBuiltStore.setOnClickListener {
            startActivity(Intent(this, NewlyBuiltStoreActivity::class.java))
        }
        initRecycleyView()
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityStoreManagementBinding {
        return ActivityStoreManagementBinding.inflate(layoutInflater)
    }

    override fun isStatusBarDarkFont(): Boolean {
        return true
    }

    override fun initData() {
        super.initData()
        mViewModel.poilis()

    }


    private fun initRecycleyView() {

        storeManagemenAdapter = object :
            BaseQuickAdapter<BranchInformationContent?, BaseViewHolder>(R.layout.item_store_managemnet),
            LoadMoreModule {
            override fun convert(
                holder: BaseViewHolder,
                item: BranchInformationContent?

            ) {
                holder.setText(R.id.tv_shopName, item?.name)
                holder.setText(R.id.tv_phone, item?.phone)
                holder.setText(R.id.tv_detailed_address, item?.address)

//
//

            }
        }
        mDatabind.ryOrderLeft.layoutManager = LinearLayoutManager(this)
        var recyclerViewDivider = RecyclerViewDivider()
        recyclerViewDivider.setDividerHeight(30)
        mDatabind.ryOrderLeft.addItemDecoration(recyclerViewDivider)
        mDatabind.ryOrderLeft.adapter = storeManagemenAdapter
        storeManagemenAdapter.setList(arrayListOf())
        storeManagemenAdapter.setEmptyView(R.layout.store_managemnet)
        storeManagemenAdapter.setOnItemClickListener { adapter, view, position ->
            startActivity(
                Intent(this, NewlyBuiltStoreActivity::class.java).putExtra(
                    "id",
                    storeManagemenAdapter.data.get(position)?.id
                )
            )
        }


    }

    override fun createObserver() {
        mViewModel.dataList.onSuccess.observe(this) {
            storeManagemenAdapter.setList(it.content)
        }
    }
}