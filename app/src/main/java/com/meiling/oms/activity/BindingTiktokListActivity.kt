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
     var tiktokData: TiktokData?=null

    override fun initView(savedInstanceState: Bundle?) {
        initRecycleyView()
        mDatabind.tvOk.setOnClickListener {
            //
            if (tiktokData != null ) {
                if(tiktokData?.isstatus==true) {
                    mViewModel.bindShop(
                        poiId,
                        tiktokData?.address!!,
                        tiktokData?.poiName!!,
                        tiktokData?.poiId!!
                    )
                }else{
                    showToast("选择店铺")
                }
            }else{
                showToast("选择店铺")
            }
        }
        mDatabind.btnSearch.setOnClickListener {
            if (TextUtils.isEmpty(mDatabind.edtSearch.text.toString())) {

                return@setOnClickListener
            }
            mViewModel.douurlauth(channelId, poiId, mDatabind.edtSearch.text.toString())
        }
        //清楚
        mDatabind.imgSearchEditClear.setOnClickListener {
            mDatabind.edtSearch.setText("")
            mViewModel.douurlauth(channelId, poiId, mDatabind.edtSearch.text.toString())
        }
        //键盘搜索
        mDatabind.edtSearch?.setOnEditorActionListener { v, actionId, event ->
            if (actionId == 0 || actionId == 3) {

                mDatabind.edtSearch.setText("")
                mViewModel.douurlauth(channelId, poiId, mDatabind.edtSearch.text.toString())
            }
            return@setOnEditorActionListener false
        }
        mDatabind.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (TextUtils.isEmpty(mDatabind.edtSearch.text.toString())) {
                    mDatabind.imgSearchEditClear.visibility=View.GONE
                } else {
                    mDatabind.imgSearchEditClear.visibility=View.VISIBLE

                }
            }

        })


    }

    var channelId: String = ""
    var poiId: String = ""
    var channename: String = ""

    override fun initData() {
        channelId = intent.getStringExtra("channelId").toString()
        poiId = intent.getStringExtra("poiId").toString()
        channename = intent.getStringExtra("channename").toString()
        mViewModel.douurlauth(channelId, poiId, "")
        mDatabind.TitleBar.title = "绑定${channename}店铺"
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityBindingTiktokBinding {
        return ActivityBindingTiktokBinding.inflate(layoutInflater)
    }

    private fun initRecycleyView() {


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
                    holder.setBackgroundResource(R.id.scbg, R.drawable.bg_true)
                    holder.setBackgroundResource(R.id.iv_status, R.drawable.ic_spu_true)
                } else {
                    holder.setBackgroundResource(R.id.scbg, R.drawable.bg_fase)
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
            if (bindingTiktokAdapter.data.get(position)?.status==false) {
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


    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.douyin.onStart.observe(this) {
            showLoading("")
        }
        mViewModel.douyin.onSuccess.observe(this) {
            disLoading()
            bindingTiktokAdapter.setList(it.pageResult?.pageData)
        }
        mViewModel.douyin.onError.observe(this) {
            disLoading()
        }
        mViewModel.bindShop.onStart.observe(this) {
            showLoading("")
        }
        mViewModel.bindShop.onSuccess.observe(this) {
            disLoading()
            startActivity(Intent(this, ChannelActivity::class.java))
        }
        mViewModel.bindShop.onError.observe(this) {
            disLoading()
            showToast(it.msg)
        }

    }

}