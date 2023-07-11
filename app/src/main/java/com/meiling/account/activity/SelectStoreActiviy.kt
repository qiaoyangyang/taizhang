package com.meiling.account.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.meiling.account.adapter.SelectShopAdapger
import com.meiling.account.databinding.ActivitySelectStoreBinding
import com.meiling.account.viewmodel.LoginViewModel
import com.meiling.account.widget.showToast
import com.meiling.common.activity.BaseActivity

//  选择门店
class SelectStoreActiviy : BaseActivity<LoginViewModel, ActivitySelectStoreBinding>() ,
    OnItemClickListener {
    private lateinit var selectShopAdapger: SelectShopAdapger
    override fun initView(savedInstanceState: Bundle?) {
        selectShopAdapger = SelectShopAdapger()
        mDatabind.rySelectShop.adapter = selectShopAdapger
        selectShopAdapger.setOnItemClickListener(this)


    }

    override fun initData() {
        super.initData()
        mViewModel.userStoreList()
       //
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivitySelectStoreBinding {
        return ActivitySelectStoreBinding.inflate(layoutInflater)
    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.UserStoreList.onStart.observe(this){
            showLoading("")
        }
        mViewModel.UserStoreList.onSuccess.observe(this){
            disLoading()
            selectShopAdapger.setList(it)

        }
        mViewModel.UserStoreList.onError.observe(this){
            disLoading()
            showToast(it.msg)

        }
    }


    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        startActivity(Intent(this, MainActivity::class.java))
    }

}