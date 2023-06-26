package com.meiling.account.fragment

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.meiling.account.R
import com.meiling.account.adapter.GoodProducttimeAdapter
import com.meiling.account.databinding.FragmentGoodProductDetailBinding
import com.meiling.account.dialog.MineExitDialog
import com.meiling.account.viewmodel.MainViewModel
import com.meiling.account.widget.InputUtil
import com.meiling.common.fragment.BaseFragment

//良品明细
class GoodProductDetailFragment : BaseFragment<MainViewModel, FragmentGoodProductDetailBinding>() {
    var goodProducttimeAdapter: GoodProducttimeAdapter? = null
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.rvGoos?.layoutManager = LinearLayoutManager(activity)
        goodProducttimeAdapter = GoodProducttimeAdapter()
        mDatabind.rvGoos?.adapter = goodProducttimeAdapter
        goodProducttimeAdapter?.setList(InputUtil.getyouhuiString())
        goodProducttimeAdapter?.addChildClickViewIds(R.id.btn_withdraw)
        goodProducttimeAdapter?.setOnItemChildClickListener { adapter, view, position ->
            when (view.id) {
                R.id.btn_withdraw -> {
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

    override fun getBind(inflater: LayoutInflater): FragmentGoodProductDetailBinding {
        return FragmentGoodProductDetailBinding.inflate(inflater)
    }
}