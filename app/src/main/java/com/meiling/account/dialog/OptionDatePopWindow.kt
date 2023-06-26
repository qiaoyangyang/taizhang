package com.meiling.account.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.haibin.calendarview.CalendarView
import com.hjq.base.BasePopupWindow
import com.hjq.base.action.AnimAction
import com.meiling.account.R
import java.util.*


class OptionDatePopWindow {


    class Builder(context: Context) : BasePopupWindow.Builder<Builder>(context) {

        private var autoDismiss = true

        init {
            var inflate =
                LayoutInflater.from(getContext()).inflate(R.layout.pop_select_single_time, null)
            inflate.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setContentView(inflate)
            setBackgroundDimAmount(0.5f)
            setOutsideTouchable(false)
            setYOffset(20)
            setWidth(500)
            setHeight(500)
            var calendarView = inflate.findViewById<CalendarView>(R.id.calendarView)
            var yearMonthTxt = inflate.findViewById<TextView>(R.id.yearMonthTxt)


            calendarView.setRange(2020, 1, 1, 2050, 12, 31)
            calendarView.post {
                calendarView.scrollToCurrent()
            }
            calendarView.setOnMonthChangeListener { year, month ->
                yearMonthTxt.text = "${year}年${month}月"
            }

            calendarView?.setOnCalendarRangeSelectListener(object :
                CalendarView.OnCalendarRangeSelectListener {


                override fun onCalendarSelectOutOfRange(calendar: com.haibin.calendarview.Calendar?) {
                }

                override fun onSelectOutOfRange(
                    calendar: com.haibin.calendarview.Calendar?,
                    isOutOfMinRange: Boolean
                ) {
                }

                override fun onCalendarRangeSelect(
                    calendar: com.haibin.calendarview.Calendar?,
                    isEnd: Boolean
                ) {
                }
            })

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
    }

}