package com.meiling.oms.dialog

import android.annotation.SuppressLint
import android.view.Gravity
import android.widget.NumberPicker
import android.widget.TextView
import com.hjq.shape.view.ShapeTextView
import com.meiling.oms.R
import com.meiling.oms.widget.*
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder


class RechargeSelectTimeDialog : BaseNiceDialog() {


    init {
        setGravity(Gravity.BOTTOM)
    }


    fun newInstance(): RechargeSelectTimeDialog {
        return RechargeSelectTimeDialog()
    }


    override fun intLayoutId(): Int {
        return R.layout.activity_test_select
    }

    private var selectTimeLister: ((startTime: String, endTime: String, type: String) -> Unit)? =
        null

    fun setSelectTime(selectTime: ((startTime: String, endTime: String, type: String) -> Unit)) {
        this.selectTimeLister = selectTime
    }

    @SuppressLint("UseCompatLoadingForDrawables", "SetTextI18n")
    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        val yearPicker = holder?.getView<NumberPicker>(R.id.yearPicker)
        val monthPicker = holder?.getView<NumberPicker>(R.id.monthPicker)
        val dayPicker = holder?.getView<NumberPicker>(R.id.dayPicker)

        val btnTime = holder?.getView<ShapeTextView>(R.id.btn_select_time_sure)
        val btnCancelTime = holder?.getView<ShapeTextView>(R.id.btn_select_time_cancel)
        val txtStartTime = holder?.getView<TextView>(R.id.txt_select_start_time)
        val txtEndTime = holder?.getView<TextView>(R.id.txt_select_end_time)

        // 设置 NumberPicker 控件的最小值、最大值和滚动监听器
        yearPicker?.minValue = 2020
        yearPicker?.maxValue = 2025
        yearPicker?.value = formatCurrentDateYear().toInt()

        monthPicker?.minValue = 1
        monthPicker?.maxValue = 12
        monthPicker?.value = formatCurrentDataMM().toInt()

        dayPicker?.minValue = 1
        dayPicker?.maxValue = 31
        dayPicker?.value = formatCurrentDataDD().toInt()

        var selectBtn = true


// 设置滚动监听器
        yearPicker?.setOnValueChangedListener { picker, oldVal, newVal ->
            if (selectBtn) {
                txtStartTime?.text =
                    yearPicker?.value.toString() + "-" + monthPicker?.value.toString() + "-" + dayPicker?.value.toString()
            } else {
                txtEndTime?.text =
                    yearPicker?.value.toString() + "-" + monthPicker?.value.toString() + "-" + dayPicker?.value.toString()
            }
        }
        monthPicker?.setOnValueChangedListener { picker, oldVal, newVal ->
            if (selectBtn) {
                txtStartTime?.text =
                    yearPicker?.value.toString() + "-" + monthPicker?.value.toString() + "-" + dayPicker?.value.toString()
            } else {
                txtEndTime?.text =
                    yearPicker?.value.toString() + "-" + monthPicker?.value.toString() + "-" + dayPicker?.value.toString()
            }
        }
        dayPicker?.setOnValueChangedListener { picker, oldVal, newVal ->
            if (selectBtn) {
                txtStartTime?.text =
                    yearPicker?.value.toString() + "-" + monthPicker?.value.toString() + "-" + dayPicker?.value.toString()
            } else {
                txtEndTime?.text =
                    yearPicker?.value.toString() + "-" + monthPicker?.value.toString() + "-" + dayPicker?.value.toString()
            }
        }
        txtStartTime?.text =
            yearPicker?.value.toString() + "-" + monthPicker?.value.toString() + "-" + dayPicker?.value.toString()
        txtStartTime?.setSingleClickListener {
            selectBtn = true
            txtStartTime.background = resources.getDrawable(R.drawable.edt_bg_select_true_20)
            txtEndTime?.background = resources.getDrawable(R.drawable.edt_bg_select_false_20)
        }
        txtEndTime?.setSingleClickListener {
            selectBtn = false
            txtEndTime.text =
                yearPicker?.value.toString() + "-" + monthPicker?.value.toString() + "-" + dayPicker?.value.toString()
            txtEndTime.background = resources.getDrawable(R.drawable.edt_bg_select_true_20)
            txtStartTime?.background = resources.getDrawable(R.drawable.edt_bg_select_false_20)
        }

        btnTime?.setOnClickListener {
            if (txtStartTime?.text.toString().isNullOrBlank() || txtEndTime?.text.toString()
                    .isNullOrBlank()
            ) {
                showToast("请选择时间")
                return@setOnClickListener
            }

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


//    private fun setNumberPickerDivider(numberPicker: NumberPicker) {
//        val count = numberPicker.childCount
//        for (i in 0 until count) {
//            try {
//                val dividerField: Field =
//                    numberPicker.javaClass.getDeclaredField("mSelectionDivider")
//                dividerField.isAccessible = true
//                val colorDrawable = ColorDrawable(
//                    ContextCompat.getColor(requireContext(), android.R.color.holo_blue_dark)
//                )
//                dividerField.set(numberPicker, colorDrawable)
//                numberPicker.invalidate()
//            } catch (e: NoSuchFieldException) {
//            } catch (e: IllegalAccessException) {
//            } catch (e: IllegalArgumentException) {
//            }
//        }
//    }
}