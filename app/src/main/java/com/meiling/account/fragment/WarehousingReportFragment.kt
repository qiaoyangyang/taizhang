package com.meiling.account.fragment

import android.os.Bundle
import android.view.LayoutInflater
import com.meiling.account.databinding.FragmentWarehousingReportBinding
import com.meiling.account.viewmodel.MainViewModel
import com.meiling.common.fragment.BaseFragment

//入库报表
class WarehousingReportFragment  : BaseFragment<MainViewModel, FragmentWarehousingReportBinding>() {
    override fun initView(savedInstanceState: Bundle?) {

    }

    override fun getBind(inflater: LayoutInflater): FragmentWarehousingReportBinding {
        return FragmentWarehousingReportBinding.inflate(inflater)
    }
}