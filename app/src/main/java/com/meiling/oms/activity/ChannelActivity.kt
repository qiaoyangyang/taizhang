package com.meiling.oms.activity

import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.GlideApp
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.Shop
import com.meiling.common.network.data.ShopBean
import com.meiling.common.utils.RecyclerViewDivider
import com.meiling.common.utils.TextDrawableUtils
import com.meiling.oms.R
import com.meiling.oms.bean.ChannShop
import com.meiling.oms.bean.ChannelX
import com.meiling.oms.databinding.ActivityChannelBinding
import com.meiling.oms.dialog.ShopDialog
import com.meiling.oms.viewmodel.StoreManagementViewModel
import com.meiling.oms.widget.showToast

//渠道店铺管理
class ChannelActivity : BaseActivity<StoreManagementViewModel, ActivityChannelBinding>() {
    lateinit var channeAdapter: BaseQuickAdapter<ChannShop?, BaseViewHolder>
    lateinit var channelXAdapter: BaseQuickAdapter<ChannelX?, BaseViewHolder>
    override fun initView(savedInstanceState: Bundle?) {
        TextDrawableUtils.setRightDrawable(mDatabind.TitleBar.titleView, R.drawable.xia1)
        initRecycleyView()
        initRecycleyView1()
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityChannelBinding {
        return ActivityChannelBinding.inflate(layoutInflater)
    }

    var shop: Shop? = null
    override fun onTitleClick(view: View) {
        var shopDialog = ShopDialog().newInstance(shopBean!!)

        shopDialog.setOnresilience(object : ShopDialog.Onresilience {


            override fun resilience(
                cityid: Int,
                cityidname: String,
                shopid: Int,
                sho: Shop
            ) {
                shop = sho
                mDatabind.TitleBar.titleView.text = cityidname + "/" + sho.name
                mViewModel.getShopAndChannelVO(sho?.id!!)
            }

            override fun Ondismiss() {
            }

        })
        shopDialog.show(supportFragmentManager)
    }

    var shopBean: ArrayList<ShopBean>? = null


    override fun initData() {
        mViewModel.citypoi()
    }

    override fun onResume() {
        super.onResume()
        if (shop != null) {
            Log.d("yjk","--shop--onResume-")
            mViewModel.getShopAndChannelVO(shop?.id!!)
        } else {
            Log.d("yjk","----onResume-")
        }
    }

    override fun isStatusBarDarkFont(): Boolean {
        return true
    }


    override fun createObserver() {
        mViewModel.shopBean.onStart.observe(this) {
            showLoading("")
        }
        mViewModel.shopBean.onSuccess.observe(this) {
            disLoading()
            shopBean = it
            shop = it.get(0).shopList?.get(0)
            mDatabind.TitleBar.title = it.get(0).name + "/" + it.get(0).shopList?.get(0)?.name
            mViewModel.getShopAndChannelVO(it.get(0).shopList?.get(0)?.id!!)

        }
        mViewModel.shopBean.onError.observe(this) {
            disLoading()
            showToast(it.msg)
        }



        mViewModel.channel.onStart.observe(this) {
            showLoading("")
        }

        mViewModel.channel.onSuccess.observe(this) {
            disLoading()
            channeAdapter.setList(it.shopList)
            channelXAdapter.setList(it.channelList)
        }

        mViewModel.channel.onError.observe(this) {
            disLoading()
            showToast(it.msg)
        }

    }

    private fun initRecycleyView() {

        channeAdapter = object :
            BaseQuickAdapter<ChannShop?, BaseViewHolder>(R.layout.item_chann_shop),
            LoadMoreModule {
            override fun convert(
                holder: BaseViewHolder,
                item: ChannShop?

            ) {
                holder.setText(R.id.tv_name, item?.channelName)
                GlideApp.with(holder.itemView.context)
                    .load(item?.channelLogo)
                    .into(holder.getView(R.id.iv_picture))

//
//

            }
        }
        mDatabind.rectangle.layoutManager = LinearLayoutManager(this)
        var recyclerViewDivider = RecyclerViewDivider()
        recyclerViewDivider.setDividerHeight(30)
        mDatabind.rectangle.addItemDecoration(recyclerViewDivider)
        mDatabind.rectangle.adapter = channeAdapter
        channeAdapter.setList(arrayListOf())
        channeAdapter.setOnItemClickListener { adapter, view, position ->

        }


    }

    private fun initRecycleyView1() {

        channelXAdapter = object :
            BaseQuickAdapter<ChannelX?, BaseViewHolder>(R.layout.item_channel_shop),
            LoadMoreModule {
            override fun convert(
                holder: BaseViewHolder,
                item: ChannelX?

            ) {
                holder.setText(R.id.tv_name, item?.name)
                GlideApp.with(holder.itemView.context)
                    .load(item?.logo)
                    .into(holder.getView(R.id.iv_picture))

//
//

            }
        }
        mDatabind.rectangle1.layoutManager = LinearLayoutManager(this)
        var recyclerViewDivider = RecyclerViewDivider()
        recyclerViewDivider.setDividerHeight(30)
        mDatabind.rectangle1.addItemDecoration(recyclerViewDivider)
        mDatabind.rectangle1.adapter = channelXAdapter
        channelXAdapter.setList(arrayListOf())
        channelXAdapter.setOnItemClickListener { adapter, view, position ->
             startActivity(Intent(this, BaseWebActivity::class.java).putExtra("url","http://dev-oms.igoodsale.com/#/userAgreement"))

        }


    }


}