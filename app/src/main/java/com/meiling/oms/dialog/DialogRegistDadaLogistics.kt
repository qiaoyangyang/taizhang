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
 * 达达物流注册
 */
class DialogRegistDadaLogistics : BaseNiceDialog() {
    init {
        setGravity(Gravity.BOTTOM)
        setOutCancel(false)
        setHeight(500)
    }
    override fun intLayoutId(): Int {
        return R.layout.dialog_regist_dada_logistics
    }

    var sureOnclickListener:(()->Unit)?=null
    fun setMySureOnclickListener(listener: ()->Unit){
        this.sureOnclickListener=listener
    }

    fun newInstance(title:String,tips:String): DialogRegistDadaLogistics{
        val args = Bundle()
        args.putString("title",title)
        args.putString("tips",tips)
        val fragment = DialogRegistDadaLogistics()
        fragment.arguments = args
        return fragment
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        var title=arguments?.getString("title")
        var tips=arguments?.getString("tips")

        var tvOperateType2=holder?.getView<TextView>(R.id.tv_operate_type2)
        tvOperateType2?.setOnClickListener {
            var operateTypeListDialog=OperateTypeListDialog()
            operateTypeListDialog.show(this.childFragmentManager)
        }

        var registLogisticsTitle=holder?.getView<TextView>(R.id.registLogisticsTitle)
        registLogisticsTitle?.setOnClickListener {
            dismiss()
        }

        var btn=holder?.getView<Button>(R.id.tv_go_on)
        btn?.setOnClickListener {
            dismiss()
            sureOnclickListener?.invoke()
        }

    }
}