package com.meiling.oms.dialog

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.network.data.OtherCategory
import com.meiling.common.network.data.OtherShop
import com.meiling.common.network.service.loginService
import com.meiling.oms.R
import com.meiling.oms.viewmodel.BindingLogisticsViewModel
import com.meiling.oms.widget.showToast
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder

/**
 * 达达注册获取经营类目列表
 */
class OperateTypeListDialog : BaseNiceDialog() {
    init {
        setGravity(Gravity.BOTTOM)
        setHeight(400)
        setOutCancel(false)
    }
    override fun intLayoutId(): Int {
        return R.layout.dialog_operate_type_list
    }

    var sureOnclickListener:((otherShop:OtherCategory)->Unit)?=null
    fun setMySureOnclickListener(listener: (otherShop:OtherCategory)->Unit){
        this.sureOnclickListener=listener
    }

    fun newInstance(poid:String): OperateTypeListDialog{
        val args = Bundle()
        args.putString("poid",poid)
        val fragment = OperateTypeListDialog()
        fragment.arguments = args
        return fragment
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        var poid=arguments?.getString("poid")
        var btn=holder?.getView<Button>(R.id.btn_ok_select_shop_city)
        var recy=holder?.getView<RecyclerView>(R.id.rv_shop_or_city)
        var ivCloseRecharge=holder?.getView<ImageView>(R.id.iv_close_select_shop_city)
        var refeshLayout=holder?.getView<SmartRefreshLayout>(R.id.refeshLayout)
        var pageIndex = 1
        var mViewModel=ViewModelProvider(requireActivity()).get(BindingLogisticsViewModel::class.java)

        var adapter=object :BaseQuickAdapter<OtherCategory,BaseViewHolder>(R.layout.item_operate_type_layout){
            override fun convert(holder: BaseViewHolder, item: OtherCategory) {
                holder.setText(R.id.txt_shop_or_city,item.categoryName)
                if(item.select==true){
                    holder.itemView.setBackgroundResource(R.drawable.bg_true2)
                    holder.setTextColorRes(R.id.txt_shop_or_city,R.color.account_FA2C19)
                }else{
                    holder.itemView.setBackgroundResource(R.drawable.bg_operate_true)
                    holder.setTextColorRes(R.id.txt_shop_or_city,R.color.home_333333)
                }
            }
        }
        adapter.setOnItemClickListener { adapter, view, position ->

            (adapter.data as ArrayList<OtherCategory> ).forEachIndexed { index, otherShop ->
                otherShop.select = index==position
            }
            adapter.notifyDataSetChanged()
        }

        recy?.adapter=adapter
        refeshLayout?.setOnRefreshListener {
            mViewModel.launchRequest(
                { loginService.getCategory("dada",poid!!) },
                onSuccess = {
                    it?.let {
                        if(it.size>=1){
                            it.get(0).select=true
                        }
                        refeshLayout?.finishRefresh()
                        adapter.setList(it)
                    }

                },
                onError = {
                    refeshLayout?.finishRefresh()
                    it?.let {
                        showToast(it)
                    }
                }
            )
        }
        refeshLayout?.autoRefresh()
        refeshLayout?.setEnableLoadMore(false)

        ivCloseRecharge?.setOnClickListener { dismiss() }
        btn?.setOnClickListener {
            dismiss()
            var selectShop=adapter.data.filter { it.select==true }
            sureOnclickListener?.invoke(selectShop!!.get(0) as OtherCategory)
        }

    }
}