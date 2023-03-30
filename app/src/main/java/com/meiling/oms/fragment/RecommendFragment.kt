package com.meiling.oms.fragment

import android.os.Bundle
import android.view.LayoutInflater
import com.alibaba.android.arouter.launcher.ARouter
import com.meiling.common.fragment.BaseFragment
import com.meiling.oms.adapter.RecommendAdapter
import com.meiling.oms.databinding.FragmentRecommendBinding
import com.meiling.oms.viewmodel.RecommendViewModel

class RecommendFragment : BaseFragment<RecommendViewModel, FragmentRecommendBinding>() {


    private val mRecommendAdapter: RecommendAdapter by lazy { RecommendAdapter() }

    companion object {
        fun newInstance() = RecommendFragment()
    }


    override fun initView(savedInstanceState: Bundle?) {

    }


    override fun initData() {

    }


    override fun getBind(inflater: LayoutInflater): FragmentRecommendBinding {
        return FragmentRecommendBinding.inflate(inflater)
    }

    override fun initListener() {

    }

}