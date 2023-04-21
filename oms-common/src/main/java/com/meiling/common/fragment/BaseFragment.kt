package com.meiling.common.fragment

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.meiling.common.BaseViewModel
import com.meiling.common.dialog.LoadingDialog


abstract class BaseFragment<VM : BaseViewModel, DB : ViewBinding> : BaseVmDbFragment<VM, DB>() {



    private var mLoadingDialog: LoadingDialog? = null


    abstract override fun initView(savedInstanceState: Bundle?)


    override fun createObserver() {}

    override fun initData() {

    }

    override fun initListener() {

    }


    override fun showLoading(message: String) {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog(mActivity)
        }
        if (!mLoadingDialog!!.isShowing()) {
            mLoadingDialog!!.show()
        }
        mLoadingDialog?.setContent(message)
    }


    override fun dismissLoading() {
        if (mLoadingDialog != null && mLoadingDialog!!.isShowing) {
            mLoadingDialog!!.dismiss()
        }
    }



}