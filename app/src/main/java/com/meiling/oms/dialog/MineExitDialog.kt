package com.meiling.oms.dialog

import android.os.Bundle
import android.widget.Button
import com.meiling.oms.R
import com.meiling.oms.widget.setSingleClickListener
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder

class MineExitDialog : BaseNiceDialog() {

    init {
        setMargin(30)
        setOutCancel(false)
    }

    var okSelectClickLister: (() -> Unit)? = null
    fun setOkClickLister(okClickLister: () -> Unit) {
        this.okSelectClickLister = okClickLister
    }

    fun newInstance(title: String, content: String, type: Boolean): MineExitDialog {
        val args = Bundle()
        args.putString("title", title)
        args.putString("content", content)
        args.putBoolean("type", type)
        val dialog = MineExitDialog()
        dialog.arguments = args
        return dialog
    }


    override fun intLayoutId(): Int {
        return R.layout.dialog_min_exit
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        val title = arguments?.getString("title") as String
        val content = arguments?.getString("content") as String

        val cancel = holder?.getView<Button>(R.id.btn_cancel_exit)
        val ok = holder?.getView<Button>(R.id.btn_ok_exit)


        holder?.setText(R.id.tv_title, title)
        holder?.setText(R.id.tv_content, content)
        cancel?.setSingleClickListener {
            dismiss()
        }
        ok?.setSingleClickListener {
            okSelectClickLister?.invoke()
        }

    }
}