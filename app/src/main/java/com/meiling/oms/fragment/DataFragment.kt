package com.meiling.oms.fragment

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.angcyo.tablayout.delegate2.ViewPager2Delegate
import com.meiling.common.fragment.BaseFragment
import com.meiling.oms.adapter.BaseFragmentPagerAdapter
import com.meiling.oms.databinding.FragmentDataBinding
import com.meiling.oms.viewmodel.MessageViewModel


class DataFragment : BaseFragment<MessageViewModel, FragmentDataBinding>() {

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

    override fun getBind(inflater: LayoutInflater): FragmentDataBinding {
        return FragmentDataBinding.inflate(inflater)
    }


    override fun initListener() {

    }

}