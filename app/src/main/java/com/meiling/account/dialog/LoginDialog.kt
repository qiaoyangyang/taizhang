package com.meiling.account.dialog

import android.os.Bundle
import android.view.View
import android.widget.Button
import com.meiling.account.R
import com.meiling.account.widget.setSingleClickListener
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder

class LoginDialog : BaseNiceDialog() {

    init {
        setMargin(30)
        setWidth(350)
        setOutCancel(false)
    }

    lateinit var okSelectClickLister: OkSelectClickLister
    fun setOkClickLister(okClickLister: OkSelectClickLister) {
        this.okSelectClickLister = okClickLister
    }

    fun newInstance(title: String, content: String, cancel_exit:String,oktext:String,type: Boolean): LoginDialog {
        val args = Bundle()
        args.putString("title", title)
        args.putString("content", content)
        args.putString("cancel_exit", cancel_exit)
        args.putString("oktext", oktext)
        args.putBoolean("type", type)
        val dialog = LoginDialog()
        dialog.arguments = args
        return dialog
    }


    override fun intLayoutId(): Int {
        return R.layout.dialog_min_exit
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        val title = arguments?.getString("title") as String
        val content = arguments?.getString("content") as String
        val cancel_exit = arguments?.getString("cancel_exit") as String
        val oktext = arguments?.getString("oktext") as String
        val type = arguments?.getBoolean("type") as Boolean


        val cancel = holder?.getView<Button>(R.id.btn_cancel_exit)
        cancel?.text=cancel_exit
        if (type){
            cancel?.visibility=View.GONE
        }else{
            cancel?.visibility=View.VISIBLE
        }
        val ok = holder?.getView<Button>(R.id.btn_ok_exit)

        ok?.text=oktext


        holder?.setText(R.id.tv_title, title)
        holder?.setText(R.id.tv_content, content)
        cancel?.setSingleClickListener {
            okSelectClickLister?.invoke(1)
        }
        ok?.setSingleClickListener {
            okSelectClickLister?.invoke(2)
        }

    }

    interface OkSelectClickLister{
      fun  invoke(type:Int)
    }
}