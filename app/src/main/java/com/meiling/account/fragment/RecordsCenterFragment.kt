package com.meiling.account.fragment

import android.os.Bundle
import android.view.LayoutInflater
import androidx.fragment.app.Fragment
import com.meiling.account.adapter.MyPagerAdapter
import com.meiling.account.databinding.FragmentRecordsCenterBinding
import com.meiling.account.viewmodel.MainViewModel
import com.meiling.common.fragment.BaseFragment

//数据中心
class RecordsCenterFragment : BaseFragment<MainViewModel, FragmentRecordsCenterBinding>() {
    private val mFragments: ArrayList<Fragment> = ArrayList()
    private val mTitles: ArrayList<String> = ArrayList()
    override fun initView(savedInstanceState: Bundle?) {
        mTitles.add("良品明细")
        mTitles.add("不良明细")
        mTitles.add("入库报表")
        for (title in mTitles) {
            mFragments.add(GoodProductDetailFragment())

        }
        mDatabind. vpHomePager.adapter = MyPagerAdapter(childFragmentManager, mFragments, mTitles)
        mDatabind. slidingTabLayout.setViewPager(mDatabind.vpHomePager)
        mDatabind. vpHomePager.setNoScroll(true)
    }

    override fun getBind(inflater: LayoutInflater): FragmentRecordsCenterBinding {
        return FragmentRecordsCenterBinding.inflate(inflater)
    }


}