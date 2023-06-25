package com.meiling.common.utils;


import android.content.Context;
import android.content.Intent;
import android.content.pm.ResolveInfo;
import android.net.Uri;
import android.os.Build;
import android.text.TextUtils;
import android.util.Log;

import androidx.annotation.RequiresApi;


import java.math.BigDecimal;
import java.text.DecimalFormat;
import java.text.NumberFormat;
import java.util.ArrayList;
import java.util.List;
import java.util.stream.DoubleStream;

/**
 * 数字格式话工具类，主要适配国际本地化格式，使用BigDecimal和NumberFormat做格式化的处理
 */
public class XNumberUtils {

    /**
     * 默认小数的格式化方式，保留最多两位小数点之后的数字
     *
     * @param s 需要格式话的字符串
     * @return 格式化之后的值
     */
    public static String StrFormat(String s) {
        NumberFormat numberInstance = NumberFormat.getNumberInstance();
        numberInstance.setMinimumFractionDigits(2);//小数点后最小的位数
        numberInstance.setMaximumFractionDigits(2);//小数点后最多的位数
        return numberInstance.format(new BigDecimal(s));
    }

    /**
     * @param s           格式化的值
     * @param minFraction 小数位最小位数
     * @param maxFraction 小数位最大位数
     * @return 格式化之后的值
     */
    public static String StrFormat(String s, int minFraction, int maxFraction) {
        NumberFormat numberInstance = NumberFormat.getNumberInstance();
        numberInstance.setMinimumFractionDigits(minFraction);
        numberInstance.setMaximumFractionDigits(maxFraction);
        numberInstance.setMinimumIntegerDigits(1);
        return numberInstance.format(new BigDecimal(s));
    }

    /**
     * @param s            格式化的值
     * @param minFraction  小数位最小位数
     * @param maxFraction  小数位最大位数
     * @param GroupingUsed 是否使用逗号分隔
     * @return 格式化之后的值
     */
    public static String StrFormat(String s, int minFraction, int maxFraction, boolean GroupingUsed) {
        NumberFormat numberInstance = NumberFormat.getNumberInstance();
        numberInstance.setMinimumFractionDigits(minFraction);
        numberInstance.setMaximumFractionDigits(maxFraction);
        numberInstance.setMinimumIntegerDigits(1);
        numberInstance.setGroupingUsed(GroupingUsed);
        return numberInstance.format(new BigDecimal(s).setScale(maxFraction, BigDecimal.ROUND_UP));//小数进位
    }


    /**
     * @param s           格式化的值
     * @param minFraction 小数位最小位数
     * @param maxFraction 小数位最大位数
     * @param minInteger  整数位最小位数
     * @param maxInteger  整数位最大位数
     * @return 格式化之后的值
     */
    public static String StrFormat(String s, int minFraction, int maxFraction, int minInteger, int maxInteger) {
        NumberFormat numberInstance = NumberFormat.getNumberInstance();
        numberInstance.setMinimumFractionDigits(minFraction);
        numberInstance.setMaximumFractionDigits(maxFraction);
        numberInstance.setMinimumIntegerDigits(minInteger);
        numberInstance.setMaximumIntegerDigits(maxInteger);
        return numberInstance.format(new BigDecimal(s));
    }

    /**
     * 默认处理百分数（保留两位小数然后转换成百分数）
     *
     * @param s 格式化的值
     * @return 格式化之后的值
     */
    public static String PercentFormat(String s) {
        NumberFormat percentInstance = NumberFormat.getPercentInstance();
        percentInstance.setMinimumFractionDigits(0);//zuida
        percentInstance.setMaximumFractionDigits(2);//0.3333这样写就会转换为33%（对应地区样式的33%,比如法国支持的百分数就是%33）,设置3就转换成33.3%
        return percentInstance.format(new BigDecimal(s));
    }

    /**
     * @param s           格式化的值
     * @param minFraction 百分数转换前的小数点后位数最小值
     * @param maxFraction 百分数转换前的小数点后位数最大值
     * @return 格式化之后的值
     */
    public static String PercentFormat(String s, int minFraction, int maxFraction) {
        NumberFormat percentInstance = NumberFormat.getPercentInstance();
        percentInstance.setMinimumFractionDigits(minFraction);//zuida
        percentInstance.setMaximumFractionDigits(maxFraction);//0.3333这样写就会转换为33%（对应地区样式的33%,比如法国支持的百分数就是%33）,设置3就转换成33.3%
        return percentInstance.format(new BigDecimal(s));
    }


