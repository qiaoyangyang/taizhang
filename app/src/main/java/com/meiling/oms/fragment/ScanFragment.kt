package com.meiling.oms.fragment

import android.os.Bundle
import android.view.LayoutInflater
import com.gyf.immersionbar.ImmersionBar
import com.meiling.common.fragment.BaseFragment
import com.meiling.oms.databinding.FragmentScanBinding
import com.meiling.oms.viewmodel.FindViewModel

class  ScanFragment : BaseFragment<FindViewModel, FragmentScanBinding>() {

    companion object {
        fun newInstance() = ScanFragment()
    }


    override fun initView(savedInstanceState: Bundle?) {
        ImmersionBar.with(this).init()
        ImmersionBar.setTitleBar(this,mDatabind.relTitle)
    }

    override fun getBind(inflater: LayoutInflater): FragmentScanBinding {
        return FragmentScanBinding.inflate(inflater)
    }
    override fun initListener() {

    }

}