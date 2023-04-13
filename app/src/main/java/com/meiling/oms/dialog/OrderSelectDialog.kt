package com.meiling.oms.dialog

import android.app.Application
import android.graphics.Color
import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.Gravity
import android.widget.RadioButton
import android.widget.RadioGroup
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.codbking.widget.DatePickDialog
import com.codbking.widget.bean.DateType
import com.hjq.shape.view.ShapeButton
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.network.data.OrderSelectPlatform
import com.meiling.common.network.data.SelectDialogDto
import com.meiling.common.network.service.orderDisService
import com.meiling.oms.R
import com.meiling.oms.widget.*
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder
import java.text.SimpleDateFormat
import java.util.*


class OrderSelectDialog : BaseNiceDialog() {
    override fun intLayoutId(): Int {
        return R.layout.dialog_select_order
    }

    init {
        setHeight(500)
        setOutCancel(false)
    }

    fun newInstance(
        selectDialogDto: SelectDialogDto,
    ): OrderSelectDialog {
        val args = Bundle()
        args.putSerializable("selectDialogDto", selectDialogDto)
        val dialog = OrderSelectDialog()
        dialog.arguments = args
        return dialog
    }

    init {
        setGravity(Gravity.BOTTOM)
        setOutCancel(true)
    }

    lateinit var selectOrderPlatformAdapter: BaseQuickAdapter<OrderSelectPlatform, BaseViewHolder>


    private var rb_yesterday: RadioButton? = null
    private var rb_today: RadioButton? = null
    private var rb_About_seven_days: RadioButton? = null
    private var rb_nearly_days: RadioButton? = null
    private var rb_starting_time: RadioButton? = null
    private var tv_final_time: RadioButton? = null
    private var RG_time: RadioGroup? = null
    var iscustom = 0//是否自定义时间
    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        val selectDialogDto =
            arguments?.getSerializable("selectDialogDto") as SelectDialogDto

        val orderPlatForm = holder?.getView<RecyclerView>(R.id.rv_select_order_platform)
        rb_today = holder?.getView<RadioButton>(R.id.rb_today)
        rb_yesterday = holder?.getView<RadioButton>(R.id.rb_yesterday)
        rb_About_seven_days = holder?.getView<RadioButton>(R.id.rb_About_seven_days)
        rb_nearly_days = holder?.getView<RadioButton>(R.id.rb_nearly_days)

        RG_time = holder?.getView<RadioGroup>(R.id.RG_time)
        var Rg_isVoucher = holder?.getView<RadioGroup>(R.id.Rg_isVoucher)
        var Rg_status = holder?.getView<RadioGroup>(R.id.Rg_status)

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
            selectDialogDto.timetype = 2

            rb_isVoucher?.isChecked = true
            selectDialogDto.isVoucher = "0"

