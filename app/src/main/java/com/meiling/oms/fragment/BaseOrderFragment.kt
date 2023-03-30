package com.meiling.oms.fragment

import android.content.ClipData
import android.content.ClipboardManager
import android.content.Context
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import android.widget.TextView
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.fragment.BaseFragment
import com.meiling.oms.R
import com.meiling.oms.databinding.FragmentBaseOrderBinding
import com.meiling.oms.viewmodel.CommunityViewModel
import com.meiling.oms.widget.CustomToast
import com.meiling.oms.widget.showToast


class BaseOrderFragment : BaseFragment<CommunityViewModel, FragmentBaseOrderBinding>() {


    lateinit var orderDisAdapter: BaseQuickAdapter<String, BaseViewHolder>

    companion object {
        fun newInstance(type: Int): Fragment {
            val baseOrderFragment = BaseOrderFragment()
            val bundle = Bundle()
            bundle.putInt("type", type)
            baseOrderFragment.arguments = bundle
            return baseOrderFragment
        }
    }


    override fun initView(savedInstanceState: Bundle?) {
//        mDatabind.txt.text = requireArguments().getInt("type").toString()
//        mDatabind.txt.setOnClickListener {
//
//        }
//        mDatabind.txtOrder

    }

    var list = ArrayList<String>()

    override fun initData() {
        super.initData()
        when (requireArguments().getInt("type")) {
            1 -> {
                list.clear()
                list.add("配送")
                list.add("自提")
                list.add("配送")
            }
            2 -> {
                list.clear()
                list.add("配送")
            }
            3 -> {
                list.clear()
                list.add("配送")
                list.add("自提")
            }
        }



        orderDisAdapter =
            object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_home_order) {
                override fun convert(holder: BaseViewHolder, item: String) {
                    holder.setText(R.id.txt_order_delivery_type, item)
                    var imgShopCopy = holder.getView<ImageView>(R.id.img_shop_copy)
                    var changeOrder = holder.getView<TextView>(R.id.txt_change_order)
                    var btnSendDis = holder.getView<TextView>(R.id.txt_order_dis)
                    changeOrder.setOnClickListener {
                        ARouter.getInstance().build("/app/OrderChangeAddressActivity").navigation()
                    }
                    imgShopCopy.setOnClickListener {
                        copyText("姓名："+"${"王奔康"} \n"+"年龄${"18"}\n")
                        ToastUtils.showLong("复制成功")
                    }
                    btnSendDis.setOnClickListener {
                        ARouter.getInstance().build("/app/OrderDisActivity").navigation()
                    }
                    if (item == "自提") {
                        holder.setGone(R.id.txt_order_dis, true)
                    } else {
                        holder.setGone(R.id.txt_order_dis, false)
                    }
                }

            }
        mDatabind.rvOrderList.adapter = orderDisAdapter
        orderDisAdapter.setList(list)




    }

    fun copyText(str:String){
        // 获取剪贴板管理器
        val clipboard =   requireContext().getSystemService (Context.CLIPBOARD_SERVICE) as ClipboardManager
        // 创建剪贴板数据对象
        val clip = ClipData.newPlainText("label", str)
        // 将剪贴板数据设置到剪贴板管理器中
        clipboard.setPrimaryClip(clip)
    }

    override fun getBind(inflater: LayoutInflater): FragmentBaseOrderBinding {
        return FragmentBaseOrderBinding.inflate(inflater)
    }

}