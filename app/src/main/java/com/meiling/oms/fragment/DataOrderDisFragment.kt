package com.meiling.oms.fragment

import android.os.Bundle
import android.view.LayoutInflater
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.fragment.BaseFragment
import com.meiling.oms.R
import com.meiling.oms.databinding.FragmentDataDisBinding
import com.meiling.oms.viewmodel.FindFollowViewModel

class DataOrderDisFragment : BaseFragment<FindFollowViewModel, FragmentDataDisBinding>() {
    lateinit var dataDisAdapter: BaseQuickAdapter<String, BaseViewHolder>

    companion object {
        fun newInstance() = DataOrderDisFragment()
    }

    override fun initView(savedInstanceState: Bundle?) {
        dataDisAdapter =
            object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_data_dis) {
                override fun convert(holder: BaseViewHolder, item: String) {
                    holder.setText(R.id.txt_data_platform, item)
                }

            }
        var list = ArrayList<String>()
        list.add("美团闪购")
        list.add("饿了么百货")
        list.add("美团外卖")
        list.add("饿了么外卖")
        list.add("口碑")
        mDatabind.rvDataDis.adapter = dataDisAdapter
        dataDisAdapter.setList(list)
    }

    override fun getBind(inflater: LayoutInflater): FragmentDataDisBinding {
        return FragmentDataDisBinding.inflate(inflater)
    }


    override fun initListener() {

    }

}