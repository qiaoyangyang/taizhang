package com.meiling.oms.dialog

import android.content.Intent
import android.util.Log
import android.view.Gravity
import androidx.constraintlayout.widget.ConstraintLayout
import androidx.recyclerview.widget.LinearLayoutManager
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hjq.shape.layout.ShapeConstraintLayout
import com.meiling.common.network.data.Shop
import com.meiling.common.utils.RecyclerViewDivider
import com.meiling.oms.R
import com.meiling.oms.activity.ChannelActivity
import com.meiling.oms.bean.Appdata
import com.meiling.oms.bean.BindMeituanShopBean
import com.meiling.oms.bean.TiktokData
import com.meiling.oms.widget.showToast
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder

class BindMeituanShopDialog : BaseNiceDialog() {
    init {
        setGravity(Gravity.BOTTOM)
        setOutCancel(false)
    }

    override fun intLayoutId(): Int {
        return R.layout.dialog_bind_meituan_shop
    }

    lateinit var bindingTiktokAdapter: BaseQuickAdapter<BindMeituanShopBean, BaseViewHolder>
    lateinit var bindMeituanShopBean: BindMeituanShopBean
    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        var recyClerView = holder?.getView<RecyclerView>(R.id.recyClerView)
        bindingTiktokAdapter = object :
            BaseQuickAdapter<BindMeituanShopBean, BaseViewHolder>(R.layout.item_bind_meituan_shop),
            LoadMoreModule {
            override fun convert(
                holder: BaseViewHolder,
                item: BindMeituanShopBean

            ) {
                holder.setText(R.id.platform_name, item?.name)
                holder.setText(R.id.tv_platform_name, item?.description)

                if (item?.isIsselect == true) {
                    holder.setBackgroundResource(R.id.scbg, R.drawable.bg_true)
                    holder.setBackgroundResource(R.id.iv_status, R.drawable.ic_spu_true)
                } else {
                    holder.setBackgroundResource(R.id.scbg, R.drawable.bg_fase)
                    holder.setBackgroundResource(R.id.iv_status, R.drawable.ic_spu_fase1)
                }


            }
        }
        recyClerView?.layoutManager = LinearLayoutManager(activity)
        var recyclerViewDivider = RecyclerViewDivider()
        recyclerViewDivider.setDividerHeight(40)
        recyClerView?.addItemDecoration(recyclerViewDivider)
        recyClerView?.adapter = bindingTiktokAdapter
        bindingTiktokAdapter.setList(Appdata.getBindMeituanShopBean())
        bindMeituanShopBean = bindingTiktokAdapter.getItem(0)
        bindingTiktokAdapter.setOnItemClickListener { adapter, view, position ->
            if (bindingTiktokAdapter.data.get(position)?.isIsselect == true) {
                bindingTiktokAdapter.data.get(position)?.isIsselect = false
                bindingTiktokAdapter.notifyDataSetChanged()

            } else {
                bindingTiktokAdapter.data.forEach {
                    it?.isIsselect = false
                }
                bindMeituanShopBean = bindingTiktokAdapter.getItem(position)
                bindingTiktokAdapter.data.get(position)?.isIsselect = true
                bindingTiktokAdapter.notifyDataSetChanged()
            }
        }
        holder?.setOnClickListener(R.id.btn_ok_exit) {
            if (onresilience != null) {
                if (bindMeituanShopBean != null) {
                    onresilience?.resilience(bindMeituanShopBean.type)
                    dismiss()
                } else {
                    Log.d("yjk", "convertView: ")
                }
            }
        }
        holder?.setOnClickListener(R.id.iv_close_recharge){
            dismiss()
        }


    }

    fun setOnresilience(onresilience: Onresilience) {
        this.onresilience = onresilience
    }

    private var onresilience: Onresilience? = null

    interface Onresilience {
        fun resilience(type: Int)
    }

}