package com.meiling.account.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.fragment.app.Fragment
import androidx.recyclerview.widget.GridLayoutManager
import androidx.viewpager.widget.ViewPager.OnPageChangeListener
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemClickListener
import com.meiling.account.adapter.MyPagerAdapter
import com.meiling.account.adapter.ShortTimeAdapter
import com.meiling.account.databinding.FragmentRecordsCenterBinding
import com.meiling.account.viewmodel.MainViewModel
import com.meiling.account.widget.InputUtil
import com.meiling.account.widget.showToast
import com.meiling.common.fragment.BaseFragment

//数据中心
class RecordsCenterFragment : BaseFragment<MainViewModel, FragmentRecordsCenterBinding>(),OnPageChangeListener ,
    OnItemClickListener {
    private val mFragments: ArrayList<Fragment> = ArrayList()
    private val mTitles: ArrayList<String> = ArrayList()
    var shortTimeAdapter: ShortTimeAdapter? = null


    override fun initView(savedInstanceState: Bundle?) {
        mTitles.add("良品明细")
        mTitles.add("不良明细")
        mTitles.add("入库报表")
        mFragments.add(GoodProductDetailFragment())
        mFragments.add(DefectiveDetailFragment())
        mFragments.add(WarehousingReportFragment())

        mDatabind. vpHomePager.adapter = MyPagerAdapter(childFragmentManager, mFragments, mTitles)
        mDatabind. slidingTabLayout.setViewPager(mDatabind.vpHomePager)
        mDatabind. vpHomePager.setNoScroll(true)

        mDatabind.rvShorTime?.layoutManager = GridLayoutManager(context,2)
        shortTimeAdapter = ShortTimeAdapter()
        mDatabind.rvShorTime?.adapter = shortTimeAdapter
        shortTimeAdapter?.setList(InputUtil.getShortTime())
        shortTimeAdapter?.setOnItemClickListener(this)
        mDatabind. vpHomePager.addOnPageChangeListener(this)
    }

    override fun getBind(inflater: LayoutInflater): FragmentRecordsCenterBinding {
        return FragmentRecordsCenterBinding.inflate(inflater)
    }

    override fun onPageScrolled(position: Int, positionOffset: Float, positionOffsetPixels: Int) {
    }

    override fun onPageSelected(position: Int) {
        if (position==2){
            mDatabind.rvShorTime.visibility=View.GONE
            mDatabind.ll3.visibility=View.VISIBLE
        }else{
            mDatabind.ll3.visibility=View.GONE
            mDatabind.rvShorTime.visibility=View.VISIBLE
        }

    }

    override fun onPageScrollStateChanged(state: Int) {
    }

    override fun onItemClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {
        shortTimeAdapter?.data?.forEach {
            it.boolean=false
        }
        shortTimeAdapter?.data?.get(position)?.boolean=true
        shortTimeAdapter?.notifyDataSetChanged()

    }


}