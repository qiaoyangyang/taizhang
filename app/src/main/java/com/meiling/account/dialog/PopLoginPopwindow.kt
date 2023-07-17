package com.meiling.account.dialog

import android.content.Context
import android.view.Gravity
import android.view.LayoutInflater
import android.view.ViewGroup
import android.widget.ImageView
import android.widget.TextView
import com.haibin.calendarview.CalendarView
import com.hjq.base.BasePopupWindow
import com.hjq.base.action.AnimAction
import com.hjq.shape.view.ShapeButton
import com.meiling.account.R

class PopLoginPopwindow {

    class Builder(context: Context) : BasePopupWindow.Builder<Builder>(context) {


        private var listener: OnListener? = null


        init {
            var inflate =
                LayoutInflater.from(getContext()).inflate(R.layout.pop_login, null)
            inflate.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.MATCH_PARENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setContentView(inflate)
            setBackgroundDimAmount(0.5f)
            setOutsideTouchable(true)
            setYOffset(20)
            setWidth(1000)
            setHeight(500)


        }

        override fun setGravity(gravity: Int): Builder = apply {
            when (gravity) {
                // 如果这个是在中间显示的
                Gravity.CENTER, Gravity.CENTER_VERTICAL -> {
                    // 重新设置动画
                    setAnimStyle(AnimAction.ANIM_SCALE)
                }
            }
            super.setGravity(gravity)
        }
        @Suppress("UNCHECKED_CAST")
        fun setListener(listener: OnListener?): Builder = apply {
            this.listener = listener
        }
    }
    interface OnListener {

        /**
         * 选择条目时回调
         */
        fun onSelected(popupWindow: BasePopupWindow?, startTimen: String, endTime:String)
    }

}