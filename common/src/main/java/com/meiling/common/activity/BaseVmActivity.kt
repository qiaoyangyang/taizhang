package com.meiling.common.activity

import android.annotation.SuppressLint
import android.app.Activity
import android.content.Context
import android.graphics.PixelFormat
import android.os.Build
import android.os.Bundle
import android.text.TextUtils
import android.util.Log
import android.view.*
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
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
import com.meiling.common.network.NetworkMonitorManager
import com.meiling.common.network.data.UserStoreList
import com.meiling.common.network.data.userInfoBean
import com.meiling.common.network.enums.NetworkState
import com.meiling.common.network.interfaces.NetworkMonitor
import com.meiling.common.network.util.NetworkStateUtils
import com.meiling.common.utils.GsonUtils
import com.meiling.common.utils.MMKVUtils
import com.meiling.common.utils.SpannableUtils
import com.umeng.analytics.MobclickAgent


abstract class BaseVmActivity<VM : BaseViewModel> : AppCompatActivity(), TitleBarAction {

    private var isUserDb = false

    lateinit var mViewModel: VM

    private var mLoadingDialog: LoadingDialog? = null


    abstract fun initView(savedInstanceState: Bundle?)


    override fun onCreate(savedInstanceState: Bundle?) {
        val flags = (View.SYSTEM_UI_FLAG_LAYOUT_STABLE
                or View.SYSTEM_UI_FLAG_LAYOUT_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_LAYOUT_FULLSCREEN
                or View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                or View.SYSTEM_UI_FLAG_FULLSCREEN
                or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
        window.decorView.systemUiVisibility = flags
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
        hideBottomUIMenu()
        hideNavigationBar()
        NetworkMonitorManager.getInstance().register(this)
        initTipView();//初始化提示View
        Log.d("yjk", "init: ")
    }


    open fun initListener() {

    }
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

    fun settitleBar() {
        val titleBar = getTitleBar()
        titleBar?.setOnTitleBarListener(this)


        // 初始化沉浸式状态栏
        if (isStatusBarEnabled()) {
            getStatusBarConfig().init()

            // 设置标题栏沉浸

            if (titleBar != null) {
                ImmersionBar.setTitleBar(this, titleBar)
            } else {
            }
        } else {
//            ImmersionBar.with(this).statusBarDarkFont(true)
//                .autoDarkModeEnable(true, 0.2f).init()
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
        if (TextUtils.isEmpty(content)) {
            mLoadingDialog?.setContent("加载中")
        } else {
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
        return true
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

    var userInfoBean: userInfoBean? = null
    open fun MyuserInfoBean(): userInfoBean? {
        userInfoBean =
            GsonUtils.getPerson(
                MMKVUtils.getString("UserBean", ""),
                com.meiling.common.network.data.userInfoBean::class.java
            )
        return userInfoBean
    }

    open fun SaveUserBean(userBean: userInfoBean?) {
        MMKVUtils.putString("UserBean", Gson().toJson(userBean))
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


    /**
     * 初始化软键盘
     */
    protected open fun initSoftKeyboard() {
        // 点击外部隐藏软键盘，提升用户体验
        getContentView()!!.setOnClickListener {
            hideSoftKeyboard()
            setContentView()
        }
    }

    open fun setContentView() {}

    override fun onResume() {
        super.onResume()
        // 自动采集选择
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
        val networkState: Boolean = NetworkStateUtils.hasNetworkCapability(this)
        hasNetWork(networkState)
    }

    override fun onPause() {
        super.onPause()
        // 自动采集选择
        MobclickAgent.setPageCollectionMode(MobclickAgent.PageMode.AUTO);
    }


    /**
     * 隐藏软键盘
     */
    open fun hideSoftKeyboard() {
        // 隐藏软键盘，避免软键盘引发的内存泄露
        val view = currentFocus
        if (view != null) {
            val manager = getSystemService(INPUT_METHOD_SERVICE) as InputMethodManager
            manager?.hideSoftInputFromWindow(view.windowToken, 0)
        }
    }

    override fun dispatchTouchEvent(ev: MotionEvent?): Boolean {
        if (ev!!.action == MotionEvent.ACTION_DOWN) {
            val v = currentFocus
//            if (isShouldHideKeyboard(v, ev)) {
//                //根据判断关闭软键盘
//               // SoftKeyboardUtils.hideKeyboard(v!!)
//                KeyBoardUtil.hideKeyBoard(this,v)
//            }
            if (v != null) {
                hideSoftKeyboard()
                // KeyBoardUtil.hideKeyBoard(this, v)
            }
        }
        return super.dispatchTouchEvent(ev)
    }

    open fun hideBottomUIMenu() {
        //隐藏虚拟按键，并且全屏
        if (Build.VERSION.SDK_INT > 11 && Build.VERSION.SDK_INT < 19) { // lower api
            val v = this.window.decorView
            v.systemUiVisibility = View.GONE
        } else if (Build.VERSION.SDK_INT >= 19) {
            val _window: Window = window
            val params: WindowManager.LayoutParams = _window.getAttributes()
            params.systemUiVisibility =
                View.SYSTEM_UI_FLAG_HIDE_NAVIGATION or View.SYSTEM_UI_FLAG_IMMERSIVE
            _window.setAttributes(params)
        }
    }

    /**
     * 隐藏系统的导航栏
     */
    @SuppressLint("NewApi")
    open fun hideNavigationBar() {
        if (Build.VERSION.SDK_INT >= 19) {
            val decorView: View = window.decorView
            val uiOptions: Int = (View.SYSTEM_UI_FLAG_HIDE_NAVIGATION
                    or View.SYSTEM_UI_FLAG_IMMERSIVE_STICKY)
            decorView.setSystemUiVisibility(uiOptions)
        }
    }

    override fun setRequestedOrientation(requestedOrientation: Int) {
        return
    }

    var mTipView: View? = null
    var mWindowManager: WindowManager? = null
    var mLayoutParams: WindowManager.LayoutParams? = null

    open fun initTipView() {
        val inflater = layoutInflater

        mTipView = inflater.inflate(R.layout.network_tip, null) //提示View布局
        mWindowManager = this.getSystemService(WINDOW_SERVICE) as WindowManager
        mLayoutParams = WindowManager.LayoutParams(
            ViewGroup.LayoutParams.MATCH_PARENT, ViewGroup.LayoutParams.WRAP_CONTENT,
            WindowManager.LayoutParams.TYPE_APPLICATION,
            WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS,  //   | WindowManager.LayoutParams.FLAG_NOT_TOUCHABLE, //注释掉可以进行事件监听
            PixelFormat.TRANSLUCENT
        )
        mLayoutParams?.flags=WindowManager.LayoutParams.FLAG_TRANSLUCENT_STATUS
        //使用非CENTER时，可以通过设置XY的值来改变View的位置
        mLayoutParams?.gravity = Gravity.TOP
        mLayoutParams?.horizontalMargin=40f
        mLayoutParams?.x =300
        mLayoutParams?.y = 0
       // mLayoutParams?.gravity = Gravity.CENTER;
    }


    open fun hasNetWork(has: Boolean) {
        Log.d("yjk", "hasNetWork: $mCheckNetWork")
        if (has) {
            if (mTipView != null && mTipView!!.parent != null) {
                mWindowManager!!.removeView(mTipView)
                Log.e("日志", "有网络")
            }
        } else {
            if (mTipView!!.parent == null) {
                mWindowManager!!.addView(mTipView, mLayoutParams)
                var tv_title = mTipView?.findViewById<TextView>(R.id.tv_title)
                SpannableUtils.setTextcolor(
                    this,
                    "网络故障，请检查WiFi连接情况后，重试...",
                    tv_title,
                    5,
                    14,
                    R.color.tv_title
                )

                var iv_Close = mTipView?.findViewById<ImageView>(R.id.iv_Close)
                iv_Close?.setOnClickListener {
                    mWindowManager!!.removeView(mTipView)
                }


                Log.e("日志", "无网络")

            }


        }
    }

    @NetworkMonitor
    fun onNetWorkStateChange(networkState: NetworkState) {
        Log.d("yjk", "onNetWorkStateChange: ")
        when (networkState) {
            NetworkState.NONE -> {
                // showToast("暂无网络")
                hasNetWork(false)
            }
            NetworkState.WIFI -> {
                hasNetWork(true)
            }
            NetworkState.CELLULAR -> {
            }
        }
    }

    var mCheckNetWork = false //默认检查网络状态

    override fun onDestroy() {
        super.onDestroy()
        NetworkMonitorManager.getInstance().unregister(this)
    }


    open fun isCheckNetWork(): Boolean {
        return mCheckNetWork
    }

    override fun finish() {
        super.finish()
        //当提示View被动态添加后直接关闭页面会导致该View内存溢出，所以需要在finish时移除
        if (mTipView != null && mTipView!!.parent != null) {
            mWindowManager!!.removeView(mTipView)
        }
    }


}