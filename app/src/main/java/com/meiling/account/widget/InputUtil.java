package com.meiling.account.widget;

import android.graphics.Color;

import com.meiling.account.bean.Goods;
import com.meiling.account.bean.Ranking;

import org.jetbrains.annotations.NotNull;

import java.util.ArrayList;
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
    public static List<Integer> colors(){
        List<Integer> colors = new ArrayList<>(); //每个模块的颜色
        colors.add(Color.parseColor("#E55A55"));
        colors.add(Color.parseColor("#FF974D"));

        colors.add(Color.parseColor("#FFDC4C"));
        colors.add(Color.parseColor("#A8E0FB"));

        colors.add(Color.parseColor("#5B6E96"));
        colors.add(Color.parseColor("#61D9AC"));

        colors.add(Color.parseColor("#5AAEF6"));
        colors.add(Color.parseColor("#26C0DB"));
        colors.add(Color.parseColor("#6E61E4"));
        colors.add(Color.parseColor("#6E61E4"));
        return colors;
    }
    @NotNull
    public static List<Float> date(){
        List<Float> date = new ArrayList<>(); //shuju
        date.add(35f);
        date.add(35f);

        date.add(35f);
        date.add(35f);
        date.add(35f);

        date.add(35f);
        date.add(35f);

        date.add(35f);
        date.add(35f);
        date.add(35f);
        return date;
    }

    @NotNull
    public static List<Ranking> setRanking(){
        List<Ranking>rankings=new ArrayList<>();
        rankings.add(new Ranking(Color.parseColor("#E55A55"),35f));
        rankings.add(new Ranking(Color.parseColor("#FF974D"),35f));
        rankings.add(new Ranking(Color.parseColor("#FFDC4C"),35f));
        rankings.add(new Ranking(Color.parseColor("#A8E0FB"),35f));
        rankings.add(new Ranking(Color.parseColor("#5B6E96"),35f));
        rankings.add(new Ranking(Color.parseColor("#61D9AC"),35f));
        rankings.add(new Ranking(Color.parseColor("#5AAEF6"),35f));
        rankings.add(new Ranking(Color.parseColor("#26C0DB"),35f));
        rankings.add(new Ranking(Color.parseColor("#6E61E4"),35f));
        rankings.add(new Ranking(Color.parseColor("#6E61E4"),35f));


        return rankings;
    }
}
