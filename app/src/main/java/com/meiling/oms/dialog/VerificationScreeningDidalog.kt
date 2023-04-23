package com.meiling.oms.dialog

import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.view.Gravity
import android.widget.CompoundButton
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import com.codbking.widget.DatePickDialog
import com.codbking.widget.bean.DateType
import com.google.android.material.internal.FlowLayout
import com.hjq.shape.view.ShapeButton
import com.meiling.common.network.data.Shop
import com.meiling.common.network.data.ShopBean
import com.meiling.common.network.data.VerificationScreening
import com.meiling.oms.R
import com.meiling.oms.widget.*
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder
import com.wayne.constraintradiogroup.ConstraintRadioGroup
import com.wayne.constraintradiogroup.OnCheckedChangeListener
import java.text.SimpleDateFormat
import java.util.*


class VerificationScreeningDidalog : BaseNiceDialog() {
    override fun intLayoutId(): Int {
        return R.layout.item_verification_screening
    }

    fun newInstance(
        verificationScreening: VerificationScreening,
        shopBean: ArrayList<ShopBean>
    ): VerificationScreeningDidalog {
        val args = Bundle()
        args.putSerializable("verificationScreening", verificationScreening)
        args.putSerializable("shopBean", shopBean)
        val dialog = VerificationScreeningDidalog()
        dialog.arguments = args
        return dialog
    }

    init {
        setGravity(Gravity.BOTTOM)
        setOutCancel(true)
    }

    private var rb_yesterday: RadioButton? = null
    private var rb_today: RadioButton? = null
    private var rb_About_seven_days: RadioButton? = null
    private var rb_nearly_days: RadioButton? = null
    private var rb_starting_time: RadioButton? = null
    private var tv_final_time: RadioButton? = null
    private var RG_time: RadioGroup? = null
    var iscustom = 0//是否自定义时间
    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        val verificationScreening =
            arguments?.getSerializable("verificationScreening") as VerificationScreening
        val shopBean = arguments?.getSerializable("shopBean") as ArrayList<ShopBean>

        rb_today = holder?.getView<RadioButton>(R.id.rb_today)
        rb_yesterday = holder?.getView<RadioButton>(R.id.rb_yesterday)
        rb_About_seven_days = holder?.getView<RadioButton>(R.id.rb_About_seven_days)
        rb_nearly_days = holder?.getView<RadioButton>(R.id.rb_nearly_days)

        RG_time = holder?.getView<RadioGroup>(R.id.RG_time)
        var Rb_shop = holder?.getView<RadioGroup>(R.id.Rb_shop)
        var Rg_isVoucher = holder?.getView<RadioGroup>(R.id.Rg_isVoucher)
        var Rg_status = holder?.getView<RadioGroup>(R.id.Rg_status)

        var rb_shop_name = holder?.getView<RadioButton>(R.id.rb_shop_name)
        var rb_shop_name_custom = holder?.getView<RadioButton>(R.id.rb_shop_name_custom)

        rb_starting_time = holder?.getView<RadioButton>(R.id.rb_starting_time)
        tv_final_time = holder?.getView<RadioButton>(R.id.tv_final_time)

        var rb_isVoucher = holder?.getView<RadioButton>(R.id.rb_isVoucher)
        var rb_voucher = holder?.getView<RadioButton>(R.id.rb_voucher)
        var rb_meal_voucher = holder?.getView<RadioButton>(R.id.rb_meal_voucher)

        var rb_status = holder?.getView<RadioButton>(R.id.rb_status)
        var rb_Written_off = holder?.getView<RadioButton>(R.id.rb_Written_off)
        var rb_revoked = holder?.getView<RadioButton>(R.id.rb_revoked)


        var tv_go_on = holder?.getView<ShapeButton>(R.id.tv_go_on)
        holder?.setOnClickListener(R.id.iv_close_recharge) {
            dismiss()
        }

        holder?.setOnClickListener(R.id.tv_revocation) {
            settiet()
            rb_today?.isChecked = true
            verificationScreening.timetype = 2
            rb_shop_name?.isChecked = true
            verificationScreening.poiIdtype = "0"


            rb_isVoucher?.isChecked = true
            verificationScreening.isVoucher = "0"

            rb_status?.isChecked = true
            verificationScreening.status = ""
            verificationScreening.poiId=""

        }

