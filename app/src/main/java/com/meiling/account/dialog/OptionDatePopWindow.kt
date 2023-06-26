package com.meiling.account.dialog

import android.content.Context
import android.graphics.Color
import android.graphics.drawable.ColorDrawable
import android.view.*
import android.widget.ImageView
import android.widget.PopupWindow
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.haibin.calendarview.CalendarView
import com.hjq.base.BasePopupWindow
import com.hjq.base.action.AnimAction
import com.hjq.shape.view.ShapeButton
import com.meiling.account.R
import com.meiling.account.widget.setSingleClickListener
import java.util.*

//选择日期

class OptionDatePopWindow {


    class Builder(context: Context) : BasePopupWindow.Builder<Builder>(context) {

        private var autoDismiss = true
        private var listener: OnListener? = null
        var startTimen = ""
        var endTime = ""

        init {
            var inflate =
                LayoutInflater.from(getContext()).inflate(R.layout.pop_select_single_time, null)
            inflate.layoutParams = ViewGroup.LayoutParams(
                ViewGroup.LayoutParams.WRAP_CONTENT,
                ViewGroup.LayoutParams.WRAP_CONTENT
            )
            setContentView(inflate)
            setBackgroundDimAmount(0.5f)
            setOutsideTouchable(true)
            setYOffset(20)
            setWidth(500)
            var calendarView = inflate.findViewById<CalendarView>(R.id.calendarView)
            var yearMonthTxt = inflate.findViewById<TextView>(R.id.yearMonthTxt)
            var iv_left_icon_ = inflate.findViewById<ImageView>(R.id.iv_left_icon_)
            var iv_right_icon_ = inflate.findViewById<ImageView>(R.id.iv_right_icon_)
            var btn_cancel = inflate.findViewById<ShapeButton>(R.id.btn_cancel)
            var btn_confirm = inflate.findViewById<ShapeButton>(R.id.btn_confirm)
            btn_cancel.setOnClickListener{
                dismiss()
            }

            btn_confirm.setOnClickListener{
                if (listener!=null){
                    listener?.onSelected(getPopupWindow(),startTimen,endTime)
                }
                dismiss()
            }

            iv_right_icon_.setOnClickListener {
                calendarView.scrollToPre()
            }
            iv_left_icon_.setOnClickListener {
                calendarView.scrollToNext()
            }

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
                    if (!isEnd) {
                        //开始时间
                        startTimen =
                            "${calendar?.year}-${calendar?.month}-${calendar?.day}"
                        endTime =
                            "${calendar?.year}-${calendar?.month}-${calendar?.day}"


                    } else {
                        endTime =
                            "${calendar?.year}-${calendar?.month}-${calendar?.day}"


                    }
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
        @Suppress("UNCHECKED_CAST")
        fun setListener(listener: OnListener?): Builder = apply {
            this.listener = listener
        }
    }
    interface OnListener {

        /**
         * 选择条目时回调
         */
        fun onSelected(popupWindow: BasePopupWindow?, startTimen: String,endTime:String)
    }


}