            rb_status?.isChecked = true
            selectDialogDto.status = ""

        }

        //验券状态 2.已核销 -1.已撤销

        if (selectDialogDto.status == "") {
            rb_status?.isChecked = true
        } else if (selectDialogDto.status == "2") {
            rb_Written_off?.isChecked = true
        } else if (selectDialogDto.status == "-1") {
            rb_revoked?.isChecked = true
        }

        Rg_status?.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_status -> {
                    selectDialogDto.status = ""
                }
                R.id.rb_Written_off -> {
                    selectDialogDto.status = "2"
                }
                R.id.rb_revoked -> {
                    selectDialogDto.status = "-1"
                }

            }
        }


        //验券类型

        if (selectDialogDto.isVoucher == "0") {
            rb_isVoucher?.isChecked = true
        } else if (selectDialogDto.isVoucher == "1") {
            rb_voucher?.isChecked = true
        } else if (selectDialogDto.isVoucher == "2") {
            rb_meal_voucher?.isChecked = true
        }

        Rg_isVoucher?.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_isVoucher -> {
                    selectDialogDto.isVoucher = "0"
                }
                R.id.rb_voucher -> {
                    selectDialogDto.isVoucher = "1"
                }
                R.id.rb_meal_voucher -> {
                    selectDialogDto.isVoucher = "2"
                }

            }
        }
        //验券日期
        if (selectDialogDto.timetype == 2) {//今日
            rb_today?.isChecked = true
        } else if (selectDialogDto.timetype == 1) {//昨天
            rb_yesterday?.isChecked = true
        } else if (selectDialogDto.timetype == 3) {//近七天
            rb_About_seven_days?.isChecked = true
        } else if (selectDialogDto.timetype == 4) {//近30天
            rb_nearly_days?.isChecked = true
        } else if (selectDialogDto.timetype == 5) {
            iscustom = 1
            rb_starting_time?.text = selectDialogDto.startDate
            rb_starting_time?.setBackgroundResource(R.drawable.selected_true)
            rb_starting_time?.setTextColor(Color.parseColor("#FFFFFFFF"))
            tv_final_time?.text = selectDialogDto.endDate
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
                    selectDialogDto.startDate = formatCurrentDateBeforeDay()
                    selectDialogDto.endDate = formatCurrentDateBeforeDay()
                    selectDialogDto.timetype = 1

                }
                R.id.rb_today -> {
                    selectDialogDto.startDate = formatCurrentDate()
                    selectDialogDto.endDate = formatCurrentDate()
                    selectDialogDto.timetype = 2

                }
                R.id.rb_About_seven_days -> {
                    selectDialogDto.startDate = getBeforeSevenDate()
                    selectDialogDto.endDate = formatCurrentDate()
                    selectDialogDto.timetype = 3

                }
                R.id.rb_nearly_days -> {
                    selectDialogDto.startDate = getBeforeMonthDate()
                    selectDialogDto.endDate = formatCurrentDate()
                    selectDialogDto.timetype = 4

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
                selectDialogDto.timetype = 5
                selectDialogDto.startDate = rb_starting_time?.text.toString()
                selectDialogDto.endDate = tv_final_time?.text.toString()
                selectOrderLister?.invoke(selectDialogDto)
                dismiss()
            } else {
                selectOrderLister?.invoke(selectDialogDto)
                dismiss()
            }
        }


        selectOrderPlatformAdapter =
            object :
                BaseQuickAdapter<OrderSelectPlatform, BaseViewHolder>(R.layout.item_recy_order_select_platform) {
                override fun convert(holder: BaseViewHolder, item: OrderSelectPlatform) {
                    val rechargeSum = holder.getView<TextView>(R.id.txt_recharge_sum)
                    rechargeSum.text = item.name
                    if (item.select) {
                        holder.setBackgroundResource(
                            R.id.txt_recharge_sum,
                            R.drawable.selected_true
                        )
                        rechargeSum.setTextColor(resources.getColor(R.color.home_999999))
//                        money = item.money
                    } else {
                        holder.setBackgroundResource(
                            R.id.txt_recharge_sum,
                            R.drawable.selected_false
                        )
                        rechargeSum.setTextColor(resources.getColor(R.color.home_666666))
                    }
                }
            }

        selectOrderPlatformAdapter.setOnItemClickListener { adapter, view, position ->
            var data = adapter.data[position] as OrderSelectPlatform
            for (xx in adapter.data) {
                (xx as RechargeDialog.rechDto).select = xx == data
            }
            selectOrderPlatformAdapter.notifyDataSetChanged()
        }

        orderPlatForm?.adapter = selectOrderPlatformAdapter
        var bs = BaseLiveData<ArrayList<OrderSelectPlatform>>()
        BaseViewModel(Application()).request(
            { orderDisService.orderChannelPlatForm() },
            bs
        )

        bs.onSuccess.observe(this) {
            if (!it.isNullOrEmpty()) {
                var orderPlatformList = ArrayList<OrderSelectPlatform>()
                orderPlatformList.add(OrderSelectPlatform(viewId = 0, name = "全部"))
                orderPlatformList.addAll(it)
                orderPlatformList[0].select = true
                selectOrderPlatformAdapter.setList(orderPlatformList)
            }
        }


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

    private fun settiet() {
        iscustom = 0
        tv_final_time?.text = "终止时间"
        rb_starting_time?.text = "起始时间"
    }

    private var selectOrderLister: ((selectDialogDto: SelectDialogDto) -> Unit)? = null
    fun setSelectOrder(selectTime: ((selectDialogDto: SelectDialogDto) -> Unit)) {
        this.selectOrderLister = selectTime
    }


    /**  * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss  *   * @param dateDate  * @return   */
    fun dateToStrLong(dateDate: Date?): String? {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(dateDate)
    }
}