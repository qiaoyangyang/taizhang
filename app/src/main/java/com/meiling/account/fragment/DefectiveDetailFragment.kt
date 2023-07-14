package com.meiling.account.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.meiling.account.R
import com.meiling.account.adapter.DefectivedetailttimeAdapter
import com.meiling.account.bean.DateSplit
import com.meiling.account.bean.DateSplitList
import com.meiling.account.data.AndIn
import com.meiling.account.databinding.FragmentGoodProductDetailBinding
import com.meiling.account.viewmodel.MainViewModel
import com.meiling.account.widget.InputUtil
import com.meiling.account.widget.showToast
import com.meiling.common.fragment.BaseFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

//不良明细
class DefectiveDetailFragment : BaseFragment<MainViewModel, FragmentGoodProductDetailBinding>() {
    var goodProducttimeAdapter: DefectivedetailttimeAdapter? = null
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.rvGoos?.layoutManager = LinearLayoutManager(activity)
        goodProducttimeAdapter = DefectivedetailttimeAdapter()
        mDatabind.rvGoos?.adapter = goodProducttimeAdapter
        goodProducttimeAdapter?.setEmptyView(R.layout.no_data)
    }

    override fun getBind(inflater: LayoutInflater): FragmentGoodProductDetailBinding {
        return FragmentGoodProductDetailBinding.inflate(inflater)
    }

    override fun initData() {
        super.initData()
        mViewModel.goodsSplit(
            DateSplit(
                startTime,
                endTime,
                2
            )
        )
    }

    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
    }

    var startTime = ""
    var endTime = ""

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun dateSplitList(refundType: DateSplitList) {
        startTime = refundType.startTime.toString()
        endTime = refundType.endTime.toString()
        if (userVisibleHint==true) {
            initData()
        }

    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.goodsSplitlist.onStart.observe(this) {
        }
        mViewModel.goodsSplitlist.onSuccess.observe(this) {
            goodProducttimeAdapter?.setList(it)


        }
        mViewModel.goodsSplitlist.onError.observe(this) {
            showToast(it.msg)
        }
    }

}