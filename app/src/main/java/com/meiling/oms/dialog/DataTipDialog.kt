package com.meiling.oms.dialog

import android.os.Bundle
import android.widget.TextView
import com.meiling.oms.R
import com.meiling.oms.widget.setSingleClickListener
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder


/**
 * 数据提示
 * **/
class DataTipDialog : BaseNiceDialog() {

    init {
        setMargin(30)
        setOutCancel(false)
    }

    fun newInstance(tip: String,title:String?="数据口径"): DataTipDialog {
        val args = Bundle()
        args.putString("dataTip", tip)
        args.putString("title", title)
        val dialog = DataTipDialog()
        dialog.arguments = args
        return dialog
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_data_tip
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        var btnOkTip = holder?.getView<TextView>(R.id.btn_data_sure)
        var tv_title = holder?.getView<TextView>(R.id.tv_title)
        var txtDataTip = holder?.getView<TextView>(R.id.txt_data_tip)
        var tip =  arguments?.getString("dataTip")
        var title =  arguments?.getString("title").toString()
        txtDataTip?.text = tip
        tv_title?.text = title
        btnOkTip?.setSingleClickListener {
            dismiss()
        }
    }
}