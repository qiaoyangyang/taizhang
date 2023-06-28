package com.meiling.common.dialog

import android.content.Context
import android.view.Window
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintAttribute.setAttributes
import com.meiling.common.R
import com.meiling.common.dialog.BaseDialog


class LoadingDialog(context: Context) : BaseDialog(context, R.style.common_dialog_style) {


    var tipTextView: TextView? = null

    override val layoutId: Int
        get() = R.layout.common_dialog_loading


    override fun initView() {
        setCancelable(false)
        setCanceledOnTouchOutside(false)


        tipTextView = findViewById<TextView>(R.id.tipTextView)
    }

    override fun initData() {

    }

    fun setContent(content: String?) {
        tipTextView!!.text = content
    }
}