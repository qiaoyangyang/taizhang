package com.meiling.oms.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.*
import com.meiling.common.network.service.loginService
import com.meiling.common.utils.GlideAppUtils
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityLogistcsRechargeLayoutBinding
import com.meiling.oms.dialog.*
import com.meiling.oms.viewmodel.LogisticsRechargeViewModel
import com.meiling.oms.widget.showToast

/**
 * 物流充值
 */
class LogisticsRechargeActivity :
    BaseActivity<LogisticsRechargeViewModel, ActivityLogistcsRechargeLayoutBinding>() {

    lateinit var adapter: BaseQuickAdapter<BalanceItem, BaseViewHolder>
    var poid = ""
    var type=""
    override fun initView(savedInstanceState: Bundle?) {

    }


    override fun onResume() {
        super.onResume()
        if(!poid.isNullOrBlank()){
            getMerchantBalance()
        }
    }
    @SuppressLint("SuspiciousIndentation")
    override fun initData() {
        super.initData()
        poid = intent?.getStringExtra("poid") ?: ""

        getMerchantBalance()

        //选择物流类型
        mDatabind.selectLogistcsType.setOnClickListener {

            var chooseViewDialog=ChooseViewDialog().newInstance("配送平台")
            chooseViewDialog.setMySureOnclickListener {
                mDatabind.selectLogistcsType.text = it.typeName
                type=it.type
                getMerchantBalance()
            }
            chooseViewDialog.show(supportFragmentManager)
        }

        //选择门店
        mDatabind.selectShop.setOnClickListener {
            var chooseViewDialog=ChooseShopViewDialog().newInstance("发货门店")
            chooseViewDialog.setMySureOnclickListener {
                mDatabind.selectShop.text = it.name
                poid=it.id!!
                getMerchantBalance()
            }
            chooseViewDialog.show(supportFragmentManager)
        }

        adapter = object : BaseQuickAdapter<BalanceItem, BaseViewHolder>(R.layout.item_logistcs_recharege) {
            override fun convert(holder: BaseViewHolder, item: BalanceItem) {
                holder.setText(R.id.txtLogistcsName, item.channelType)
                var img = holder.getView<ImageView>(R.id.img)
                GlideAppUtils.loadUrl(img, item.iconUrl)
                holder?.setText(R.id.txtMoney,item.balance)
                holder?.setText(R.id.txtPhone,item.accountNo)
                if(item.stationCommonId.isNotEmpty()){
                    if(item.stationCommonId.size>1){
                        holder?.setText(R.id.txtShopName,item.stationCommonId.get(0).name+">")
                    }else{
                        holder?.setText(R.id.txtShopName,item.stationCommonId.get(0).name)
                    }
                }
            }
        }

        adapter.addChildClickViewIds(R.id.txtShopName,R.id.textToRecharege)
        adapter.setOnItemChildClickListener { adapter, view, position ->
            when(view.id){
                R.id.txtShopName->{
                    var showOtherBindShopDialog=ShowOtherBindShopDialog().newInstance("使用门店",(adapter.data.get(position) as BalanceItem).stationCommonId)
                    showOtherBindShopDialog.show(supportFragmentManager)
                }
                R.id.textToRecharege->{
                    click(adapter,position)
                }
            }
        }


        mDatabind.recyClerView.adapter = adapter

    }

    @SuppressLint("SuspiciousIndentation")
    private fun click(adapte: BaseQuickAdapter<*, *>, position: Int) {
        var merchant = adapte.data.get(position) as BalanceItem

        if (merchant.channelType != "dada") {
            mViewModel?.launchRequest(
                {
                    loginService.merchantRecharge(MerchantRecharge("1","H5",merchant.channelType,merchant.stationCommonId.get(0).id))
                },
                onSuccess = {
                    startActivity(Intent(this,BaseWebActivity::class.java).putExtra("url", it))
                },
                onError = {
                    it?.let { showToast(it) }
                }
            )
        }else{
            var dadaRechargeDialog=DadaRechargeDialog()
            dadaRechargeDialog.setMySureOnclickListener {
                mViewModel?.launchRequest(
                    {
                        loginService.merchantRecharge(MerchantRecharge(it,"H5",merchant.channelType,merchant.stationCommonId.get(0).id))
                    },
                    onSuccess = {
                        startActivity(Intent(this,BaseWebActivity::class.java).putExtra("url", it))
                    },
                    onError = {
                        it?.let { showToast(it) }
                    }
                )
            }
            dadaRechargeDialog.show(supportFragmentManager)
        }
    }


    /**
     * 获取余额列表
     */
    fun getMerchantBalance(){
        mViewModel.launchRequest(
            {
                loginService.getMerchantBalanceList(type,poid)
            },
            onSuccess = {
                it?.let { adapter?.setList(it) }
            },
            onError = {
                adapter?.data?.clear()
            }
        )
    }


    override fun createObserver() {

    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityLogistcsRechargeLayoutBinding {
        return ActivityLogistcsRechargeLayoutBinding.inflate(layoutInflater)
    }


}


