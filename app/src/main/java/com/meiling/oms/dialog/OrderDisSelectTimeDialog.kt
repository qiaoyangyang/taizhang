package com.meiling.oms.dialog

import android.widget.Button
import android.widget.NumberPicker
import com.meiling.oms.R
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

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        val yearPicker = holder?.getView<NumberPicker>(R.id.yearPicker)
        val monthPicker = holder?.getView<NumberPicker>(R.id.monthPicker)
        val dayPicker = holder?.getView<NumberPicker>(R.id.dayPicker)

//        val btnTime = holder?.getView<Button>(R.id.btn_select_sure)

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
// 处理年份选择的变化
        }
        monthPicker?.setOnValueChangedListener { picker, oldVal, newVal ->
// 处理月份选择的变化
        }
        dayPicker?.setOnValueChangedListener { picker, oldVal, newVal ->
// 处理日期选择的变化
        }

//        btnTime?.setOnClickListener {
//            val selectedYear = yearPicker?.value
//            val selectedMonth = monthPicker?.value
//            val selectedDay = dayPicker?.value
//
//            showToast("selectedYear${selectedYear}")
//        }


    }
}