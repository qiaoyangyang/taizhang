package com.meiling.account.fragment

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.meiling.account.R
import com.meiling.account.adapter.DefectivedetailttimeAdapter
import com.meiling.account.adapter.GoodProductDetailAdapter
import com.meiling.account.adapter.GoodProducttimeAdapter
import com.meiling.account.adapter.GoodaAdapter
import com.meiling.account.databinding.FragmentGoodProductDetailBinding
import com.meiling.account.viewmodel.MainViewModel
import com.meiling.account.widget.InputUtil
import com.meiling.common.fragment.BaseFragment

//不良明细
class DefectiveDetailFragment : BaseFragment<MainViewModel, FragmentGoodProductDetailBinding>() {
    var goodProducttimeAdapter: DefectivedetailttimeAdapter? = null
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.rvGoos?.layoutManager = LinearLayoutManager(activity)
        goodProducttimeAdapter = DefectivedetailttimeAdapter()
        mDatabind.rvGoos?.adapter = goodProducttimeAdapter
        goodProducttimeAdapter?.setList(InputUtil.getyouhuiString())
    }

    override fun getBind(inflater: LayoutInflater): FragmentGoodProductDetailBinding {
        return FragmentGoodProductDetailBinding.inflate(inflater)
    }
}