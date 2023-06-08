package com.meiling.oms.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import android.widget.TextView
import androidx.lifecycle.ViewModelProvider
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hjq.shape.view.ShapeTextView
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.*
import com.meiling.common.network.service.loginService
import com.meiling.common.utils.GlideAppUtils
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityLogistcsRechargeLayoutBinding
import com.meiling.oms.dialog.*
import com.meiling.oms.viewmodel.LogisticsRechargeViewModel
import com.meiling.oms.viewmodel.MainViewModel2
import com.meiling.oms.widget.showToast

/**
 * 物流充值
 */
class LogisticsRechargeActivity :
    BaseActivity<LogisticsRechargeViewModel, ActivityLogistcsRechargeLayoutBinding>() {

    lateinit var adapter: BaseQuickAdapter<BalanceItem, BaseViewHolder>
    var poid = ""
    var type=""
    var logistics="-1"
    lateinit var mainViewModel: MainViewModel2
    override fun initView(savedInstanceState: Bundle?) {
        mainViewModel =
            ViewModelProvider(MainActivity.mainActivity!!).get(MainViewModel2::class.java)
        adapter = object : BaseQuickAdapter<BalanceItem, BaseViewHolder>(R.layout.item_logistcs_recharege) {
            override fun convert(holder: BaseViewHolder, item: BalanceItem) {
                holder.setText(R.id.txtLogistcsName, item.channelTypeName.toString())
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


    override fun onResume() {
        super.onResume()
        if(logistics=="1"){
            getMerchantBalance()
        }
    }
    @SuppressLint("SuspiciousIndentation")
    override fun initData() {
        super.initData()
        poid = intent?.getStringExtra("poid") ?: ""

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
                    startActivity(Intent(this,BaseWebActivity::class.java).putExtra("url", it).putExtra("title","物流充值"))
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
                        dadaRechargeDialog?.dismiss()
                        startActivity(Intent(this,BaseWebActivity::class.java).putExtra("url", it).putExtra("title","物流充值"))
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
                it?.let { showToast(it) }
                adapter?.data?.clear()
            }
        )
    }


    override fun createObserver() {
        mainViewModel.getByTenantId.observe(this) {
            if (it.poi == 1) {//门店是否创建 1绑定;-1没绑定
                if (it.logistics == 1) {//物流是否绑定 1绑定;-1没绑定
                    mDatabind.topConlay.visibility = View.VISIBLE
                    logistics="1"
                    val view =
                        LayoutInflater.from(this).inflate(R.layout.store_managemnet2, null, false)
                    var tv_decreate = view.findViewById<TextView>(R.id.txt_error)
                    tv_decreate.text="未查询到内容"
                    adapter.setEmptyView(view)
                    getMerchantBalance()
                } else {
                    val view =
                        LayoutInflater.from(this).inflate(R.layout.empty_logistics_layout, null, false)
                    var tv_decreate = view.findViewById<ShapeTextView>(R.id.tv_decreate)
                    tv_decreate.setOnClickListener {
                        startActivity(
                            Intent(this,BindingLogisticsActivity::class.java))
                    }
                    adapter.setEmptyView(view)
                    mDatabind.topConlay.visibility = View.GONE
                }
            } else {
                val view =
                    LayoutInflater.from(this).inflate(R.layout.store_managemnet1, null, false)
                var tv_decreate = view.findViewById<ShapeTextView>(R.id.tv_decreate)
                var tv_name_t= view.findViewById<TextView>(R.id.tv_name_t)
                tv_name_t.visibility=View.GONE
                tv_decreate.setOnClickListener {
                    startActivity(Intent(this, NewlyBuiltStoreActivity::class.java))
                }
                adapter.setEmptyView(view)
                mDatabind.topConlay.visibility = View.GONE
            }

        }
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityLogistcsRechargeLayoutBinding {
        return ActivityLogistcsRechargeLayoutBinding.inflate(layoutInflater)
    }


}


