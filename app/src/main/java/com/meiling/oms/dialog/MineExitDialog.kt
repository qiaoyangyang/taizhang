package com.meiling.oms.dialog

import android.os.Bundle
import android.widget.Button
import com.meiling.oms.R
import com.meiling.oms.widget.setSingleClickListener
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder

class MineExitDialog : BaseNiceDialog() {

    var okClickLister: (() -> Unit)? = null

    fun setOkClickLister(okClickLister: () -> Unit) {
        this.okClickLister = okClickLister
    }

    fun newInstance(title: String, content: String): MineExitDialog {
        val args = Bundle()
        args.putString("title", title)
        args.putString("content", content)
        val dialog = MineExitDialog()
        dialog.arguments = args
        return MineExitDialog()
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
            okClickLister?.invoke()
        }

    }
}