package com.meiling.oms.dialog

import android.widget.Button
import android.widget.NumberPicker
import com.meiling.common.network.data.SelectDialogDto
import com.meiling.oms.R
import com.meiling.oms.widget.compareTimeCompare
import com.meiling.oms.widget.formatCurrentDateBefore90
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder

class OrderDisSelectTimeDialog : BaseNiceDialog() {


    fun newInstance(): OrderDisSelectTimeDialog {
        return OrderDisSelectTimeDialog()
    }


    override fun intLayoutId(): Int {
        return R.layout.activity_test_select
    }

    private var selectTimeLister: ((startTime: String, endTime: String, type: String) -> Unit)? =
        null

    fun setSelectTime(selectTime: ((startTime: String, endTime: String, type: String) -> Unit)) {
        this.selectTimeLister = selectTime
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        val yearPicker = holder?.getView<NumberPicker>(R.id.yearPicker)
        val monthPicker = holder?.getView<NumberPicker>(R.id.monthPicker)
        val dayPicker = holder?.getView<NumberPicker>(R.id.dayPicker)

        val btnTime = holder?.getView<Button>(R.id.btn_select_time_sure)
        val btnCancelTime = holder?.getView<Button>(R.id.btn_select_time_cancel)
        val txtStartTime = holder?.getView<Button>(R.id.txt_select_start_time)
        val txtEndTime = holder?.getView<Button>(R.id.txt_select_end_time)

        // 设置 NumberPicker 控件的最小值、最大值和滚动监听器
        yearPicker?.minValue = 2020
        yearPicker?.maxValue = 2025
        yearPicker?.value = 2021

        monthPicker?.minValue = 1
        monthPicker?.maxValue = 12
        monthPicker?.value = 4

        dayPicker?.minValue = 1
        dayPicker?.maxValue = 31
        dayPicker?.value = 13


// 设置滚动监听器
        yearPicker?.setOnValueChangedListener { picker, oldVal, newVal ->
            yearPicker?.value = picker.value
        }
        monthPicker?.setOnValueChangedListener { picker, oldVal, newVal ->
            monthPicker?.value = picker.value
        }
        dayPicker?.setOnValueChangedListener { picker, oldVal, newVal ->
            dayPicker.value = picker.value
        }

        txtStartTime?.text = yearPicker?.value.toString()
        txtStartTime?.text = yearPicker?.value.toString()
        txtStartTime?.text = yearPicker?.value.toString()
        txtStartTime?.setSingleClickListener {
            txtStartTime.text = yearPicker?.value.toString()
            txtStartTime.text = yearPicker?.value.toString()
            txtStartTime.text = yearPicker?.value.toString()
        }
        txtEndTime?.setSingleClickListener {
            txtEndTime.text = yearPicker?.value.toString()
            txtEndTime.text = yearPicker?.value.toString()
            txtEndTime.text = yearPicker?.value.toString()
        }


        btnTime?.setOnClickListener {
            if (!compareTimeCompare(txtStartTime?.text.toString(), txtEndTime?.text.toString())) {
                showToast("请选择正确的时间格式")
                return@setOnClickListener
            }
            selectTimeLister?.invoke(
                txtStartTime?.text.toString(),
                txtEndTime?.text.toString(),
                "0"
            )
            dismiss()
        }
        btnCancelTime?.setOnClickListener {
            selectTimeLister?.invoke(
                txtStartTime?.text.toString(),
                txtEndTime?.text.toString(),
                "1"
            )
            dismiss()
        }


    }
}