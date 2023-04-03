package com.meiling.common.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ImmersionBar
import com.hjq.bar.TitleBar
import com.meiling.common.BaseViewModel
import com.meiling.common.R
import com.meiling.common.action.TitleBarAction


abstract class BaseActivity<VM : BaseViewModel, DB : ViewBinding> : BaseVmDbActivity<VM, DB>(),
    TitleBarAction {

    /** 标题栏对象 */
    private var titleBar: TitleBar? = null

    /** 状态栏沉浸 */
    private var immersionBar: ImmersionBar? = null


    abstract override fun initView(savedInstanceState: Bundle?)

    override fun createObserver() {}
    /**
     * 设置标题栏的标题
     */
    override fun setTitle(@StringRes id: Int) {
        title = getString(id)
    }

    /**
     * 设置标题栏的标题
     */
    override fun setTitle(title: CharSequence?) {
    }

    override fun getTitleBar(): TitleBar? {
        if (titleBar == null) {
            titleBar = obtainTitleBar(getContentView())
        }
        return titleBar
    }

    override fun initDataBind() {
        super.initDataBind()
        val titleBar = getTitleBar()
        titleBar?.setOnTitleBarListener(this)

        // 初始化沉浸式状态栏
        if (isStatusBarEnabled()) {
            getStatusBarConfig().init()

            // 设置标题栏沉浸

            if (titleBar != null) {
                Log.d("yjk", "initListener: ")
                ImmersionBar.setTitleBar(this, titleBar)
            }else{
                Log.d("yjk", "initListener ==null ")
            }
        }
    }
    override fun initListener() {
        super.initListener()

    }

    override fun onLeftClick(view: View) {
        onBackPressed()
    }
    /**
     * 是否使用沉浸式状态栏
     */
    protected open fun isStatusBarEnabled(): Boolean {
        return true
    }

    /**
     * 状态栏字体深色模式
     */
    open fun isStatusBarDarkFont(): Boolean {
        return false
    }
    /**
     * 获取状态栏沉浸的配置对象
     */
    open fun getStatusBarConfig(): ImmersionBar {
        if (immersionBar == null) {
            immersionBar = createStatusBarConfig()
        }
        return immersionBar!!
    }

    /**
     * 初始化沉浸式状态栏
     */
    protected open fun createStatusBarConfig(): ImmersionBar {
        return ImmersionBar.with(this) // 默认状态栏字体颜色为黑色
            .statusBarDarkFont(isStatusBarDarkFont()) // 指定导航栏背景颜色
            .autoDarkModeEnable(true, 0.2f)
    }




}
