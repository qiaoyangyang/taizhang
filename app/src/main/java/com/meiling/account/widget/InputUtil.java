package com.meiling.account.widget;

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
}
