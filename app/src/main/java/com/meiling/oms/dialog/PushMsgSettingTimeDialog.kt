package com.meiling.oms.dialog

import android.view.Gravity
import android.view.View
import android.widget.Button
import android.widget.EditText
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import androidx.recyclerview.widget.StaggeredGridLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.oms.R
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder
import java.math.BigDecimal


class PushMsgSettingTimeDialog : BaseNiceDialog() {


    data class rechDto(var time: String) {
        var select = false
    }

    init {
        setGravity(Gravity.BOTTOM)
        setOutCancel(false)
    }

    var okSelectClickLister: ((time: String, channel: String) -> Unit)? = null
    fun setOkClickLister(okClickLister: (time: String, channel: String) -> Unit) {
        this.okSelectClickLister = okClickLister
    }

    var list = ArrayList<rechDto>()


    fun newInstance(): PushMsgSettingTimeDialog {
//        val args = Bundle()
//        args.putString("title", title)
//        args.putString("content", content)
//        args.putBoolean("type", type)
//        val dialog = RechargeDialog()
//        dialog.arguments = args
        return PushMsgSettingTimeDialog()
    }


    override fun intLayoutId(): Int {
        return R.layout.dialog_msg_setting_time
    }

    lateinit var rechargeAdapter: BaseQuickAdapter<rechDto, BaseViewHolder>

    var time = ""
    var channel = "2"

    var isSelecttime = false

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
//        val title = arguments?.getString("title") as String
//        val content = arguments?.getString("content") as String
        list.add(rechDto("1"))
        list.add(rechDto("3"))
        list.add(rechDto("5"))
        list.add(rechDto("10"))
        list.add(rechDto("15"))
        list.add(rechDto("20"))
        list.add(rechDto("30"))
        list.add(rechDto("60"))
        list.add(rechDto(""))
        val cancel = holder?.getView<Button>(R.id.btn_cancel_select)
        val close = holder?.getView<ImageView>(R.id.iv_close_recharge)
        val ok = holder?.getView<Button>(R.id.btn_ok_recharge)
        val rvSetting = holder?.getView<RecyclerView>(R.id.rv_select_setting_time)

//
//        edttime?.setOnFocusChangeListener { v, hasFocus ->
//            if (hasFocus) {
//                //获得焦点时，修改背景属性
//                //R.drawable.edit_text_bg_focus为背景资源
//                for (timeDto in rechargeAdapter.data) {
//                    timeDto.select = false
//                    isSelecttime = false
//                    rechargeAdapter.notifyDataSetChanged()
//                    KeyBoardUtil.openKeyBord(edttime!!, requireContext())
//                }
//                v?.setBackgroundResource(R.drawable.recharge_bg_select_true);
//            } else {
//                KeyBoardUtil.closeKeyBord(edttime!!, requireContext())
//                v?.setBackgroundResource(R.drawable.recharge_bg_select_false);
//            }
//        }

        close?.setSingleClickListener {
            dismiss()
        }

//        edttime?.addTextChangedListener(object : TextWatcher {
//            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
//            }
//
//            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
//            }
//
//            override fun afterTextChanged(s: Editable?) {
//                if (s.toString().isNotEmpty()) {
//                    for (timeDto in rechargeAdapter.data) {
//                        timeDto.select = false
//                        isSelecttime = false
//                        rechargeAdapter.notifyDataSetChanged()
//                    }
//                }
//            }
//        })

//        edttime?.setOnClickListener {
//            edttime.isFocusable = true
//            edttime.isFocusableInTouchMode = true
//            edttime.requestFocus()
//            edttime.findFocus()
//        }

//        rechargeAdapter =
//            object : BaseQuickAdapter<rechDto, BaseViewHolder>(R.layout.item_recy_recharge_editext) {
//                override fun convert(holder: BaseViewHolder, item: rechDto) {
//                    val rechargeSum = holder.getView<TextView>(R.id.txt_recharge_sum)
//                    val editText = holder.getView<TextView>(R.id.edit_text_recharge)
//                    rechargeSum.text = item.time + "分钟"
//                    if (item.select) {
//                        holder.setBackgroundResource(
//                            R.id.txt_recharge_sum,
//                            R.drawable.recharge_bg_select_true
//                        )
//                        rechargeSum.setTextColor(resources.getColor(R.color.red))
//                        time = item.time
//                        isSelecttime = true
//                    } else {
//                        holder.setBackgroundResource(
//                            R.id.txt_recharge_sum,
//                            R.drawable.recharge_bg_select_false
//                        )
//                        rechargeSum.setTextColor(resources.getColor(R.color.home_666666))
//                    }
//
//                    if (holder.layoutPosition==8){
//                        editText.visibility = View.VISIBLE
//                    }else{
//                        editText.visibility = View.GONE
//                    }
//
//                }
//            }
        // 记录最后一个条目的位置
        rechargeAdapter = object : BaseQuickAdapter<rechDto, BaseViewHolder>(R.layout.item_recy_recharge_editext) {



            override fun convert(holder: BaseViewHolder, item: rechDto) {
                val rechargeSum = holder.getView<TextView>(R.id.txt_recharge_sum)
                val editText = holder.getView<EditText>(R.id.edit_text_recharge)
                rechargeSum.text = item.time + "分钟"

                // 判断是否为最后一个条目
                val isLastPosition = holder.layoutPosition ==  (rechargeAdapter.data.size - 1)

                if (item.select) {
                    holder.setBackgroundResource(R.id.txt_recharge_sum, R.drawable.recharge_bg_select_true)
                    rechargeSum.setTextColor(resources.getColor(R.color.red))
                    time = item.time
                    isSelecttime = true
                } else {
                    holder.setBackgroundResource(R.id.txt_recharge_sum, R.drawable.recharge_bg_select_false)
                    rechargeSum.setTextColor(resources.getColor(R.color.home_666666))
                }

                // 如果是最后一个条目，修改布局
                if (isLastPosition) {
                    editText.visibility = View.VISIBLE
//                    // 如果当前的 position 是最后一个条目
//                    // 修改最后一个条目的布局方式
//                    val layoutParams = holder.itemView.layoutParams
//                    if (layoutParams is StaggeredGridLayoutManager.LayoutParams) {
//                        (layoutParams as StaggeredGridLayoutManager.LayoutParams).isFullSpan = true
//                    }
//                    // 修改最后一个条目的视图
//                    val textView: TextView = holder.itemView.findViewById(R.id.txt_recharge_sum)
//                    textView.text = "这是最后一个条目"
//                    // TODO: 对最后一个条目的布局进行修改
                } else {
                    editText.visibility = View.GONE
                }
            }
        }

// 显示EditText

        rechargeAdapter.setOnItemClickListener { adapter, view, position ->
            when (position) {
                else -> {
                    var data = adapter.data[position] as rechDto
                    for (xx in adapter.data) {
                        (xx as rechDto).select = xx == data
                    }
                }
            }
            rechargeAdapter.notifyDataSetChanged()
        }

        rvSetting?.adapter = rechargeAdapter
        list[0].select = true
        rechargeAdapter.setList(list)
        cancel?.setSingleClickListener {
            dismiss()
        }
        ok?.setSingleClickListener {
            if (!isSelecttime) {

            }
            if (time.isNullOrBlank()) {
                showToast("请输入时间")
                return@setSingleClickListener
            }
            if (BigDecimal(time) <= BigDecimal("0")) {
                showToast("请选择或者输入正确金额")
                return@setSingleClickListener
            }

            okSelectClickLister?.invoke(time, channel)
            dismiss()

        }

    }


}