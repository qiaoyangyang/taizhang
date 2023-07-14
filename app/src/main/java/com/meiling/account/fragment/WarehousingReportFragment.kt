package com.meiling.account.fragment

import android.os.Bundle
import android.view.LayoutInflater
import androidx.recyclerview.widget.LinearLayoutManager
import com.meiling.account.R
import com.meiling.account.adapter.RankingAdapter
import com.meiling.account.bean.*
import com.meiling.account.data.AndIn
import com.meiling.account.databinding.FragmentWarehousingReportBinding
import com.meiling.account.manager.LineChartManager
import com.meiling.account.manager.PieChartManager
import com.meiling.account.viewmodel.MainViewModel
import com.meiling.account.widget.InputUtil
import com.meiling.common.fragment.BaseFragment
import com.meiling.common.utils.LocalJsonAnalyzeUtil
import org.greenrobot.eventbus.EventBus
import org.greenrobot.eventbus.Subscribe
import org.greenrobot.eventbus.ThreadMode

class WarehousingReportFragment :
    BaseFragment<MainViewModel, FragmentWarehousingReportBinding>() {
    private var lineChartBean: LineChartBean? = null
    private var incomeBeanList //个人收益
            : List<IncomeBean>? = null
    private var shanghai //沪市指数
            : List<CompositeIndexBean>? = null
    private var lineChartManager1: LineChartManager? = null
    private var rankingAdapter: RankingAdapter? = null
    override fun initView(savedInstanceState: Bundle?) {
        lineChartManager1 = LineChartManager(mDatabind!!.lineChart)
    }

    override fun getBind(inflater: LayoutInflater): FragmentWarehousingReportBinding {
        return FragmentWarehousingReportBinding.inflate(inflater)
    }

    override fun initData() {

        mDatabind!!.rvRanking.layoutManager = LinearLayoutManager(activity)
        rankingAdapter = RankingAdapter()

        mDatabind!!.rvRanking.adapter = rankingAdapter

    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun data(efundType: AndIn) {
        if (userVisibleHint == true) {
            var statistics = Statistics(
                efundType.startWarehouseDate, efundType.endWarehouseDate,
                userStoreList()!!.viewId!!
            )
            mViewModel!!.ranking(
                statistics
            )
            mViewModel!!.periodTime(
                statistics
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

    private var startTime: String? = ""
    private var endTime: String? = ""

    @Subscribe(threadMode = ThreadMode.MAIN)
    fun dateSplitList(dateSplitList: DateSplitList) {
        startTime = dateSplitList.startTime
        endTime = dateSplitList.endTime
        if (userVisibleHint) {
            initData()
        }
    }

    override fun createObserver() {
        super.createObserver()
        mViewModel.periodTimedata.onStart.observe(this) {

        }
        mViewModel.periodTimedata.onSuccess.observe(this) {
            if (it.size != 0) {
                incomeBeanList = InputUtil.incomeBeanList(it)
                shanghai = InputUtil.shanghai(it)
                lineChartManager1!!.showLineChart(
                    incomeBeanList,
                    "良品入库",
                    resources.getColor(R.color.Blue), InputUtil.getmax(it)
                )
                lineChartManager1!!.addLine(shanghai, "不良品入库", resources.getColor(R.color.red))
                //设置曲线填充色 以及 MarkerView
                val drawable = resources.getDrawable(R.drawable.fade_blue)
                val drawable1 = resources.getDrawable(R.drawable.fade_blue1)
                lineChartManager1!!.setChartFillDrawable(drawable)
                lineChartManager1!!.setChartFillDrawable1(drawable1)
                lineChartManager1!!.setMarkerView(activity)
            }


        }
        mViewModel.periodTimedata.onError.observe(this) {

        }
        mViewModel!!.rankingdata.onStart.observe(this) {


        }
        mViewModel!!.rankingdata.onSuccess.observe(this) {
            rankingAdapter!!.setList(it)
            //饼状图管理类
            val pieChartManager1 = PieChartManager(mDatabind!!.pieChart1)
            if (pieChartManager1 != null) {
                pieChartManager1.setPieChart(InputUtil.date(it), InputUtil.colors(it))
            }
        }
    }
}