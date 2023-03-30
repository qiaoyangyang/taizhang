package com.meiling.common.activity

import android.content.Context
import android.os.Bundle
import android.view.inputmethod.InputMethodManager
import android.widget.EditText
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.BarUtils
import com.meiling.common.dialog.LoadingDialog
import com.meiling.common.getVmClazz
import com.meiling.common.BaseViewModel


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
        mLoadingDialog?.setContent(content)
    }

    open fun disLoading() {
        if (mLoadingDialog != null && mLoadingDialog!!.isShowing) {
            mLoadingDialog!!.dismiss()
        }
    }
}