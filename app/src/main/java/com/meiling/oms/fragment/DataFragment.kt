package com.meiling.oms.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.angcyo.tablayout.delegate2.ViewPager2Delegate
import com.meiling.common.fragment.BaseFragment
import com.meiling.common.network.data.Shop
import com.meiling.common.network.data.ShopBean
import com.meiling.oms.adapter.BaseFragmentPagerAdapter
import com.meiling.oms.databinding.FragmentDataBinding
import com.meiling.oms.dialog.ShopDialog
import com.meiling.oms.eventBusData.MessageSelectShopPo
import com.meiling.oms.viewmodel.DataViewModel
import com.meiling.oms.widget.setSingleClickListener
import com.meiling.oms.widget.showToast
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode


class DataFragment : BaseFragment<DataViewModel, FragmentDataBinding>() {

    companion object {
        fun newInstance() = DataFragment()
    }

    private val fragmentList: MutableList<Fragment> = ArrayList()

    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.viewPager.isUserInputEnabled = false
        fragmentList.add(DataShopFragment.newInstance())
        fragmentList.add(DataChannelFragment.newInstance())
        fragmentList.add(DataOrderDisFragment.newInstance())
        mDatabind.viewPager.adapter =
            BaseFragmentPagerAdapter(childFragmentManager, lifecycle, fragmentList)
        mDatabind.viewPager.setCurrentItem(0, false)
        ViewPager2Delegate.install(mDatabind.viewPager, mDatabind.tabLayout)

    }

    lateinit var shopDialog: ShopDialog

    override fun initData() {
        mViewModel.cityShop("1")
        mDatabind.TitleBar.setSingleClickListener {
            mViewModel.cityShop("1")
            shopDialog.show(childFragmentManager)
        }
    }

    override fun getBind(inflater: LayoutInflater): FragmentDataBinding {
        return FragmentDataBinding.inflate(inflater)
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    override fun initListener() {

    }

    override fun createObserver() {
        mViewModel.shopBean.onSuccess.observe(this) {
            if (it.isNotEmpty()) {
                var shopBeanList = ArrayList<ShopBean>()
                var shopList = ArrayList<Shop?>()
                shopList.add(Shop(name = "所有门店", id = "0"))
                shopBeanList.add(
                    0, ShopBean(
                        name = "全国", shopList = shopList
                    )
                )
                shopBeanList.addAll(it)
                mDatabind.TitleBar.text =
                    shopBeanList[0].name + "/" + "${shopBeanList[0].shopList?.get(0)!!.name}"
                shopDialog = ShopDialog().newInstance(shopBeanList)
                shopDialog.setOnresilience(object : ShopDialog.Onresilience {
                    override fun resilience(
                        cityid: Int, cityidname: String, shopid: Int, shop: Shop
                    ) {
                        var idArrayList = ArrayList<String>()
                        if (cityidname == "全国") {
                            idArrayList = ArrayList()
                        } else {
                            idArrayList.addAll(shop.id!!.split(",").toTypedArray())
                        }
                        Log.d("shop", "=====${idArrayList}")
                        mDatabind.TitleBar.text = cityidname + "/" + shop.name
                        EventBus.getDefault().post(MessageSelectShopPo(idArrayList))
                    }
                    override fun Ondismiss() {
                    }
                })
            }else{
                mDatabind.TitleBar.text = "全国"+ "/" + "所有门店"
                EventBus.getDefault().post(MessageSelectShopPo(ArrayList()))
            }


        }

        mViewModel.shopBean.onError.observe(this) {
            showToast(it.msg)
        }
    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun eventSelectTime(messageSelectShopPo: MessageSelectShopPo) {
    }

}