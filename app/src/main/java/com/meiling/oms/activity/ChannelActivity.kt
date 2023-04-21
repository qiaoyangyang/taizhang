package com.meiling.oms.activity

import android.content.Intent
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.Shop
import com.meiling.common.network.data.ShopBean
import com.meiling.common.utils.TextDrawableUtils
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityChannelBinding
import com.meiling.oms.dialog.ShopDialog
import com.meiling.oms.viewmodel.StoreManagementViewModel
import com.meiling.oms.widget.showToast

//渠道店铺管理
class ChannelActivity : BaseActivity<StoreManagementViewModel, ActivityChannelBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        TextDrawableUtils.setRightDrawable(mDatabind.TitleBar.titleView, R.drawable.xia1)
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

    override fun initData() {
        mViewModel.citypoi()
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
        }

        mViewModel.channel.onError.observe(this) {
            disLoading()
            showToast(it.msg)
        }

    }


}