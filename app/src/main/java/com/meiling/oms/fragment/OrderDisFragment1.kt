package com.meiling.oms.fragment

import android.os.Bundle
import android.view.LayoutInflater
import com.meiling.common.fragment.BaseFragment
import com.meiling.oms.databinding.FragmentDis1Binding
import com.meiling.oms.viewmodel.FindFollowViewModel

class OrderDisFragment1 : BaseFragment<FindFollowViewModel, FragmentDis1Binding>() {

    companion object {
        fun newInstance() = OrderDisFragment1()
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun getBind(inflater: LayoutInflater): FragmentDis1Binding {
        return FragmentDis1Binding.inflate(inflater)
    }


    override fun initListener() {

    }

}