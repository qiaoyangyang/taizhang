package com.meiling.common.activity

import android.app.Activity
import android.content.Context
import android.os.Bundle
import android.text.TextUtils
import android.view.View
import android.view.ViewGroup
import android.view.Window
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.BarUtils
import com.gyf.immersionbar.ImmersionBar
import com.meiling.common.BaseViewModel
import com.meiling.common.dialog.LoadingDialog
import com.meiling.common.getVmClazz


abstract class BaseVmActivity<VM : BaseViewModel> : AppCompatActivity() {

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
    }

    open fun setBar(context: Activity, view: View) {
        ImmersionBar.with(context).init()
        ImmersionBar.setTitleBar(context, view)
    }

    abstract fun isLightMode(): Boolean

    private fun init(savedInstanceState: Bundle?) {
        mViewModel = createViewModel()
        initView(savedInstanceState)
        createObserver()
        initListener()
        initData()

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

    open fun initDataBind() {}


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
}