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
import com.meiling.common.network.data.Merchant
import com.meiling.common.network.data.PutMerChant
import com.meiling.common.network.service.loginService
import com.meiling.common.utils.GlideAppUtils
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityBandingLogistcsLayoutBinding
import com.meiling.oms.dialog.BindOtherLogistics
import com.meiling.oms.dialog.LogisticsPlatformInformationDidalog
import com.meiling.oms.dialog.MineExitDialog
import com.meiling.oms.dialog.UUBinding
import com.meiling.oms.viewmodel.BindingLogisticsViewModel
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast

class BindingLogisticsActivity : BaseActivity<BindingLogisticsViewModel,ActivityBandingLogistcsLayoutBinding>() {

    lateinit var adapter:BaseQuickAdapter<Merchant,BaseViewHolder>
    var name=""
    var tenantId=""
    var account=""
    var pwd=""
    var poid=""
    var from=""
    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun onLeftClick(view: View) {

        val dialog: MineExitDialog =
            MineExitDialog().newInstance("温馨提示", "确定退出当前页面吗？", "取消", "确认", false)
        dialog.setOkClickLister {
            dialog.dismiss()
            if(from.isNullOrBlank()){
                finish()
            }else{
                startActivity(Intent(this,LoginActivity::class.java))
                ActivityUtils.finishAllActivities()
            }

        }
        dialog.show(supportFragmentManager)
    }
    override fun onBackPressed() {
        val dialog: MineExitDialog =
            MineExitDialog().newInstance("温馨提示", "确定退出当前页面吗？", "取消", "确认", false)
        dialog.setOkClickLister {
            dialog.dismiss()
            if(from.isNullOrBlank()){
                finish()
            }else{
                startActivity(Intent(this,LoginActivity::class.java))
                ActivityUtils.finishAllActivities()
            }
        }
        dialog.show(supportFragmentManager)
    }
    @SuppressLint("SuspiciousIndentation")
    override fun initData() {
        super.initData()
        name= intent?.getStringExtra("name")?:""
        tenantId= intent?.getStringExtra("tenantId")?:""
        account= intent?.getStringExtra("account")?:""
        pwd= intent?.getStringExtra("pwd")?:""
        poid=intent?.getStringExtra("poid")?:""
        from=intent?.getStringExtra("from")?:""
        adapter=object :BaseQuickAdapter<Merchant,BaseViewHolder>(R.layout.item_merchant){
            override fun convert(holder: BaseViewHolder, item: Merchant) {
                holder.setText(R.id.name,item.typeName)
                var img=holder.getView<ImageView>(R.id.img)
                GlideAppUtils.loadUrl(img,item.iconUrl)
                if(item.status=="1"){
                    holder.setVisible(R.id.selectImg,true)
                }else{
                    holder.setGone(R.id.selectImg,true)
                }
            }
        }
        adapter.setOnItemClickListener { adapte, view, position ->
            var merchant = adapter.data.get(position)

            if(merchant.type=="uu"){

                var uuBinding=UUBinding()
                uuBinding.setCodeListener {
                    mViewModel.launchRequest(
                        { loginService.getCode(it,"uu")},
                        true,
                        onSuccess = {

                        },
                        onError = {
                            it?.let { it1 -> showToast(it1) }
                        }
                    )
                }
                uuBinding.setUUSureOnclickListener { phone, code ->

                    mViewModel.launchRequest(
                        { loginService.getOpenId(code,phone,poid,"uu")},
                        true,
                        onSuccess = {

                        },
                        onError = {
                            it?.let { it1 -> showToast(it1) }
                        }
                    )
                }
                uuBinding.show(supportFragmentManager)
            }else {
                when (merchant.type) {
                    "dada" -> {
                        var bindingOther = BindOtherLogistics()
                            .newInstance(
                                "已有达达快送APP账号",
                                "达达快送APP帐号授权后即可发单，与达达里价格、优惠等活动一致。\n如果没有账号，如果没有账号，请先下载达达快送APP后，注册并开通企业版。")
                        bindingOther.setMySureOnclickListener {

                        }
                        bindingOther.show(supportFragmentManager)
                    }
                    "sf_tc" -> {
                        var bindingOther = BindOtherLogistics().newInstance("已有顺丰同城账号",
                            "顺丰同城帐号授权后即可发单，与顺丰同城里价格、优惠等活动一致。\n如果没有账号，请先下载顺丰同城APP，注册并开通商户版。")
                        bindingOther.setMySureOnclickListener {

                        }
                        bindingOther.show(supportFragmentManager)
                    }
                    "ss" -> {
                        var bindingOther = BindOtherLogistics().newInstance("已有闪送账号",
                            "闪送帐号授权后即可发单，与闪送里价格、优惠等活动一致。\n如果没有账号，请先下载闪送商家版APP后，注册账号。")
                        bindingOther.setMySureOnclickListener {

                        }
                        bindingOther.show(supportFragmentManager)
                    }

                }
            }
//                var logisticsPlatformInformationDidalog = LogisticsPlatformInformationDidalog().newInstance(merchant )
//                logisticsPlatformInformationDidalog.setOnclickListener{
//                    var item=adapter.data.get(position) as Merchant
//                    if(it.typeName==item.typeName){
//                        item.thirdMerchantId=it.thirdMerchantId
//                        item.appSecret=it.appSecret
//                        item.appId=it.appId
//                        item.status=it.status
//                        adapter.notifyItemChanged(position)
//                    }
//                }
//                logisticsPlatformInformationDidalog.setOnGoWebListener{
//                    var intent=Intent(this,BaseWebActivity::class.java)
//                    intent.putExtra("title","物流手册")
//                    intent.putExtra("url",it.guideUrl)
//                    startActivity(intent)
//
//                }
//                logisticsPlatformInformationDidalog.show(supportFragmentManager)
            }

        mDatabind.recyClerView.adapter=adapter
        //获取物流
        mViewModel.launchRequest(//9024
            { loginService.getMerChantList("")},
            onSuccess = {
                it?.let {
                    adapter.setList(it)
                }
            },
            onError = {
                it?.let { showToast(it) }
            }
        )
        //getShopList
        mViewModel.launchRequest(
            { loginService.getShopList("1","20","156207273","uu")},
            true,
            onSuccess = {},
            onError = {
                it?.let {
                    showToast(it)
                }
            }
        )

        //注册成功
        mDatabind.btnSuccess.setSingleClickListener(1000L) {
            showLoading("")
            var selectList=adapter.data.filter { it.status=="1" }
            if(!selectList.isEmpty()){
                mViewModel.launchRequest(
                    { loginService.merChantSave(PutMerChant(name=name, tenantId = tenantId, selectList as ArrayList<Merchant>))},
                    onSuccess = {

                        showToast("注册成功")
                        disLoading()
                        startActivity(Intent(this,ForgetPwdFinishActivity::class.java)
                            .putExtra("account",account)
                            .putExtra("password",pwd)
                            .putExtra("title","注册成功")
                            .putExtra("context","注册成功"))
                    },
                    onError = {
                        disLoading()
                        it?.let { showToast(it) }
                    }
                )
            }else{
                mViewModel.launchRequest(
                    { loginService.merChantSave(PutMerChant(name=name, tenantId = tenantId, adapter.data as ArrayList<Merchant>))},
                    onSuccess = {

                        showToast("注册成功")
                        disLoading()
                        startActivity(Intent(this,ForgetPwdFinishActivity::class.java)
                            .putExtra("account",account)
                            .putExtra("password",pwd)
                            .putExtra("title","注册成功")
                            .putExtra("context","注册成功"))
                    },
                    onError = {
                        disLoading()
                        it?.let { showToast(it) }
                    }
                )
            }

        }

    }



    override fun createObserver() {

    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityBandingLogistcsLayoutBinding {
        return ActivityBandingLogistcsLayoutBinding.inflate(layoutInflater)
    }


}
