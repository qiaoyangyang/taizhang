package com.meiling.oms.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.view.LayoutInflater
import android.view.MotionEvent
import android.view.View
import android.widget.TextView
import com.alibaba.android.arouter.launcher.ARouter
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.RechargeRequest
import com.meiling.common.utils.TextDrawableUtils
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityReachargeToPayBinding
import com.meiling.oms.fragment.*
import com.meiling.oms.pay.AliPayResp
import com.meiling.oms.pay.PayUtils
import com.meiling.oms.viewmodel.RechargeViewModel
import com.meiling.oms.widget.*
import io.reactivex.rxjava3.core.Observer
import io.reactivex.rxjava3.disposables.Disposable
import org.json.JSONObject
import java.math.BigDecimal


/**
 * 充值
 * **/
class MyRechargeToPayActivity : BaseActivity<RechargeViewModel, ActivityReachargeToPayBinding>() {

    data class rechDto(var money: String, var give: String?) {
        var select = false
    }

    lateinit var rechargeAdapter: BaseQuickAdapter<rechDto, BaseViewHolder>

    var list = ArrayList<rechDto>()
    var money = ""
    var channel = "2"

    var isSelectMoney = false
    var isPayType = true
    override fun initView(savedInstanceState: Bundle?) {
        list.add(rechDto("200", "100"))
        list.add(rechDto("500", "100"))
        list.add(rechDto("1000", ""))
        list.add(rechDto("2000", "300"))
        list.add(rechDto("5000", "500"))
        list.add(rechDto("10000", "1000"))
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityReachargeToPayBinding {
        return ActivityReachargeToPayBinding.inflate(layoutInflater)
    }

    override fun initListener() {

        mDatabind.btnOkRecharge.setSingleClickListener {
            mDatabind.txtRechargeOther.isFocusable = false
            if (!isSelectMoney) {
                money = mDatabind.txtRechargeOther.text.toString()
            }

            if (money.isNullOrBlank()) {
                showToast("请选择或者输入金额")
                return@setSingleClickListener
            }
            if (BigDecimal(money) <= BigDecimal("0")) {
                showToast("请选择或者输入正确金额")
                return@setSingleClickListener
            }

            mViewModel.rechargeRequest(RechargeRequest(money, "3", channel, ""))
        }

        mDatabind.btnCancelRecharge.setSingleClickListener {
            finish()
        }
        mDatabind.txtRechargeOther.setOnClickListener {
            mDatabind.txtRechargeOther.isFocusable = true
            mDatabind.txtRechargeOther.isFocusableInTouchMode = true
            mDatabind.txtRechargeOther.requestFocus()
            mDatabind.txtRechargeOther.findFocus()
        }


        mDatabind.txtRechargeOther?.setOnFocusChangeListener { v, hasFocus ->
            if (hasFocus) {
                //获得焦点时，修改背景属性
                //R.drawable.edit_text_bg_focus为背景资源
                for (moneyDto in rechargeAdapter.data) {
                    moneyDto.select = false
                    isSelectMoney = false
                    rechargeAdapter.notifyDataSetChanged()
                    KeyBoardUtil.openKeyBord(mDatabind.txtRechargeOther, this)
                }
//                v?.setBackgroundResource(R.drawable.recharge_bg_select_true);
            } else {
                KeyBoardUtil.closeKeyBord(mDatabind.txtRechargeOther, this)
//                v?.setBackgroundResource(R.drawable.recharge_bg_select_false);
            }
        }

        mDatabind.txtWeixinPay?.setSingleClickListener {
            isPayType = true
            TextDrawableUtils.setRightDrawable(mDatabind.txtWeixinPay, R.drawable.ic_spu_true)
            TextDrawableUtils.setRightDrawable(mDatabind.txtAliPay, R.drawable.ic_spu_fase)
            channel = "3"
        }
        mDatabind.txtAliPay.setSingleClickListener {
            isPayType = false
            TextDrawableUtils.setRightDrawable(mDatabind.txtAliPay, R.drawable.ic_spu_true)
            TextDrawableUtils.setRightDrawable(mDatabind.txtWeixinPay, R.drawable.ic_spu_fase)
            channel = "2"
        }

        rechargeAdapter =
            object :
                BaseQuickAdapter<rechDto, BaseViewHolder>(R.layout.item_recy_recharge_to_pay) {
                @SuppressLint("SetTextI18n")
                override fun convert(holder: BaseViewHolder, item: rechDto) {
                    val rechargeSum = holder.getView<TextView>(R.id.txt_recharge_money)
                    val  rechargeGive = holder.getView<TextView>(R.id.txt_recharge_give)
                    rechargeSum.text = item.money + "元"
                    if (item.give?.isNotEmpty() == true) {
                        rechargeGive.visibility = View.VISIBLE
                    } else {
                        rechargeGive.visibility = View.GONE
                    }
                    rechargeGive.text = "赠送${item.give}元"
                    if (item.select) {
                        holder.setBackgroundResource(
                            R.id.ll_recharge,
                            R.drawable.recharge_bg_true
                        )
                        rechargeSum.setTextColor(resources.getColor(R.color.red))
                        rechargeGive.setTextColor(resources.getColor(R.color.red))
                        money = item.money
                        isSelectMoney = true
                        mDatabind.txtRechargeOther?.isFocusable = false
                    } else {
                        holder.setBackgroundResource(
                            R.id.ll_recharge,
                            R.drawable.recharge_bg_false
                        )
                        rechargeSum.setTextColor(resources.getColor(R.color.home_666666))
                        rechargeGive.setTextColor(resources.getColor(R.color.home_666666))
                    }
                }
            }

        rechargeAdapter.setOnItemClickListener { adapter, view, position ->
            val data = adapter.data[position] as rechDto
            for (xx in adapter.data) {
                (xx as rechDto).select = xx == data
            }
            mDatabind.txtRechargeOther.setText(data.money)
            rechargeAdapter.notifyDataSetChanged()
        }

        mDatabind.rvRecharge?.adapter = rechargeAdapter
//        list[0].select = true
        rechargeAdapter.setList(list as MutableList<rechDto>)
    }

    override fun onResume() {
        super.onResume()
        mViewModel.getBalance()
    }

    @SuppressLint("SetTextI18n")
    override fun createObserver() {
        mViewModel.rechargeDto.onStart.observe(this) {
            showLoading("加载中")
        }
        mViewModel.rechargeDto.onSuccess.observe(this) {
            disLoading()
            val jsonObject = JSONObject(it)
            var from = jsonObject.get("form")
            PayUtils.aliPay(this,
                from.toString(),
                object : Observer<AliPayResp> {
                    override fun onSubscribe(d: Disposable) {
                    }

                    override fun onError(e: Throwable) {
                    }

                    override fun onComplete() {
                    }

                    override fun onNext(t: AliPayResp) {
//                        if (t.isSuccess) {
                        ARouter.getInstance().build("/app/RechargeFinishActivity")
                            .navigation()
//                        } else {
////                            showToast(t.message)
//                            ARouter.getInstance().build("/app/RechargeFinishActivity")
//                                .navigation()
//                        }
                    }

                }
            )
        }
        mViewModel.rechargeDto.onError.observe(this) {
            disLoading()
        }
        mViewModel.balance.onSuccess.observe(this) {

        }
    }

    override fun onTouchEvent(event: MotionEvent?): Boolean {
        return super.onTouchEvent(event)
    }

    override fun onDestroy() {
        super.onDestroy()
    }


}