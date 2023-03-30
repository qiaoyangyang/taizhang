package com.meiling.oms.fragment

import android.os.Bundle
import android.view.LayoutInflater
import com.meiling.common.fragment.BaseFragment
import com.meiling.oms.databinding.FragmentDataBinding
import com.meiling.oms.viewmodel.MessageViewModel

class DataFragment : BaseFragment<MessageViewModel, FragmentDataBinding>() {

    companion object {
        fun newInstance() = DataFragment()
    }


    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun getBind(inflater: LayoutInflater): FragmentDataBinding {
        return FragmentDataBinding.inflate(inflater)
    }


    override fun initListener() {

    }

}