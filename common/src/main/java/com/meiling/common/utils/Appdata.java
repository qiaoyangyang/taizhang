package com.meiling.common.utils;

import java.util.ArrayList;
import java.util.List;

public class Appdata {
    public static List<String> getet(){
        List<String>strings=new ArrayList<>();
        for (int i = 0; i <18; i++) {

            strings.add("123"+i);

        }
        return strings;
    }
}
