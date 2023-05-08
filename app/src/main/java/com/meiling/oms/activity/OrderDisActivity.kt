package com.meiling.oms.activity

import android.annotation.SuppressLint
import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import androidx.viewpager2.widget.ViewPager2
import com.alibaba.android.arouter.facade.annotation.Route
import com.alibaba.android.arouter.launcher.ARouter
import com.angcyo.tablayout.delegate2.ViewPager2Delegate
import com.meiling.common.BaseViewModel
import com.meiling.common.activity.BaseActivity
import com.meiling.oms.adapter.BaseFragmentPagerAdapter
import com.meiling.oms.databinding.ActivityDisBinding
import com.meiling.oms.fragment.*
import com.meiling.oms.viewmodel.OrderDisFragmentViewModel
import com.meiling.common.network.data.OrderDto
import com.meiling.common.network.data.OrderSendAddress
import com.meiling.oms.eventBusData.MessageEvent
import com.meiling.oms.widget.showToast
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

@Route(path = "/app/OrderDisActivity")
class OrderDisActivity : BaseActivity<OrderDisFragmentViewModel, ActivityDisBinding>() {

    private val fragmentList: MutableList<Fragment> = ArrayList()
    lateinit var content: OrderDto.Content
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.viewPager.isUserInputEnabled = false


        content =
            intent.getSerializableExtra("kk") as OrderDto.Content
        mDatabind.ivDetail.setOnClickListener {
            if (orderSendAddress != null) {
                ARouter.getInstance().build("/app/OrderChangeAddressActivity")
                    .withString("receiveTime", content?.order?.arriveTime)
                    .withString("receiveName", orderSendAddress?.recvName)
                    .withString("receivePhone", orderSendAddress?.recvPhone)
                    .withString("receiveAddress", orderSendAddress?.recvAddr)
                    .withString("receiveRemark", "")
                    .withString("lat", orderSendAddress?.lat)
                    .withString("lon", orderSendAddress?.lon)
                    .withString("orderId", content.order!!.viewId)
                    .withInt("isreceiveRemark", 1)
                    .withInt("index", index)
                    .navigation()
            }
        }
        mDatabind.viewPager.registerOnPageChangeCallback(object :
            ViewPager2.OnPageChangeCallback() {
            override fun onPageSelected(position: Int) {
                super.onPageSelected(position)
                Log.d("yjk", "onPageSelected-- $position")
                index = position
            }
        })

        EventBus.getDefault().register(this)

    }

    var index = 0;

    override fun initData() {

        mViewModel.getOrderAndPoiDeliveryDate(
            content.order?.poiId!!,
            content.order!!.viewId!!,
            "TC"
        )

    }

    override fun onResume() {
        super.onResume()

    }

    override fun getBind(layoutInflater: LayoutInflater): ActivityDisBinding {
        return ActivityDisBinding.inflate(layoutInflater)
    }

    lateinit var orderSendAddress: OrderSendAddress


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
            orderSendAddress = it
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

            mDatabind.viewPager.adapter =
                BaseFragmentPagerAdapter(supportFragmentManager, lifecycle, fragmentList)
            ViewPager2Delegate.install(mDatabind.viewPager, mDatabind.tabLayout)
            Log.d("yjk","onMessageEvent-----${index}")
            mDatabind.viewPager.setCurrentItem(index, false)
        }
        mViewModel.orderSendAddress.onError.observe(this) {
            showToast("${it.msg}")
        }
    }

    override fun initListener() {
    }



    @Subscribe(threadMode = ThreadMode.MAIN)
    fun onMessageEvent(event: MessageEvent) {
        Log.d("yjk","onMessageEvent-------")
        if (event != null) {
            Log.d("yjk","onMessageEvent")
            index = event.message
            mViewModel.getOrderAndPoiDeliveryDate(
                content.order?.poiId!!,
                content.order!!.viewId!!,
                "TC"
            )
        }

    }
}