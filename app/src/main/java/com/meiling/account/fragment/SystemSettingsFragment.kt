package com.meiling.account.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import com.meiling.account.BuildConfig
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

    override fun initData() {
        super.initData()
        mViewModel.appUpdate()
    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.appUpdate.onStart.observe(this){

        }
        mViewModel.appUpdate.onSuccess.observe(this){
            if (it!!.updateId != null && it.downloadUrl != null) {
//                            updateInstall 1 强制更新
                val i: Int = it.versionCode!!.toInt()
//                            是否更新
                if (i > BuildConfig.VERSION_CODE) {
                    mDatabind.tvSoftwareVersion.text="更新"
                    Log.d("yjk", "createObserver: -1-")

                } else {
                    mDatabind.tvSoftwareVersion.text="最新版本"
                    Log.d("yjk", "createObserver: -2-")
                }
                mDatabind.tvSoftwareVersions.text="V"+BuildConfig.VERSION_NAME.toString()
                mDatabind.tvLastUpdateSoftware.text=it.updateTime.toString()+"已是最新版本，无需更新"
//
            }
        }
        mViewModel.appUpdate.onError.observe(this){

        }
    }
}