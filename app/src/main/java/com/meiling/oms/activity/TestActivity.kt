package com.meiling.oms.activity

import android.app.AlertDialog
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.NumberPicker
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityTestBinding
import com.meiling.oms.viewmodel.TestViewModel
import com.meiling.oms.widget.showToast


class TestActivity : BaseActivity<TestViewModel, ActivityTestBinding>() {

    override fun initView(savedInstanceState: Bundle?) {
//        val fragments: MutableList<Fragment> = arrayListOf()
//        fragments.add(
//            DataFragment.newInstance()
//        )
//        fragments.add(
//            DataFragment.newInstance()
//        )
//        mDatabind.ViewPager2.isUserInputEnabled = false
//        mDatabind.ViewPager2.offscreenPageLimit = 1
//        mDatabind.ViewPager2.adapter =
//            BaseFragmentPagerAdapter(supportFragmentManager, lifecycle, fragments)
//        mDatabind.MyFlipOver.setBgView(mDatabind.ViewPager2, object : MyFlipOver.OnPageListener {
//            override fun OnPreviousPage() {
//                mDatabind.MyFlipOver.clear()
//                Log.e("上一页", "/*************")
//                mDatabind.ViewPager2.setCurrentItem(0, false)
//            }
//
//            override fun OnNextPage() {
//                mDatabind.MyFlipOver.clear()
//                Log.e("上一页", "/000000000000")
//                mDatabind.ViewPager2.setCurrentItem(1, false)
//            }
//        })
        val inflater = LayoutInflater.from(this)
        val dialogView = inflater.inflate(R.layout.activity_test_select, null)

// 获取 NumberPicker 控件
        val yearPicker = dialogView.findViewById<NumberPicker>(R.id.yearPicker)
        val monthPicker = dialogView.findViewById<NumberPicker>(R.id.monthPicker)
        val dayPicker = dialogView.findViewById<NumberPicker>(R.id.dayPicker)
        val hourPicker = dialogView.findViewById<NumberPicker>(R.id.hourPicker)
        val minutePicker = dialogView.findViewById<NumberPicker>(R.id.minutePicker)
        val secondPicker = dialogView.findViewById<NumberPicker>(R.id.secondPicker)

// 设置 NumberPicker 控件的最小值、最大值和滚动监听器
        yearPicker.minValue = 2020
        yearPicker.maxValue = 2025
        yearPicker.value = 2021

        monthPicker.minValue = 1
        monthPicker.maxValue = 12
        monthPicker.value = 4

        dayPicker.minValue = 1
        dayPicker.maxValue = 31
        dayPicker.value = 13

        hourPicker.minValue = 0
        hourPicker.maxValue = 23
        hourPicker.value = 10

        minutePicker.minValue = 0
        minutePicker.maxValue = 59
        minutePicker.value = 30

        yearPicker.minValue = 2020
        yearPicker.maxValue = 2025
        yearPicker.value = 2021

        secondPicker.minValue = 0
        secondPicker.maxValue = 59
        secondPicker.value = 0
// 设置滚动监听器
        yearPicker.setOnValueChangedListener { picker, oldVal, newVal ->
// 处理年份选择的变化
        }
        monthPicker.setOnValueChangedListener { picker, oldVal, newVal ->
// 处理月份选择的变化
        }
        dayPicker.setOnValueChangedListener { picker, oldVal, newVal ->
// 处理日期选择的变化
        }
        hourPicker.setOnValueChangedListener { picker, oldVal, newVal ->
// 处理小时选择的变化
        }
        minutePicker.setOnValueChangedListener { picker, oldVal, newVal ->
// 处理分钟选择的变化
        }
        secondPicker.setOnValueChangedListener { picker, oldVal, newVal ->
// 处理秒钟选择的变化
        }
// 构建对话框
        val builder = AlertDialog.Builder(this)
        builder.setView(dialogView)
            .setTitle("选择日期和时间")
            .setPositiveButton("确定") { dialog, which ->
// 处理确定按钮的点击事件
                val selectedYear = yearPicker.value
                val selectedMonth = monthPicker.value
                val selectedDay = dayPicker.value
                val selectedHour = hourPicker.value
                val selectedMinute = minutePicker.value
                val selectedSecond = secondPicker.value

                showToast("selectedYear${selectedYear}")
            }.setNegativeButton("取消") { dialog, which ->
                // 处理取消按钮的点击事件
                dialog.dismiss()
            }
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityTestBinding {
        return ActivityTestBinding.inflate(layoutInflater)

    }


}