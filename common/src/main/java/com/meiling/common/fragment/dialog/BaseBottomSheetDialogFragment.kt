package com.meiling.common.fragment.dialog

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.meiling.common.BaseViewModel

abstract class BaseBottomSheetDialogFragment<VM : BaseViewModel, DB : ViewBinding> : BaseVmDbBottomSheetDialogFragment<VM, DB>() {

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