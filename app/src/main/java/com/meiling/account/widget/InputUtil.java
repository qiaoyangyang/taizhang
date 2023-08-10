package com.meiling.account.widget;

import android.graphics.Color;
import android.text.TextUtils;

import com.meiling.account.bean.CompositeIndexBean;
import com.meiling.account.bean.Goods;
import com.meiling.account.bean.IncomeBean;
import com.meiling.account.bean.PeriodTimeItem;
import com.meiling.account.bean.Ranking;
import com.meiling.account.bean.SetBaen;
import com.meiling.account.bean.Spezifikation;

import org.jetbrains.annotations.NotNull;
import org.jetbrains.annotations.Nullable;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.Collection;
import java.util.List;

public class InputUtil {

    @NotNull
    public static List<String> getyouhuiString() {
        List<String> strings = new ArrayList<>();
        strings.add("1");
        strings.add("2");
        strings.add("3");
        strings.add("4");
        strings.add("5");
        strings.add("6");
        strings.add("7");
        strings.add("8");
        strings.add("9");
        strings.add(".");
        strings.add("0");
        strings.add("20");
        return strings;
    }
    public static List<Goods> getisExpen(){
        List<Goods>goods=new ArrayList<>();
        for (int i = 0; i < 100; i++) {
            Goods goods1=new Goods();
            goods.add(goods1);

        }
        return goods;
    }


    @NotNull
    public static List<Integer> colors(ArrayList<Ranking> rankings){
        List<Integer> colors = new ArrayList<>(); //每个模块的颜色
        for (int i = 0; i < rankings.size(); i++) {
            colors.add(Color.parseColor(rankings.get(i).getRankColour()));
        }


        return colors;
    }

    @NotNull
    public static List<Float> date(ArrayList<Ranking> rankings){
        List<Float> date = new ArrayList<>(); //shuju
        for (int i = 0; i < rankings.size(); i++) {
            date.add(rankings.get(i).getGoodsNumber());

        }

        return date;
    }


    @Nullable
    public static List<IncomeBean> incomeBeanList(@Nullable ArrayList<PeriodTimeItem> it) {
        List<IncomeBean> incomeBeans=new ArrayList<>();
        for (int i = 0; i < it.size(); i++) {
            IncomeBean incomeBean=new IncomeBean();
            incomeBean.setTradeDate(it.get(i).getDateValue());
            incomeBean.setValue(it.get(i).getGoodProductNumber());
            incomeBeans.add(incomeBean);
        }
        return incomeBeans;
    }

    @Nullable
    public static List<CompositeIndexBean> shanghai(@Nullable ArrayList<PeriodTimeItem> it) {
        List<CompositeIndexBean> compositeIndexBeanList=new ArrayList<>();
        for (int i = 0; i < it.size(); i++) {
            CompositeIndexBean compositeIndexBean=new CompositeIndexBean();
            compositeIndexBean.setTradeDate(it.get(i).getDateValue());
            compositeIndexBean.setRate(it.get(i).getDefectiveProductNumber());
            compositeIndexBeanList.add(compositeIndexBean);
        }
        return compositeIndexBeanList;

    }
    public static Integer getmax(ArrayList<PeriodTimeItem> it){
        //it.stream().map(item->item.getGoodProductNumber()).max(it.)
        int maxField = 0;
        int goodProductNumber = 0;
        int defectiveProductNumber = 0;
        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            goodProductNumber = (int) it.stream()
                    .mapToDouble(PeriodTimeItem::getGoodProductNumber) // 将对象映射为字段的整数值
                    .max()
                    .orElse(0);
        }

        if (android.os.Build.VERSION.SDK_INT >= android.os.Build.VERSION_CODES.N) {
            defectiveProductNumber = (int) it.stream()
                    .mapToDouble(PeriodTimeItem::getDefectiveProductNumber) // 将对象映射为字段的整数值
                    .max()
                    .orElse(0);
        }

        if (goodProductNumber>defectiveProductNumber){
            maxField=goodProductNumber;
        }else {
            maxField=defectiveProductNumber;
        }

        return maxField;
    }
    public static List<Spezifikation> getSpezifikation(String data){
        List<Spezifikation> spezifikations=new ArrayList<>();
        List<String> stringList = Arrays.asList((data.split(",")));
        for (int i = 0; i < stringList.size(); i++) {
            if (!TextUtils.isEmpty(stringList.get(i))){
                Spezifikation spezifikation=new Spezifikation();
                spezifikation.setGoodsSpecsValus(stringList.get(i));
                spezifikation.setIsselect(false);
                spezifikations.add(spezifikation);

            }

        }
        return spezifikations;

    }

    @Nullable
    public static Collection<SetBaen> gettsettinglist() {
        List<SetBaen> setBaens=new ArrayList<>();
        setBaens.add(new SetBaen("基础信息",true));
        setBaens.add(new SetBaen("登录密码",false));
        setBaens.add(new SetBaen("微信登录",false));
        setBaens.add(new SetBaen("切换门店",false));
        setBaens.add(new SetBaen("系统设置",false));
        return setBaens;


    }
}
