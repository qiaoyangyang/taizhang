package com.meiling.account.fragment

import android.os.Bundle
import android.view.LayoutInflater
import com.meiling.account.databinding.FragmentHomeBinding
import com.meiling.account.databinding.FragmnetBasicInformationBinding
import com.meiling.account.databinding.FragmnetLoginPasswordBinding
import com.meiling.account.databinding.FragmnetSwitchStoresBinding
import com.meiling.account.databinding.FragmnetWechatLoginBinding
import com.meiling.account.viewmodel.MainViewModel
import com.meiling.common.fragment.BaseFragment
//切换门店
class SwitchStoresFragment : BaseFragment<MainViewModel, FragmnetSwitchStoresBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun getBind(inflater: LayoutInflater): FragmnetSwitchStoresBinding {
        return FragmnetSwitchStoresBinding.inflate(inflater)
    }
}