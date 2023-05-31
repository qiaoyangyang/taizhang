package com.meiling.oms.dialog

import android.app.Application
import android.os.Bundle
import android.view.Gravity
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.network.data.OrderSelectPlatform
import com.meiling.common.network.data.SelectDialogDto
import com.meiling.common.network.service.orderDisService
import com.meiling.oms.R
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder


class OrdeFilterSortDialog : BaseNiceDialog() {
    override fun intLayoutId(): Int {
        return R.layout.dialog_select_order
    }

    init {
        setHeight(500)
        setOutCancel(false)
    }

    fun newInstance(
        selectDialogDto: SelectDialogDto,
    ): OrdeFilterSortDialog {
        val args = Bundle()
        args.putSerializable("selectDialogDto", selectDialogDto)
        val dialog = OrdeFilterSortDialog()
        dialog.arguments = args
        return dialog
    }

    init {
        setGravity(Gravity.BOTTOM)
        setOutCancel(true)
    }

    lateinit var selectOrderPlatformAdapter: BaseQuickAdapter<OrderSelectPlatform, BaseViewHolder>
    lateinit var selectSortAdapter: BaseQuickAdapter<OrderSelectPlatform, BaseViewHolder>

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        var selectDialogDto =
            arguments?.getSerializable("selectDialogDto") as SelectDialogDto
        var resetDto = selectDialogDto.copy()
        val orderPlatForm = holder?.getView<RecyclerView>(R.id.rv_select_order_platform)
        val orderStore = holder?.getView<RecyclerView>(R.id.rv_order_sort)

        holder?.setOnClickListener(R.id.iv_close_order_select) {
            selectOrderCloseLister?.invoke(resetDto)
            dismiss()
        }

        holder?.setOnClickListener(R.id.tv_revocation) {
            selectDialogDto.timetype = 2
            selectDialogDto.orderTime = "1"
            for (xx in selectOrderPlatformAdapter.data) {
                xx.select = false
            }
            selectOrderPlatformAdapter.data[0].select = true
            selectOrderPlatformAdapter.notifyDataSetChanged()
        }

        selectOrderPlatformAdapter =
            object :
                BaseQuickAdapter<OrderSelectPlatform, BaseViewHolder>(R.layout.item_recy_order_select_platform) {
                override fun convert(holder: BaseViewHolder, item: OrderSelectPlatform) {
                    val rechargeSum = holder.getView<TextView>(R.id.txt_recharge_sum)
                    rechargeSum.text = item.name
                    if (item.select) {
                        holder.setBackgroundResource(
                            R.id.txt_recharge_sum,
                            R.drawable.selected_true
                        )
                        rechargeSum.setTextColor(resources.getColor(R.color.white))
                        selectDialogDto.channelId = item.id
                    } else {
                        holder.setBackgroundResource(
                            R.id.txt_recharge_sum,
                            R.drawable.selected_false
                        )
                        rechargeSum.setTextColor(resources.getColor(R.color.home_666666))
                    }
                }
            }
        selectSortAdapter =
            object :
                BaseQuickAdapter<OrderSelectPlatform, BaseViewHolder>(R.layout.item_recy_order_select_platform) {
                override fun convert(holder: BaseViewHolder, item: OrderSelectPlatform) {
                    val rechargeSum = holder.getView<TextView>(R.id.txt_recharge_sum)
                    rechargeSum.text = item.name
                    if (item.select) {
                        holder.setBackgroundResource(
                            R.id.txt_recharge_sum,
                            R.drawable.selected_true
                        )
                        rechargeSum.setTextColor(resources.getColor(R.color.white))
                        selectDialogDto.channelId = item.id
                    } else {
                        holder.setBackgroundResource(
                            R.id.txt_recharge_sum,
                            R.drawable.selected_false
                        )
                        rechargeSum.setTextColor(resources.getColor(R.color.home_666666))
                    }
                }
            }
        var arralist = ArrayList<OrderSelectPlatform>()
        arralist.add(OrderSelectPlatform("1", "最近下单在前"))
        arralist.add(OrderSelectPlatform("1", "最早下单在前"))
        arralist.add(OrderSelectPlatform("1", "最近收货在前"))
        arralist.add(OrderSelectPlatform("1", "最近发货在前"))
        selectSortAdapter.setList(arralist)

        orderStore!!.adapter = selectSortAdapter
        selectOrderPlatformAdapter.setOnItemClickListener { adapter, view, position ->
            var data = adapter.data[position] as OrderSelectPlatform
            for (xx in adapter.data) {
                (xx as OrderSelectPlatform).select = xx == data
            }
            selectOrderPlatformAdapter.notifyDataSetChanged()
        }

        orderPlatForm?.adapter = selectOrderPlatformAdapter
        var bs = BaseLiveData<ArrayList<OrderSelectPlatform>>()
        BaseViewModel(Application()).request(
            { orderDisService.orderChannelPlatForm() },
            bs
        )

        bs.onSuccess.observe(this) {
            if (!it.isNullOrEmpty()) {
                var orderPlatformList = ArrayList<OrderSelectPlatform>()
                orderPlatformList.add(OrderSelectPlatform(id = "0", name = "全部"))
                orderPlatformList.addAll(it)
                for (platform in orderPlatformList) {
                    platform.select =
                        (platform as OrderSelectPlatform).id == selectDialogDto.channelId
                }
                selectOrderPlatformAdapter.setList(orderPlatformList)
            }
        }
    }

    private var selectOrderLister: ((selectDialogDto: SelectDialogDto) -> Unit)? = null

    private var selectOrderCloseLister: ((selectDialogDto: SelectDialogDto) -> Unit)? = null
    fun setSelectOrder(selectTime: ((selectDialogDto: SelectDialogDto) -> Unit)) {
        this.selectOrderLister = selectTime
    }

    fun setSelectCloseOrder(selectTime: ((selectDialogDto: SelectDialogDto) -> Unit)) {
        this.selectOrderCloseLister = selectTime
    }

}