    /**
     * 字符串的+
     *
     * @param s1 "1"
     * @param s2 "2"
     * @return "3"
     */
    public static String StrAdd(String s1, String s2) {
        s1 = ("".equals(s1) || null == s1) ? "0" : s1;
        s2 = ("".equals(s2) || null == s2) ? "0" : s2;

        BigDecimal bigDecimal1 = new BigDecimal(s1);
        BigDecimal bigDecimal2 = new BigDecimal(s2);
        //保留两位小数方式一
        DecimalFormat df1 = new DecimalFormat("0.00");
        String str = df1.format(bigDecimal1.add(bigDecimal2).doubleValue());
        return str;
    }

    public static String abs(String s) {
        BigDecimal bigDecimal1 = new BigDecimal(s);
        BigDecimal abs1 = bigDecimal1.abs();
        String abs = String.valueOf(abs1);
        Log.d("yjk", "abs: " + abs);
        return abs;
    }


    /**
     * 称重
     * 字符串的+
     *
     * @param s1 "1"
     * @param s2 "2"
     * @return "3"
     */
    public static String StrAddWeigh(String s1, String s2) {
        BigDecimal bigDecimal1 = new BigDecimal(s1);
        BigDecimal bigDecimal2 = new BigDecimal(s2);
        //保留两位小数方式一
        DecimalFormat df1 = new DecimalFormat("0.000");
        String str = df1.format(bigDecimal1.add(bigDecimal2).doubleValue());
        return str;
    }

    /**
     * 要货
     * 字符串的+ 保留三位小数
     *
     * @param s1 "1"
     * @param s2 "2"
     * @return "3"
     */
    public static String enquiryAdd(String s1, String s2) {
        try {


            Log.d("yjk", "enquiryAdd:-- " + s1 + "---" + s2);
            s1.replaceAll(",", "");
            s2.replaceAll(",", "");

            boolean flag = false;
            if (isDouble(s1)) {
                s1 = stringThree(s1);
                flag = true;
            }
            if (isDouble(s2) && !flag) {
                s2 = stringThree(s2);
            }
            //保留两位小数方式一
            BigDecimal bigDecimal1 = new BigDecimal(s1);
            BigDecimal bigDecimal2 = new BigDecimal(s2);
            String str = String.valueOf(bigDecimal1.add(bigDecimal2));
            Log.d("yjk", "enquiryAdd:结果 " + str);
            return str;
        } catch (NumberFormatException e) {
            return "0";
        }

    }

    public static String StrAddToIntString(String s1, String s2) {
        s1 = ("".equals(s1) || null == s1) ? "0" : s1;
        s2 = ("".equals(s2) || null == s2) ? "0" : s2;

        BigDecimal bigDecimal1 = new BigDecimal(s1);
        BigDecimal bigDecimal2 = new BigDecimal(s2);

        return String.valueOf(bigDecimal1.add(bigDecimal2).intValue());
    }


    public static Double StrAddtoDouble(String s1, String s2) {
        BigDecimal bigDecimal1 = new BigDecimal(s1);
        BigDecimal bigDecimal2 = new BigDecimal(s2);

        return bigDecimal1.add(bigDecimal2).doubleValue();
    }

    /**
     * 字符串的-
     *
     * @param s1 "3"
     * @param s2 "2"
     * @return "1"
     */
    public static String Strsubtract(String s1, String s2) {

        s1 = ("".equals(s1) || null == s1) ? "0" : s1;
        s2 = ("".equals(s2) || null == s2) ? "0" : s2;

        BigDecimal bigDecimal1 = new BigDecimal(s1);
        BigDecimal bigDecimal2 = new BigDecimal(s2);
        //保留两位小数方式一
        DecimalFormat df1 = new DecimalFormat("0.00");
        String str = df1.format(bigDecimal1.subtract(bigDecimal2).doubleValue());
        return str;
    }

