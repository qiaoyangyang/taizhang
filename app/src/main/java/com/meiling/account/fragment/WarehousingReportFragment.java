package com.meiling.account.fragment;

import android.graphics.Color;
import android.graphics.drawable.Drawable;
import android.os.Bundle;
import android.util.Log;
import android.view.LayoutInflater;

import androidx.annotation.NonNull;
import androidx.annotation.Nullable;
import androidx.recyclerview.widget.LinearLayoutManager;

import com.meiling.account.R;
import com.meiling.account.adapter.RankingAdapter;
import com.meiling.account.bean.CompositeIndexBean;
import com.meiling.account.bean.IncomeBean;
import com.meiling.account.bean.LineChartBean;
import com.meiling.account.data.AndIn;
import com.meiling.account.databinding.FragmentWarehousingReportBinding;
import com.meiling.account.manager.PieChartManager;
import com.meiling.account.viewmodel.MainViewModel;
import com.meiling.account.widget.InputUtil;
import com.meiling.common.fragment.BaseFragment;
import com.meiling.account.manager.LineChartManager;
import com.meiling.common.utils.LocalJsonAnalyzeUtil;
import com.meiling.common.utils.RecyclerViewDivider;

import org.greenrobot.eventbus.EventBus;
import org.greenrobot.eventbus.Subscribe;
import org.greenrobot.eventbus.ThreadMode;

import java.util.ArrayList;
import java.util.List;

public class WarehousingReportFragment extends BaseFragment<MainViewModel, FragmentWarehousingReportBinding> {
    private LineChartBean lineChartBean;
    private List<IncomeBean> incomeBeanList;//个人收益
    private List<CompositeIndexBean> shanghai;//沪市指数

    private LineChartManager lineChartManager1;

    private RankingAdapter rankingAdapter;

    @Override
    public void initView(@Nullable Bundle savedInstanceState) {
        lineChartManager1 = new LineChartManager(mDatabind.lineChart);


    }

    @NonNull
    @Override
    public FragmentWarehousingReportBinding getBind(@NonNull LayoutInflater inflater) {
        return FragmentWarehousingReportBinding.inflate(inflater);
    }

    @Override
    public void initData() {
        //获取数据
        lineChartBean = LocalJsonAnalyzeUtil.JsonToObject(getContext(), "line_chart.json", LineChartBean.class);
        incomeBeanList = lineChartBean.getGRID0().getResult().getClientAccumulativeRate();

        shanghai = lineChartBean.getGRID0().getResult().getCompositeIndexShanghai();

        //展示图表
        lineChartManager1.showLineChart(incomeBeanList, "良品入库", getResources().getColor(R.color.Blue));
        lineChartManager1.addLine(shanghai, "不良品入库", getResources().getColor(R.color.red));
        //设置曲线填充色 以及 MarkerView
        Drawable drawable = getResources().getDrawable(R.drawable.fade_blue);
        Drawable drawable1 = getResources().getDrawable(R.drawable.fade_blue1);

        lineChartManager1.setChartFillDrawable(drawable);
        lineChartManager1.setChartFillDrawable1(drawable1);
        lineChartManager1.setMarkerView(getActivity());


        List<String> names = new ArrayList<>(); //每个模块的内容描述
        names.add("模块一");
        names.add("模块二");
        names.add("模块三");
        names.add("模块四");
        names.add("模块五");
        names.add("模块六");
        names.add("模块七");
        names.add("模块八");
        names.add("模块九");
        names.add("模块十");


        //饼状图管理类
        PieChartManager pieChartManager1 = new PieChartManager(mDatabind.pieChart1);
        pieChartManager1.setPieChart(names, InputUtil.date(), InputUtil.colors());

        mDatabind.rvRanking.setLayoutManager(new LinearLayoutManager(getActivity()));


        rankingAdapter = new RankingAdapter();

        mDatabind.rvRanking.setAdapter(rankingAdapter);
        rankingAdapter.setList(InputUtil.setRanking());


    }

    @Subscribe(threadMode = ThreadMode.MAIN)
    public void data(AndIn efundType) {
        Log.d("yjk", "data:  1 " + efundType.getVoucherType());
    }

    @Override
    public void onStart() {
        super.onStart();
        EventBus.getDefault().register(this);
    }

    @Override
    public void onStop() {
        super.onStop();
        EventBus.getDefault().unregister(this);
    }


}
