package com.meiling.oms.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.EditText
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.codbking.widget.DatePickDialog
import com.codbking.widget.bean.DateType
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.AccountItemSelect
import com.meiling.common.network.data.OrderCreateGoodsDto
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityOrderCreatBinding
import com.meiling.oms.dialog.AccountSelectDialog
import com.meiling.oms.viewmodel.OrderCreateViewModel
import com.meiling.oms.widget.KeyBoardUtil
import com.meiling.oms.widget.formatCurrentDateToString
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast
import java.util.*
import kotlin.collections.ArrayList


class OrderCreateActivity : BaseActivity<OrderCreateViewModel, ActivityOrderCreatBinding>() {


    var goodsList = ArrayList<OrderCreateGoodsDto>()
    lateinit var orderGoodsAdapter: BaseQuickAdapter<OrderCreateGoodsDto, BaseViewHolder>

    override fun initView(savedInstanceState: Bundle?) {
        goodsList.add(OrderCreateGoodsDto("默认商品", 1, "0"))
        orderGoodsAdapter = object :
            BaseQuickAdapter<OrderCreateGoodsDto, BaseViewHolder>(R.layout.item_create_order_goods) {
            override fun convert(holder: BaseViewHolder, item: OrderCreateGoodsDto) {
                holder.setText(R.id.txt_order_goods_name, item.name)
                holder.setText(R.id.txt_order_goods_price, item.price)
                var view = holder.getView<EditText>(R.id.edt_add_num)
                var selectImg = holder.getView<ImageView>(R.id.img_order_goods_select)
                view.setText("${item.num}")
                if (itemCount - 1 == 0) {
                    mDatabind.txtDeleteGoods.visibility = View.GONE
                    selectImg.setImageDrawable(resources.getDrawable(R.drawable.icon_goods_checkbox))
                } else {
                    if (item.isSelectGoods) {
                        mDatabind.txtDeleteGoods.visibility = View.VISIBLE
                        selectImg.setImageDrawable(resources.getDrawable(R.drawable.ic_spu_true))
                    } else {
                        mDatabind.txtDeleteGoods.visibility = View.GONE
                        selectImg.setImageDrawable(resources.getDrawable(R.drawable.ic_spu_fase1))
                    }
                }

            }
        }
        orderGoodsAdapter.setNewInstance(goodsList)
        mDatabind.rvCreateGoods.adapter = orderGoodsAdapter

        orderGoodsAdapter.addChildClickViewIds(R.id.edt_add_jian, R.id.edt_add_jia);

        orderGoodsAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.edt_add_jian -> {
                    if ((adapter.data[position] as OrderCreateGoodsDto).num <= 1) {
                        (R.id.edt_add_jian as ImageView).setImageDrawable(resources.getDrawable(R.drawable.icon_goods_subtract_false))
                    } else {
                        (R.id.edt_add_jian as ImageView).setImageDrawable(resources.getDrawable(R.drawable.icon_goods_subtract_true))
                        (adapter.data[position] as OrderCreateGoodsDto).num =
                            (adapter.data[position] as OrderCreateGoodsDto).num - 1
                    }
                    orderGoodsAdapter.notifyDataSetChanged()
                }
                R.id.edt_add_jia -> {
                    (adapter.data[position] as OrderCreateGoodsDto).num =
                        (adapter.data[position] as OrderCreateGoodsDto).num + 1
                    orderGoodsAdapter.notifyDataSetChanged()
                }

                R.id.img_order_goods_select -> {
                    if (orderGoodsAdapter.data.size > 1) {
                        (adapter.data[position] as OrderCreateGoodsDto).isSelectGoods =
                            !(adapter.data[position] as OrderCreateGoodsDto).isSelectGoods
                        orderGoodsAdapter.notifyItemChanged(position)
                    }
                    orderGoodsAdapter.notifyDataSetChanged()
                }
            }

        }
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityOrderCreatBinding {
        return ActivityOrderCreatBinding.inflate(layoutInflater)
    }

    var selectAuthWay = "1"
    private var isSelectDiscern = true
    override fun initListener() {
        mDatabind.txtSelectStore.setSingleClickListener {
            showToast("发货门店")
        }
        mDatabind.txtDiscern.setSingleClickListener {
            if (isSelectDiscern) {
                isSelectDiscern = false
                mDatabind.rlInputReceiveAddress.visibility = View.VISIBLE
            } else {
                isSelectDiscern = true
                mDatabind.rlInputReceiveAddress.visibility = View.GONE
            }

        }
        mDatabind.txtDiscernInfo.setSingleClickListener {
            showToast("地址识别")
        }
        mDatabind.txtSelectStore.setSingleClickListener {
            showToast("发货门店")
        }
        mDatabind.txtAddGoods.setSingleClickListener {
            goodsList.add(OrderCreateGoodsDto("默认商品", 1, "0"))
            KeyBoardUtil.openKeyBoard(this, mDatabind.txtAddGoods)
            orderGoodsAdapter.notifyDataSetChanged()
        }
        mDatabind.txtReceiveSelectTime.setSingleClickListener {
            showDatePickDialog(DateType.TYPE_YMDHM)//收货时间选择
        }

        mDatabind.txtReceiveWay.text = "商家配送"
        mDatabind.txtReceiveWay.setSingleClickListener {
            var arrayLiatDto = ArrayList<AccountItemSelect>()
            arrayLiatDto.add(AccountItemSelect("1", "商家配送"))
            arrayLiatDto.add(AccountItemSelect("2", "到店自提"))
            var accountSelectDialog = AccountSelectDialog().newInstance("收货方式", arrayLiatDto)
            accountSelectDialog.show(supportFragmentManager)
            accountSelectDialog.setOkClickItemLister { id, name ->
                selectAuthWay = id
                mDatabind.txtReceiveWay.text = name
                if (id == "1") {
                    mDatabind.btnSaveAndDis.visibility = View.VISIBLE
                    mDatabind.rlInputReceiveDetailAddress.visibility = View.VISIBLE
                    mDatabind.rlSelectReceiveAddress.visibility = View.VISIBLE
                    mDatabind.txtDiscernInfo.visibility = View.VISIBLE
                } else {
                    mDatabind.btnSaveAndDis.visibility = View.GONE
                    mDatabind.rlInputReceiveDetailAddress.visibility = View.GONE
                    mDatabind.rlSelectReceiveAddress.visibility = View.GONE
                    mDatabind.txtDiscernInfo.visibility = View.GONE
                }
            }
        }
        mDatabind.btnOkSave.setSingleClickListener {
            if (mDatabind.txtSelectStore.text.toString().trim().isNullOrBlank()) {
                showToast("请选择发货门店")
                return@setSingleClickListener
            }
            if (mDatabind.edtReceiveName.text.toString().trim().isNullOrBlank()) {
                showToast("请输入收货人姓名")
                return@setSingleClickListener
            }
            if (mDatabind.edtReceivePhone.text.toString().trim().isNullOrBlank()) {
                showToast("请输入收货人电话")
                return@setSingleClickListener
            }
            if (!isPhoneNumber(mDatabind.edtReceivePhone.text.toString().trim())) {
                showToast("请输入正确手机号")
                return@setSingleClickListener
            }
            if (mDatabind.txtReceiveSelectTime.text.toString().trim().isNullOrBlank()) {
                showToast("请选择收货时间")
                return@setSingleClickListener
            }

            showToast("保存成功")
        }
        mDatabind.btnSaveAndDis.setSingleClickListener {
            if (mDatabind.txtSelectStore.text.toString().trim().isNullOrBlank()) {
                showToast("请选择发货门店")
                return@setSingleClickListener
            }
            if (mDatabind.txtSelectStore.text.toString().trim().isNullOrBlank()) {
                showToast("请选择收货地址")
                return@setSingleClickListener
            }
            if (mDatabind.edtReceiveName.text.toString().trim().isNullOrBlank()) {
                showToast("请输入收货人姓名")
                return@setSingleClickListener
            }
            if (mDatabind.edtReceivePhone.text.toString().trim().isNullOrBlank()) {
                showToast("请输入收货人电话")
                return@setSingleClickListener
            }
            if (!isPhoneNumber(mDatabind.edtReceivePhone.text.toString().trim())) {
                showToast("请输入正确手机号")
                return@setSingleClickListener
            }
            if (mDatabind.txtReceiveSelectTime.text.toString().trim().isNullOrBlank()) {
                showToast("请选择收货时间")
                return@setSingleClickListener
            }

            showToast("保存并且发送成功")
        }
    }

    @SuppressLint("SetTextI18n")
    private fun showDatePickDialog(
        type: DateType,
    ) {
        val dialog = DatePickDialog(this)
        //设置上下年分限制
        dialog.setYearLimt(2)
        dialog.setStartDate(Date(System.currentTimeMillis()))
        //设置标题
        dialog.setTitle("选择时间")
        //设置类型
        dialog.setType(type)
        //设置消息体的显示格式，日期格式
        dialog.setMessageFormat("yyyy-MM-dd hh:mm")
        //设置选择回调
        dialog.setOnChangeLisener(null)
        //设置点击确定按钮回调
        dialog.setOnSureLisener { date ->
            // TODO: 时间校验
//            if (isSelectTimeCompare("${formatCurrentDateToString(date)}:00")){
//                mDatabind.txtOrderChangeTime.text = "${formatCurrentDateToString(date)}:00"
//            }else{
//                showToast("请选择正确时间")
//            }

            mDatabind.txtSelectStore.text = "${formatCurrentDateToString(date)}:00"
        }
        dialog.show()
    }

    private fun isPhoneNumber(input: String): Boolean {
        val regex = Regex("^1[3-9]\\d{9}$")
        return regex.matches(input)
    }
}