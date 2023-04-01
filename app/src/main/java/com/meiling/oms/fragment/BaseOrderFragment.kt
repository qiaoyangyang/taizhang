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
import com.meiling.oms.widget.copyText
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
                    val imgShopCopy = holder.getView<ImageView>(R.id.img_shop_copy)
                    val changeOrder = holder.getView<TextView>(R.id.txt_change_order)
                    val btnSendDis = holder.getView<TextView>(R.id.txt_order_dis)
                    changeOrder.setOnClickListener {
                        ARouter.getInstance().build("/app/OrderChangeAddressActivity").navigation()
                    }

                    imgShopCopy.setOnClickListener {
                        copyText(context,
                            "订单来源：" + "${"王奔康"} \n" +
                                    "门店名称${"18"}\n" +
                                    "订单编号${"18"}\n" +
                                    "-------\n" +
                                    "商品信息${"（白羊座"}${"星愿白羊奶油蛋糕6英寸"}${"x1"}￥${"288"}\n" +
                                    "-------\n" +
                                    "收货时间${"2023-03-31 17:00:00"}\n" +
                                    "收货人${"普女士18629079896"}\n" +
                                    "收货地址${"陕西省西安市雁塔区芙蓉东路曲江紫汀苑"}\n" +
                                    "-------\n" +
                                    "备注${"蜡烛18已收费"}\n"
                        )
                        ToastUtils.showLong("复制成功")
                        showToast("1212")
                    }
                    btnSendDis.setOnClickListener {
                        if (item == "自提") {
                            ARouter.getInstance().build("/app/OrderDisAddTipActivity").navigation()
                        } else {
                            ARouter.getInstance().build("/app/OrderDisActivity").navigation()
                        }
                    }
                    if (item == "自提") {
                        holder.setText(R.id.txt_order_dis, "配送详情")
                        holder.setText(R.id.txt_change_order, "已完成")
                    } else {
                        holder.setText(R.id.txt_change_order, "配送")
                        holder.setText(R.id.txt_order_dis, "发起配送")
                    }
                }

            }
        mDatabind.rvOrderList.adapter = orderDisAdapter
        orderDisAdapter.setList(list)


    }


    override fun getBind(inflater: LayoutInflater): FragmentBaseOrderBinding {
        return FragmentBaseOrderBinding.inflate(inflater)
    }

}