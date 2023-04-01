package com.meiling.oms.fragment

import android.os.Bundle
import android.view.LayoutInflater
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.viewholder.BaseViewHolder
import com.meiling.common.fragment.BaseFragment
import com.meiling.oms.R
import com.meiling.oms.databinding.FragmentDataShopBinding
import com.meiling.oms.viewmodel.FindFollowViewModel

class DataShopFragment : BaseFragment<FindFollowViewModel, FragmentDataShopBinding>() {

    companion object {
        fun newInstance() = DataShopFragment()
    }

    lateinit var dataShopAdapter: BaseQuickAdapter<String, BaseViewHolder>

    override fun initView(savedInstanceState: Bundle?) {

        dataShopAdapter =
            object : BaseQuickAdapter<String, BaseViewHolder>(R.layout.item_data_shop) {
                override fun convert(holder: BaseViewHolder, item: String) {
                    holder.setText(R.id.txt_data_time, item)
                }

            }
        var list = ArrayList<String>()
        list.add("12:00～至今")
        list.add("11：00～12：00")
        list.add("11：00～12：00")
        list.add("11：00～12：00")
        mDatabind.rvDataShop.adapter = dataShopAdapter
        dataShopAdapter.setList(list)

    }

    override fun getBind(inflater: LayoutInflater): FragmentDataShopBinding {
        return FragmentDataShopBinding.inflate(inflater)
    }


    override fun initListener() {

    }

}