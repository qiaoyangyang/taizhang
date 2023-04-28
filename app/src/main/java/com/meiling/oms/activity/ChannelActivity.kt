package com.meiling.oms.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hjq.shape.view.ShapeTextView
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
import com.meiling.oms.dialog.MineExitDialog
import com.meiling.oms.dialog.ShopDialog
import com.meiling.oms.viewmodel.StoreManagementViewModel
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast

//渠道店铺管理
class ChannelActivity : BaseActivity<StoreManagementViewModel, ActivityChannelBinding>() {
    lateinit var channeAdapter: BaseQuickAdapter<ChannShop?, BaseViewHolder>
    lateinit var channelXAdapter: BaseQuickAdapter<ChannelX?, BaseViewHolder>
    lateinit var channelX: ChannelX
    override fun initView(savedInstanceState: Bundle?) {
        TextDrawableUtils.setRightDrawable(mDatabind.TitleBar.titleView, R.drawable.xia)
        initRecycleyView()
        initRecycleyView1()
        mDatabind.tvNewlyBuiltStore.setSingleClickListener() {
            if (channelX.id == "32") {
                startActivity(Intent(this, TiktokBindingActvity::class.java))
            }else {
                mViewModel.urlauth(channelX.id!!, shop?.id!!, channelX?.id!!)
            }

        }
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

            }

            override fun Ondismiss() {
            }

        })
        shopDialog.show(supportFragmentManager)
    }

    var shopBean: ArrayList<ShopBean>? = null
    var poi = -1

    override fun initData() {
        mViewModel.citypoi()
        poi = intent.getIntExtra("poi", -1)

    }

    override fun onResume() {
        super.onResume()
        mViewModel.getShopAndChannelVO()

    }


    override fun createObserver() {
        mViewModel.shopBean.onStart.observe(this) {
            showLoading("")
        }
        mViewModel.shopBean.onSuccess.observe(this) {
            disLoading()
            if (it != null && it.size != 0) {
                shopBean = it
                shop = it.get(0).shopList?.get(0)
                mDatabind.TitleBar.title = it.get(0).name + "/" + it.get(0).shopList?.get(0)?.name

            } else if (ByTenantId()?.poi == -1) {
                val view =
                    LayoutInflater.from(this).inflate(R.layout.store_managemnet1, null, false)
                var tv_decreate = view.findViewById<ShapeTextView>(R.id.tv_decreate)
                tv_decreate.setOnClickListener {
                    startActivity(Intent(this, NewlyBuiltStoreActivity::class.java))
                }

                channelXAdapter.setEmptyView(view)
            }

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
            it[0]?.isselect = true

            channelXAdapter.setList(it)
            channelX = it[0]
            mViewModel.shop_list(channelX.id!!, shop?.id!!)
        }

        mViewModel.channel.onError.observe(this) {
            disLoading()
            showToast(it.msg)
        }
        mViewModel.channShop.onStart.observe(this) {
            showLoading("")


        }
        mViewModel.channShop.onError.observe(this) {
            disLoading()
            showToast(it.msg)
        }
        mViewModel.channShop.onSuccess.observe(this) {
            disLoading()
//            if (it.data?.size==0){
//                val view =
//                    LayoutInflater.from(this).inflate(R.layout.store_managemnet, null, false)
//                var tv_decreate = view.findViewById<ShapeTextView>(R.id.txt_error)
//                tv_decreate.text = "暂无数据，请去绑定渠道店铺"
//
//
//                channeAdapter.setEmptyView(view)
//                channeAdapter.setList(arrayListOf())
//            }else {
//
//            }
            channeAdapter.setList(it.data)

        }
        mViewModel.urlauth.onSuccess.observe(this) {
            startActivity(Intent(this, BaseWebActivity::class.java).putExtra("url", it.url))
        }


    }


    var isposition = -1
    private fun initRecycleyView() {

        channeAdapter = object :
            BaseQuickAdapter<ChannShop?, BaseViewHolder>(R.layout.item_chann_shop),
            LoadMoreModule {
            override fun convert(
                holder: BaseViewHolder,
                item: ChannShop?

            ) {
                holder.setText(R.id.tv_name_t, "三方平台名称:" + item?.name)
                holder.setText(R.id.tv_channel_id, "三方平台名称:" + item?.channelShopId)


            }
        }
        mDatabind.rectangle.layoutManager = LinearLayoutManager(this)
        var recyclerViewDivider = RecyclerViewDivider()
        recyclerViewDivider.setDividerHeight(30)
        mDatabind.rectangle.addItemDecoration(recyclerViewDivider)
        mDatabind.rectangle.adapter = channeAdapter
        channeAdapter.setEmptyView(R.layout.store_managemnet2)
        channeAdapter.setList(arrayListOf())

        channeAdapter.addChildClickViewIds(R.id.tv_unbundle)
        channeAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.tv_unbundle -> {
                    val dialog: MineExitDialog =
                        MineExitDialog().newInstance(
                            "温馨提示",
                            "解绑后，订单将不会同步，确定是否解除绑定？",
                            "取消",
                            "确认",
                            false
                        )
                    dialog.setOkClickLister {
                        isposition = position
                        dialog.dismiss()


                    }
                    dialog.show(supportFragmentManager)
                }
            }
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
                var tv_name = holder.getView<TextView>(R.id.tv_name)
                if (item?.isselect == true) {
                    holder.setTextColor(R.id.tv_name, Color.parseColor("#212121"))
                    tv_name.textSize = 18f
                    //tv_name.textSize = context.resources.getDimension(R.dimen.sp_16)
                    holder.setGone(R.id.tv_xin, false)
                    tv_name.setTypeface(null, Typeface.BOLD)
                } else {
                    holder.setGone(R.id.tv_xin, true)
                    holder.setTextColor(R.id.tv_name, Color.parseColor("#757575"))
                    // tv_name.textSize = context.resources.getDimension(R.dimen.sp_12)
                    tv_name.textSize = 14f
                    tv_name.setTypeface(null, Typeface.NORMAL)

                }
//
//

            }
        }
        mDatabind.rectangle1.layoutManager = LinearLayoutManager(
            this,
            RecyclerView.HORIZONTAL, false
        )
        var recyclerViewDivider = RecyclerViewDivider()
        mDatabind.rectangle1.addItemDecoration(recyclerViewDivider)
        mDatabind.rectangle1.adapter = channelXAdapter
        channelXAdapter.setList(arrayListOf())
        channelXAdapter.setOnItemClickListener { adapter, view, position ->
            channelXAdapter.data.forEach {
                it?.isselect = false
            }
            channelXAdapter.data.get(position)?.isselect = true
            channelXAdapter.notifyDataSetChanged()
            channelX = channelXAdapter?.data?.get(position)!!
            mViewModel.shop_list(channelX.id!!, shop?.id!!)
        }


    }


}