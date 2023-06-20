package com.meiling.account.fragment

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.GridLayoutManager
import androidx.recyclerview.widget.LinearLayoutManager
import com.meiling.account.R
import com.meiling.account.adapter.GoodProductDetailAdapter
import com.meiling.account.adapter.GoodProducttimeAdapter
import com.meiling.account.adapter.GoodaAdapter
import com.meiling.account.databinding.FragmentGoodProductDetailBinding
import com.meiling.account.viewmodel.MainViewModel
import com.meiling.account.widget.InputUtil
import com.meiling.common.fragment.BaseFragment

//良品明细
class GoodProductDetailFragment : BaseFragment<MainViewModel, FragmentGoodProductDetailBinding>() {
    var goodProducttimeAdapter: GoodProducttimeAdapter? = null
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.rvGoos?.layoutManager = LinearLayoutManager(activity)
        goodProducttimeAdapter = GoodProducttimeAdapter()
        mDatabind.rvGoos?.adapter = goodProducttimeAdapter
        goodProducttimeAdapter?.setList(InputUtil.getyouhuiString())
    }

    override fun getBind(inflater: LayoutInflater): FragmentGoodProductDetailBinding {
        return FragmentGoodProductDetailBinding.inflate(inflater)
    }
}