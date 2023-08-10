package com.meiling.account.fragment

import android.os.Bundle
import android.view.LayoutInflater
import com.meiling.account.databinding.FragmentHomeBinding
import com.meiling.account.databinding.FragmnetBasicInformationBinding
import com.meiling.account.databinding.FragmnetLoginPasswordBinding
import com.meiling.account.databinding.FragmnetWechatLoginBinding
import com.meiling.account.viewmodel.MainViewModel
import com.meiling.common.fragment.BaseFragment
//微信登录
class WechatLoginFragment : BaseFragment<MainViewModel, FragmnetWechatLoginBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
    }

    override fun getBind(inflater: LayoutInflater): FragmnetWechatLoginBinding {
        return FragmnetWechatLoginBinding.inflate(inflater)
    }
}