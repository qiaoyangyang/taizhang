package com.meiling.account.fragment

import android.os.Bundle
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.meiling.account.R
import com.meiling.account.adapter.GoodProducttimeAdapter
import com.meiling.account.adapter.OnListener
import com.meiling.account.databinding.FragmentGoodProductDetailBinding
import com.meiling.account.dialog.MineExitDialog
import com.meiling.account.viewmodel.MainViewModel
import com.meiling.account.widget.InputUtil
import com.meiling.common.fragment.BaseFragment

//良品明细
class GoodProductDetailFragment : BaseFragment<MainViewModel, FragmentGoodProductDetailBinding>(),
    OnItemChildClickListener, OnListener {
    var goodProducttimeAdapter: GoodProducttimeAdapter? = null
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.rvGoos?.layoutManager = LinearLayoutManager(activity)
        goodProducttimeAdapter = GoodProducttimeAdapter()
        mDatabind.rvGoos?.adapter = goodProducttimeAdapter
        goodProducttimeAdapter?.setList(InputUtil.getyouhuiString())
        goodProducttimeAdapter?.setListener(this)
        goodProducttimeAdapter?.setOnItemChildClickListener(this)


    }

    override fun getBind(inflater: LayoutInflater): FragmentGoodProductDetailBinding {
        return FragmentGoodProductDetailBinding.inflate(inflater)
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {

    }

    override fun onSelected(position: Int, id: Int) {
        when(id){
            R.id.btn_withdraw->{
                val dialog: MineExitDialog =
                    MineExitDialog().newInstance("确认撤销入库操作", "请确认要撤销当前商品的入库操作，这将影响库存统计，请谨慎操作。", "取消", "确认", false)
                dialog.setOkClickLister {

                    // mViewModel.setUmengToken()

                }
                dialog.show(childFragmentManager)

            }
        }
    }
}