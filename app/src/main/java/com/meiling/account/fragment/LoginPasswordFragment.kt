package com.meiling.account.fragment

import android.os.Bundle
import android.view.LayoutInflater
import com.meiling.account.databinding.FragmentHomeBinding
import com.meiling.account.databinding.FragmnetBasicInformationBinding
import com.meiling.account.databinding.FragmnetLoginPasswordBinding
import com.meiling.account.viewmodel.MainViewModel
import com.meiling.common.fragment.BaseFragment

class LoginPasswordFragment : BaseFragment<MainViewModel, FragmnetLoginPasswordBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun getBind(inflater: LayoutInflater): FragmnetLoginPasswordBinding {
        return FragmnetLoginPasswordBinding.inflate(inflater)
    }
}