package com.meiling.oms.fragment

import android.os.Bundle
import android.view.LayoutInflater
import com.meiling.common.fragment.BaseFragment
import com.meiling.oms.databinding.FragmentSelectedBinding
import com.meiling.oms.viewmodel.SelectedViewModel

class SelectedFragment : BaseFragment<SelectedViewModel, FragmentSelectedBinding>() {

    companion object {
        fun newInstance() = SelectedFragment()
    }

    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun getBind(inflater: LayoutInflater): FragmentSelectedBinding {
        return FragmentSelectedBinding.inflate(inflater)
    }


}