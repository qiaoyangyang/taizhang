package com.meiling.oms.activity

import android.content.Intent
import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.widget.ImageView
import androidx.appcompat.widget.AppCompatButton
import androidx.lifecycle.ViewModelProvider
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.hjq.shape.view.ShapeTextView
import com.meiling.common.activity.BaseVmDbActivity
import com.meiling.common.network.data.Merchant
import com.meiling.common.network.data.Printing
import com.meiling.common.utils.GlideAppUtils
import com.meiling.oms.R
import com.meiling.oms.databinding.ActivityBindPrintDeviceListBinding
import com.meiling.oms.dialog.MineExitDialog
import com.meiling.oms.viewmodel.MainViewModel2
import com.meiling.oms.viewmodel.RegisterViewModel
import com.meiling.oms.widget.showToast

/**
 * 打印配置
 */
class PrintDeviceListActivity :
    BaseVmDbActivity<RegisterViewModel, ActivityBindPrintDeviceListBinding>() {
    lateinit var mAdapter: BaseQuickAdapter<Printing, BaseViewHolder>
    override fun initView(savedInstanceState: Bundle?) {
        initRecycleyView()
        mDatabind.btnAdd.setOnClickListener {
            startActivity(Intent(this, SelectPrintDeviceActivity::class.java))
        }
        mDatabind.sflLayout.setOnRefreshListener {
            initData()
        }
        mainViewModel =
            ViewModelProvider(MainActivity.mainActivity!!).get(MainViewModel2::class.java)
    }
    lateinit var mainViewModel: MainViewModel2

    override fun createObserver() {

        mainViewModel.getByTenantId.observe(this) {

            if (it.poi == -1) {//门店是否创建 1绑定;-1没绑定

                val view =
                    LayoutInflater.from(this).inflate(R.layout.activity_no_store, null, false)
                var tv_decreate = view.findViewById<AppCompatButton>(R.id.creatStoreBtn)
                tv_decreate.setOnClickListener {
                    startActivity(Intent(this, NewlyBuiltStoreActivity::class.java))
                }

                mAdapter.setEmptyView(view)
            } else {
                mViewModel.getprintChannelList()mViewModel.getprintChannelList()
                //mAdapter.setEmptyView(R.layout.activity_no_print_device)
            }
        }
        mViewModel.delDev.onStart.observe(this){
            showLoading("")
        }
        mViewModel.delDev.onSuccess.observe(this){
            disLoading()
            mAdapter.removeAt(isposition)
            showToast("删除成功")
            mAdapter.notifyDataSetChanged()
            if (mAdapter.data.size==0){
                mDatabind.lin2.visibility=View.GONE
                mDatabind.btnAdd.visibility=View.GONE
                val view = LayoutInflater.from(this)
                    .inflate(R.layout.activity_no_print_device, null, false)
                var creatStoreBtn = view.findViewById<AppCompatButton>(R.id.creatStoreBtn)
                creatStoreBtn.setOnClickListener {
                    startActivity(Intent(this, SelectPrintDeviceActivity::class.java))
                }

                mAdapter.setEmptyView(view)
            }
        }
        mViewModel.delDev.onError.observe(this){
            disLoading()
            showToast(it.msg)
        }


        mViewModel.printChannelList.onStart.observe(this){
            showLoading("")
        }
        mViewModel.printChannelList.onError.observe(this){
            disLoading()
            mDatabind.sflLayout.finishRefresh()
            showToast(it.msg)
        }

        mViewModel.printChannelList.onSuccess.observe(this) {
            disLoading()
            mDatabind.sflLayout.finishRefresh()
            if (it .size!=0 ) {
                mDatabind.lin2.visibility=View.VISIBLE
                mDatabind.btnAdd.visibility=View.VISIBLE
                mAdapter.setList(it)
                if (mAdapter.data.size>=6){
                    mDatabind.btnAdd.visibility=View.GONE
                }

            } else {
                mDatabind.lin2.visibility=View.GONE
                mDatabind.btnAdd.visibility=View.GONE
                val view = LayoutInflater.from(this)
                    .inflate(R.layout.activity_no_print_device, null, false)
                var creatStoreBtn = view.findViewById<AppCompatButton>(R.id.creatStoreBtn)
                creatStoreBtn.setOnClickListener {
                    startActivity(Intent(this, SelectPrintDeviceActivity::class.java))
                }

                mAdapter.setEmptyView(view)
            }
        }
    }
    var isposition = -1
    override fun getBind(layoutInflater: LayoutInflater): ActivityBindPrintDeviceListBinding {
        return ActivityBindPrintDeviceListBinding.inflate(layoutInflater)
    }

    override fun onResume() {
        super.onResume()
        initData()
    }

    private fun initRecycleyView() {
        mAdapter =
            object : BaseQuickAdapter<Printing, BaseViewHolder>(R.layout.list_print_device_item) {
                override fun convert(holder: BaseViewHolder, item: Printing) {
                    holder.setText(R.id.txtPrintName2, item.name)
                    holder.setText(R.id.txtPinPai2, item.brandCodeName)
//                holder.setText(R.id.name,item.typeName)
                    var img = holder.getView<ImageView>(R.id.img_print_online)
                    if (item.printStatus == 0) {
                        holder.setBackgroundResource(R.id.img_print_online,R.drawable.print_offline)
                        //GlideAppUtils.loadResUrl(img, R.drawable.print_offline)

                    } else if (item.printStatus == 1) {
                        holder.setBackgroundResource(R.id.img_print_online,R.drawable.print_online)
                       // GlideAppUtils.loadResUrl(img, R.drawable.print_online)

                    } else if (item.printStatus == 2) {
                        holder.setBackgroundResource(R.id.img_print_online,R.drawable.print_nopage)
                       // GlideAppUtils.loadResUrl(img, R.drawable.print_nopage)

                    }
                }
            }
        mAdapter.setList(arrayListOf())
        mDatabind.recyClerView.adapter = mAdapter

        mAdapter.addChildClickViewIds(R.id.btn_change, R.id.btn_del)
        mAdapter.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.btn_change -> {
                    startActivity(
                        Intent(
                            this, BindPrintDeviceActivity::class.java
                        ).putExtra("deviceid", mAdapter.data.get(position).deviceid.toString())
                    )
                }
                R.id.btn_del -> {

                    val dialog: MineExitDialog = MineExitDialog().newInstance(
                        "温馨提示", "删除后,该打印机将不可用，请确认操作～", "取消", "确认", false
                    )
                    dialog.setOkClickLister {
                        isposition=position
                        dialog.dismiss()
                        mViewModel.delDev( mAdapter.data.get(position).deviceid.toString())

                    }
                    dialog.show(supportFragmentManager)
                }
            }
        }

    }

    override fun initData() {
        super.initData()

        if (mainViewModel.getByTenantId.value?.poi!=-1) {
            mViewModel.getprintChannelList()
        }


    }

}