package com.meiling.oms.dialog

import android.view.Gravity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.utils.TextDrawableUtils
import com.meiling.oms.R
import com.meiling.oms.widget.setSingleClickListener
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder

class RechargeDialog : BaseNiceDialog() {


    data class rechDto(var money: String) {
        var select = false
    }

    init {
        setGravity(Gravity.BOTTOM)
        setOutCancel(false)
    }

    var okSelectClickLister: (() -> Unit)? = null
    fun setOkClickLister(okClickLister: () -> Unit) {
        this.okSelectClickLister = okClickLister
    }

    var list = ArrayList<rechDto>()


    fun newInstance(): RechargeDialog {
//        val args = Bundle()
//        args.putString("title", title)
//        args.putString("content", content)
//        args.putBoolean("type", type)
//        val dialog = RechargeDialog()
//        dialog.arguments = args
        return RechargeDialog()
    }


    override fun intLayoutId(): Int {
        return R.layout.dialog_reacharge
    }

    lateinit var rechargeAdapter: BaseQuickAdapter<rechDto, BaseViewHolder>

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
//        val title = arguments?.getString("title") as String
//        val content = arguments?.getString("content") as String
        list.add(rechDto("200"))
        list.add(rechDto("500"))
        list.add(rechDto("1000"))
        list.add(rechDto("2000"))
        list.add(rechDto("5000"))
        list.add(rechDto("10000"))
        var isPayType = true
        val cancel = holder?.getView<Button>(R.id.btn_cancel_recharge)
        val close = holder?.getView<ImageView>(R.id.iv_close_recharge)
        val ok = holder?.getView<Button>(R.id.btn_ok_recharge)
        val btnWeixin = holder?.getView<TextView>(R.id.txt_weixin_pay)
        val btnZhifubao = holder?.getView<TextView>(R.id.txt_ali_pay)
        val rvRecharge = holder?.getView<RecyclerView>(R.id.rv_recharge)
        close?.setSingleClickListener {
            dismiss()
        }

        btnWeixin?.setSingleClickListener {
            isPayType = true
            TextDrawableUtils.setRightDrawable(btnWeixin, R.drawable.ic_spu_true)
            TextDrawableUtils.setRightDrawable(btnZhifubao, R.drawable.ic_spu_fase)
        }
        btnZhifubao?.setSingleClickListener {
            isPayType = false
            TextDrawableUtils.setRightDrawable(btnZhifubao, R.drawable.ic_spu_true)
            TextDrawableUtils.setRightDrawable(btnWeixin, R.drawable.ic_spu_fase)
        }

        rechargeAdapter =
            object : BaseQuickAdapter<rechDto, BaseViewHolder>(R.layout.item_recy_recharge) {
                override fun convert(holder: BaseViewHolder, item: rechDto) {
                    val rechargeSum = holder.getView<TextView>(R.id.txt_recharge_sum)
                    rechargeSum.text = item.money + "元"
                    if (item.select) {
                        holder.setBackgroundResource(
                            R.id.txt_recharge_sum,
                            R.drawable.recharge_bg_select_true
                        )
                        rechargeSum.setTextColor(resources.getColor(R.color.red))
                    } else {
                        holder.setBackgroundResource(
                            R.id.txt_recharge_sum,
                            R.drawable.recharge_bg_select_false
                        )
                        rechargeSum.setTextColor(resources.getColor(R.color.home_666666))
                    }

                }
            }
        rvRecharge?.adapter = rechargeAdapter
        rechargeAdapter.setList(list)
//        holder?.setText(R.id.tv_title, title)
//        holder?.setText(R.id.tv_content, content)

        cancel?.setSingleClickListener {
            dismiss()
        }
        ok?.setSingleClickListener {
            dismiss()
            okSelectClickLister?.invoke()
        }

    }


}