    /**
     * 字符串的-
     *
     * @param s1 "3"
     * @param s2 "2"
     * @return "1"
     */
    public static String StrsubtractInt(String s1, String s2) {
        s1 = ("".equals(s1) || null == s1) ? "0" : s1;
        s2 = ("".equals(s2) || null == s2) ? "0" : s2;
        BigDecimal bigDecimal1 = new BigDecimal(s1);
        BigDecimal bigDecimal2 = new BigDecimal(s2);
        //保留两位小数方式一
        DecimalFormat df1 = new DecimalFormat("0");
        String str = df1.format(bigDecimal1.subtract(bigDecimal2).intValue());
        return str;
    }

    /**
     * 要货
     * 字符串的-
     *
     * @param s1 "3"
     * @param s2 "2"
     * @return "1"
     */
    public static String enquirysubtract(String s1, String s2) {
        try {


            Log.d("yjk", "enquiryAdd:-- " + s1 + "---" + s2);
            s1.replaceAll(",", "");
            s2.replaceAll(",", "");

            boolean flag = false;
            if (isDouble(s1)) {
                s1 = stringThree(s1);
                flag = true;
            }
            if (isDouble(s2) && !flag) {
                s2 = stringThree(s2);
            }
            //保留两位小数方式一
            BigDecimal bigDecimal1 = new BigDecimal(s1);
            BigDecimal bigDecimal2 = new BigDecimal(s2);
            String str = String.valueOf(bigDecimal1.subtract(bigDecimal2));
            Log.d("yjk", "enquiryAdd:结果 " + str);
            if (compareTo(str, "0") == -1) {
                str = "0";
            }
            return str;
        } catch (NumberFormatException e) {
            return "0";
        }
    }

    /**
     * 字符串的取小数部分
     *
     * @param s1 "3"
     * @return "1"
     */
    public static String StrSubtractKillZero(String s1) {
        BigDecimal bigDecimal1 = new BigDecimal(s1);
        //保留两位小数方式一
        BigDecimal bigRe = bigDecimal1.remainder(BigDecimal.ONE);
        return bigRe.toString();
    }

    /**
     * 字符串的取整数部分
     *
     * @param s1 "3"
     * @return "1"
     */
    public static String StrSubtractKillZero1(String s1) {
        BigDecimal bigDecimal1 = new BigDecimal(s1);
        BigDecimal bigDecimal = bigDecimal1.setScale(0, BigDecimal.ROUND_DOWN).stripTrailingZeros();
        return bigDecimal.toPlainString();
    }

    public static String StrsubtractToIntString(String s1, String s2) {
        BigDecimal bigDecimal1 = new BigDecimal(s1);
        BigDecimal bigDecimal2 = new BigDecimal(s2);

        return String.valueOf(bigDecimal1.subtract(bigDecimal2).intValue());
    }

    public static Double StrsubtractToDouble(String s1, String s2) {
        BigDecimal bigDecimal1 = new BigDecimal(s1);
        BigDecimal bigDecimal2 = new BigDecimal(s2);

        return bigDecimal1.subtract(bigDecimal2).doubleValue();
    }

    /**
     * 字符串的乘*
     *
     * @param s1 "1"
     * @param s2 "2"
     * @return "2"
     */
    public static String Strmultiply(String s1, String s2) {
        s1 = ("".equals(s1) || null == s1) ? "0" : s1;
        s2 = ("".equals(s2) || null == s2) ? "0" : s2;
        BigDecimal bigDecimal1 = new BigDecimal(s1);
        BigDecimal bigDecimal2 = new BigDecimal(s2);
        //保留两位小数方式一
        DecimalFormat df1 = new DecimalFormat("0.00");
        String str = df1.format(bigDecimal1.multiply(bigDecimal2).doubleValue());
        return str;

    }

    /**
     * 字符串的乘*
     *
     * @param s1 "1"
     * @param s2 "2"
     * @return "2"
     */
    public static String Strmultiply1(String s1, String s2) {

        try {


            BigDecimal bigDecimal1 = new BigDecimal(s1);
            BigDecimal bigDecimal2 = new BigDecimal(s2);
            //保留两位小数方式一
            DecimalFormat df1 = new DecimalFormat("0.000");
            String str = df1.format(bigDecimal1.multiply(bigDecimal2).doubleValue());
            return str;
        } catch (NumberFormatException e) {
            return "0";
        }
    }

    public static String Strmultiply2(String s1, String s2) {
        BigDecimal bigDecimal1 = new BigDecimal(s1);
        BigDecimal bigDecimal2 = new BigDecimal(s2);
        //保留两位小数方式一
        DecimalFormat df1 = new DecimalFormat("0.0");
        String str = df1.format(bigDecimal1.multiply(bigDecimal2).doubleValue());
        return str;
    }

