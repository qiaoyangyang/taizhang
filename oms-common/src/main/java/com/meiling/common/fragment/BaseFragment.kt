package com.meiling.common.fragment

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.meiling.common.BaseViewModel


abstract class BaseFragment<VM : BaseViewModel, DB : ViewBinding> : BaseVmDbFragment<VM, DB>() {





    abstract override fun initView(savedInstanceState: Bundle?)


    override fun createObserver() {}

    override fun initData() {

    }

    override fun initListener() {

    }


    override fun showLoading(message: String) {

    }


    override fun dismissLoading() {

    }




}