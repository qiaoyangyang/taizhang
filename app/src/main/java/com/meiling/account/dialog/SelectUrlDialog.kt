package com.meiling.account.dialog

import android.widget.Button
import android.widget.TextView
import com.blankj.utilcode.util.SPStaticUtils
import com.meiling.common.constant.SPConstants
import com.meiling.account.R
import com.meiling.account.widget.setSingleClickListener
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder


/**
 * 客服
 * **/
class SelectUrlDialog : BaseNiceDialog() {

    init {
        setMargin(30)
        setOutCancel(false)
    }

    var onclose: ((restart:Boolean)-> Unit)? = null

    @JvmName("setOnclose1")
    fun setOnclose(close:(restart:Boolean)-> Unit){
        this.onclose=close
    }
    fun newInstance(): SelectUrlDialog {
        return SelectUrlDialog()
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_select_url
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        var btnOkTip = holder?.getView<Button>(R.id.btn_ok_sure)
        var btnOkTip2 = holder?.getView<Button>(R.id.btn_ok_sure2)
        var urlTxt = holder?.getView<TextView>(R.id.urlTxt)
        var closeBtn=holder?.getView<Button>(R.id.closeBtn)
        urlTxt?.setText("当前："+SPStaticUtils.getString(SPConstants.IP,""))
        btnOkTip?.setSingleClickListener {
            SPStaticUtils.put(SPConstants.IP,"http://test-oms-api.igoodsale.com",true)
            onclose?.invoke(true)
        }
        btnOkTip2?.setSingleClickListener {
            SPStaticUtils.put(SPConstants.IP,"https://ods-api.igoodsale.com",true)
            onclose?.invoke(true)
        }
        closeBtn?.setOnClickListener{
            onclose?.invoke(false)
        }
    }
}