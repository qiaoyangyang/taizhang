package com.meiling.oms.activity

import android.content.Intent
import android.graphics.Color
import android.graphics.Paint
import android.os.Bundle
import android.text.TextUtils
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import androidx.recyclerview.widget.LinearLayoutManager
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.blankj.utilcode.util.TimeUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.module.LoadMoreModule
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hjq.shape.view.ShapeTextView
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.Shop
import com.meiling.common.network.data.ThrillItem
import com.meiling.common.network.data.WriteoffhistoryPageData
import com.meiling.common.utils.RecyclerViewDivider
import com.meiling.common.utils.SpannableUtils
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityGiveBinding
import com.meiling.oms.dialog.MineExitDialog
import com.meiling.oms.viewmodel.VoucherInspectionHistoryViewModel

//核销成功
@Route(path = "/app/WriteOffActivity")
class WriteOffActivity : BaseActivity<VoucherInspectionHistoryViewModel, ActivityGiveBinding>() {
    lateinit var orderLeftRecyAdapter: BaseQuickAdapter<ThrillItem?, BaseViewHolder>

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.tvGoOn.setOnClickListener {
            finish()
        }
        initRecycleyView()
        mDatabind.tvRevocation.setOnClickListener {

            var code=""
            orderLeftRecyAdapter.data.forEach {
                if (TextUtils.isEmpty(code)){
                    code=it?.couponCode!!
                }else{
                    code=","+code+it?.couponCode
                }
            }


            val dialog: MineExitDialog =
                MineExitDialog().newInstance("温馨提示", "确认撤销核销吗？", "我再想想","确定撤销",false)
            dialog.setOkClickLister {
                dialog.dismiss()
                mViewModel.cancel(
                    code,
                    shopId
                )

            }
            dialog.show(supportFragmentManager)
//

        }

    }

    override fun createObserver() {

        mViewModel.cancelstring.onSuccess.observe(this){
            finish()
            startActivity(Intent(this, WriteDetailsActivity::class.java))

        }
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityGiveBinding {
        return ActivityGiveBinding.inflate(layoutInflater)
    }

    var thrillitem: ArrayList<ThrillItem>? = null
    var shopId:String=""
    override fun initData() {
        super.initData()
        shopId=intent.getStringExtra("shopId").toString()
        thrillitem = intent.getSerializableExtra("thrillitem") as ArrayList<ThrillItem>
        orderLeftRecyAdapter.setList(thrillitem)

    }

    private fun initRecycleyView() {

        orderLeftRecyAdapter = object :
            BaseQuickAdapter<ThrillItem?, BaseViewHolder>(R.layout.item_succeed) {
            override fun convert(
                holder: BaseViewHolder,
                item: ThrillItem?

            ) {

                holder.setText(R.id.tv_name, item?.dealTitle)
                holder.setText(R.id.tv_dealValue, item?.dealValue)
                holder.setText(R.id.tv_couponBuyPrice, item?.couponBuyPrice)
                holder.setText(R.id.tv_couponCode, item?.couponCode)
                holder.setText(R.id.tv_couponUseTime, item?.couponUseTime)
                holder.setText(R.id.tv_orderId, item?.orderId)
                holder.getView<TextView>(R.id.tv_dealValue).paint?.flags = Paint.STRIKE_THRU_TEXT_FLAG
                if (item?.undoType == 1) {
                    holder.setTextColor(R.id.tv_status,Color.parseColor("#31D288"))
                    var tv_status = holder.getView<ShapeTextView>(R.id.tv_status)

                    tv_status.shapeDrawableBuilder.setSolidColor(Color.parseColor("#EDFFF4"))
                        .intoBackground()
                    tv_status.text = "已撤销"

                } else if (item?.undoType == 0) {
                    holder.setTextColor(R.id.tv_status,Color.parseColor("#FB9716"))
                    var tv_status = holder.getView<ShapeTextView>(R.id.tv_status)

                    tv_status.shapeDrawableBuilder.setSolidColor(Color.parseColor("#FFF1DF"))
                        .intoBackground()
                    tv_status.text = "已核销"


                }
                //1。团购 2。代金
                if (item?.isVoucher == 1) {
                    holder.setText(R.id.tv_type, "团购券")
                } else if (item?.isVoucher == 2) {
                    holder.setText(R.id.tv_type, "代金券")
                }
                var tv_shopName = holder.getView<TextView>(R.id.tv_shopName)

                var conet = "由 ${item?.shopName} 店验证"
                SpannableUtils.setTextcolor(
                    holder.itemView.context,
                    conet,
                    tv_shopName,
                    1,
                    conet.length - 2,
                    R.color.pwd_1180FF
                )




            }
        }
        mDatabind.recyclerView.layoutManager = LinearLayoutManager(this)
        var recyclerViewDivider = RecyclerViewDivider()
        recyclerViewDivider.setDividerHeight(10)
        mDatabind.recyclerView.addItemDecoration(recyclerViewDivider)
        mDatabind.recyclerView.adapter = orderLeftRecyAdapter
        orderLeftRecyAdapter.setList(arrayListOf())
        orderLeftRecyAdapter.setEmptyView(R.layout.empty_want_goods_recycler)


    }


}