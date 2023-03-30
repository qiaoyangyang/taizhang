package com.meiling.oms.fragment

import android.os.Bundle
import android.view.LayoutInflater
import com.meiling.common.fragment.BaseFragment
import com.meiling.oms.databinding.FragmentDis3Binding
import com.meiling.oms.viewmodel.FindFollowViewModel

class OrderDisFragment3 : BaseFragment<FindFollowViewModel, FragmentDis3Binding>() {

    companion object {
        fun newInstance() = OrderDisFragment3()
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun getBind(inflater: LayoutInflater): FragmentDis3Binding {
        return FragmentDis3Binding.inflate(inflater)
    }


    override fun initListener() {

    }

}