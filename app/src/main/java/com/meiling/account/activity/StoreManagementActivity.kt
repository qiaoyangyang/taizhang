package com.meiling.account.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.activity.BaseActivity
import com.meiling.common.utils.RecyclerViewDivider
import com.meiling.account.R
import com.meiling.account.bean.BranchInformationContent
import com.meiling.account.databinding.ActivityStoreManagementBinding
import com.meiling.account.dialog.MineExitDialog
import com.meiling.account.viewmodel.StoreManagementViewModel
import com.meiling.account.widget.showToast

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


    override fun initData() {
        super.initData()


    }

    override fun onResume() {
        super.onResume()
        mViewModel.poilis()
    }
    var isposition=-1

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
                holder.setText(R.id.tv_detailed_address, item?.address?.replace("@@","  "))

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

        storeManagemenAdapter.setOnItemClickListener { adapter, view, position ->

        }
        storeManagemenAdapter.addChildClickViewIds(R.id.tv_delete,R.id.tv_compile)
        storeManagemenAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.tv_delete -> {
                    val dialog: MineExitDialog =
                        MineExitDialog().newInstance("温馨提示", "若门店存在绑定的渠道店铺，则无法删除。请确认是否删除门店？", "取消", "确认", false)
                    dialog.setOkClickLister {
                        isposition=position
                        dialog.dismiss()
                        mViewModel.deletePoi(storeManagemenAdapter.getItem(position)?.id!!)

                    }
                    dialog.show(supportFragmentManager)
                }
                R.id.tv_compile ->{
                    startActivity(
                        Intent(this, NewlyBuiltStoreActivity::class.java).putExtra(
                            "id",
                            storeManagemenAdapter.data.get(position)?.id
                        )
                    )
                }
            }
        }


    }

    override fun createObserver() {
        mViewModel.deletePoi.onStart.observe(this){
            showLoading("")
        }
        mViewModel.deletePoi.onSuccess.observe(this){
            disLoading()
            storeManagemenAdapter.removeAt(isposition)
            storeManagemenAdapter.notifyDataSetChanged()
            if (storeManagemenAdapter.data.size==0){
                mDatabind.tvType.visibility=View.GONE
                storeManagemenAdapter.setEmptyView(R.layout.store_managemnet)
            }else{
                mDatabind.tvType.visibility=View.VISIBLE
            }
            showToast("门店删除成功")

        }
        mViewModel.deletePoi.onError.observe(this){
            disLoading()
            showToast(it.msg)
        }

        mViewModel.dataList.onSuccess.observe(this) {
            if (it.content==null||it?.content?.size==0){
                mDatabind.tvType.visibility=View.GONE
            }else{
                mDatabind.tvType.visibility=View.VISIBLE
            }
            storeManagemenAdapter.setList(it.content)
        }
    }
}