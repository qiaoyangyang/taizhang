package com.meiling.common.fragment.dialog

import android.content.Context
import android.graphics.Color
import android.os.Bundle
import android.view.Gravity
import android.view.View
import android.view.WindowManager
import androidx.appcompat.app.AppCompatActivity
import androidx.lifecycle.ViewModelProvider

import com.meiling.common.dialog.LoadingDialog
import com.meiling.common.getVmClazz
import com.google.android.material.bottomsheet.BottomSheetDialogFragment
import com.meiling.common.BaseViewModel
import com.meiling.common.R

abstract class BaseVmBottomSheetDialogFragment<VM : BaseViewModel> : BottomSheetDialogFragment() {

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
        setStyle(BottomSheetDialogFragment.STYLE_NORMAL, R.style.CommonShowDialogBottom);
        initView(savedInstanceState)
        createObserver()
        initData()
        initListener()
    }


    override fun onStart() {
        super.onStart()
        dialog!!.window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        dialog!!.window!!.setWindowAnimations(R.style.CommonShowDialogBottom)
        if (isTransparent()) {
            dialog!!.window!!.findViewById<View>(R.id.design_bottom_sheet)
                .setBackgroundColor(Color.TRANSPARENT);
        }
        dialog!!.window!!.setGravity(Gravity.BOTTOM);
        dialog!!.setCanceledOnTouchOutside(isCanceledOnTouchOutside())
        dialog!!.window!!.clearFlags(WindowManager.LayoutParams.FLAG_DIM_BEHIND)
    }


    private fun createViewModel(): VM {
        return ViewModelProvider(this, ViewModelProvider.NewInstanceFactory()).get(getVmClazz(this))
    }

    abstract fun initView(savedInstanceState: Bundle?)


    abstract fun createObserver()


    open fun initData() {}

    open fun initListener() {}

    abstract fun showLoading(message: String = "请求网络中...")

    abstract fun dismissLoading()

    abstract fun isCanceledOnTouchOutside(): Boolean

    abstract fun isTransparent(): Boolean
}