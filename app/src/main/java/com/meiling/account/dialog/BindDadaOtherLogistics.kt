package com.meiling.account.dialog

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.CheckBox
import android.widget.ImageView
import android.widget.TextView
import androidx.constraintlayout.widget.ConstraintLayout
import com.meiling.account.R
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder

/**
 * 绑定其他物流
 */
class BindDadaOtherLogistics : BaseNiceDialog() {
    init {
        setGravity(Gravity.BOTTOM)
        setOutCancel(false)
    }
    override fun intLayoutId(): Int {
        return R.layout.dialog_binding_other2
    }

    var sureOnclickListener:((type:String,type2:String)->Unit)?=null
    fun setMySureOnclickListener(listener: (type:String,type2:String)->Unit){
        this.sureOnclickListener=listener
    }

    fun newInstance(title:String,tips:String,type:String): BindDadaOtherLogistics{
        val args = Bundle()
        args.putString("title",title)
        args.putString("tips",tips)
        args.putString("type",type)
        val fragment = BindDadaOtherLogistics()
        fragment.arguments = args
        return fragment
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        var title=arguments?.getString("title")
        var tips=arguments?.getString("tips")
        var type=arguments?.getString("type")
        
        var btn=holder?.getView<Button>(R.id.btn_uu_sure)
        var txtTitle2=holder?.getView<TextView>(R.id.txt_title2)
        var txtUuTip=holder?.getView<TextView>(R.id.txt_uu_tip)
        var ivCloseRecharge=holder?.getView<ImageView>(R.id.iv_close_recharge)

        var tipConLay=holder?.getView<ConstraintLayout>(R.id.tipConLay)
        var tipConLay2=holder?.getView<ConstraintLayout>(R.id.tipConLay2)
        var checkBox=holder?.getView<CheckBox>(R.id.checkBox)
        var checkBox2=holder?.getView<CheckBox>(R.id.checkBox2)
        var type2="1"
        tipConLay?.setOnClickListener {
            tipConLay?.setBackgroundResource(R.drawable.logistics_bg_select_red)
            tipConLay2?.setBackgroundResource(R.drawable.logistics_bg_noselect_whight)
            btn?.text="开始授权"
            checkBox?.isChecked=true
            checkBox2?.isChecked=false
            type2="1"
        }
        tipConLay2?.setOnClickListener {
            tipConLay?.setBackgroundResource(R.drawable.logistics_bg_noselect_whight)
            tipConLay2?.setBackgroundResource(R.drawable.logistics_bg_select_red)
            btn?.text="一键注册"
            checkBox?.isChecked=false
            checkBox2?.isChecked=true
            type2="2"
        }
        ivCloseRecharge?.setOnClickListener { dismiss() }
        txtTitle2?.text=title
        txtUuTip?.text=tips
        btn?.setOnClickListener {
            dismiss()
            sureOnclickListener?.invoke(type.toString(),type2)
        }

    }
}