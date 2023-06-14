package com.meiling.account.dialog

import android.view.Gravity
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import com.meiling.account.R
import com.meiling.account.widget.*
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder

class DataSelectTimeDialog : BaseNiceDialog() {


    private var selectTimeLister: ((type: String, name: String) -> Unit)? = null
    fun setSelectTime(selectTime: ((type: String, name: String) -> Unit)) {
        this.selectTimeLister = selectTime
    }

    init {
        setGravity(Gravity.BOTTOM)
        setOutCancel(false)
    }


    fun newInstance(): DataSelectTimeDialog {
        return DataSelectTimeDialog()
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_data_select_time
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {

        var timeShow = holder?.getView<TextView>(R.id.txt_change_time_show)
        var currentDay = holder?.getView<TextView>(R.id.txt_current_day)
        var selectTime = formatCurrentDateBeforeDay()
        var name = "昨日"
        currentDay?.text = formatCurrentDateBeforeDay()
        timeShow?.text = formatCurrentDateBeforeDay()
        holder?.getView<RadioGroup>(R.id.radio_group_select)
            ?.setOnCheckedChangeListener { group, checkedId ->
                when (checkedId) {
                    R.id.radio_button -> {
                        selectTime = formatCurrentDateBeforeDay()
                        timeShow?.text = formatCurrentDateBeforeDay()
                        name = "昨日"
                    }
                    R.id.radio_button1 -> {
                        selectTime = formatCurrentDateBeforeWeek()
                        timeShow?.text = formatCurrentDateBeforeWeek()
                        name = "近7天"
                    }
                    R.id.radio_button2 -> {
                        selectTime = formatCurrentDateBefore15()
                        timeShow?.text = formatCurrentDateBefore15()
                        name = "近15天"
                    }
                    R.id.radio_button3 -> {
                        selectTime = formatCurrentDateBeforeMouth()
                        timeShow?.text = formatCurrentDateBeforeMouth()
                        name = "近30天"
                    }
                    R.id.radio_button4 -> {
                        selectTime = formatCurrentDateBefore90()
                        timeShow?.text = formatCurrentDateBefore90()
                        name = "近90天"
                    }
                }
            }

        holder?.getView<TextView>(R.id.btn_ok_select_time)?.setSingleClickListener {
            selectTimeLister?.invoke(selectTime,name)
            dismiss()
        }
        holder?.getView<ImageView>(R.id.iv_close_select_time)?.setSingleClickListener {
            dismiss()
        }
    }
}