        //验券状态 2.已核销 -1.已撤销

        if (verificationScreening.status == "") {
            rb_status?.isChecked = true
        } else if (verificationScreening.status == "2") {
            rb_Written_off?.isChecked = true
        } else if (verificationScreening.status == "-1") {
            rb_revoked?.isChecked = true
        }

        Rg_status?.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_status -> {
                    verificationScreening.status = ""
                }
                R.id.rb_Written_off -> {
                    verificationScreening.status = "2"
                }
                R.id.rb_revoked -> {
                    verificationScreening.status = "-1"
                }

            }
        }


        //验券类型

        if (verificationScreening.isVoucher == "0") {
            rb_isVoucher?.isChecked = true
        } else if (verificationScreening.isVoucher == "2") {
            rb_voucher?.isChecked = true
        } else if (verificationScreening.isVoucher == "1") {
            rb_meal_voucher?.isChecked = true
        }

        Rg_isVoucher?.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_isVoucher -> {
                    verificationScreening.isVoucher = "0"
                }
                R.id.rb_voucher -> {
                    verificationScreening.isVoucher = "2"
                }
                R.id.rb_meal_voucher -> {
                    verificationScreening.isVoucher = "1"
                }

            }
        }

        //验券门店
        if (verificationScreening.poiIdtype == "0") {
            rb_shop_name?.isChecked = true
        } else {

            rb_shop_name_custom?.isChecked = true
            rb_shop_name_custom?.text = verificationScreening.shopName
        }
        rb_shop_name_custom?.setOnClickListener {


            if (shopBean.size != null) {
                var shopDialog = ShopDialog().newInstance(shopBean)

                shopDialog.setOnresilience(object : ShopDialog.Onresilience {
                    override fun resilience(
                        cityid: Int,
                        cityidname: String,
                        shopid: Int,
                        shop: Shop
                    ) {
                        if (TextUtils.isEmpty(shop.poiId)) {
                            verificationScreening.poiId = ""
                        } else {
                            verificationScreening.poiId = shop?.poiId!!

                        }
                        rb_shop_name_custom?.text = shop.name
                        verificationScreening.shopName = shop?.name!!
                        verificationScreening.poiIdtype = "1"

                    }

                    override fun Ondismiss() {
                        rb_shop_name?.isChecked = true
                    }

                })
                shopDialog.show(activity?.supportFragmentManager)
            }
        }

        Rb_shop?.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_shop_name -> {
                    rb_shop_name_custom?.text="自定义门店"
                    verificationScreening.poiIdtype = "0"
                }
                R.id.rb_shop_name_custom -> {



                }
            }

        }
        //验券日期

        if (verificationScreening.timetype == 2) {//今日
            rb_today?.isChecked = true
        } else if (verificationScreening.timetype == 1) {//昨天
            rb_yesterday?.isChecked = true
        } else if (verificationScreening.timetype == 3) {//近七天
            rb_About_seven_days?.isChecked = true
        } else if (verificationScreening.timetype == 4) {//近30天
            rb_nearly_days?.isChecked = true
        } else if (verificationScreening.timetype == 5) {
            iscustom = 1
            rb_starting_time?.text = verificationScreening.startDate
            rb_starting_time?.setBackgroundResource(R.drawable.selected_true)
            rb_starting_time?.setTextColor(Color.parseColor("#FFFFFFFF"))
            tv_final_time?.text = verificationScreening.endDate
            tv_final_time?.setBackgroundResource(R.drawable.selected_true)
            tv_final_time?.setTextColor(Color.parseColor("#FFFFFFFF"))
        }
        rb_starting_time?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (rb_starting_time?.text.toString() == "起始时间") {
                    rb_starting_time?.setBackgroundResource(R.drawable.selected_false)
                    rb_starting_time?.setTextColor(Color.parseColor("#666666"))

                } else {
                    rb_starting_time?.setBackgroundResource(R.drawable.selected_true)
                    rb_starting_time?.setTextColor(Color.parseColor("#FFFFFFFF"))

                }
            }

        })
        tv_final_time?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (tv_final_time?.text.toString() == "终止时间") {
                    tv_final_time?.setBackgroundResource(R.drawable.selected_false)
                    tv_final_time?.setTextColor(Color.parseColor("#666666"))

                } else {
                    tv_final_time?.setBackgroundResource(R.drawable.selected_true)
                    tv_final_time?.setTextColor(Color.parseColor("#FFFFFFFF"))

                }
            }

        })

        rb_starting_time?.setOnClickListener {
            showDatePickDialog(
                DateType.TYPE_YMD,
                rb_starting_time!!,
            )
        }
        tv_final_time?.setOnClickListener {
            showDatePickDialog(
                DateType.TYPE_YMD,
                tv_final_time!!,
            )
        }


        rb_yesterday?.setOnClickListener {
            settiet()
        }
        rb_today?.setOnClickListener {
            settiet()
        }
        rb_About_seven_days?.setOnClickListener {
            settiet()
        }
        rb_nearly_days?.setOnClickListener {
            settiet()
        }

        RG_time?.setOnCheckedChangeListener { group, checkedId ->


            when (checkedId) {
                R.id.rb_yesterday -> {
                    verificationScreening.startDate = formatCurrentDateBeforeDay()
                    verificationScreening.endDate = formatCurrentDateBeforeDay()
                    verificationScreening.timetype = 1

                }
                R.id.rb_today -> {
                    verificationScreening.startDate = formatCurrentDate()
                    verificationScreening.endDate = formatCurrentDate()
                    verificationScreening.timetype = 2

                }
                R.id.rb_About_seven_days -> {
                    verificationScreening.startDate = getBeforeSevenDate()
                    verificationScreening.endDate = formatCurrentDate()
                    verificationScreening.timetype = 3

                }
                R.id.rb_nearly_days -> {
                    verificationScreening.startDate = getBeforeMonthDate()
                    verificationScreening.endDate = formatCurrentDate()
                    verificationScreening.timetype = 4

                }


            }
        }


        tv_go_on?.setOnClickListener {
            if (iscustom != 0) {
                if (rb_starting_time?.text.toString() == "起始时间") {
                    showToast("请选择起始时间")
                    return@setOnClickListener
                }
                if (tv_final_time?.text.toString() == "终止时间") {
                    showToast("请选择终止时间")
                    return@setOnClickListener
                }
                verificationScreening.timetype = 5
                verificationScreening.startDate = rb_starting_time?.text.toString()
                verificationScreening.endDate = tv_final_time?.text.toString()
                onresilience?.resilience(verificationScreening)
                dismiss()
            } else {
                onresilience?.resilience(verificationScreening)
                dismiss()
            }


        }


    }

    fun setOnresilience(onresilience: Onresilience) {
        this.onresilience = onresilience
    }

    private fun showDatePickDialog(
        type: DateType,
        textView: RadioButton,
    ) {
        val dialog = DatePickDialog(context)
        //设置上下年分限制
        dialog.setYearLimt(2)
        dialog.setStartDate(Date(System.currentTimeMillis()))
        //设置标题
        dialog.setTitle("选择日期")
        //设置类型
        dialog.setType(type)
        //设置消息体的显示格式，日期格式
        dialog.setMessageFormat("yyyy-MM-dd")
        //设置选择回调
        dialog.setOnChangeLisener(null)
        //设置点击确定按钮回调
        dialog.setOnSureLisener { date ->
            // TODO: 时间校验


            iscustom = 1
            RG_time?.clearCheck()
            var dateToStrLong = dateToStrLong(date)
            textView.text = dateToStrLong
            // setba(textView, true)


        }
        dialog.show()
    }

    fun settiet() {
        iscustom = 0
        tv_final_time?.text = "终止时间"
        rb_starting_time?.text = "起始时间"
    }

    private var onresilience: Onresilience? = null

    interface Onresilience {
        fun resilience(verificationScreening: VerificationScreening)
    }

    /**  * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss  *   * @param dateDate  * @return   */
    fun dateToStrLong(dateDate: Date?): String? {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(dateDate)
    }
}