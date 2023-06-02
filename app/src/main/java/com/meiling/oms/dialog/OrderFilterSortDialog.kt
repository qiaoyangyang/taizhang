package com.meiling.oms.dialog

import android.app.Application
import android.os.Bundle
import android.util.Log
import android.view.Gravity
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hjq.shape.view.ShapeButton
import com.meiling.common.BaseLiveData
import com.meiling.common.BaseViewModel
import com.meiling.common.network.data.OrderSelectPlatform
import com.meiling.common.network.data.OrderSelectSort
import com.meiling.common.network.data.SelectOrderDialogDto
import com.meiling.common.network.service.orderDisService
import com.meiling.oms.R
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder


class OrderFilterSortDialog : BaseNiceDialog() {
    override fun intLayoutId(): Int {
        return R.layout.dialog_base_order_select
    }

    init {
        setHeight(400)
        setOutCancel(false)
    }

    fun newInstance(
        selectDialogDto: SelectOrderDialogDto,
    ): OrderFilterSortDialog {
        val args = Bundle()
        args.putSerializable("selectDialogDto", selectDialogDto)
        val dialog = OrderFilterSortDialog()
        dialog.arguments = args
        return dialog
    }

    init {
        setGravity(Gravity.BOTTOM)
        setOutCancel(true)
    }

    lateinit var selectOrderPlatformAdapter: BaseQuickAdapter<OrderSelectPlatform, BaseViewHolder>
    lateinit var selectSortAdapter: BaseQuickAdapter<OrderSelectSort, BaseViewHolder>

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        var selectDialogDto =
            arguments?.getSerializable("selectDialogDto") as SelectOrderDialogDto
        var resetDto = selectDialogDto.copy()
        val orderPlatForm = holder?.getView<RecyclerView>(R.id.rv_select_order_platform)
        val orderStore = holder?.getView<RecyclerView>(R.id.rv_order_sort)
        val orderSort = holder?.getView<ShapeButton>(R.id.txt_order_sort)

        holder?.setOnClickListener(R.id.iv_close_order_select) {
            selectOrderCloseLister?.invoke(resetDto)
            dismiss()
        }

        holder?.setOnClickListener(R.id.tv_revocation) {
            for (xx in selectOrderPlatformAdapter.data) {
                xx.select = false
            }
            selectOrderPlatformAdapter.data[0].select = true
            selectSortAdapter.data[0].select = true
            selectSortAdapter.notifyDataSetChanged()
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
                BaseQuickAdapter<OrderSelectSort, BaseViewHolder>(R.layout.item_recy_order_select_platform) {
                override fun convert(holder: BaseViewHolder, item: OrderSelectSort) {
                    val rechargeSum = holder.getView<TextView>(R.id.txt_recharge_sum)
                    rechargeSum.text = item.name
                    if (item.select) {
                        holder.setBackgroundResource(
                            R.id.txt_recharge_sum,
                            R.drawable.selected_true
                        )
                        rechargeSum.setTextColor(resources.getColor(R.color.white))
                        selectDialogDto.orderSort = item.id
                    } else {
                        holder.setBackgroundResource(
                            R.id.txt_recharge_sum,
                            R.drawable.selected_false
                        )
                        rechargeSum.setTextColor(resources.getColor(R.color.home_666666))
                    }
                }
            }
        var arralist = ArrayList<OrderSelectSort>()
        arralist.add(OrderSelectSort("4", "最近下单在前"))
        arralist.add(OrderSelectSort("3", "最早下单在前"))
        arralist.add(OrderSelectSort("3", "最近收货在前"))
        arralist.add(OrderSelectSort("4", "最近发货在前"))
        Log.d("lwq", "convertView:${selectDialogDto.toString()} ")
        for (platform in arralist) {
            platform.select =
                (platform as OrderSelectSort).id == selectDialogDto.orderSort
        }
        selectSortAdapter.setList(arralist)

        orderStore!!.adapter = selectSortAdapter
        selectSortAdapter.setOnItemClickListener { adapter, view, position ->
            var data = adapter.data[position] as OrderSelectSort
            for (xx in adapter.data) {
                (xx as OrderSelectSort).select = xx == data
            }
            selectSortAdapter.notifyDataSetChanged()
        }
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
                Log.d("lwq", "convertView:${selectDialogDto.channelId} ")
                orderPlatformList.addAll(it)
                for (platform in orderPlatformList) {
                    platform.select =
                        (platform as OrderSelectPlatform).id == selectDialogDto.channelId
                }
                selectOrderPlatformAdapter.setList(orderPlatformList)
            }
        }

        orderSort?.setOnClickListener {
            selectOrderLister?.invoke(selectDialogDto)
            dismiss()
        }
    }

    private var selectOrderLister: ((selectDialogDto: SelectOrderDialogDto) -> Unit)? = null

    private var selectOrderCloseLister: ((selectDialogDto: SelectOrderDialogDto) -> Unit)? = null
    fun setSelectOrder(selectTime: ((selectDialogDto: SelectOrderDialogDto) -> Unit)) {
        this.selectOrderLister = selectTime
    }

    fun setSelectCloseOrder(selectTime: ((selectDialogDto: SelectOrderDialogDto) -> Unit)) {
        this.selectOrderCloseLister = selectTime
    }

}