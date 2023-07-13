package com.meiling.common.fragment

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.google.gson.Gson
import com.meiling.common.BaseViewModel
import com.meiling.common.dialog.LoadingDialog
import com.meiling.common.network.data.UserStoreList
import com.meiling.common.utils.GsonUtils
import com.meiling.common.utils.MMKVUtils


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
        if (!mLoadingDialog!!.isShowing) {
            mLoadingDialog!!.show()
        }
        mLoadingDialog?.setContent(message)
    }


    override fun dismissLoading() {
        if (mLoadingDialog != null && mLoadingDialog!!.isShowing) {
            mLoadingDialog!!.dismiss()
        }
    }

    var userStoreList: UserStoreList? = null
    open fun userStoreList(): UserStoreList? {
        userStoreList =
            GsonUtils.getPerson(
                MMKVUtils.getString("UserStoreList", ""),
                com.meiling.common.network.data.UserStoreList::class.java
            )
        return userStoreList
    }

    open fun SaveUserStoreList(UserStoreList: UserStoreList?) {
        MMKVUtils.putString("UserStoreList", Gson().toJson(UserStoreList))
    }


}