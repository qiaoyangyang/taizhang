package com.meiling.oms.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.lifecycle.ViewModelProvider
import com.blankj.utilcode.util.ActivityUtils
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.activity.BaseActivity
import com.meiling.common.network.data.Merchant
import com.meiling.common.network.data.PutMerChant
import com.meiling.common.network.data.Shop
import com.meiling.common.network.data.ShopBean
import com.meiling.common.network.service.loginService
import com.meiling.common.network.service.meService
import com.meiling.common.utils.GlideAppUtils
import com.meiling.common.utils.SpannableUtils
import com.meiling.common.utils.TextDrawableUtils
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityBandingLogistcsLayoutBinding
import com.meiling.oms.dialog.*
import com.meiling.oms.viewmodel.BindingLogisticsViewModel
import com.meiling.oms.viewmodel.MainViewModel2
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast

class BindingLogisticsActivity :
    BaseActivity<BindingLogisticsViewModel, ActivityBandingLogistcsLayoutBinding>() {

    lateinit var adapter: BaseQuickAdapter<Merchant, BaseViewHolder>
    lateinit var adapterNoBind: BaseQuickAdapter<Merchant, BaseViewHolder>
    var name = ""//品牌名称，默认企业名称的简称
    var  shopName=""//门店名称
    var tenantId = ""
    var account = ""//管理员账号，默认注册时输入的手机号
    var pwd = ""
    var poid = ""
    var from = ""
    var shopList = ArrayList<ShopBean>()
    lateinit var mainViewModel: MainViewModel2
    override fun initView(savedInstanceState: Bundle?) {
        MainActivity.mainActivity?.let {
            mainViewModel =
                ViewModelProvider(it).get(MainViewModel2::class.java)
        } ?: run {
            mainViewModel =
                ViewModelProvider(this@BindingLogisticsActivity).get(MainViewModel2::class.java)
        }
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
                getLogisticsList(poid)
            }

            override fun Ondismiss() {
            }

        })
        shopDialog.show(supportFragmentManager)
    }

    override fun onLeftClick(view: View) {
        if (from.isNullOrBlank()) {
            finish()
        }else{
            val dialog: MineExitDialog =
                MineExitDialog().newInstance("温馨提示", "确定退出当前页面吗？", "取消", "确认", false)
            dialog.setOkClickLister {
                dialog.dismiss()
                startActivity(Intent(this, LoginActivity::class.java))
                ActivityUtils.finishAllActivities()

            }
            dialog.show(supportFragmentManager)
        }


    }

    override fun onRightClick(view: View) {
        super.onRightClick(view)
        if (!from.isNullOrBlank()) {
            val dialog: MineExitDialog =
                MineExitDialog().newInstance("温馨提示", "确定跳过绑定物流流程？\n" +
                        "（登录后可在「我的」中进行设置）", "取消", "确认", false)
            dialog.setOkClickLister {
                dialog.dismiss()
                startActivity(Intent(this, ForgetPwdFinishActivity::class.java)
                    .putExtra("account", account)
                    .putExtra("password", pwd)
                    .putExtra("title", "注册成功")
                    .putExtra("context", "注册成功"))
                ActivityUtils.finishAllActivities()
            }
            dialog.show(supportFragmentManager)

        }
    }



    override fun onBackPressed() {
        if (from.isNullOrBlank()) {
            finish()
        }else{
            val dialog: MineExitDialog =
                MineExitDialog().newInstance("温馨提示", "确定退出当前页面吗？", "取消", "确认", false)
            dialog.setOkClickLister {
                dialog.dismiss()
                startActivity(Intent(this, LoginActivity::class.java))
                ActivityUtils.finishAllActivities()

            }
            dialog.show(supportFragmentManager)
        }
    }

    override fun onResume() {
        super.onResume()
        if(!poid.isNullOrBlank()){
            getLogisticsList(poid)
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
        if (from.isNullOrBlank()) {
            TextDrawableUtils.setRightDrawable(mDatabind.TitleBar.titleView, R.drawable.xia1)
            mDatabind.btnSuccess.visibility = View.GONE

            //获取门店列表
            mViewModel.launchRequest(
                { meService.citypoi() },
                false,
                onSuccess = {
                    it?.let {
                        if (it.size >= 1) {
                            mDatabind.TitleBar.titleView.text = it.get(0).shopList?.get(0)?.name
                            poid = it.get(0).shopList?.get(0)?.id.toString()
                            getLogisticsList(poid)
                        }
                        shopList = it

                    }
                },
                onError = {}
            )
        }else{
            mDatabind.shopName2.text=shopName
            mDatabind.shopName.visibility=View.VISIBLE
            mDatabind.shopName2.visibility=View.VISIBLE
            mDatabind.TitleBar.rightTitle = "跳过"
            getLogisticsList(poid)
        }

        SpannableUtils.setTextcolor(
            this,
            "绑定完成后,点击刷新,可获取绑定结果",
            mDatabind.bindRefresh,
            8,10,
            R.color.red
        )

        mDatabind.bindRefresh.setOnClickListener {

            getLogisticsList(poid)

        }

        adapter = object : BaseQuickAdapter<Merchant, BaseViewHolder>(R.layout.item_merchant) {
            override fun convert(holder: BaseViewHolder, item: Merchant) {
                holder.setText(R.id.name, item.typeName)
                var img = holder.getView<ImageView>(R.id.img)
                GlideAppUtils.loadUrl(img, item.iconUrl)
            }
        }
        adapterNoBind= object : BaseQuickAdapter<Merchant, BaseViewHolder>(R.layout.item_merchant) {
            override fun convert(holder: BaseViewHolder, item: Merchant) {
                holder.setText(R.id.name, item.typeName)
                holder.setText(R.id.textBind,"更换")
                var img = holder.getView<ImageView>(R.id.img)
                GlideAppUtils.loadUrl(img, item.iconUrl)
            }
        }

        adapter.setOnItemClickListener { adapte, view, position ->
            click(adapte,position)
        }

        adapterNoBind.setOnItemClickListener { adapte, view, position ->
            click(adapte,position)
        }

        mDatabind.recyClerView.adapter = adapter
        mDatabind.recyClerView2.adapter = adapterNoBind

        //注册成功
        mDatabind.btnSuccess.setSingleClickListener(1000L) {
            showLoading("")
            var selectList = adapter.data.filter { it.status == "1" }
            if (!selectList.isEmpty()) {
                mViewModel.launchRequest(
                    {
                        loginService.merChantSave(PutMerChant(name = name,
                            tenantId = tenantId,
                            selectList as ArrayList<Merchant>))
                    },
                    onSuccess = {

                        showToast("注册成功")
                        disLoading()
                        startActivity(Intent(this, ForgetPwdFinishActivity::class.java)
                            .putExtra("account", account)
                            .putExtra("password", pwd)
                            .putExtra("title", "注册成功")
                            .putExtra("context", "注册成功"))
                    },
                    onError = {
                        disLoading()
                        it?.let { showToast(it) }
                    }
                )
            } else {
                mViewModel.launchRequest(
                    {
                        loginService.merChantSave(PutMerChant(name = name,
                            tenantId = tenantId,
                            adapter.data as ArrayList<Merchant>))
                    },
                    onSuccess = {

                        showToast("注册成功")
                        disLoading()
                        startActivity(Intent(this, ForgetPwdFinishActivity::class.java)
                            .putExtra("account", account)
                            .putExtra("password", pwd)
                            .putExtra("title", "注册成功")
                            .putExtra("context", "注册成功"))
                    },
                    onError = {
                        disLoading()
                        it?.let { showToast(it) }
                    }
                )
            }

        }

    }

    private fun click(adapte: BaseQuickAdapter<*, *>, position: Int) {
        var merchant = adapte.data.get(position) as Merchant

        if (merchant.type == "uu") {

            var uuBinding = UUBinding()
            uuBinding.setCodeListener {
                mViewModel.launchRequest(
                    { loginService.getCode(it, "uu") },
                    true,
                    onSuccess = {
                        showToast("获取验证码成功")
                    },
                    onError = {
                        it?.let { it1 -> showToast(it1) }
                    }
                )
            }
            uuBinding.setUUSureOnclickListener { phone, code ->
                uuBinding.dismiss()
                mViewModel.launchRequest(
                    { loginService.getOpenId(code, phone, poid, "uu") },
                    true,
                    onSuccess = {
                        getShopList("uu")
                    },
                    onError = {
                        it?.let { it1 -> showToast(it1) }
                    }
                )
            }
            uuBinding.show(supportFragmentManager)
        } else {
            when (merchant.type) {
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
                            var dialogRegistDadaLogistics=DialogRegistDadaLogistics().newInstance(account,pwd,poid)
                            dialogRegistDadaLogistics.setMySureOnclickListener {
                                getLogisticsList(poid)
                            }
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

    fun getLogisticsList(poid:String){
        //获取物流
        mViewModel.launchRequest(//9024
            { loginService.getMerChantList(poid) },
            onSuccess = {
                it?.let {
                    var hasBindingList=it.filter { it.status=="0" }
                    if(hasBindingList.isNullOrEmpty()){
                        mDatabind.view1.visibility=View.GONE
                        mDatabind.title.visibility=View.GONE
                        mDatabind.recyClerView.visibility=View.GONE
                        mainViewModel.getByTenantId.value = mainViewModel.getByTenantId.value?.copy(logistics = 1)

                    }else{
                        mDatabind.view1.visibility=View.VISIBLE
                        mDatabind.title.visibility=View.VISIBLE
                        mDatabind.recyClerView.visibility=View.VISIBLE
                        adapter.setList(hasBindingList)
                    }

                    var noBindList=it.filter { it.status=="1" }
                    if(noBindList.isNullOrEmpty()){
                        mDatabind.recyClerView2.visibility=View.GONE
                        mDatabind.title2.visibility=View.GONE
                        mDatabind.view2.visibility=View.GONE
                    }else{
                        mDatabind.recyClerView2.visibility=View.VISIBLE
                        mDatabind.title2.visibility=View.VISIBLE
                        mDatabind.view2.visibility=View.VISIBLE
                        adapterNoBind.setList(it.filter { it.status=="1" })
                    }
                }
            },
            onError = {
                it?.let { showToast(it) }
            }
        )
    }


    /**
     * 手动获取三方门店列表
     */
    fun getShopList(type: String) {
        var otherShopListDialog = OtherShopListDialog().newInstance(poid)
        otherShopListDialog.setMySureOnclickListener {
            showLoading("正在绑定")
            mViewModel.launchRequest(
                { loginService.bindShop(poid, it.thirdShopId, it.thirdShopName, type) },
                onSuccess = {
                    showToast("绑定成功")
                    getLogisticsList(poid)
                    disLoading()
                },
                onError = {
                    disLoading()
                    it?.let { showToast(it) }
                }
            )
        }
        otherShopListDialog.show(supportFragmentManager)
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

    override fun getBind(layoutInflater: LayoutInflater): ActivityBandingLogistcsLayoutBinding {
        return ActivityBandingLogistcsLayoutBinding.inflate(layoutInflater)
    }


}


