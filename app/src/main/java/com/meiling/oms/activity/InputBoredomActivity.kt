package com.meiling.oms.activity

import android.content.Intent
import android.os.Bundle
import android.text.Editable
import android.text.TextUtils
import android.text.TextWatcher
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import android.widget.TextView
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.Meituan
import com.meiling.common.network.data.Shop
import com.meiling.common.network.data.ThrillItem
import com.meiling.common.utils.AutoSeparateTextWatcher
import com.meiling.common.utils.TextDrawableUtils
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityInputBoredomBinding
import com.meiling.oms.dialog.CheckCouponInformationDidalog
import com.meiling.oms.dialog.CheckCouponInformationDidalog1
import com.meiling.oms.dialog.ShopDialog
import com.meiling.oms.viewmodel.InputBoredomViewModel
import com.meiling.oms.viewmodel.VoucherinspectionViewModel
import com.meiling.oms.widget.showToast

// 手动输入
class InputBoredomActivity :
    BaseActivity<VoucherinspectionViewModel, ActivityInputBoredomBinding>() {


    var StockCode = ""
    override fun initView(savedInstanceState: Bundle?) {
        TextDrawableUtils.setRightDrawable(mDatabind.TitleBar.titleView, R.drawable.xia)
        mDatabind.tv0.setOnClickListener {
            setStockCode(mDatabind.tv0)
        }
        mDatabind.tv1.setOnClickListener {
            setStockCode(mDatabind.tv1)
        }
        mDatabind.tv2.setOnClickListener {
            setStockCode(mDatabind.tv2)
        }
        mDatabind.tv3.setOnClickListener {
            setStockCode(mDatabind.tv3)
        }
        mDatabind.tv4.setOnClickListener {
            setStockCode(mDatabind.tv4)
        }
        mDatabind.tv5.setOnClickListener {
            setStockCode(mDatabind.tv5)
        }
        mDatabind.tv6.setOnClickListener {
            setStockCode(mDatabind.tv6)
        }

        mDatabind.tv7.setOnClickListener {
            setStockCode(mDatabind.tv7)
        }
        mDatabind.tv8.setOnClickListener {
            setStockCode(mDatabind.tv8)
        }
        mDatabind.tv9.setOnClickListener {
            setStockCode(mDatabind.tv9)
        }
        mDatabind.ivSearchCloseEdt.setOnClickListener {
            StockCode = ""
            mDatabind.tvStockCode.setText(StockCode)
        }
        mDatabind.tvClear.setOnClickListener {
            if (mDatabind.tvStockCode.text.isNotEmpty()) {
                StockCode = StockCode.substring(0, StockCode.length - 1)
                mDatabind.tvStockCode.setText(StockCode)
            }
        }
        mDatabind.tvOk.setOnClickListener {
            if (TextUtils.isEmpty(mDatabind.tvStockCode.text.toString())) {
                return@setOnClickListener
            }
            if (type == "1") {
                mViewModel.prepare(shopId, 1, mDatabind.tvStockCode.text.toString())
            } else {
                mViewModel.mttgprepare(mDatabind.tvStockCode.text.toString(), shopId)
            }


        }

        mDatabind.tvStockCode.isLongClickable = false


    }

    var shopdata: Shop? = null
    override fun onTitleClick(view: View) {
        super.onTitleClick(view)
        mViewModel.shopBean.onSuccess.observe(this) {
            //DataPickerUtitl.setpickData(this,it)
            if (it.size != 0) {
                it.forEach {
                    it.shopList?.forEach { shop ->
                        if (TextUtils.isEmpty(shop?.poiId)) {
                            shop?.poiId = ""
                        }
                        if (TextUtils.isEmpty(shop?.status)) {
                            shop?.status = ""
                        }

                    }
                }
                var shopDialog = ShopDialog().newInstance(it)

                shopDialog.setOnresilience(object : ShopDialog.Onresilience {


                    override fun resilience(
                        cityid: Int,
                        cityidname: String,
                        shopid: Int,
                        shop: Shop
                    ) {
                        mViewModel.Shop.onSuccess.postValue(shop)
                        shopId = shop?.id.toString()
                        shopdata = shop
                        mDatabind.TitleBar.titleView.text = cityidname + shop.name
                    }

                    override fun Ondismiss() {
                    }

                })
                shopDialog.show(supportFragmentManager)
            }

        }

    }

    fun setStockCode(TextView: TextView) {
        StockCode += TextView.text.toString()
        mDatabind.tvStockCode.setText(StockCode)
        mDatabind.tvStockCode.addTextChangedListener(object : TextWatcher {
            override fun beforeTextChanged(s: CharSequence?, start: Int, count: Int, after: Int) {
            }

            override fun onTextChanged(s: CharSequence?, start: Int, before: Int, count: Int) {
            }

            override fun afterTextChanged(s: Editable?) {
                if (TextUtils.isEmpty(s.toString())) {
                    mDatabind.ivSearchCloseEdt.visibility = View.GONE
                } else {
                    mDatabind.ivSearchCloseEdt.visibility = View.VISIBLE
                }
            }

        })
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityInputBoredomBinding {
        return ActivityInputBoredomBinding.inflate(layoutInflater)
    }

    var type = ""
    var meituan: Meituan? = null
    override fun initData() {
        super.initData()

        type = intent?.getStringExtra("type").toString()
        mViewModel.cityshop(type)
        val textWatcher = AutoSeparateTextWatcher(mDatabind.tvStockCode)
        textWatcher.setRULES(intArrayOf(4, 4, 4, 4, 4))
        textWatcher.separator = ' '
        mDatabind.tvStockCode.addTextChangedListener(textWatcher)
        if (type == "1") {
            mDatabind.tvTypeName.text = "抖音团购验券"
            TextDrawableUtils.setLeftDrawable(mDatabind.tvTypeName, R.drawable.douyinx)
        } else {
            mDatabind.tvTypeName.text = "美团团购验券"
            TextDrawableUtils.setLeftDrawable(mDatabind.tvTypeName, R.drawable.meituan)
        }

    }

    var shopId: String = ""
    override fun createObserver() {
        mViewModel.shopBean.onSuccess.observe(this) {
            if (it.size != 0) {
                shopdata = it[0].shopList?.get(0)
                shopId = it.get(0).shopList?.get(0)?.id!!
                mDatabind.TitleBar.titleView.text =
                    it.get(0).name + "/" + it.get(0).shopList?.get(0)?.name
            }
        }

        mViewModel.thrillBen.onStart.observe(this) {
            showLoading("")
        }

        mViewModel.thrillBen.onSuccess.observe(this) {
            disLoading()
            if (it.size != 0) {

                var checkCouponInformationDidalog = CheckCouponInformationDidalog().newInstance(it)
                checkCouponInformationDidalog.setOnresilience(object :
                    CheckCouponInformationDidalog.Onresilience {
                    override fun resilience(encryptedCode: String) {
                        mViewModel.verify(shopId, encryptedCode)
                    }

                })

                checkCouponInformationDidalog.show(supportFragmentManager)
            }


        }

        //扫码核销失败
        mViewModel.thrillBen.onError.observe(this) {
            disLoading()
            showToast("${it.msg}")
        }



        mViewModel.verifythrillBen.onStart.observe(this) {
            showLoading("")
        }
        //核销成功
        mViewModel.verifythrillBen.onSuccess.observe(this) {
            disLoading()
            if (type == "1") {
                var it1 = it as ArrayList<ThrillItem>
                startActivity(
                    Intent(this, WriteOffActivity::class.java).putExtra(
                        "thrillitem",
                        it1
                    ).putExtra("shopId", shopId)
                )
            } else {
                Log.d("yjk", "核销成功mei---")

            }

        }
        //确认核销失败
        mViewModel.verifythrillBen.onError.observe(this) {
            disLoading()
            showToast("${it.msg}")
        }

        mViewModel.meituan.onStart.observe(this) {
            showLoading("")
        }
        //美团扫码返回
        mViewModel.meituan.onSuccess.observe(this) {
            disLoading()
            var checkCouponInformationDidalog = CheckCouponInformationDidalog1().newInstance(it)
            checkCouponInformationDidalog.setOnresilience(object :
                CheckCouponInformationDidalog1.Onresilience {
                override fun resilience(encryptedCode: String, count: String, mode: Meituan) {
                    meituan = mode
                    mViewModel.consume(encryptedCode, count, shopId)
                }

            })

            checkCouponInformationDidalog.show(supportFragmentManager)
        }
        mViewModel.meituan.onError.observe(this) {
            disLoading()
            showToast("${it.msg}")
        }

        mViewModel.consume.onStart.observe(this) {
            showLoading("")
        }

        mViewModel.consume.onSuccess.observe(this) {
            disLoading()
            startActivity(
                Intent(this, MeituanActivity::class.java).putExtra(
                    "meituan",
                    meituan
                ).putExtra("shopId", shopId).putExtra("code", it)
            )
        }

        mViewModel.consume.onError.observe(this){
            disLoading()
            showToast("${it.msg}")
        }


    }


}