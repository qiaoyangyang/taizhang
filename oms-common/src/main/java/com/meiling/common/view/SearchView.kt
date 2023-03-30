package com.meiling.common.view

import android.content.Context
import android.graphics.Color
import android.util.AttributeSet
import android.widget.RelativeLayout
import androidx.appcompat.widget.AppCompatImageView
import androidx.appcompat.widget.AppCompatTextView
import androidx.appcompat.widget.LinearLayoutCompat
import com.meiling.common.R

class SearchView @JvmOverloads constructor(
    context: Context?,
    attrs: AttributeSet? = null,
    defStyleAttr: Int = -1
) : RelativeLayout(context, attrs, defStyleAttr) {

    private var type = 0
    private var mOnScreenClickListener: OnScreenClickListener? = null
    private lateinit var llGoods: LinearLayoutCompat
    private lateinit var llPrice: LinearLayoutCompat
    private lateinit var llTime: LinearLayoutCompat
    private lateinit var atvGoods: AppCompatTextView
    private lateinit var atvPrice: AppCompatTextView
    private lateinit var atvTime: AppCompatTextView
    private lateinit var aivGoods: AppCompatImageView
    private lateinit var aivPrice: AppCompatImageView
    private lateinit var aivTime: AppCompatImageView

    init {
        val view = inflate(context, R.layout.common_view_search, this)
        llGoods = findViewById<LinearLayoutCompat>(R.id.ll_goods)
        llPrice = findViewById<LinearLayoutCompat>(R.id.ll_price)
        llTime = findViewById<LinearLayoutCompat>(R.id.ll_time)
        atvGoods = findViewById<AppCompatTextView>(R.id.atv_goods)
        atvPrice = findViewById<AppCompatTextView>(R.id.atv_price)
        atvTime = findViewById<AppCompatTextView>(R.id.atv_time)
        aivGoods = findViewById<AppCompatImageView>(R.id.aiv_goods)
        aivPrice = findViewById<AppCompatImageView>(R.id.aiv_price)
        aivTime = findViewById<AppCompatImageView>(R.id.aiv_time)
        initView()
    }

    private fun initView() {
        llGoods.setOnClickListener {
            type = 0
            atvGoods.setTextColor(Color.parseColor("#F9C930"))
            atvPrice.setTextColor(Color.parseColor("#9B9B9B"))
            atvTime.setTextColor(Color.parseColor("#9B9B9B"))
            aivGoods.setImageResource(R.mipmap.icon_select_lower)
            aivPrice.setImageResource(R.mipmap.icon_select_up)
            aivTime.setImageResource(R.mipmap.icon_select_up)
            mOnScreenClickListener?.onClick(type)
        }
        llPrice.setOnClickListener {
            if (type == 1) {
                type = 2
                aivPrice.setImageResource(R.mipmap.icon_select_lower_up)
            } else {
                type = 1
                aivPrice.setImageResource(R.mipmap.icon_select_lower)
            }
            atvPrice.setTextColor(Color.parseColor("#F9C930"))
            atvGoods.setTextColor(Color.parseColor("#9B9B9B"))
            atvTime.setTextColor(Color.parseColor("#9B9B9B"))
            aivTime.setImageResource(R.mipmap.icon_select_up)
            aivGoods.setImageResource(R.mipmap.icon_select_up)
            mOnScreenClickListener?.onClick(type)
        }
        llTime.setOnClickListener {
            if (type == 3) {
                type = 4
                aivTime.setImageResource(R.mipmap.icon_select_lower_up)
            } else {
                type = 3
                aivTime.setImageResource(R.mipmap.icon_select_lower)
            }
            atvTime.setTextColor(Color.parseColor("#F9C930"))
            atvGoods.setTextColor(Color.parseColor("#9B9B9B"))
            atvPrice.setTextColor(Color.parseColor("#9B9B9B"))
            aivPrice.setImageResource(R.mipmap.icon_select_up)
            aivGoods.setImageResource(R.mipmap.icon_select_up)
            mOnScreenClickListener?.onClick(type)
        }
    }


    fun addOnScreenClickListener(listener: OnScreenClickListener) {
        this.mOnScreenClickListener = listener
    }

    interface OnScreenClickListener {
        fun onClick(type: Int)
    }


}