package com.meiling.account.dialog

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
import com.meiling.account.R
import com.meiling.account.widget.*
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
        var selectDialogDto =
            arguments?.getSerializable("selectDialogDto") as SelectDialogDto
        var resetDto = selectDialogDto.copy()
        val orderPlatForm = holder?.getView<RecyclerView>(R.id.rv_select_order_platform)
        rb_today = holder?.getView<RadioButton>(R.id.rb_today)
        rb_yesterday = holder?.getView<RadioButton>(R.id.rb_yesterday)
        rb_About_seven_days = holder?.getView<RadioButton>(R.id.rb_About_seven_days)
        rb_nearly_days = holder?.getView<RadioButton>(R.id.rb_nearly_days)

        RG_time = holder?.getView<RadioGroup>(R.id.RG_time)
        var Rg_isVoucher = holder?.getView<RadioGroup>(R.id.Rg_isVoucher)
        var rgIsValid = holder?.getView<RadioGroup>(R.id.rg_isValid)

        rb_starting_time = holder?.getView<RadioButton>(R.id.rb_starting_time)
        tv_final_time = holder?.getView<RadioButton>(R.id.tv_final_time)

        var rb_isVoucher = holder?.getView<RadioButton>(R.id.rb_isVoucher)
        var rb_voucher = holder?.getView<RadioButton>(R.id.rb_voucher)
        var rb_meal_voucher = holder?.getView<RadioButton>(R.id.rb_meal_voucher)

        var rbIsValidAll = holder?.getView<RadioButton>(R.id.rb_isValid_all)
        var rbIsValid1 = holder?.getView<RadioButton>(R.id.rb_isValid1)
        var rbIsValid0 = holder?.getView<RadioButton>(R.id.rb_isValid0)


        var tv_go_on = holder?.getView<ShapeButton>(R.id.tv_go_on)
        holder?.setOnClickListener(R.id.iv_close_recharge) {
            selectOrderCloseLister?.invoke(resetDto)
            dismiss()
        }

        holder?.setOnClickListener(R.id.tv_revocation) {
            settiet()
            rb_today?.isChecked = true
            selectDialogDto.timetype = 2
            rb_isVoucher?.isChecked = true
            selectDialogDto.orderTime = "1"
            selectDialogDto.isValid = ""
            rbIsValidAll?.isChecked = true
            for (xx in selectOrderPlatformAdapter.data) {
                xx.select = false
            }
            selectOrderPlatformAdapter.data[0].select = true
            selectOrderPlatformAdapter.notifyDataSetChanged()
        }

//        var timetype: Int,//时间类型 0自定义时间 1 昨天 2 今天 3 近七天 4 进30天
//        var dateStatus: String,// 日期类型  1.下单时间，2 收货时间，出货时间 4,完成时间
//        var channelId: String,// 平台  渠道全部传null,根据返回渠道
        // 下单时间
        if (selectDialogDto.orderTime == "1") {
            rb_isVoucher?.isChecked = true
        } else if (selectDialogDto.orderTime == "2") {
            rb_voucher?.isChecked = true
        } else if (selectDialogDto.orderTime == "3") {
            rb_meal_voucher?.isChecked = true
        }
        // 标签时间
        if (selectDialogDto.isValid.isNullOrEmpty()) {
            rbIsValidAll?.isChecked = true
        } else {
            if (selectDialogDto.isValid == "1") {
                rbIsValid1?.isChecked = true
            } else {
                rbIsValid0?.isChecked = true
            }
        }
        Rg_isVoucher?.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_isVoucher -> {
                    selectDialogDto.orderTime = "1"
                }
                R.id.rb_voucher -> {
                    selectDialogDto.orderTime = "2"
                }
                R.id.rb_meal_voucher -> {
                    selectDialogDto.orderTime = "3"
                }

            }
        }
        rgIsValid?.setOnCheckedChangeListener { group, checkedId ->
            when (checkedId) {
                R.id.rb_isValid_all -> {
                    selectDialogDto.isValid = ""
                }
                R.id.rb_isValid1 -> {
                    selectDialogDto.isValid = "1"
                }
                R.id.rb_isValid0 -> {
                    selectDialogDto.isValid = "0"
                }

            }
        }
        //订单日期
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
            rb_starting_time?.setBackgroundResource(R.drawable.bg_order_red_ture)
            rb_starting_time?.setTextColor(Color.parseColor("#FD4B48"))
            tv_final_time?.text = selectDialogDto.endDate
            tv_final_time?.setBackgroundResource(R.drawable.bg_order_red_ture)
            tv_final_time?.setTextColor(Color.parseColor("#FD4B48"))
        }
        rb_starting_time?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (rb_starting_time?.text.toString() == "起始时间") {
                    rb_starting_time?.setBackgroundResource(R.drawable.bg_order_red_false)
                    rb_starting_time?.setTextColor(Color.parseColor("#666666"))

                } else {
                    rb_starting_time?.setBackgroundResource(R.drawable.bg_order_red_ture)
                    rb_starting_time?.setTextColor(Color.parseColor("#FD4B48"))

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
                    tv_final_time?.setBackgroundResource(R.drawable.bg_order_red_false)
                    tv_final_time?.setTextColor(Color.parseColor("#666666"))

                } else {
                    tv_final_time?.setBackgroundResource(R.drawable.bg_order_red_ture)
                    tv_final_time?.setTextColor(Color.parseColor("#FD4B48"))

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
                            R.drawable.bg_order_red_ture
                        )
                        rechargeSum.setTextColor(resources.getColor(R.color.red))
                        selectDialogDto.channelId = item.id
                    } else {
                        holder.setBackgroundResource(
                            R.id.txt_recharge_sum,
                            R.drawable.bg_order_red_false
                        )
                        rechargeSum.setTextColor(resources.getColor(R.color.home_666666))
                    }
                }
            }

        selectOrderPlatformAdapter.setOnItemClickListener { adapter, view, position ->
            var data = adapter.data[position] as OrderSelectPlatform
            for (xx in adapter.data) {
                (xx as OrderSelectPlatform).select = xx == data
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
                orderPlatformList.add(OrderSelectPlatform(id = "0", name = "全部"))
                orderPlatformList.addAll(it)
                for (platform in orderPlatformList) {
                    platform.select = (platform as OrderSelectPlatform).id == selectDialogDto.channelId
                }
//                orderPlatformList[0].select = true
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

    private var selectOrderCloseLister: ((selectDialogDto: SelectDialogDto) -> Unit)? = null
    fun setSelectOrder(selectTime: ((selectDialogDto: SelectDialogDto) -> Unit)) {
        this.selectOrderLister = selectTime
    }

    fun setSelectCloseOrder(selectTime: ((selectDialogDto: SelectDialogDto) -> Unit)) {
        this.selectOrderCloseLister = selectTime
    }


    /**  * 将长时间格式时间转换为字符串 yyyy-MM-dd HH:mm:ss  *   * @param dateDate  * @return   */
    fun dateToStrLong(dateDate: Date?): String? {
        val formatter = SimpleDateFormat("yyyy-MM-dd")
        return formatter.format(dateDate)
    }
}