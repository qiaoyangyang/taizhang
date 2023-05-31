package com.meiling.common.fragment

import android.content.Context
import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.appcompat.app.AppCompatActivity
import androidx.fragment.app.Fragment
import androidx.lifecycle.ViewModelProvider
import com.google.gson.Gson
import com.gyf.immersionbar.ImmersionBar
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
       // initImmersion()
    }

    /**
     * 初始化沉浸式
     */
    protected open fun initImmersion() {
        Log.d("yjk","initImmersion---${isStatusBarEnabled()}")
        // 初始化沉浸式状态栏
            getStatusBarConfig().init()
            Log.d("yjk","BaseVmFragment---isStatusBarEnabled")

    }
    /**
     * 状态栏字体深色模式
     */
    open fun isStatusBarDarkFont(): Boolean {
        return true
    }

    /** 状态栏沉浸 */
    private var immersionBar: ImmersionBar? = null
    /**
     * 获取状态栏沉浸的配置对象
     */
    open fun getStatusBarConfig(): ImmersionBar {
        if (immersionBar == null) {

            immersionBar = createStatusBarConfig()
        }else{
            immersionBar = createStatusBarConfig()
        }
        return immersionBar!!
    }
    /**
     * 初始化沉浸式状态栏
     */
    protected open fun createStatusBarConfig(): ImmersionBar {
        Log.d("yjk","初始化沉浸式状态栏--------"+isStatusBarDarkFont())
        return ImmersionBar.with(this) // 默认状态栏字体颜色为黑色
            .statusBarDarkFont(isStatusBarDarkFont()) // 指定导航栏背景颜色
            .autoDarkModeEnable(true, 0.2f)
    }
    /**
     * 是否在Fragment使用沉浸式
     */
    open fun isStatusBarEnabled(): Boolean {
        return false
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