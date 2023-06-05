package com.meiling.oms.dialog

import android.os.Bundle
import android.view.Gravity
import android.widget.Button
import android.widget.ImageView
import android.widget.TextView
import androidx.recyclerview.widget.RecyclerView
import com.meiling.common.network.data.BalanceChildItem
import com.meiling.common.network.data.Merchant
import com.meiling.oms.R
import com.meiling.oms.adapter.ChooseViewAdapter
import com.shehuan.nicedialog.BaseNiceDialog
import com.shehuan.nicedialog.ViewHolder

/**
 * 三方绑定的店铺列表
 */
class ShowOtherBindShopDialog : BaseNiceDialog() {
    init {
        setGravity(Gravity.BOTTOM)
        setHeight(400)
        setOutCancel(false)
    }
    override fun intLayoutId(): Int {
        return R.layout.dialog_shop_list
    }

    var sureOnclickListener:((otherShop: BalanceChildItem)->Unit)?=null
    fun setMySureOnclickListener(listener: (otherShop:BalanceChildItem)->Unit){
        this.sureOnclickListener=listener
    }

    fun newInstance(title:String,list:ArrayList<BalanceChildItem>): ShowOtherBindShopDialog{
        val args = Bundle()
        args.putString("title",title)
        args.putSerializable("list",list)
        val fragment = ShowOtherBindShopDialog()
        fragment.arguments = args
        return fragment
    }

    override fun convertView(holder: ViewHolder?, dialog: BaseNiceDialog?) {
        var titleString=arguments?.getString("title")
        var list=arguments?.getSerializable("list") as ArrayList<BalanceChildItem>
        var title=holder?.getView<TextView>(R.id.txt_title_item_city)
        titleString?.let {
            title?.setText(titleString)
        }
        var btn=holder?.getView<Button>(R.id.btn_ok_select_shop_city)
        var recy=holder?.getView<RecyclerView>(R.id.rv_shop_or_city)
        var ivCloseRecharge=holder?.getView<ImageView>(R.id.iv_close_select_shop_city)
        var adapter= ChooseViewAdapter<BalanceChildItem>()

        recy?.adapter=adapter

        adapter.setList(list)
        ivCloseRecharge?.setOnClickListener { dismiss() }
        btn?.setOnClickListener {
            dismiss()
        }

    }
}