package com.meiling.oms.fragment

import android.os.Bundle
import android.view.LayoutInflater
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.fragment.BaseFragment
import com.meiling.oms.R
import com.meiling.oms.databinding.FragmentDataChannelBinding
import com.meiling.oms.viewmodel.DataFragmentViewModel

class DataChannelFragment : BaseFragment<DataFragmentViewModel, FragmentDataChannelBinding>() {

    lateinit var dataChannelAdapter: BaseQuickAdapter<String, BaseViewHolder>


    companion object {
        fun newInstance() = DataChannelFragment()
    }

    override fun initView(savedInstanceState: Bundle?) {
        dataChannelAdapter =
            object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_data_shop) {
                override fun convert(holder: BaseViewHolder, item: String) {
                    holder.setText(R.id.txt_data_time, item)
                }

            }
        var list = ArrayList<String>()
        list.add("美团闪购")
        list.add("饿了么百货")
        list.add("美团外卖")
        list.add("饿了么外卖")
        list.add("口碑")
        mDatabind.rvDataChannel.adapter = dataChannelAdapter
        mDatabind.rvDataChannelHistory.adapter = dataChannelAdapter
        dataChannelAdapter.setList(list)
    }

    override fun getBind(inflater: LayoutInflater): FragmentDataChannelBinding {
        return FragmentDataChannelBinding.inflate(inflater)
    }


    override fun initListener() {

    }

}