package com.meiling.account.manager;

import android.graphics.Color;
import android.graphics.Typeface;

import com.github.mikephil.charting.animation.Easing;
import com.github.mikephil.charting.charts.PieChart;
import com.github.mikephil.charting.components.Description;
import com.github.mikephil.charting.components.Legend;
import com.github.mikephil.charting.data.PieData;
import com.github.mikephil.charting.data.PieDataSet;
import com.github.mikephil.charting.data.PieEntry;
import com.github.mikephil.charting.formatter.PercentFormatter;
import com.github.mikephil.charting.utils.ColorTemplate;

import java.util.ArrayList;
import java.util.List;

/**
 * Created by loptop on 2017/6/2.
 */
public class PieChartManager {
    PieChart pieChart;


    public PieChartManager(PieChart mPieChart) {
        this.pieChart = mPieChart;
        initChart();
    }

    private void initChart() {
//        pieChart.setHoleRadius(30f);//半径    
//        pieChart.setTransparentCircleRadius(40f);// 半透明圈    
//        pieChart.setDrawCenterText(true);//饼状图中间可以添加文字    
//        pieChart.setDrawHoleEnabled(true);
//        pieChart.setRotationAngle(50);// 初始旋转角度    
//        pieChart.setRotationEnabled(true);// 可以手动旋转    
//        pieChart.setUsePercentValues(true);//显示成百分比  

        pieChart.setUsePercentValues(true);
        pieChart.setRotationEnabled(false);
        pieChart.getDescription().setEnabled(false);
       // pieChart.setExtraOffsets(5, 10, 5, 5);

     //   pieChart.setDragDecelerationFrictionCoef(0.95f);


        pieChart.setCenterText("");


        pieChart.setDrawHoleEnabled(true);
        pieChart.setHoleColor(Color.WHITE);

        pieChart.setTransparentCircleColor(Color.RED);
        pieChart.setTransparentCircleAlpha(15);

        pieChart.setHoleRadius(28f);
        pieChart.setTransparentCircleRadius(31f);

        pieChart.setDrawCenterText(true);

        pieChart.setRotationAngle(0);
        // enable rotation of the pieChart by touch
        pieChart.setRotationEnabled(true);
        pieChart.setHighlightPerTapEnabled(true);


        // add a selection listener







        Legend l = pieChart.getLegend();
        l.setVerticalAlignment(Legend.LegendVerticalAlignment.TOP);
        l.setHorizontalAlignment(Legend.LegendHorizontalAlignment.RIGHT);
        l.setOrientation(Legend.LegendOrientation.VERTICAL);
        l.setDrawInside(false);
        l.setEnabled(false);

        pieChart.animateXY(1000, 1000);//设置动画  
    }

    /**
     * 设置饼状图
     *饼状图分类的名字
     * @param date   数值
     * @param colors 颜色集合
     */
    public void setPieChart( List<Float> date, List<Integer> colors) {
        List<PieEntry> yValue = new ArrayList<>();
        for (int i = 0; i < date.size(); i++) {
            PieEntry entry = new PieEntry(date.get(i));
            yValue.add(entry);
        }
        PieDataSet dataSet = new PieDataSet(yValue, "Election Results");
        dataSet.setSliceSpace(3f);
        dataSet.setSelectionShift(5f);
        dataSet.setColors(colors);
       // colors.add(ColorTemplate.getHoloBlue());


        dataSet.setValueLinePart1OffsetPercentage(85.f);
        dataSet.setValueLinePart1Length(0.5f);
        dataSet.setValueLinePart2Length(0.8f);

        //dataSet.setXValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        dataSet.setYValuePosition(PieDataSet.ValuePosition.OUTSIDE_SLICE);
        PieData data = new PieData(dataSet);
        data.setValueFormatter(new PercentFormatter());
        data.setValueTextSize(11f);

        data.setValueTextColor(Color.BLACK);
       // data.setValueTypeface(tf);
        pieChart.setData(data);
        pieChart.invalidate(); // refresh
    }

    /**
     * 设实心饼状图
     *
     * @param name   饼状图分类的名字
     * @param date   数值
     * @param colors 颜色集合
     */
    public void setSolidPieChart(List<String> name, List<Float> date, List<Integer> colors) {

        pieChart.setHoleRadius(0);//实心圆   
        pieChart.setTransparentCircleRadius(0);// 半透明圈  
        pieChart.setDrawCenterText(false);//饼状图中间不可以添加文字  

        List<PieEntry> yValue = new ArrayList<>();
        for (int i = 0; i < date.size(); i++) {
            PieEntry entry = new PieEntry(date.get(i), name.get(i));
            yValue.add(entry);
        }
        PieDataSet set = new PieDataSet(yValue, "");
        set.setDrawValues(true);
        set.setValueTextSize(12);
        set.setColors(colors);

        set.setValueTextColor(Color.WHITE);
        PieData data = new PieData(set);
        pieChart.setData(data);
        pieChart.invalidate(); // refresh
    }

    /**
     * 设置饼状图中间的描述内容
     *
     * @param str
     */
    public void setCenterDescription(String str, int color) {
        pieChart.setCenterText(str);
        pieChart.setCenterTextColor(color);
        pieChart.setCenterTextSize(12);
        pieChart.invalidate();
    }

    /**
     * 设置描述信息
     *
     * @param str
     */
    public void setDescription(String str) {
        Description description = new Description();
        description.setText(str);
        pieChart.setDescription(description);
        pieChart.invalidate();
    }
}
