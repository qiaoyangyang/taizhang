package com.meiling.oms.fragment

import android.os.Bundle
import android.view.LayoutInflater
import com.meiling.common.fragment.BaseFragment
import com.meiling.oms.databinding.FragmentDis2Binding
import com.meiling.oms.viewmodel.DataFragmentViewModel

class OrderDisFragment2 : BaseFragment<DataFragmentViewModel, FragmentDis2Binding>() {

    companion object {
        fun newInstance() = OrderDisFragment2()
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun getBind(inflater: LayoutInflater): FragmentDis2Binding {
        return FragmentDis2Binding.inflate(inflater)
    }


    override fun initListener() {

    }

}