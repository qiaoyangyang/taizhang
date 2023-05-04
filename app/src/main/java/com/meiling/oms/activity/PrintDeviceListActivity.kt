package com.meiling.oms.activity

import android.os.Bundle
import android.view.LayoutInflater
import android.widget.ImageView
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.activity.BaseVmDbActivity
import com.meiling.common.network.data.Merchant
import com.meiling.common.utils.GlideAppUtils
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityBindPrintDeviceListBinding
import com.meiling.oms.viewmodel.RegisterViewModel

/**
 * 打印配置
 */
class PrintDeviceListActivity : BaseVmDbActivity<RegisterViewModel,ActivityBindPrintDeviceListBinding>() {
    lateinit var mAdapter: BaseQuickAdapter<Merchant, BaseViewHolder>
    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun createObserver() {
    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityBindPrintDeviceListBinding {
        return ActivityBindPrintDeviceListBinding.inflate(layoutInflater)
    }

    override fun initData() {
        super.initData()
        mAdapter=object :BaseQuickAdapter<Merchant,BaseViewHolder>(R.layout.list_print_device_item){
            override fun convert(holder: BaseViewHolder, item: Merchant) {
//                holder.setText(R.id.name,item.typeName)
                var img=holder.getView<ImageView>(R.id.img_print_online)
                if(item.status=="1"){
                    GlideAppUtils.loadResUrl(img,R.drawable.print_offline)

                }else if(item.status=="2"){
                    GlideAppUtils.loadResUrl(img,R.drawable.print_online)

                }else if(item.status=="3"){
                    GlideAppUtils.loadResUrl(img,R.drawable.print_nopage)

                }
            }
        }
        var list= arrayListOf<Merchant>()
        list.add(Merchant(status = "1"))
        list.add(Merchant(status = "2"))
        list.add(Merchant(status = "3"))
        mAdapter.setList(list)
        mDatabind.recyClerView.adapter=mAdapter

    }

}