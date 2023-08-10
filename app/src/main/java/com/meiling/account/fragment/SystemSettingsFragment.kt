package com.meiling.account.fragment

import android.os.Bundle
import android.view.LayoutInflater
import com.meiling.account.databinding.FragmentHomeBinding
import com.meiling.account.databinding.FragmnetBasicInformationBinding
import com.meiling.account.databinding.FragmnetLoginPasswordBinding
import com.meiling.account.databinding.FragmnetSystemSettingsBinding
import com.meiling.account.databinding.FragmnetWechatLoginBinding
import com.meiling.account.viewmodel.MainViewModel
import com.meiling.common.fragment.BaseFragment
//系统设置
class SystemSettingsFragment : BaseFragment<MainViewModel, FragmnetSystemSettingsBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun getBind(inflater: LayoutInflater): FragmnetSystemSettingsBinding {
        return FragmnetSystemSettingsBinding.inflate(inflater)
    }
}