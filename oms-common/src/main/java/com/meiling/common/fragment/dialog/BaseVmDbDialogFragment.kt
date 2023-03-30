package com.meiling.common.fragment.dialog

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.meiling.common.BaseViewModel

abstract class BaseVmDbDialogFragment<VM : BaseViewModel, DB : ViewBinding> :
    BaseVmDialogFragment<VM>() {

    lateinit var mDatabind: DB

    override fun onCreateView(
        inflater: LayoutInflater,
        container: ViewGroup?,
        savedInstanceState: Bundle?
    ): View? {
        mDatabind = getBind(inflater)
        return mDatabind.root
    }

    abstract fun getBind(inflater: LayoutInflater): DB
}