package com.meiling.account.dialog

import android.widget.TextView
import com.meiling.common.utils.SpannableUtils
import com.meiling.account.R
import com.meiling.account.widget.setSingleClickListener
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder


/**
 * 隐私
 * **/
class AgreementDialog : BaseNiceDialog() {

    init {
        setMargin(30)
        setOutCancel(false)
    }

    fun newInstance(): AgreementDialog {

        return AgreementDialog()
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_agreement
    }

    var agreeClickLister: ((type: String) -> Unit)? = null
    fun setOkClickLister(okClickLister: (type: String) -> Unit) {
        this.agreeClickLister = okClickLister
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        var btnOkTip = holder?.getView<TextView>(R.id.btn_sure_agreement)
        var btnCancel = holder?.getView<TextView>(R.id.btn_no_agreement)
        var contextShow = holder?.getView<TextView>(R.id.txt_agree_tip)

        var con = "感谢您使用小喵来客聚合配送平台，小喵来客尊重并保护所有使用服务用户的个人隐私权，为了给您提供更准确、更安全的产品和服务，若您同意，我们可能会收集您的相关信息。在使用前，请认真阅读《小喵来客隐私政策》。\n" +
                "您在同意此隐私政策协议之时，即视为您已经同意本隐私政策的全部内容，我们将尽全力保护您的信息安全。"
        SpannableUtils.setAgreeTextColor(
            context,
            con,
            contextShow,
            90,
            98,
            R.color.pwd_1180FF
        )

        btnOkTip?.setSingleClickListener {
            agreeClickLister?.invoke("1")
            dismiss()
        }
        btnCancel?.setSingleClickListener {
            agreeClickLister?.invoke("2")
            dismiss()
        }
    }
}