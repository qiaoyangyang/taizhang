package com.meiling.oms.widget

import android.content.Context
import android.view.Gravity
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import android.widget.Toast
import com.meiling.oms.R

/**
 * 自定义Toast
 */
class CustomToast {
    private var toast: Toast
    private var textView: TextView

    enum class CustomType(var type: Int) {
        SUCCESS(0),
        ERROR(1),
        DEFAULT(2),
        WARNING(3),
    }

    constructor(context: Context?, message: String) : this(context, message, Toast.LENGTH_SHORT)

     constructor(context: Context?, message: String, type: CustomType) {
        toast = Toast(context)
        val view = View.inflate(context, R.layout.toast_custom, null)
        textView = view.findViewById(R.id.tv_prompt)
        var errorImg = view.findViewById<ImageView>(R.id.errorImg)
        textView.text = message
        toast.view = view
        toast.setGravity(Gravity.CENTER, 0, 10)
    }
    constructor(context: Context?, message: String, duration: Int) {
        toast = Toast(context)
        val view = View.inflate(context, R.layout.toast_custom, null)
        toast.duration = duration
        textView = view.findViewById(R.id.tv_prompt)
        textView.text = message
        toast.view = view
        toast.setGravity(Gravity.CENTER, 0, 10)
    }

    fun show() {
        //通过反射给Toast设置动画

        toast.show()
    }


}