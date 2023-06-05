package com.meiling.oms.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import com.blankj.utilcode.util.ActivityUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.*
import com.meiling.common.network.service.loginService
import com.meiling.common.network.service.meService
import com.meiling.common.utils.GlideAppUtils
import com.meiling.common.utils.TextDrawableUtils
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
    var name = ""//品牌名称，默认企业名称的简称
    var  shopName=""//门店名称
    var tenantId = ""
    var account = ""//管理员账号，默认注册时输入的手机号
    var pwd = ""
    var poid = ""
    var from = ""
    var shopList = ArrayList<ShopBean>()
    var type=""
    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun onTitleClick(view: View) {
        showShopListDialog(shopList)
    }

    fun showShopListDialog(shopBean: ArrayList<ShopBean>) {
        var shopDialog = ShopDialog().newInstance(shopBean!!, "选择发货门店")

        shopDialog.setOnresilience(object : ShopDialog.Onresilience {

            override fun resilience(
                cityid: Int,
                cityidname: String,
                shopid: Int,
                sho: Shop,
            ) {
                poid = sho.id.toString()
                mDatabind.TitleBar.titleView.text = sho.name
                getMerchantBalance()
            }

            override fun Ondismiss() {
            }

        })
        shopDialog.show(supportFragmentManager)
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
        name = intent?.getStringExtra("name") ?: ""
        tenantId = intent?.getStringExtra("tenantId") ?: ""
        account = intent?.getStringExtra("account") ?: ""
        pwd = intent?.getStringExtra("pwd") ?: ""
        poid = intent?.getStringExtra("poid") ?: ""
        from = intent?.getStringExtra("from") ?: ""
        shopName= intent?.getStringExtra("shopName") ?: ""

        getMerchantBalance()


        mDatabind.selectLogistcsType.setOnClickListener {

            var chooseViewDialog=ChooseViewDialog()
            chooseViewDialog.setMySureOnclickListener {
                mDatabind.selectLogistcsType.text = it.typeName
                type=it.type
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


        adapter.setOnItemClickListener { adapte, view, position ->
            click(adapte,position)
        }



        mDatabind.recyClerView.adapter = adapter


    }

    private fun click(adapte: BaseQuickAdapter<*, *>, position: Int) {
        var merchant = adapte.data.get(position) as BalanceItem

        if (merchant.channelType == "uu") {
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

        } else {
            when (merchant.channelType) {
                "dada" -> {
                    var bindingOther = BindDadaOtherLogistics()
                        .newInstance(
                            "已有达达快送APP账号",
                            "达达快送APP帐号授权后即可发单，与达达里价格、优惠等活动一致。","dada")
                    bindingOther.setMySureOnclickListener {type,type2->
                        if(type2=="1"){
                            getUrl(type)

                        }else{
                            //TODO 去注册
                            var dialogRegistDadaLogistics=DialogRegistDadaLogistics()
                            dialogRegistDadaLogistics.show(supportFragmentManager)
                        }
                    }
                    bindingOther.show(supportFragmentManager)
                }
                "sf_tc" -> {
                    var bindingOther = BindOtherLogistics().newInstance("已有顺丰同城账号",
                        "顺丰同城帐号授权后即可发单，与顺丰同城里价格、优惠等活动一致。\n如果没有账号，请先下载顺丰同城APP，注册并开通商户版。")
                    bindingOther.setMySureOnclickListener {
                        getUrl("sf_tc")
                    }
                    bindingOther.show(supportFragmentManager)
                }
                "ss" -> {
                    var bindingOther = BindOtherLogistics().newInstance("已有闪送账号",
                        "闪送帐号授权后即可发单，与闪送里价格、优惠等活动一致。\n如果没有账号，请先下载闪送商家版APP后，注册账号。")
                    bindingOther.setMySureOnclickListener {
                        getUrl("ss")
                    }
                    bindingOther.show(supportFragmentManager)
                }

            }
        }
    }



    /**
     * 手动获取三方门店列表
     */
    fun getShopList(type: String) {
//        var otherShopListDialog = OtherShopListDialog().newInstance(poid)
//        otherShopListDialog.setMySureOnclickListener {
//            showLoading("正在绑定")
//            mViewModel.launchRequest(
//                { loginService.bindShop(poid, it.thirdShopId, it.thirdShopName, type) },
//                onSuccess = {
//                    showToast("绑定成功")
//                    getLogisticsList(poid)
//                    disLoading()
//                },
//                onError = {
//                    disLoading()
//                    it?.let { showToast(it) }
//                }
//            )
//        }
//        otherShopListDialog.show(supportFragmentManager)

        var chooseViewDialog=ChooseViewDialog().newInstance("配送平台")
        chooseViewDialog.setMySureOnclickListener {
            showToast(it.typeName)
        }
        chooseViewDialog.show(supportFragmentManager)
    }


    /**
     * 获取余额列表
     */
    fun getMerchantBalance(){
        mViewModel.launchRequest(
            {
                loginService.getMerchantBalanceList(type,"")
            },
            onSuccess = {
                it?.let { adapter?.setList(it) }
            },
            onError = {

            }
        )
    }

    /**
     * 获取授权链接
     */
    fun getUrl(type: String) {
        mViewModel.launchRequest(
            {
                loginService.getUrl(originId = null, poid, type)
            },
            true,
            onSuccess = {
                startActivity(Intent(this, BaseWebActivity::class.java).putExtra("url", it))
            },
            onError = {
                it?.let { it1 -> showToast(it1) }
            }
        )
    }

    override fun createObserver() {

    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityLogistcsRechargeLayoutBinding {
        return ActivityLogistcsRechargeLayoutBinding.inflate(layoutInflater)
    }


}


