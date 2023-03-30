package com.meiling.common.activity

import android.os.Bundle
import androidx.viewbinding.ViewBinding
import com.meiling.common.BaseViewModel


abstract class BaseActivity<VM : BaseViewModel, DB : ViewBinding> : BaseVmDbActivity<VM, DB>() {

    abstract override fun initView(savedInstanceState: Bundle?)

    override fun createObserver() {}

    override fun initListener() {}

}
