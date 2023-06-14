package com.meiling.account.fragment

import android.os.Bundle
import android.view.LayoutInflater
import com.meiling.common.fragment.BaseFragment
import com.meiling.account.databinding.FragmentCommunityBinding
import com.meiling.account.viewmodel.BaseOrderFragmentViewModel

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