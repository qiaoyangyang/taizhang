package com.meiling.common.fragment

import android.content.Context
import android.os.Bundle
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.meiling.common.BaseViewModel
import com.meiling.common.dialog.LoadingDialog
import com.meiling.common.getVmClazz
import com.meiling.common.network.data.ByTenantId
import com.meiling.common.utils.GsonUtils
import com.meiling.common.utils.MMKVUtils


/**

 * @Author : xf

 * @Time : On 2021/3/3 11:01

 * @Description : BaseVmFragment

 */
abstract class BaseVmFragment<VM : BaseViewModel> : Fragment() {



    private var isFirst: Boolean = true


    lateinit var mViewModel: VM

    lateinit var mActivity: AppCompatActivity
    private var mLoadingDialog: LoadingDialog? = null


    override fun onAttach(context: Context) {
        super.onAttach(context)
        mActivity = context as AppCompatActivity
    }


    override fun onViewCreated(view: View, savedInstanceState: Bundle?) {
        super.onViewCreated(view, savedInstanceState)
        isFirst = true
        mViewModel = createViewModel()
        initView(savedInstanceState)
        createObserver()
        initData()
        initListener()
    }



    private fun createViewModel(): VM {
//        return ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(getVmClazz(this))
        return ViewModelProvider(this)
            .get(getVmClazz(this))
    }

    abstract fun initView(savedInstanceState: Bundle?)

    abstract fun createObserver()

    open fun initData() {}

    open fun initListener() {}

    abstract fun showLoading(message: String = "请求网络中...")

    abstract fun dismissLoading()


    var byTenantId: ByTenantId?=null
    open fun ByTenantId(): ByTenantId? {
        byTenantId = GsonUtils.getPerson(MMKVUtils.getString("byTenantId", ""), ByTenantId::class.java)
        return byTenantId
    }

    open fun SaveUserBean(byTenantId: ByTenantId?) {
        MMKVUtils.putString("byTenantId", Gson().toJson(byTenantId))
    }





}