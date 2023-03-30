package com.meiling.oms.fragment

import android.os.Bundle
import android.view.LayoutInflater
import com.meiling.common.BaseViewModel
import com.meiling.common.fragment.BaseFragment
import com.meiling.oms.databinding.FragmentMessageCenterBinding

class MessageCenterFragment : BaseFragment<BaseViewModel, FragmentMessageCenterBinding>() {


    companion object {
        fun newInstance() = MessageCenterFragment()
    }


    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun getBind(inflater: LayoutInflater): FragmentMessageCenterBinding {
        return FragmentMessageCenterBinding.inflate(inflater)
    }


}