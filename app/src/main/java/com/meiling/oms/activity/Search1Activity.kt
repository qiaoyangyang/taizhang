package com.meiling.oms.activity

import android.os.Bundle
import android.text.Editable
import android.text.TextWatcher
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.RelativeLayout
import android.widget.TextView
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.ToastUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivitySearch1Binding
import com.meiling.oms.widget.KeyBoardUtil
import com.meiling.oms.widget.copyText
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast

@Route(path = "/app/Search1Activity")
class Search1Activity : BaseActivity<BaseViewModel, ActivitySearch1Binding>() {

    lateinit var orderDisAdapter: BaseQuickAdapter<String, BaseViewHolder>
    override fun initView(savedInstanceState: Bundle?) {
        setBar(this, mDatabind.cosTitle)

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
                        copyText(
                            context,
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
        mDatabind.rvHistoryOrderList.adapter = orderDisAdapter
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivitySearch1Binding {
        return ActivitySearch1Binding.inflate(layoutInflater)
    }

    private var b = true

    override fun initListener() {
        mDatabind.imgSearchBack.setOnClickListener {
            finish()
        }

        mDatabind.edtSearch.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun onTextChanged(p0: CharSequence?, p1: Int, p2: Int, p3: Int) {
            }

            override fun afterTextChanged(p0: Editable?) {
                if (p0.toString().isNotEmpty()) {
                    mDatabind.imgSearchEditClear.visibility = View.VISIBLE
                } else {
                    mDatabind.imgSearchEditClear.visibility = View.GONE
                }
            }
        })

        mDatabind.imgSearchEditClear.setSingleClickListener {
            mDatabind.edtSearch.setText("")
            orderDisAdapter.setList(null)
            orderDisAdapter.notifyDataSetChanged()
            mDatabind.rlOrderEmpty.visibility = View.VISIBLE
            mDatabind.txtErrorMsg.text = "支持通过订单编号、收货人姓名、手机号进行搜索"
        }

        mDatabind.btnSearch.setSingleClickListener {
            KeyBoardUtil.closeKeyBord(mDatabind.edtSearch, this)

            var list = ArrayList<String>()
            if (mDatabind.edtSearch.text.toString() == "123") {
                list.add("自提")
                list.add("派送")
                orderDisAdapter.setList(list)
                mDatabind.rlOrderEmpty.visibility = View.GONE
            } else {
                list.clear()
                orderDisAdapter.setList(list)
                orderDisAdapter.notifyDataSetChanged()
                if (list.isNullOrEmpty()) {
                    mDatabind.rlOrderEmpty.visibility = View.VISIBLE
                    mDatabind.txtErrorMsg.text = "查询无果"
                }
            }
        }

//        mDatabind.aivImg.setOnClickListener {
//            if (b) {
//                ARouter.getInstance().build("/app/SearchActivity").navigation()
//            } else {
//                finish()
//            }
//            b = !b
//        }
    }


}