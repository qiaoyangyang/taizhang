package com.friendwing.universe.common.base.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.os.Bundle
import android.view.Gravity
import android.view.Window
import android.view.WindowManager
import com.google.android.material.bottomsheet.BottomSheetDialog
import com.meiling.common.R

abstract class BaseBottomSheetDialog @JvmOverloads constructor(
    context: Context,
    themeResId: Int = R.style.common_dialog_style
) : BottomSheetDialog(context, themeResId) {

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
        window?.setWindowAnimations(R.style.dialog_animation);
        window.setBackgroundDrawable(ColorDrawable(Color.TRANSPARENT))
        window.setGravity(Gravity.BOTTOM)
        initDialogStyle(window)
        initView()
        initData()
    }

    open fun initDialogStyle(window: Window?) {}
    abstract val layoutId: Int
    abstract fun initView()
    abstract fun initData()


}