package com.meiling.account.fragment

import android.os.Bundle
import android.view.LayoutInflater
import com.meiling.account.databinding.FragmentHomeBinding
import com.meiling.account.databinding.FragmnetBasicInformationBinding
import com.meiling.account.dialog.ModifyingAUserNameDialog
import com.meiling.account.viewmodel.MainViewModel
import com.meiling.common.fragment.BaseFragment

//修改基础信息
class BasicInformationFragment : BaseFragment<MainViewModel, FragmnetBasicInformationBinding>() {
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.tvRename.setOnClickListener {
            var modifyingAUserNameDialog = ModifyingAUserNameDialog()
            modifyingAUserNameDialog.show(activity?.supportFragmentManager)

        }
    }

    override fun getBind(inflater: LayoutInflater): FragmnetBasicInformationBinding {
        return FragmnetBasicInformationBinding.inflate(inflater)
    }
}