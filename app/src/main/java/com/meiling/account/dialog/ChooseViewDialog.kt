package com.meiling.account.dialog

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.meiling.common.network.data.Merchant
import com.meiling.account.R
import com.meiling.account.adapter.ChooseViewAdapter
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder

/**
 * 选择物流类型列表
 */
class ChooseViewDialog : BaseNiceDialog() {
    init {
        setGravity(Gravity.BOTTOM)
        setHeight(500)
        setOutCancel(false)
    }
    override fun intLayoutId(): Int {
        return R.layout.dialog_shop_list
    }

    var sureOnclickListener:((otherShop:Merchant)->Unit)?=null
    fun setMySureOnclickListener(listener: (otherShop:Merchant)->Unit){
        this.sureOnclickListener=listener
    }

    fun newInstance(title:String): ChooseViewDialog{
        val args = Bundle()
        args.putString("title",title)
        val fragment = ChooseViewDialog()
        fragment.arguments = args
        return fragment
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        var titleString=arguments?.getString("title")
        var title=holder?.getView<TextView>(R.id.txt_title_item_city)
        titleString?.let {
            title?.setText(titleString)
        }
        var btn=holder?.getView<Button>(R.id.btn_ok_select_shop_city)
        var recy=holder?.getView<RecyclerView>(R.id.rv_shop_or_city)
        var ivCloseRecharge=holder?.getView<ImageView>(R.id.iv_close_select_shop_city)
        var adapter= ChooseViewAdapter<Merchant>()


        adapter.setOnItemClickListener { adapter, view, position ->

            (adapter.data as ArrayList<Merchant> ).forEachIndexed { index, otherShop ->
                otherShop.isSelect = index==position
            }
            adapter.notifyDataSetChanged()
        }

        recy?.adapter=adapter

        var list=ArrayList<Merchant>()
        list.add(Merchant(typeName ="全部配送", type = "", isSelect = true ))
        list.add(Merchant(typeName ="达达", type = "dada" ))
        list.add(Merchant(typeName ="顺丰同城",type="sf_tc" ))
        list.add(Merchant(typeName ="UU跑腿",type="uu" ))
        list.add(Merchant(typeName ="闪送",type="ss" ))
        adapter.setList(list)
        ivCloseRecharge?.setOnClickListener { dismiss() }
        btn?.setOnClickListener {
            dismiss()
            var selectShop=adapter.data.filter { it.isSelect==true }
            sureOnclickListener?.invoke(selectShop!!.get(0) )
        }

    }
}