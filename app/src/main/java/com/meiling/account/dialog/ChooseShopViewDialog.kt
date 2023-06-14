package com.meiling.account.dialog

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import androidx.recyclerview.widget.RecyclerView
import com.meiling.common.network.data.PoiContentList
import com.meiling.common.network.service.accountService
import com.meiling.account.R
import com.meiling.account.adapter.ChooseViewAdapter
import com.meiling.account.viewmodel.BindingLogisticsViewModel
import com.meiling.account.widget.showToast
import com.scwang.smart.refresh.layout.SmartRefreshLayout
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder

/**
 * 物流充值选择门店列表
 */
class ChooseShopViewDialog : BaseNiceDialog() {
    init {
        setGravity(Gravity.BOTTOM)
        setHeight(500)
        setOutCancel(false)
    }
    override fun intLayoutId(): Int {
        return R.layout.dialog_shop_list
    }

    var sureOnclickListener:((otherShop:PoiContentList)->Unit)?=null
    fun setMySureOnclickListener(listener: (otherShop:PoiContentList)->Unit){
        this.sureOnclickListener=listener
    }

    fun newInstance(title:String): ChooseShopViewDialog{
        val args = Bundle()
        args.putString("title",title)
        val fragment = ChooseShopViewDialog()
        fragment.arguments = args
        return fragment
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        var title=arguments?.getString("title")
        var titleString=holder?.getView<TextView>(R.id.txt_title_item_city)
        titleString?.let {
            titleString?.setText(title)
        }
        var btn=holder?.getView<Button>(R.id.btn_ok_select_shop_city)
        var recy=holder?.getView<RecyclerView>(R.id.rv_shop_or_city)
        var ivCloseRecharge=holder?.getView<ImageView>(R.id.iv_close_select_shop_city)
        var refeshLayout=holder?.getView<SmartRefreshLayout>(R.id.refeshLayout)
        var pageIndex = 1
        var mViewModel=ViewModelProvider(requireActivity()).get(BindingLogisticsViewModel::class.java)
        var adapter= ChooseViewAdapter<PoiContentList>()

        adapter.setOnItemClickListener { adapter, view, position ->

            (adapter.data as ArrayList<PoiContentList> ).forEachIndexed { index, otherShop ->
                otherShop.isSelect = index==position
            }
            adapter.notifyDataSetChanged()
        }

        recy?.adapter=adapter
        refeshLayout?.setOnRefreshListener {
            mViewModel.launchRequest(
                { accountService.getPoiList(1, "20") },
                true,
                onSuccess = {
                    it?.let {
                        var poiContentList=PoiContentList(name = "全部门店",id="")
                        poiContentList.isSelect=true
                        it.content!!.add(0,poiContentList)

                        refeshLayout?.finishRefresh()
                        if(!it.content.isNullOrEmpty()){
                            adapter.setList(it.content!!)
                        }
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
            var selectShop=adapter.data.filter { it.isSelect==true }
            sureOnclickListener?.invoke(selectShop!!.get(0)!!)
        }

    }
}