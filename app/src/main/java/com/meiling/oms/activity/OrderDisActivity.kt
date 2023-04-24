package com.meiling.oms.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.alibaba.android.arouter.facade.annotation.Route
import com.angcyo.tablayout.delegate2.ViewPager2Delegate
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.adapter.BaseFragmentPagerAdapter
import com.meiling.oms.databinding.ActivityDisBinding
import com.meiling.oms.fragment.*
import com.meiling.oms.viewmodel.OrderDisFragmentViewModel
import com.meiling.common.network.data.OrderDto
import com.meiling.oms.widget.showToast

@Route(path = "/app/OrderDisActivity")
class OrderDisActivity : BaseActivity<OrderDisFragmentViewModel, ActivityDisBinding>() {

    private val fragmentList: MutableList<Fragment> = ArrayList()
    lateinit var content: OrderDto.Content
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.viewPager.isUserInputEnabled = false


        content =
            intent.getSerializableExtra("kk") as OrderDto.Content

    }

    override fun initData() {
        mViewModel.getOrderAndPoiDeliveryDate(
            content.order?.poiId!!,
            content.order!!.viewId!!,
            "TC"
        )


    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityDisBinding {
        return ActivityDisBinding.inflate(layoutInflater)
    }

    @SuppressLint("SetTextI18n")
    override fun createObserver() {
        mViewModel.orderSendAddress.onStart.observe(this) {}
        mViewModel.orderSendAddress.onSuccess.observe(this) {
            mDatabind.txtOrderDisName.text = it.poiName
            mDatabind.txtOrderDisPhone.text = it.poiPhone
            mDatabind.txtOrderDisAddress.text = it.poiAddr
            mDatabind.txtOrderDisRecName.text = it.recvName
            mDatabind.txtOrderDisRecPhone.text = it.recvPhone
            mDatabind.txtOrderDisRecAddress.text = it.recvAddr?.replace("@@", "")

            fragmentList.add(
                OrderDisFragment1.newInstance(
                    it,
                    content.order?.poiId!!,
                    content.order!!.viewId!!,
                    content.order?.payPrice!!
                )
            )
            fragmentList.add(
                OrderDisFragment2.newInstance(
                    content.order?.poiId!!,
                    content.order!!.viewId!!,
                    content.order?.payPrice!!
                )
            )
            fragmentList.add(
                OrderDisFragment3.newInstance(
                    content.order?.poiId!!,
                    content.order!!.viewId!!,
                    content.order?.payPrice!!
                )
            )
            mDatabind.viewPager.setCurrentItem(0, false)
            mDatabind.viewPager.adapter =
                BaseFragmentPagerAdapter(supportFragmentManager, lifecycle, fragmentList)
            ViewPager2Delegate.install(mDatabind.viewPager, mDatabind.tabLayout)
        }
        mViewModel.orderSendAddress.onError.observe(this) {
            showToast("${it.msg}")
        }
    }

    override fun initListener() {
    }
}