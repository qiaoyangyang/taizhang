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
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.WriteoffhistoryPageData
import com.meiling.common.utils.RecyclerViewDivider
import com.meiling.common.utils.SpannableUtils
import com.meiling.oms.R
import com.meiling.oms.adapter.BindingTiktokAdapter
import com.meiling.oms.bean.TiktokData
import com.meiling.oms.databinding.ActivityBindingTiktokBinding
import com.meiling.oms.databinding.ActivityTiktoKnindingBinding
import com.meiling.oms.viewmodel.StoreManagementViewModel
import com.meiling.oms.widget.showToast

class BindingTiktokListActivity :

    BaseActivity<StoreManagementViewModel, ActivityBindingTiktokBinding>() {
    lateinit var bindingTiktokAdapter: BaseQuickAdapter<TiktokData?, BaseViewHolder>
    lateinit var tiktokData: TiktokData

    override fun initView(savedInstanceState: Bundle?) {
        initRecycleyView()
        mDatabind.tvOk.setOnClickListener {
           //
            if (tiktokData != null) {
                mViewModel.bindTenant(tiktokData?.poiId!!)
            }
        }
    }

    var channelId: String = ""
    var poiId: String = ""

    override fun initData() {
        channelId = intent.getStringExtra("channelId").toString()
        poiId = intent.getStringExtra("poiId").toString()
        mViewModel.douurlauth(channelId, poiId, "")
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityBindingTiktokBinding {
        return ActivityBindingTiktokBinding.inflate(layoutInflater)
    }

    private fun initRecycleyView() {
        mViewModel.bindTenant.onStart.observe(this){
            showLoading("")
        }
        mViewModel.bindTenant.onSuccess.observe(this){
            disLoading()
            startActivity(Intent(this, ChannelActivity::class.java))
        }
        mViewModel.bindTenant.onError.observe(this){
            disLoading()
            showToast(it.msg)
        }

        bindingTiktokAdapter = object :
            BaseQuickAdapter<TiktokData?, BaseViewHolder>(R.layout.item_binding_tiktok),
            LoadMoreModule {
            override fun convert(
                holder: BaseViewHolder,
                item: TiktokData?

            ) {
                holder.setText(R.id.tv_platform_name, item?.poiName)
                holder.setText(R.id.tv_platform_id, item?.poiId)
                if (item?.status == true) {
                    holder.setGone(R.id.iv_status, true)
                    holder.setGone(R.id.s_status, false)
                } else {
                    holder.setGone(R.id.iv_status, false)
                    holder.setGone(R.id.s_status, true)
                }
                if (item?.isstatus == true) {
                    holder.setBackgroundResource(R.id.iv_status, R.drawable.ic_spu_true)
                } else {
                    holder.setBackgroundResource(R.id.iv_status, R.drawable.ic_spu_fase1)
                }


            }
        }
        mDatabind.tiktokRecyclerView.layoutManager = LinearLayoutManager(this)
        var recyclerViewDivider = RecyclerViewDivider()
        recyclerViewDivider.setDividerHeight(40)
        mDatabind.tiktokRecyclerView.addItemDecoration(recyclerViewDivider)
        mDatabind.tiktokRecyclerView.adapter = bindingTiktokAdapter
        bindingTiktokAdapter.setList(arrayListOf())
        bindingTiktokAdapter.setEmptyView(R.layout.store_managemnet)
        bindingTiktokAdapter.setOnItemClickListener { adapter, view, position ->
            if (bindingTiktokAdapter.data.get(position)?.isstatus == true) {
                bindingTiktokAdapter.data.get(position)?.isstatus = false
                bindingTiktokAdapter.notifyDataSetChanged()

            } else {
                bindingTiktokAdapter.data.forEach {
                    it?.isstatus = false
                }
                tiktokData = bindingTiktokAdapter?.getItem(position)!!
                bindingTiktokAdapter.data.get(position)?.isstatus = true
                bindingTiktokAdapter.notifyDataSetChanged()
            }

        }


    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.douyin.onSuccess.observe(this) {
            bindingTiktokAdapter.setList(it.pageResult?.pageData)
        }

    }

}