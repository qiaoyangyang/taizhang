package com.meiling.common.activity

import android.os.Bundle
import android.view.LayoutInflater
import androidx.viewbinding.ViewBinding
import com.meiling.common.BaseViewModel


abstract class BaseVmDbActivity<VM : BaseViewModel, DB : ViewBinding> : BaseVmActivity<VM>() {


    lateinit var mDatabind: DB

    override fun onCreate(savedInstanceState: Bundle?) {
        userDataBinding(true)
        super.onCreate(savedInstanceState)
    }


    override fun initDataBind() {
        mDatabind = getBind(layoutInflater)
        setContentView(mDatabind.root)
    }

    abstract fun getBind(layoutInflater: LayoutInflater): DB


    override fun isLightMode(): Boolean {
        return false
    }
}
