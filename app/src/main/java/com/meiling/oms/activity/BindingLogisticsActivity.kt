package com.meiling.oms.activity

import android.annotation.SuppressLint
import android.content.Intent
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.gyf.immersionbar.ImmersionBar
import com.meiling.common.activity.BaseActivity
import com.meiling.common.activity.BaseVmActivity
import com.meiling.common.network.data.Merchant
import com.meiling.common.network.data.PutMerChant
import com.meiling.common.network.service.loginService
import com.meiling.common.utils.GlideAppUtils
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityBandingLogistcsLayoutBinding
import com.meiling.oms.databinding.ActivityRegisterNextBinding
import com.meiling.oms.dialog.LogisticsPlatformInformationDidalog
import com.meiling.oms.viewmodel.BindingLogisticsViewModel
import com.meiling.oms.widget.showToast

class BindingLogisticsActivity : BaseActivity<BindingLogisticsViewModel,ActivityBandingLogistcsLayoutBinding>() {

    lateinit var adapter:BaseQuickAdapter<Merchant,BaseViewHolder>

    override fun initView(savedInstanceState: Bundle?) {
    }

    @SuppressLint("SuspiciousIndentation")
    override fun initData() {
        super.initData()
        adapter=object :BaseQuickAdapter<Merchant,BaseViewHolder>(R.layout.item_merchant){
            override fun convert(holder: BaseViewHolder, item: Merchant) {
                holder.setText(R.id.name,item.typeName)
                var img=holder.getView<ImageView>(R.id.img)
                GlideAppUtils.loadUrl(img,item.iconUrl)
            }
        }
        adapter.setOnItemClickListener { adapte, view, position ->
            var merchant = adapter.data.get(position)

            var logisticsPlatformInformationDidalog = LogisticsPlatformInformationDidalog().newInstance(merchant )
            logisticsPlatformInformationDidalog.setOnclickListener{
                var item=adapter.data.get(position) as Merchant
                if(it.typeName==item.typeName){
                    adapter.notifyItemChanged(position)
                }
//                mViewModel.launchRequest(
//                    { loginService.merChantSave(PutMerChant(name=it.typeName, tenantId = "9024", arrayListOf(it)))},
//                    onSuccess = {
//
//                    },
//                    onError = {
//                        it?.let { showToast(it) }
//                    }
//                )
            }
            logisticsPlatformInformationDidalog.setOnGoWebListener{
                var intent=Intent(this,AgreementActivity::class.java)
                intent.putExtra("title","物流手册")
                intent.putExtra("url",it.guideUrl)
                startActivity(intent)

            }
            logisticsPlatformInformationDidalog.show(supportFragmentManager)
        }

        mDatabind.recyClerView.adapter=adapter
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

    }



    override fun createObserver() {

    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityBandingLogistcsLayoutBinding {
        return ActivityBandingLogistcsLayoutBinding.inflate(layoutInflater)
    }


}
