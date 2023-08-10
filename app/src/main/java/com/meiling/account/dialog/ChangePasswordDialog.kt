package com.meiling.account.dialog

import android.text.Editable
import android.text.TextWatcher
import android.widget.TextView
import com.hjq.shape.view.ShapeEditText
import com.meiling.account.R
import com.meiling.account.widget.showToast
import com.meiling.common.utils.SpannableUtils
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder

//修改密码
class ChangePasswordDialog: BaseNiceDialog() {
    init {
        setMargin(30)
        setWidth(450)
        setOutCancel(false)
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_change_password
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        var et_content = holder?.getView<ShapeEditText>(R.id.et_content)
        var tv_tet_num = holder?.getView<TextView>(R.id.tv_tet_num)
        SpannableUtils.setTextcolor(
            activity,
            "*新用户名：",
            holder?.getView(R.id.tv_new_name),
            0,
            1,
            com.meiling.common.R.color.tv_red

        )
        holder?.setOnClickListener(R.id.btn_ok_exit) {
            dismiss()
        }
        holder?.setOnClickListener(R.id.btn_cancel_exit) {
            dismiss()

        }
        //监听输入最大输入的内容
        et_content?.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
                val inputCount: Int = s!!.length
                val maxCount = 16
                if (inputCount > maxCount) {
                    val str = s.toString().substring(0, maxCount)
                    et_content.setText(str)
                    et_content.setSelection(str.length)
                    showToast("最多只能输入" + maxCount + "个字")

                }else{
                   // itemRemark = s.toString()
                }
            }

            override fun afterTextChanged(s: Editable?) {

                tv_tet_num?.text = et_content.text.toString().length.toString() + "/16"
            }

        })

    }

}