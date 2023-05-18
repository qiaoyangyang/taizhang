package com.meiling.oms.dialog

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.ImageView
import androidx.recyclerview.widget.RecyclerView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.network.data.OtherShop
import com.meiling.oms.R
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder

/**
 * 三方门店列表
 */
class OtherShopListDialog : BaseNiceDialog() {
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

    fun newInstance(list:ArrayList<OtherShop>): OtherShopListDialog{
        val args = Bundle()
        args.putSerializable("list",list)
        val fragment = OtherShopListDialog()
        fragment.arguments = args
        return fragment
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        var list=arguments?.getSerializable("list") as ArrayList<OtherShop>
        var btn=holder?.getView<Button>(R.id.btn_ok_select_shop_city)
        var recy=holder?.getView<RecyclerView>(R.id.rv_shop_or_city)
        var ivCloseRecharge=holder?.getView<ImageView>(R.id.iv_close_select_shop_city)
        var adapter=object :BaseQuickAdapter<OtherShop,BaseViewHolder>(R.layout.item_dialog_shop_list){
            override fun convert(holder: BaseViewHolder, item: OtherShop) {
                holder.setText(R.id.txt_shop_or_city,item.thirdShopName)
                if(item.select==true){
                    holder.itemView.setBackgroundResource(R.color.red)
                    holder.setTextColorRes(R.id.txt_shop_or_city,R.color.white)
                }else{
                    holder.itemView.setBackgroundResource(R.color.white)
                    holder.setTextColorRes(R.id.txt_shop_or_city,R.color.home_333333)
                }
            }
        }
        adapter.setOnItemClickListener { adapter, view, position ->

            list.forEachIndexed { index, otherShop ->
                otherShop.select = index==position
            }
            adapter.notifyDataSetChanged()
        }
        if(list.size>=1){
            list.get(0).select=true
        }
        adapter.setList(list)
        recy?.adapter=adapter

        ivCloseRecharge?.setOnClickListener { dismiss() }
        btn?.setOnClickListener {
            dismiss()
            var selectShop=list.filter { it.select==true }
            sureOnclickListener?.invoke(selectShop!!.get(0) as OtherShop)
        }

    }
}