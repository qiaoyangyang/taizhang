package com.meiling.common.dialog

import android.content.Context
import android.app.Dialog
import android.view.Window
import android.view.WindowManager
import android.graphics.drawable.ColorDrawable
import android.graphics.Color
import android.os.Bundle
import com.meiling.common.R

abstract class BaseDialog @JvmOverloads constructor(
    context: Context,
    themeResId: Int = R.style.common_dialog_style
) : Dialog(context, themeResId) {

    public override fun onStart() {
        super.onStart()
    }


    override fun onCreate(savedInstanceState: Bundle?) {
        super.onCreate(savedInstanceState)
        setContentView(layoutId)
        val window = window
        window!!.setLayout(
            WindowManager.LayoutParams.MATCH_PARENT,
            WindowManager.LayoutParams.WRAP_CONTENT
        )
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        initDialogStyle(window)
        initView()
        initData()
    }

    open fun initDialogStyle(window: Window?) {}
    abstract val layoutId: Int
    abstract fun initView()
    abstract fun initData()


}