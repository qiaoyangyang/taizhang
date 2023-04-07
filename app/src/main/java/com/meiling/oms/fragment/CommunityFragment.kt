package com.meiling.oms.fragment

import android.os.Bundle
import android.view.LayoutInflater
import com.meiling.common.fragment.BaseFragment
import com.meiling.oms.databinding.FragmentCommunityBinding
import com.meiling.oms.viewmodel.BaseOrderFragmentViewModel

class CommunityFragment : BaseFragment<BaseOrderFragmentViewModel, FragmentCommunityBinding>() {



    companion object {
        fun newInstance() = CommunityFragment()
    }


    override fun initView(savedInstanceState: Bundle?) {


    }

    override fun getBind(inflater: LayoutInflater): FragmentCommunityBinding {
        return FragmentCommunityBinding.inflate(inflater)
    }

}