    public static String Strmultiply3(String s1, String s2) {
        try {


            BigDecimal bigDecimal1 = new BigDecimal(s1);
            BigDecimal bigDecimal2 = new BigDecimal(s2);
            //保留两位小数方式一
            DecimalFormat df1 = new DecimalFormat("0.000");
            String str = df1.format(bigDecimal1.multiply(bigDecimal2).doubleValue());
            return str;
        } catch (NumberFormatException e) {
            return "0";
        }

    }

    /**
     * 字符串的/
     *
     * @param s1 "1"
     * @param s2 "2" 非零
     * @return "0.5"
     */
    public static String Strdivide(String s1, String s2) {
        try {
            BigDecimal bigDecimal1 = new BigDecimal(s1);
            BigDecimal bigDecimal2 = new BigDecimal(s2);
            return String.valueOf(bigDecimal1.divide(bigDecimal2, 2, BigDecimal.ROUND_HALF_UP).doubleValue());

        } catch (Exception e) {
            return "0";

        }
    }


    /**
     * 单独处理保留小数点后两位的方法
     */
    public static String FormatFromBigDecimal(String s) {
        s = ("".equals(s) || null == s) ? "0" : s;
        Double d = Double.valueOf(s);
        BigDecimal b = new BigDecimal(d);
        return b.setScale(2, BigDecimal.ROUND_DOWN).toString();
    }

    /**
     * 单独处理保留小数点后两位的方法，四舍五入算法
     */
    public static String FormatFromBigDecimal1(String s, int i) {
        try {
            Double d = Double.valueOf(s);
            BigDecimal b = new BigDecimal(d);
            return b.setScale(i, BigDecimal.ROUND_HALF_UP).toString();
        } catch (Exception e) {
            return "";
        }

    }

    /**
     * 单独处理保留小数点后两位的方法，四舍五入算法
     */
    public static String FormatFromBigDecimal2(String s, int i) {
        try {
            BigDecimal b = new BigDecimal(s);
            String string = b.setScale(i, BigDecimal.ROUND_DOWN).toString();
            Log.d("yjk", "------" + string);
            return string;

        } catch (Exception e) {
            return "";
        }
    }


    public static String formatZero(String type, String s) {
        //type 为1 抹分保留1位小数
        //type 为2 抹角保留整数
        //type 为3 四舍分 保留原来数，或一位小数
        //type 为4 五入角 保留原来数，或一位小数
        String a = "";
        BigDecimal b = new BigDecimal(String.valueOf(s));
        switch (type) {
            case "1": {
                a = b.setScale(1, BigDecimal.ROUND_DOWN).toString();
                break;
            }
            case "2": {
                a = b.setScale(0, BigDecimal.ROUND_DOWN).toString();
                break;
            }
            case "3": {
                BigDecimal bigDecimal = b.setScale(1, BigDecimal.ROUND_HALF_UP);
                a = b.compareTo(bigDecimal) < 0 ? String.valueOf(b) : String.valueOf(b.setScale(1, BigDecimal.ROUND_DOWN));
                break;
            }
            case "4": {
                BigDecimal bigDecimal = b.setScale(1, BigDecimal.ROUND_HALF_UP);
                a = b.compareTo(bigDecimal) < 0 ? String.valueOf(bigDecimal) : String.valueOf(b);
            }
        }
        Log.d("lwq", "===抹零方式=====" + type + "======金额" + s);
        return a;
    }


    /**
     * 去掉小数点后不需要的0
     */
    public static String FormatStrRemoveZero(String s) {
        try {
            return new BigDecimal(s).stripTrailingZeros().toPlainString();
        }catch (Exception e) {
            return "0";
        }

    }

    public static int getx(String x) {
        Log.d("yunsaun--除法", div(String.valueOf(x), "100", 1, true));
        Log.d("yunsaun", "getx: " + Integer.parseInt(mul("450", div(String.valueOf(x), "100", 0, false), 0)));
        return Integer.parseInt(mul("450", div(String.valueOf(x), "100", 4, true), 0));

    }

