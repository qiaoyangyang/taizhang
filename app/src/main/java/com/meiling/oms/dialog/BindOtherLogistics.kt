package com.meiling.oms.dialog

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import com.meiling.oms.R
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder

/**
 * 绑定其他物流
 */
class BindOtherLogistics : BaseNiceDialog() {
    init {
        setGravity(Gravity.BOTTOM)
        setOutCancel(false)
    }
    override fun intLayoutId(): Int {
        return R.layout.dialog_binding_other
    }
    fun newInstance(title:String,tips:String): BindOtherLogistics{
        val args = Bundle()
        args.putString("title",title)
        args.putString("tips",tips)
        val fragment = BindOtherLogistics()
        fragment.arguments = args
        return fragment
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        var title=arguments?.getString("title")
        var tips=arguments?.getString("tips")
        var btn=holder?.getView<Button>(R.id.btn_uu_sure)
        var txtTitle2=holder?.getView<TextView>(R.id.txt_title2)
        var txtUuTip=holder?.getView<TextView>(R.id.txt_uu_tip)
        var ivCloseRecharge=holder?.getView<ImageView>(R.id.iv_close_recharge)
        ivCloseRecharge?.setOnClickListener { dismiss() }
        txtTitle2?.text=title
        txtUuTip?.text=tips
        btn?.setOnClickListener { dismiss() }

    }
}