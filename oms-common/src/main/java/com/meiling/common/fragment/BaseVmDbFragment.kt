package com.meiling.common.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import android.view.ViewGroup
import androidx.viewbinding.ViewBinding
import com.meiling.common.BaseViewModel


/**

 * @Author : xf

 * @Time : On 2021/3/3 11:04

 * @Description : BaseVmDbFragment

 */
abstract class BaseVmDbFragment<VM : BaseViewModel, DB : ViewBinding> : BaseVmFragment<VM>() {


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