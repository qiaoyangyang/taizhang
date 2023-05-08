package com.meiling.oms.dialog

import android.view.Gravity
import android.widget.TextView
import com.hjq.shape.view.ShapeButton
import com.meiling.oms.R
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder

/***
 * 选择小票尺寸
 */
@Suppress("DEPRECATION")
class SelectPrintPageSizeDialog : BaseNiceDialog() {
    init {
        setGravity(Gravity.BOTTOM)
        setOutCancel(false)
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_select_print_page_size
    }


    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        var btn_ok_exit = holder?.getView<ShapeButton>(R.id.btn_ok_exit)
        var size58=holder?.getView<TextView>(R.id.size58)
        var size80=holder?.getView<TextView>(R.id.size80)
        size58?.setOnClickListener {
            selectPageClick?.invoke("58")
            dismiss()
        }

        size80?.setOnClickListener {
            selectPageClick?.invoke("80")
            dismiss()
        }
        btn_ok_exit?.setOnClickListener {
            dismiss()
        }


    }

    private var selectPageClick: ((size:String)-> Unit)? =null
    fun setSelectPageClick(selectListener:(size:String)->Unit){
        this.selectPageClick=selectListener
    }

}