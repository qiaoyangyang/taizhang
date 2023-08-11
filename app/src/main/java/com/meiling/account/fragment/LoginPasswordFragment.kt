package com.meiling.account.fragment

import android.os.Bundle
import android.view.LayoutInflater
import com.meiling.account.databinding.FragmentHomeBinding
import com.meiling.account.databinding.FragmnetBasicInformationBinding
import com.meiling.account.databinding.FragmnetLoginPasswordBinding
import com.meiling.account.dialog.ChangePasswordDialog
import com.meiling.account.viewmodel.MainViewModel
import com.meiling.common.fragment.BaseFragment
//修改密码
class LoginPasswordFragment : BaseFragment<MainViewModel, FragmnetLoginPasswordBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.tvChangePassword.setOnClickListener {
            var changePasswordDialog = ChangePasswordDialog()
            changePasswordDialog.show(activity?.supportFragmentManager)

        }
    }

    override fun getBind(inflater: LayoutInflater): FragmnetLoginPasswordBinding {
        return FragmnetLoginPasswordBinding.inflate(inflater)
    }
}