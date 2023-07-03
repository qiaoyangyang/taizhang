package com.meiling.common.activity

import android.os.Bundle
import android.util.Log
import android.view.View
import androidx.annotation.StringRes
import androidx.appcompat.widget.Toolbar
import androidx.viewbinding.ViewBinding
import com.gyf.immersionbar.ImmersionBar
import com.hjq.bar.TitleBar
import com.meiling.common.BaseViewModel
import com.meiling.common.R
import com.meiling.common.action.TitleBarAction


abstract class BaseActivity<VM : BaseViewModel, DB : ViewBinding> : BaseVmDbActivity<VM, DB>(){



    abstract override fun initView(savedInstanceState: Bundle?)

    override fun createObserver() {}


    override fun initDataBind() {
        super.initDataBind()

    }
    override fun initListener() {
        super.initListener()
    }



}
