package com.meiling.oms.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.Typeface
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hjq.shape.view.ShapeTextView
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.Shop
import com.meiling.common.network.data.ShopBean
import com.meiling.common.utils.RecyclerViewDivider
import com.meiling.common.utils.TextDrawableUtils
import com.meiling.oms.R
import com.meiling.oms.bean.ChannShop
import com.meiling.oms.bean.ChannelX
import com.meiling.oms.databinding.ActivityChannelBinding
import com.meiling.oms.dialog.BindMeituanShopDialog
import com.meiling.oms.dialog.MineExitDialog
import com.meiling.oms.dialog.ShopDialog
import com.meiling.oms.viewmodel.MainViewModel2
import com.meiling.oms.viewmodel.StoreManagementViewModel
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast

//渠道店铺管理
class ChannelActivity : BaseActivity<StoreManagementViewModel, ActivityChannelBinding>() {
    lateinit var channeAdapter: BaseQuickAdapter<ChannShop?, BaseViewHolder>
    lateinit var channelXAdapter: BaseQuickAdapter<ChannelX?, BaseViewHolder>
    lateinit var channelX: ChannelX
    lateinit var mainViewModel: MainViewModel2
    override fun initView(savedInstanceState: Bundle?) {
        TextDrawableUtils.setRightDrawable(mDatabind.TitleBar.titleView, R.drawable.xia)
        initRecycleyView()
        initRecycleyView1()
        mDatabind.tvNewlyBuiltStore.setSingleClickListener() {
            if (channelX.id == "32") {
                mViewModel.isTenant()


            } else if (channelX.id == "1") {
                var bindMeituanShopDialog = BindMeituanShopDialog()
                bindMeituanShopDialog.setOnresilience(object : BindMeituanShopDialog.Onresilience {
                    override fun resilience(type: Int) {
                        mViewModel.urlauth(
                            type.toString(),
                            shop?.id!!, type.toString()
                        )//channelX?.id!!

                    }

                })
                bindMeituanShopDialog.show(supportFragmentManager)
            }else if (channelX.id=="100"){
                startActivity(Intent(this,LebaiRetailBindingActivity::class.java))
            } else {
                mViewModel.urlauth(channelX.id!!, shop?.id!!, channelX?.id!!)
            }

        }
        mainViewModel =
            ViewModelProvider(MainActivity.mainActivity!!).get(MainViewModel2::class.java)
        mDatabind.refeshLayout.setOnRefreshListener {
            if (shop == null) {
                mDatabind.refeshLayout.finishRefresh()
            } else {
                mViewModel.shop_list(channelX.id!!, shop?.id!!)
            }
        }
        mViewModel.citypoi()

    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityChannelBinding {
        return ActivityChannelBinding.inflate(layoutInflater)
    }

    var shop: Shop? = null
    var type = ""
    var cityposition= 0
    var shopidposition = 0
    override fun onTitleClick(view: View) {


       var shopDialog = ShopDialog().newInstance(shopBean!!, "选择发货门店",cityposition,shopidposition)
        shopDialog.show(supportFragmentManager)
        shopDialog.setOnresilience(object : ShopDialog.Onresilience {


            override fun resilience(
                cityi: Int,
                cityidname: String,
                shopid: Int,
                sho: Shop
            ) {
                cityposition=cityi
                shopidposition=shopid
                shop = sho
                mDatabind.TitleBar.titleView.text = cityidname + "/" + sho.name

                mViewModel.shop_list(channelX.id!!, shop?.id!!)

            }

            override fun Ondismiss() {
            }

        })

    }

    var shopBean: ArrayList<ShopBean>? = null
    var poi = -1

    override fun initData() {
        type = intent.getStringExtra("type").toString()
        poi = intent.getIntExtra("poi", -1)

    }

    override fun onResume() {
        super.onResume()
        Log.d("yjk", "${channelXAdapter.data.size}")
        if (channelXAdapter.data.size == 0) {
            mViewModel.getShopAndChannelVO()
        } else {
            if (shopBean != null) {
                mViewModel.shop_list(channelX.id!!, shop?.id!!)
            }
        }

    }


    override fun createObserver() {
        mainViewModel.getByTenantId.observe(this) {

            if (it.poi == -1) {//门店是否创建 1绑定;-1没绑定

                mDatabind.tvNewlyBuiltStore.visibility = View.GONE
                val view =
                    LayoutInflater.from(this).inflate(R.layout.store_managemnet1, null, false)
                var tv_decreate = view.findViewById<ShapeTextView>(R.id.tv_decreate)
                tv_decreate.setOnClickListener {
                    startActivity(Intent(this, NewlyBuiltStoreActivity::class.java))
                }

                channeAdapter.setEmptyView(view)
            } else {
                channeAdapter.setEmptyView(R.layout.store_managemnet2)
            }
        }
        //修改发货门店
        mViewModel.updateShop.onStart.observe(this) {
            showLoading("")
        }
        mViewModel.updateShop.onSuccess.observe(this) {
            disLoading()
            channeAdapter.removeAt(isposition)
            showToast("修改发货门店成功")
            channeAdapter.notifyDataSetChanged()
        }
        mViewModel.updateShop.onError.observe(this) {

            disLoading()
            showToast(it.msg)
        }
        //解绑
        mViewModel.releasebind.onStart.observe(this) {
            showLoading("")
        }
        mViewModel.releasebind.onSuccess.observe(this) {
            disLoading()
            if (channelX.id == "1") {

                startActivity(Intent(this, BaseWebActivity::class.java).putExtra("url", it))

            } else {
                channeAdapter.removeAt(isposition)
                showToast("解绑成功")
                channeAdapter.notifyDataSetChanged()
            }
        }
        mViewModel.releasebind.onError.observe(this) {

            disLoading()
            showToast(it.msg)
        }


        //是否绑定租户
        mViewModel.isTenant.onStart.observe(this) {
            showLoading("")
        }
        mViewModel.isTenant.onSuccess.observe(this) {
            disLoading()
            if (it) {
                startActivity(
                    Intent(this, BindingTiktokListActivity::class.java).putExtra(
                        "channelId", channelX.id
                    )
                        .putExtra("poiId", shop?.id).putExtra("channename", channelX.name)

                )
            } else {
                startActivity(
                    Intent(this, TiktokBindingActvity::class.java)
                        .putExtra("channelId", channelX.id)
                        .putExtra("poiId", shop?.id)
                        .putExtra("channename", channelX.name)
                )
            }

        }
        mViewModel.isTenant.onError.observe(this) {
            disLoading()
            showToast(it.msg)
        }


        mViewModel.shopBean.onStart.observe(this) {
            showLoading("")
        }
        mViewModel.shopBean.onSuccess.observe(this) {
            disLoading()
            if (it != null && it.size != 0) {
                shopBean = it
                shop = it.get(0).shopList?.get(0)
                mDatabind.TitleBar.title =
                    it.get(0).name + "/" + it.get(0).shopList?.get(0)?.name
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

            it.forEachIndexed { index, channe ->
                if (type == "1") {
                    if (channe.id == "32") {
                        it[index]?.isselect = true
                        channelX = it[index]
                        mDatabind.rectangle1.scrollToPosition(index)

                    }
                } else if (type == "2") {
                    if (channe.id == "7") {
                        it[index]?.isselect = true
                        channelX = it[index]
                        mDatabind.rectangle1.scrollToPosition(index)
                    }
                } else {
                    it[0]?.isselect = true
                    channelX = it[0]
                    channelXAdapter.setList(it)
                    mViewModel.shop_list(channelX.id!!, shop?.id!!)
                }
            }

            channelXAdapter.setList(it)
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
            mDatabind.refeshLayout.finishRefresh()
        }
        mViewModel.channShop.onSuccess.observe(this) {
            mDatabind.refeshLayout.finishRefresh()
            disLoading()
            channeAdapter.setList(it.data)

        }
        mViewModel.urlauth.onSuccess.observe(this) {

            startActivity(Intent(this, BaseWebActivity::class.java).putExtra("url", it.url))
        }
        mViewModel.urlauth.onError.observe(this) {
            showToast(it.msg)
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
                if (TextUtils.isEmpty(item?.channelShopId))
                    holder.setText(R.id.tv_channel_id, "三方平台ID:" + item?.channelShopId)
                if (item?.mtModel == 2) {
                    holder.setGone(R.id.s_status, false);
                } else {
                    holder.setGone(R.id.s_status, true);
                }


            }
        }
        mDatabind.rectangle.layoutManager = LinearLayoutManager(this)
        var recyclerViewDivider = RecyclerViewDivider()
        recyclerViewDivider.setDividerHeight(30)
        mDatabind.rectangle.addItemDecoration(recyclerViewDivider)
        mDatabind.rectangle.adapter = channeAdapter
        channeAdapter.setEmptyView(R.layout.store_managemnet2)
        channeAdapter.setList(arrayListOf())

        channeAdapter.addChildClickViewIds(R.id.tv_delete, R.id.tv_compile)
        channeAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.tv_compile -> {
                    var shopDialog = ShopDialog().newInstance(shopBean!!, "修改发货门店")

                    shopDialog.setOnresilience(object : ShopDialog.Onresilience {


                        override fun resilience(
                            cityid: Int,
                            cityidname: String,
                            shopid: Int,
                            sho: Shop
                        ) {

                            if (shop?.id == sho.id) {
                                showToast("已经是当前门店啦，请重新选择门店或关闭窗口")
                                return
                            }
                            shopDialog.dismiss()
                            isposition = position
                            mViewModel.updateShop(
                                channeAdapter.getItem(position)?.id!!,
                                sho?.id!!
                            )
                        }

                        override fun Ondismiss() {
                        }

                    })
                    shopDialog.show(supportFragmentManager)
                }
                R.id.tv_delete -> {
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
                        mViewModel.releasebind(
                            channelX.id!!,
                            channeAdapter?.getItem(position)?.id!!,
                        )
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