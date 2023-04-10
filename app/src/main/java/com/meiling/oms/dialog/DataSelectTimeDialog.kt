package com.meiling.oms.dialog

import android.view.Gravity
import android.widget.ImageView
import android.widget.RadioGroup
import android.widget.TextView
import com.meiling.oms.R
import com.meiling.oms.widget.setSingleClickListener
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder

class DataSelectTimeDialog : BaseNiceDialog() {


    private var selectTimeLister: ((type: String) -> Unit)? = null
    fun setSelectTime(selectTime: ((type: String) -> Unit)) {
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

        var selectTime = "2023-04-06"

//        holder?.getView<RadioGroup>(R.id.radio_group_select).setonCli

        holder?.getView<TextView>(R.id.btn_ok_select_time)?.setSingleClickListener {
            selectTimeLister?.invoke(selectTime)
            dismiss()
        }
        holder?.getView<ImageView>(R.id.iv_close_select_time)?.setSingleClickListener {
            dismiss()
        }
    }
}