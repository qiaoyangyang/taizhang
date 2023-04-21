package com.meiling.common.dialog

import android.content.Context
import android.widget.TextView
import com.meiling.common.R
import com.meiling.common.dialog.BaseDialog


class LoadingDialog(context: Context) : BaseDialog(context, R.style.LoadingDialog) {


    var tipTextView: TextView? = null

    override val layoutId: Int
        get() = R.layout.common_dialog_loading

    override fun initView() {
        setCancelable(true)
        setCanceledOnTouchOutside(true)
        tipTextView = findViewById<TextView>(R.id.tipTextView)
    }

    override fun initData() {

    }

    fun setContent(content: String?) {
        tipTextView!!.text = content
    }
}