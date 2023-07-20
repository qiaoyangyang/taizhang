package com.meiling.account.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.meiling.account.R
import com.meiling.account.adapter.DefectiveDetailAdapter
import com.meiling.account.adapter.DefectivedetailttimeAdapter
import com.meiling.account.adapter.GoodProductDetailAdapter
import com.meiling.account.bean.DateSplit
import com.meiling.account.bean.DateSplitList
import com.meiling.account.data.AndIn
import com.meiling.account.databinding.FragmentGoodProductDetailBinding
import com.meiling.account.dialog.MineExitDialog
import com.meiling.account.viewmodel.MainViewModel
import com.meiling.account.widget.InputUtil
import com.meiling.account.widget.showToast
import com.meiling.common.fragment.BaseFragment
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

//不良明细
class DefectiveDetailFragment : BaseFragment<MainViewModel, FragmentGoodProductDetailBinding>(),
    DefectivedetailttimeAdapter.onSelected {
    var goodProducttimeAdapter: DefectivedetailttimeAdapter? = null
    override fun initView(savedInstanceState: Bundle?) {
        mDatabind.rvGoos?.layoutManager = LinearLayoutManager(activity)
        goodProducttimeAdapter = DefectivedetailttimeAdapter()
        mDatabind.rvGoos?.adapter = goodProducttimeAdapter
        goodProducttimeAdapter?.setEmptyView(R.layout.no_data)
        goodProducttimeAdapter?.setListener(this)
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


    var goodProductDetailAdapter: DefectiveDetailAdapter? = null
    var goodProductDetailAdapterposition = -1
    override fun onSelected(
        position: Int,
        id: Int,
        intt: Int,
        goodProductDetailAdapter: DefectiveDetailAdapter
    ) {
        this.goodProductDetailAdapterposition = intt
        this.goodProductDetailAdapter = goodProductDetailAdapter
        when (id) {
            R.id.btn_withdraw -> {
                val dialog: MineExitDialog =
                    MineExitDialog().newInstance(
                        "确认撤销入库操作",
                        "请确认要撤销当前商品的入库操作，这将影响库存统计，请谨慎操作。",
                        "取消",
                        "确认",
                        false
                    )
                dialog.setOkClickLister {
                    dialog.dismiss()
                    mViewModel.goodsSplit(
                        goodProducttimeAdapter?.getItem(intt)?.infoList?.get(
                            position
                        )?.viewId.toString()
                    )

                }
                dialog.show(activity?.supportFragmentManager)

            }
        }
    }

    var startTime = ""
    var endTime = ""

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun dateSplitList(refundType: DateSplitList) {
        if (userVisibleHint == true) {
            startTime = refundType.startTime.toString()
            endTime = refundType.endTime.toString()

            mViewModel.goodsSplit(
                DateSplit(
                    refundType.startTime.toString(),
                    refundType.endTime.toString(),
                    2
                )
            )
        }
    }


    override fun onStart() {
        super.onStart()
        EventBus.getDefault().register(this)
    }

    override fun onStop() {
        super.onStop()
        EventBus.getDefault().unregister(this)
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
        mViewModel.storageGoodsdata.onStart.observe(this) {
            showLoading("")
        }
        mViewModel.storageGoodsdata.onSuccess.observe(this) {
            dismissLoading()
            var outAndIn = AndIn(
                "",
                "",
                0

            )
            EventBus.getDefault().post(outAndIn)

        }
        mViewModel.storageGoodsdata.onError.observe(this) {
            dismissLoading()
            showToast(it.msg)
        }

    }



}