    public static int gety(String x) {
        Log.d("yunsaun", "getx: " + Integer.parseInt(mul("320", div(String.valueOf(x), "100", 0, false), 0)));
        return Integer.parseInt(mul("320", div(String.valueOf(x), "100", 4, true), 0));

    }

    /**
     * 提供精确的除法运算。当发生除不尽的情况时，由scale参数指定精度，以后的数字四舍五入
     *
     * @param v1    被除数
     * @param v2    除数
     * @param scale 表示需要精确到小数点以后几位
     * @param isUp  是否是四舍五入，否则，去掉
     * @return 两个参数的商
     */
    public static String div(String v1, String v2, int scale, boolean isUp) {
        try {
            if (scale < 0) {
                scale = 0;
            }
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            if (isUp) {
                BigDecimal divide = b1.divide(b2, scale, BigDecimal.ROUND_HALF_UP);
                return divide.toString();
            } else {
                BigDecimal divide = b1.divide(b2, scale, BigDecimal.ROUND_DOWN);
                return divide.toString();
            }
        }catch (Exception e){
            return "0";
        }

    }

    /**
     * 提供精确的乘法运算
     *
     * @param v1    被乘数
     * @param v2    乘数
     * @param scale 保留scale 位小数
     * @return 两个参数的积
     */
    public static String mul(String v1, String v2, int scale) {
        v1 = ("".equals(v1) || null == v1) ? "0" : v1;
        v2 = ("".equals(v2) || null == v2) ? "0" : v2;

        if (scale < 0) {
            scale = 0;
        }
        BigDecimal b1 = new BigDecimal(v1);
        BigDecimal b2 = new BigDecimal(v2);
        return b1.multiply(b2).setScale(scale, BigDecimal.ROUND_HALF_UP).toString();
    }

    /**
     * 判断字符串是否是数字
     */
    public static boolean isNumber(String value) {
        return isInteger(value) || isDouble(value);
    }

    /**
     * 判断字符串是否是整数
     */
    public static boolean isInteger(String value) {
        try {
            Integer.parseInt(value);
            return true;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 返回保留三位小数字符串
     */
    public static String stringThree(String value) {

        try {

            BigDecimal decimal = new BigDecimal("0.000");
            BigDecimal decimalValue = new BigDecimal(value);
            BigDecimal add = decimal.add(decimalValue);
            return String.valueOf(add);

        } catch (NumberFormatException e) {
            return "0";
        }
    }

    /**
     * 判断字符串是否是浮点数
     */
    public static boolean isDouble(String value) {
        try {
            Double.parseDouble(value);
            if (value.contains("."))
                return true;
            return false;
        } catch (NumberFormatException e) {
            return false;
        }
    }

    /**
     * 精确对比两个数字
     *
     * @param v1 需要被对比的第一个数
     * @param v2 需要被对比的第二个数
     * @return 如果两个数一样则返回0，如果第一个数比第二个数大则返回1，反之返回-1
     */
    public static int compareTo(String v1, String v2) {
        try {
            BigDecimal b1 = new BigDecimal(v1);
            BigDecimal b2 = new BigDecimal(v2);
            return b1.compareTo(b2);
        } catch (NumberFormatException e) {
            return -1;
        }

    }


    public static Boolean isinstall(Context context) {
        String url = "http://www.baidu.com";
        Intent intent = new Intent(Intent.ACTION_VIEW, Uri.parse(url));
        List<ResolveInfo> list = context.getPackageManager().queryIntentActivities(intent, 0);
        if (list.size() > 0) {
            ///浏览器存在
            return true;
        } else {
            return false;
            ///浏览器不存在
        }
    }

    public static boolean isNumeric(String str) {

        for (int i = str.length(); --i >= 0; ) {

            if (!Character.isDigit(str.charAt(i))) {

                return false;

            }

        }

        return true;

    }

    public static String isZERON(String s) {
        try {
            BigDecimal a = new BigDecimal(s);
            if (a.compareTo(BigDecimal.ZERO) == 0) {
                s = "0";
            }
            return s;
        } catch (Exception e) {
            return "0";
        }



    }

    /**
     * 1 去除小数点无效0
     * 2。保留小数点
     * 3。
     * @param s
     * @param u
     * @return
     */

    public static String formatteddata(String s ,int u) {
        return XNumberUtils.FormatStrRemoveZero(XNumberUtils.isZERON( XNumberUtils.FormatFromBigDecimal2(s, u)));
    }

}
