package com.meiling.oms.dialog

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.network.data.OtherShop
import com.meiling.common.network.service.loginService
import com.meiling.oms.R
import com.meiling.oms.adapter.ChooseViewAdapter
import com.meiling.oms.bean.LogisticsBean
import com.meiling.oms.viewmodel.BindingLogisticsViewModel
import com.meiling.oms.widget.showToast
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder

/**
 * 三方门店列表
 */
class ChooseShopViewDialog : BaseNiceDialog() {
    init {
        setGravity(Gravity.BOTTOM)
        setHeight(600)
        setOutCancel(false)
    }
    override fun intLayoutId(): Int {
        return R.layout.dialog_shop_list
    }

    var sureOnclickListener:((otherShop:OtherShop)->Unit)?=null
    fun setMySureOnclickListener(listener: (otherShop:OtherShop)->Unit){
        this.sureOnclickListener=listener
    }

    fun newInstance(poid:String): ChooseShopViewDialog{
        val args = Bundle()
        args.putString("poid",poid)
        val fragment = ChooseShopViewDialog()
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
        var adapter= ChooseViewAdapter<OtherShop>()

        adapter.setOnItemClickListener { adapter, view, position ->

            (adapter.data as ArrayList<OtherShop> ).forEachIndexed { index, otherShop ->
                otherShop.isSelect = index==position
            }
            adapter.notifyDataSetChanged()
        }

        recy?.adapter=adapter
        refeshLayout?.setOnRefreshListener {
            mViewModel.launchRequest(
                { loginService.getShopList("1", "20", poid!!, "uu") },
                true,
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
        refeshLayout?.setEnableLoadMore(true)
        refeshLayout?.setOnLoadMoreListener {
            pageIndex++
            mViewModel.launchRequest(
                { loginService.getShopList(pageIndex.toString(), "20", poid!!, "uu") },
                true,
                onSuccess = {
                    it?.let {
                        refeshLayout?.finishLoadMore()
                        adapter.addData(it)
                    }?: let{
                        refeshLayout?.finishLoadMore()
                    }

                },
                onError = {
                    refeshLayout?.finishLoadMore()
                    it?.let {
                        showToast(it)
                    }
                }
            )
        }

        ivCloseRecharge?.setOnClickListener { dismiss() }
        btn?.setOnClickListener {
            dismiss()
            var selectShop=adapter.data.filter { it.isSelect==true }
            sureOnclickListener?.invoke(selectShop!!.get(0) as OtherShop)
        }

    }
}