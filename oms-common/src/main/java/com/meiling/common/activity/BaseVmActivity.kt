package com.meiling.common.activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.annotation.StringRes
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.BarUtils
import com.google.gson.Gson
import com.gyf.immersionbar.ImmersionBar
import com.hjq.bar.TitleBar
import com.meiling.common.BaseViewModel
import com.meiling.common.R
import com.meiling.common.action.TitleBarAction
import com.meiling.common.dialog.LoadingDialog
import com.meiling.common.getVmClazz
import com.meiling.common.network.data.ByTenantId
import com.meiling.common.utils.GsonUtils
import com.meiling.common.utils.MMKVUtils
import com.umeng.analytics.MobclickAgent


abstract class BaseVmActivity<VM : BaseViewModel> : AppCompatActivity() , TitleBarAction {

    private var isUserDb = false

    lateinit var mViewModel: VM

    private var mLoadingDialog: LoadingDialog? = null

    abstract fun initView(savedInstanceState: Bundle?)


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        ARouter.getInstance().inject(this)
        BarUtils.setStatusBarLightMode(this, isLightMode());
        initDataBind()
        init(savedInstanceState)
        settitleBar()
        overridePendingTransition(R.anim.activity_right_in, R.anim.activity_right_out)
    }


    open fun setBar(context: Activity, view: View) {
    }

    abstract fun isLightMode(): Boolean

    private fun init(savedInstanceState: Bundle?) {
        mViewModel = createViewModel()
        initView(savedInstanceState)
        createObserver()
        initListener()
        initData()
        overridePendingTransition(R.anim.activity_right_in, R.anim.activity_right_out)
    }


    open fun initListener() {}
    open fun initData() {}

    /**
     * 创建viewModel
     */
    private fun createViewModel(): VM {
        return ViewModelProvider(this)[getVmClazz(this)]
    }


    /**
     * 创建LiveData数据观察者
     */
    abstract fun createObserver()


    fun userDataBinding(isUserDb: Boolean) {
        this.isUserDb = isUserDb
    }
    fun settitleBar(){
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
    open fun initDataBind() {

    }


    fun showInput(editText: EditText) {
        editText.requestFocus()
        val inputMethodManager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.showSoftInput(editText, InputMethodManager.SHOW_IMPLICIT)
    }

    open fun hintKbTwo(editText: EditText) {
        editText.clearFocus()
        val inputMethodManager =
            getSystemService(Context.INPUT_METHOD_SERVICE) as InputMethodManager
        inputMethodManager.hideSoftInputFromWindow(editText.getWindowToken(), 0);
    }


    open fun showLoading(content: String?) {
        if (mLoadingDialog == null) {
            mLoadingDialog = LoadingDialog(this)
        }
        if (!mLoadingDialog!!.isShowing()) {
            mLoadingDialog!!.show()
        }
        if (TextUtils.isEmpty(content)){
            mLoadingDialog?.setContent("加载中")
        }else {
            mLoadingDialog?.setContent(content)
        }
    }

    open fun disLoading() {
        if (mLoadingDialog != null && mLoadingDialog!!.isShowing) {
            mLoadingDialog!!.dismiss()
        }
    }

    /**
     * 和 setContentView 对应的方法
     */
    open fun getContentView(): ViewGroup? {
        return findViewById(Window.ID_ANDROID_CONTENT)
    }
    /* 设置标题栏的标题
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




    override fun onLeftClick(view: View) {
        onBackPressed()
        overridePendingTransition(R.anim.activity_left_in, R.anim.activity_left_out)
    }

    override fun onTitleClick(view: View) {

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
    /** 标题栏对象 */
    private var titleBar: TitleBar? = null

    /** 状态栏沉浸 */
    private var immersionBar: ImmersionBar? = null
    /**
     * 初始化沉浸式状态栏
     */
    protected open fun createStatusBarConfig(): ImmersionBar {
        return ImmersionBar.with(this) // 默认状态栏字体颜色为黑色
            .statusBarDarkFont(isStatusBarDarkFont()) // 指定导航栏背景颜色
            .autoDarkModeEnable(true, 0.2f)
    }
    var byTenantId: ByTenantId?=null
    open fun ByTenantId(): ByTenantId? {
        byTenantId = GsonUtils.getPerson(MMKVUtils.getString("UserBean", ""), ByTenantId::class.java)
        return byTenantId
    }

    open fun SaveUserBean(userBean: ByTenantId?) {
        MMKVUtils.putString("UserBean", Gson().toJson(userBean))
    }


    override fun onResume() {
        super.onResume()
        // 自动采集选择
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
    }

    override fun onPause() {
        super.onPause()
        // 自动采集选择
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
    }

}