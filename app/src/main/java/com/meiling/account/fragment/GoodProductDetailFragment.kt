package com.meiling.account.fragment

import android.os.Bundle
import android.util.Log
import android.view.LayoutInflater
import android.view.View
import androidx.recyclerview.widget.LinearLayoutManager
import com.chad.library.adapter.base.BaseQuickAdapter
import com.chad.library.adapter.base.listener.OnItemChildClickListener
import com.meiling.account.R
import com.meiling.account.adapter.GoodProductDetailAdapter
import com.meiling.account.adapter.GoodProducttimeAdapter
import com.meiling.account.adapter.OnListener
import com.meiling.account.bean.DateSplit
import com.meiling.account.bean.DateSplitList
import com.meiling.account.data.AndIn
import com.meiling.account.databinding.FragmentGoodProductDetailBinding
import com.meiling.account.dialog.MineExitDialog
import com.meiling.account.viewmodel.MainViewModel
import com.meiling.account.widget.showToast
import com.meiling.common.fragment.BaseFragment
import com.meiling.common.utils.PreventUtils
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

//良品明细
class GoodProductDetailFragment : BaseFragment<MainViewModel, FragmentGoodProductDetailBinding>(),
    OnItemChildClickListener, OnListener {

    var goodProducttimeAdapter: GoodProducttimeAdapter? = null

    override fun initView(savedInstanceState: Bundle?) {
        var manager = mDatabind.rvGoos.layoutManager as LinearLayoutManager
        mDatabind.rvGoos?.layoutManager = manager

        goodProducttimeAdapter = GoodProducttimeAdapter()
        mDatabind.rvGoos?.adapter = goodProducttimeAdapter

        goodProducttimeAdapter?.setListener(this)
        goodProducttimeAdapter?.setOnItemChildClickListener(this)

        goodProducttimeAdapter?.setEmptyView(R.layout.no_data)

    }

    override fun initData() {


    }

    override fun getBind(inflater: LayoutInflater): FragmentGoodProductDetailBinding {
        return FragmentGoodProductDetailBinding.inflate(inflater)
    }

    override fun onItemChildClick(adapter: BaseQuickAdapter<*, *>, view: View, position: Int) {

    }

    var goodProductDetailAdapter: GoodProductDetailAdapter? = null
    var goodProductDetailAdapterposition = -1
    override fun onSelected(
        position: Int,
        id: Int,
        intt: Int,
        goodProductDetailAdapter: GoodProductDetailAdapter
    ) {
        this.goodProductDetailAdapterposition = intt
        this.goodProductDetailAdapter = goodProductDetailAdapter
        if (!PreventUtils.isFastClick()) {
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
                    1, userStoreList()!!.viewId!!
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
            Log.d("yjk", "-----1---------")
//            if (goodProductDetailAdapter != null) {
//                Log.d("yjk","-----2--------")
//                goodProductDetailAdapter?.data?.removeAt(goodProductDetailAdapterposition)
//                goodProductDetailAdapter?.notifyDataSetChanged()
//
//              //  ( activity?.supportFragmentManager as RecordsCenterFragment).initData()
//
//            } else {
//                Log.d("yjk","----3--------")
//            }


        }
        mViewModel.storageGoodsdata.onError.observe(this) {
            dismissLoading()
            showToast(it.msg)
        }

    }


}

