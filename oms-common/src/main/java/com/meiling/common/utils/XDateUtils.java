package com.meiling.common.utils;


import java.text.DateFormat;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.ArrayList;
import java.util.Calendar;
import java.util.Date;
import java.util.List;
import java.util.Locale;
import java.util.TimeZone;

/**
 * 日期操作工具类.
 * SimpleDateFormat函数语法：
 * G 年代标志符
 * y 年
 * M 月
 * d 日
 * h 时 在上午或下午 (1~12)
 * H 时 在一天中 (0~23)
 * m 分
 * s 秒
 * S 毫秒
 * E 星期
 * D 一年中的第几天
 * F 一月中第几个星期几
 * w 一年中第几个星期
 * W 一月中第几个星期
 * a 上午 / 下午 标记符
 * k 时 在一天中 (1~24)
 * K 时 在上午或下午 (0~11)
 * z 时区
 */
public class XDateUtils {


    private XDateUtils() {
        throw new UnsupportedOperationException("u can't instantiate me...");
    }


    /**
     * 秒与毫秒的倍数
     */
    public static final int SEC = 1000;
    /**
     * 分与毫秒的倍数
     */
    public static final int MIN = SEC * 60;
    /**
     * 时与毫秒的倍数
     */
    public static final int HOUR = MIN * 60;
    /**
     * 天与毫秒的倍数
     */
    public static final int DAY = HOUR * 24;

    /**
     * 周与毫秒的倍数
     */
    public static final int WEEK = DAY * 7;

    /**
     * 月与毫秒的倍数
     */
    public static final int MONTH = DAY * 30;

    /**
     * 年与毫秒的倍数
     */
    public static final int YEAR = DAY * 365;

    /**
     * 默认格式
     */
    public static final String DEFAULT_PATTERN = "yyyy-MM-dd HH:mm:ss";

    /**
     * SimpleDateFormat不是线程安全的，以下是线程安全实例化操作
     */
    private static final ThreadLocal<SimpleDateFormat> local = new ThreadLocal<SimpleDateFormat>() {
        @Override
        protected SimpleDateFormat initialValue() {
            return new SimpleDateFormat();
        }
    };

    /**
     * 获取SimpleDateFormat实例
     *
     * @param pattern 模式串
     * @return
     */
    public static SimpleDateFormat getSimpleDateFormat(String pattern) {
        SimpleDateFormat format = local.get();
        format.applyPattern(pattern);
        return format;
    }

    /**
     * 获取当前时间的字符串
     *
     * @return
     */
    public static String getCurrentDate() {
        return format(new Date(), DEFAULT_PATTERN);
    }

    /**
     * 获取表示当前时间的字符串
     *
     * @param pattern 模式串
     * @return
     */
    public static String getCurrentDate(String pattern) {
        return format(new Date(), pattern);
    }

    /**
     * 日期时间格式化
     *
     * @param date Date
     * @return
     */
    public static String format(Date date) {
        SimpleDateFormat format = getSimpleDateFormat(DEFAULT_PATTERN);
        return format.format(date);
    }

    /**
     * 日期时间格式化
     *
     * @param date    Date
     * @param pattern 模式串
     * @return
     */
    public static String format(Date date, String pattern) {
        SimpleDateFormat format = getSimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 将时间戳转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param millis 毫秒时间戳
     * @return 时间字符串
     */
    public static String millis2String(long millis) {
        SimpleDateFormat format = getSimpleDateFormat(DEFAULT_PATTERN);
        return format.format(new Date(millis));
    }

    /**
     * 将时间戳转为时间字符串
     * <p>格式为pattern</p>
     *
     * @param millis  毫秒时间戳
     * @param pattern 时间格式
     * @return 时间字符串
     */
    public static String millis2String(long millis, String pattern) {
        SimpleDateFormat format = getSimpleDateFormat(pattern);
        return format.format(new Date(millis));
    }

    /**
     * 将时间戳转为时间字符串(国际时间)
     * <p>格式为pattern</p>
     *
     * @param millis  毫秒时间戳
     * @param pattern 时间格式
     * @return 时间字符串
     */
    public static String millis2String2(long millis, String pattern) {
        SimpleDateFormat format = getSimpleDateFormat(pattern);
        format.setTimeZone(TimeZone.getTimeZone("GMT+0"));//**TimeZone时区，加上这句话就解决啦**
        return format.format(new Date(millis));
    }


    /**
     * 将时间字符串转为时间戳
     * <p>time格式为pattern</p>
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return 毫秒时间戳
     */
    public static long string2Millis(String time, String pattern) {
        SimpleDateFormat format = getSimpleDateFormat(pattern);
        try {
            return format.parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return -1;
    }

    //xx年xx月xx
    public static String longToYMDC(long time) {
        if (time < 10000000000L) {
            time = time * 1000;
        }
        Date date = new Date(time);
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        String timeString = sdf.format(date);//Date-->String
        String[] split = timeString.split("-");
        StringBuffer stringBuffer = new StringBuffer();
        stringBuffer.append(split[0]);
        stringBuffer.append("/");
        stringBuffer.append(split[1]);
        stringBuffer.append("/");
        stringBuffer.append(split[2]);
        return stringBuffer.toString();
    }

    /**
     * @param time
     * @desc 字符串转时间戳
     * @example time="2019-04-19 00:00:00"
     **/
    public static Long getTimestamp(String time) {
        Long timestamp = null;
        try {
            timestamp = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss").parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }

    /**
     * 将时间字符串转为Date类型
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return Date类型
     */
    public static Date string2Date(String time) {
        return string2Date(time, DEFAULT_PATTERN);
    }

    /**
     * 将时间字符串转为Date类型
     * <p>time格式为pattern</p>
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return Date类型
     */
    public static Date string2Date(String time, String pattern) {
        return new Date(string2Millis(time, pattern));
    }

    /**
     * 将Date类型转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param date Date类型时间
     * @return 时间字符串
     */
    public static String date2String(Date date) {
        return date2String(date, DEFAULT_PATTERN);
    }

    /**
     * 将Date类型转为时间字符串
     * <p>格式为pattern</p>
     *
     * @param date    Date类型时间
     * @param pattern 时间格式
     * @return 时间字符串
     */
    public static String date2String(Date date, String pattern) {
        SimpleDateFormat format = getSimpleDateFormat(pattern);
        return format.format(date);
    }

    /**
     * 将Date类型转为时间戳
     *
     * @param date Date类型时间
     * @return 毫秒时间戳
     */
    public static long date2Millis(Date date) {
        return date.getTime();
    }

    /**
     * 将时间戳转为Date类型
     *
     * @param millis 毫秒时间戳
     * @return Date类型时间
     */
    public static Date millis2Date(long millis) {
        return new Date(millis);
    }

    /**
     * 获取与当前时间的时间差
     *
     * @param date 需要计算的时间，应小于当前时间
     * @return DateDifference实体类，内封装有获取相差的毫秒、秒、分钟、小时、天的方法
     */
//    public static DateDifference getTwoDataDifference(Date date) {
//
//        return getTwoDataDifference(new Date(), date);
//    }

    /**
     * 获取与当前时间的时间差
     *
     * @param str 需要计算的时间，应小于当前时间
     * @return DateDifference实体类，内封装有获取相差的毫秒、秒、分钟、小时、天的方法
     */
//    public static DateDifference getTwoDataDifference(String str) {
//        return getTwoDataDifference(new Date(), string2Date(str));
//    }

    /**
     * 获取当前时差转换为刚刚、几分钟前、几小时前、几天前等
     */
//    public static String getDataDifferenceNow(long millis) {
//
//        String backStr;
//        DateDifference twoDataDifference = getTwoDataDifference(millis2Date(millis));
//        long day = twoDataDifference.getDay();
//        long hour = twoDataDifference.getHour();
//        long minute = twoDataDifference.getMinute();
//        long second = twoDataDifference.getSecond();
//
//        if (day > 365) {
//            backStr = "最近更新于："+millis2String(millis, "yyyy-MM-dd");
//        } else if (day > 30) {
//            backStr ="最近更新于："+ millis2String(millis, "MM-dd HH:mm");
//        } else if (day > 1) {
//            backStr = "最近更新于："+day + "天前";
//        } else if (hour > 1) {
//            backStr = "最近更新于："+hour + "小时前";
//        } else if (minute > 1) {
//            backStr = "最近更新于："+minute + "分钟前";
//        } else if (second > 1) {
//            backStr = "刚刚更新";
//        } else {
//            backStr = "刚刚更新";
//        }
//
//        return backStr;
//    }


    /**
     * 得到二个日期间的时间差
     *
     * @param str1 两个时间中较大的那个
     * @param str2 两个时间中较小的那个
     * @return DateDifference实体类，内封装有获取相差的毫秒、秒、分钟、小时、天的方法
     */
//    public static DateDifference getTwoDataDifference(String str1, String str2) {
//        return getTwoDataDifference(string2Date(str1), string2Date(str2));
//    }

    /**
     * 得到二个日期间的时间差
     *
     * @param date1 两个时间中较大的那个
     * @param date2 两个时间中较小的那个
     * @return DateDifference实体类，内封装有获取相差的毫秒、秒、分钟、小时、天的方法
     */
//    public static DateDifference getTwoDataDifference(Date date1, Date date2) {
//        DateDifference difference = new DateDifference();
//        long millis = date1.getTime() - date2.getTime();
//        difference.setMillisecond(millis);
//        difference.setSecond(millis / SEC);
//        difference.setMinute(millis / MIN);
//        difference.setHour(millis / HOUR);
//        difference.setDay(millis / DAY);
//
//        return difference;
//    }


    /**
     * 判断是否同一天
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isSameDay(String time) {
        return isSameDay(string2Millis(time, DEFAULT_PATTERN));
    }

    /**
     * 判断是否同一天
     * <p>time格式为pattern</p>
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isSameDay(String time, String pattern) {
        return isSameDay(string2Millis(time, pattern));
    }

    /**
     * 判断是否同一天
     *
     * @param date Date类型时间
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isSameDay(Date date) {
        return isSameDay(date.getTime());
    }

    /**
     * 判断是否同一天
     *
     * @param millis 毫秒时间戳
     * @return {@code true}: 是<br>{@code false}: 否
     */
    public static boolean isSameDay(long millis) {
        long wee = (System.currentTimeMillis() / DAY) * DAY;
        return millis >= wee && millis < wee + DAY;
    }

    /**
     * 判断是否闰年
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return {@code true}: 闰年<br>{@code false}: 平年
     */
    public static boolean isLeapYear(String time) {
        return isLeapYear(string2Date(time, DEFAULT_PATTERN));
    }

    /**
     * 判断是否闰年
     * <p>time格式为pattern</p>
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return {@code true}: 闰年<br>{@code false}: 平年
     */
    public static boolean isLeapYear(String time, String pattern) {
        return isLeapYear(string2Date(time, pattern));
    }

    /**
     * 判断是否闰年
     *
     * @param date Date类型时间
     * @return {@code true}: 闰年<br>{@code false}: 平年
     */
    public static boolean isLeapYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int year = cal.get(Calendar.YEAR);
        return isLeapYear(year);
    }

    /**
     * 判断是否闰年
     *
     * @param millis 毫秒时间戳
     * @return {@code true}: 闰年<br>{@code false}: 平年
     */
    public static boolean isLeapYear(long millis) {
        return isLeapYear(millis2Date(millis));
    }

    /**
     * 判断是否闰年
     *
     * @param year 年份
     * @return {@code true}: 闰年<br>{@code false}: 平年
     */
    public static boolean isLeapYear(int year) {
        return year % 4 == 0 && year % 100 != 0 || year % 400 == 0;
    }

    /**
     * 获取星期
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 星期
     */
    public static String getWeek(String time) {
        return getWeek(string2Date(time, DEFAULT_PATTERN));
    }

    /**
     * 获取星期
     * <p>time格式为pattern</p>
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return 星期
     */
    public static String getWeek(String time, String pattern) {
        return getWeek(string2Date(time, pattern));
    }

    /**
     * 获取星期
     *
     * @param date Date类型时间
     * @return 星期
     */
    public static String getWeek(Date date) {
        return new SimpleDateFormat("EEEE", Locale.getDefault()).format(date);
    }

    /**
     * 获取星期
     *
     * @param millis 毫秒时间戳
     * @return 星期
     */
    public static String getWeek(long millis) {
        return getWeek(new Date(millis));
    }

    /**
     * 获取星期
     * <p>注意：周日的Index才是1，周六为7</p>
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 1...5
     */
    public static int getWeekIndex(String time) {
        return getWeekIndex(string2Date(time, DEFAULT_PATTERN));
    }

    /**
     * 获取星期
     * <p>注意：周日的Index才是1，周六为7</p>
     * <p>time格式为pattern</p>
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return 1...7
     */
    public static int getWeekIndex(String time, String pattern) {
        return getWeekIndex(string2Date(time, pattern));
    }

    /**
     * 获取星期
     * <p>注意：周日的Index才是1，周六为7</p>
     *
     * @param date Date类型时间
     * @return 1...7
     */
    public static int getWeekIndex(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.DAY_OF_WEEK);
    }

    /**
     * 获取星期
     * <p>注意：周日的Index才是1，周六为7</p>
     *
     * @param millis 毫秒时间戳
     * @return 1...7
     */
    public static int getWeekIndex(long millis) {
        return getWeekIndex(millis2Date(millis));
    }

    /**
     * 获取月份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 1...5
     */
    public static int getWeekOfMonth(String time) {
        return getWeekOfMonth(string2Date(time, DEFAULT_PATTERN));
    }

    /**
     * 获取月份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     * <p>time格式为pattern</p>
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return 1...5
     */
    public static int getWeekOfMonth(String time, String pattern) {
        return getWeekOfMonth(string2Date(time, pattern));
    }

    /**
     * 获取月份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     *
     * @param date Date类型时间
     * @return 1...5
     */
    public static int getWeekOfMonth(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_MONTH);
    }

    /**
     * 获取月份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     *
     * @param millis 毫秒时间戳
     * @return 1...5
     */
    public static int getWeekOfMonth(long millis) {
        return getWeekOfMonth(millis2Date(millis));
    }

    /**
     * 获取年份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 1...54
     */
    public static int getWeekOfYear(String time) {
        return getWeekOfYear(string2Date(time, DEFAULT_PATTERN));
    }

    /**
     * 获取年份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     * <p>time格式为pattern</p>
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return 1...54
     */
    public static int getWeekOfYear(String time, String pattern) {
        return getWeekOfYear(string2Date(time, pattern));
    }

    /**
     * 获取年份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     *
     * @param date Date类型时间
     * @return 1...54
     */
    public static int getWeekOfYear(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return cal.get(Calendar.WEEK_OF_YEAR);
    }

    /**
     * 获取年份中的第几周
     * <p>注意：国外周日才是新的一周的开始</p>
     *
     * @param millis 毫秒时间戳
     * @return 1...54
     */
    public static int getWeekOfYear(long millis) {
        return getWeekOfYear(millis2Date(millis));
    }

    private static final String[] CHINESE_ZODIAC = {"猴", "鸡", "狗", "猪", "鼠", "牛", "虎", "兔", "龙", "蛇", "马", "羊"};

    /**
     * 获取生肖
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 生肖
     */
    public static String getChineseZodiac(String time) {
        return getChineseZodiac(string2Date(time, DEFAULT_PATTERN));
    }

    /**
     * 获取生肖
     * <p>time格式为pattern</p>
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return 生肖
     */
    public static String getChineseZodiac(String time, String pattern) {
        return getChineseZodiac(string2Date(time, pattern));
    }

    /**
     * 获取生肖
     *
     * @param date Date类型时间
     * @return 生肖
     */
    public static String getChineseZodiac(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        return CHINESE_ZODIAC[cal.get(Calendar.YEAR) % 12];
    }

    /**
     * 获取生肖
     *
     * @param millis 毫秒时间戳
     * @return 生肖
     */
    public static String getChineseZodiac(long millis) {
        return getChineseZodiac(millis2Date(millis));
    }

    /**
     * 获取生肖
     *
     * @param year 年
     * @return 生肖
     */
    public static String getChineseZodiac(int year) {
        return CHINESE_ZODIAC[year % 12];
    }

    private static final String[] ZODIAC = {"水瓶座", "双鱼座", "白羊座", "金牛座", "双子座", "巨蟹座", "狮子座", "处女座", "天秤座", "天蝎座", "射手座", "魔羯座"};
    private static final int[] ZODIAC_FLAGS = {20, 19, 21, 21, 21, 22, 23, 23, 23, 24, 23, 22};

    /**
     * 获取星座
     * <p>time格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param time 时间字符串
     * @return 生肖
     */
    public static String getZodiac(String time) {
        return getZodiac(string2Date(time, DEFAULT_PATTERN));
    }

    /**
     * 获取星座
     * <p>time格式为pattern</p>
     *
     * @param time    时间字符串
     * @param pattern 时间格式
     * @return 生肖
     */
    public static String getZodiac(String time, String pattern) {
        return getZodiac(string2Date(time, pattern));
    }

    /**
     * 获取星座
     *
     * @param date Date类型时间
     * @return 星座
     */
    public static String getZodiac(Date date) {
        Calendar cal = Calendar.getInstance();
        cal.setTime(date);
        int month = cal.get(Calendar.MONTH) + 1;
        int day = cal.get(Calendar.DAY_OF_MONTH);
        return getZodiac(month, day);
    }

    /**
     * 获取星座
     *
     * @param millis 毫秒时间戳
     * @return 星座
     */
    public static String getZodiac(long millis) {
        return getZodiac(millis2Date(millis));
    }

    /**
     * 获取星座
     *
     * @param month 月
     * @param day   日
     * @return 星座
     */
    public static String getZodiac(int month, int day) {
        return ZODIAC[day >= ZODIAC_FLAGS[month - 1]
                ? month - 1
                : (month + 10) % 12];
    }


    /**
     * UTC时间 ---> 当地时间
     *
     * @param utcTime         UTC时间
     * @param utcTimePatten   UTC时间格式
     * @param localTimePatten 当地时间格式
     * @return
     */
    public static String utc2Local(String utcTime, String utcTimePatten,
                                   String localTimePatten) {
        if (utcTimePatten == null) {
            utcTimePatten = "yyyy-MM-dd HH:mm:ss";
        }
        if (localTimePatten == null) {
            localTimePatten = "yyyy-MM-dd HH:mm:ss";
        }
        SimpleDateFormat utcFormater = new SimpleDateFormat(utcTimePatten);
        utcFormater.setTimeZone(TimeZone.getTimeZone("UTC"));
        Date gpsUTCDate = null;
        try {
            gpsUTCDate = utcFormater.parse(utcTime);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        SimpleDateFormat localFormater = new SimpleDateFormat(localTimePatten);
        localFormater.setTimeZone(TimeZone.getDefault());
        String localTime = localFormater.format(gpsUTCDate.getTime());
        return localTime;
    }

    public static String getDataString(int year, int month, int day) {
        Calendar c = Calendar.getInstance();
        c.set(year, month, day);
        return millis2String2(c.getTimeInMillis(), "yyyy-MM-dd");
    }

    /**
     * 获取当前年份
     */

    public static String getCurrentYear() {
        Calendar c = Calendar.getInstance();
        return String.valueOf(c.get(Calendar.YEAR));
    }


    public static String showDate(long millis) {
        Date date = millis2Date(millis);
        String dateStr = format(date);
        //yyyy-MM-dd HH:mm:ss
        SimpleDateFormat format = getSimpleDateFormat("yyyy");
        format.format(date);
        String year =getSimpleDateFormat("yyyy").format(date);
//        int month = Integer.parseInt(dateStr.substring(5, 7)); // Long yearNum =Long.parseLong(year);
        int month= Integer.parseInt(getSimpleDateFormat("MM").format(date));

//        int day = Integer.parseInt(dateStr.substring(8, 10));
        int day = Integer.parseInt(getSimpleDateFormat("dd").format(date));

//        String hour = dateStr.substring(11, 13);
        String hour =getSimpleDateFormat("HH").format(date);
//        String minute = dateStr.substring(14, 16);
        String minute =getSimpleDateFormat("mm").format(date);

        Calendar cal = Calendar.getInstance();
        cal.set(Calendar.HOUR, 0);
        cal.set(Calendar.SECOND, 0);
        cal.set(Calendar.MINUTE, 0);
        cal.set(Calendar.MILLISECOND, 0);
        long T_time = cal.getTimeInMillis();//今天0点开始的毫秒数
        long addtime = date.getTime();//待显示的时间的毫秒数
        long Y_time = T_time - 1000 * 60 * 60 * 24;
        long B_Y_time = Y_time - 1000 * 60 * 60 * 24;
        long now_time = System.currentTimeMillis();//当前时间的毫秒数
// Log.i("test", "T_time:"+T_time+"-------addtime:"+addtime+"-----Y_time:"+Y_time+"-----B_Y_time:"+B_Y_time+"----now_time:"+now_time);
        String result = "";
        if (addtime > T_time) {
//显示ms大于0点ms----今天
            result = "今天 " + hour + ":" + minute;
        } else if (addtime < T_time && addtime > Y_time) {
//显示ms大于昨天0点ms并且小于今天0点ms-----昨天
            result = "昨天 " + hour + ":" + minute;
        } else if (addtime < Y_time && addtime > B_Y_time) {
//显示ms大于前天0点ms并且小于昨天0点ms-----前天
            result = "前天 " + hour + ":" + minute;
        } else if (addtime > now_time) {
//显示ms大于当前时间---异常显示
            result = "未知";
        } else if (addtime < B_Y_time) {
//其余情况显示年月日
            result = year + "-" + month + "-" + day;
        }

        return result;
    }

    /**
     *      * 日期格式化
     *      * @param date      需要格式化的日期
     *      * @param pattern   时间格式，如：yyyy-MM-dd HH:mm:ss
     *      * @return          返回格式化后的时间字符串
     *     
     */
    public static String format2(Date date, String pattern) {
        SimpleDateFormat sdf = new SimpleDateFormat(pattern);
        return sdf.format(date);
    }

    public static String getStringTimeByLongData(long time) {

        StringBuffer stringBuffer = new StringBuffer();
        long mill = time % 60;//剩余的秒
        long sec = (time / 60) % 60;//剩余分钟
        long hour = ((time / 60) / 60) % 24;//剩余小时
        long day = time / (60 * 60 * 24);
        if (day > 0) {//超过一天
            stringBuffer.append(day + "天");
        }
        if ((time / (60 * 60)) > 0) {//超过一小时
            stringBuffer.append(hour + "小时");
        }
        if ((time / 60) > 0) {
            stringBuffer.append(sec + "分钟");
        }
        if (mill > 0) {
            stringBuffer.append(mill+"秒");
        }
        return stringBuffer.toString();
    }
    /**
     * 比较两个日期的大小，日期格式为yyyyMMdd
     *
     * @param str1 the first date
     * @param str2 the second date
     * @return true <br/>false
     */
    public static boolean isDateOneBigger(String str1, String str2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyyMMdd");
        Date dt1 = new Date();
        Date dt2 = new Date();
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() < dt2.getTime() || dt1.getTime() == dt2.getTime()) {
            isBigger = true;
        } else {
            isBigger = false;
        }
        return isBigger;
    }
    /**
     * 比较两个日期的大小，日期格式为yyyyMMdd
     *
     * @param str1 the first date
     * @param str2 the second date
     * @return true <br/>false
     */
    public static boolean isDateOneBigger1(String str1, String str2) {
        boolean isBigger = false;
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date dt1 = new Date();
        Date dt2 = new Date();
        try {
            dt1 = sdf.parse(str1);
            dt2 = sdf.parse(str2);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        if (dt1.getTime() < dt2.getTime() || dt1.getTime() == dt2.getTime()) {
            isBigger = true;
        } else {
            isBigger = false;
        }
        return isBigger;
    }

    public static boolean isDeliveredByToday() {
        Calendar calendar = Calendar.getInstance();
        int hour = calendar.get(Calendar.HOUR_OF_DAY);
        return hour < 15;
    }

    /**
     * @param time
     * @desc 字符串转时间戳
     **/
    public static Long getStr2Timestamp(String time) {
        Long timestamp = null;
        try {
            timestamp = new SimpleDateFormat("yyyy-MM-dd").parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }
    /**
     * @param time
     * @desc 字符串转时间戳
     **/
    public static Long getStr3Timestamp(String time) {
        Long timestamp = null;
        try {
            timestamp = new SimpleDateFormat("yyyy-MM-dd").parse(time).getTime();
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return timestamp;
    }

//    public static Day getSelectedDay() {
//
//        Calendar cal = Calendar.getInstance();
//        int day = cal.get(Calendar.DAY_OF_MONTH);
//        int hour = cal.get(Calendar.HOUR_OF_DAY);
//
//        int month = cal.get(Calendar.MONTH);
//        int year = cal.get(Calendar.YEAR);
//        return new Day(year, month + 1, day);
//    }


//    public static Day getSelectedDay1(int t) {
//
//
//        Calendar cal = Calendar.getInstance();
//        cal.add(Calendar.DAY_OF_MONTH,t );
//        int day = cal.get(Calendar.DAY_OF_MONTH);
//        int hour = cal.get(Calendar.HOUR_OF_DAY);
//
//        int month = cal.get(Calendar.MONTH);
//        int year = cal.get(Calendar.YEAR);
//        return new Day(year, month + 1, day);
//    }

    public static String formatString(Date date, int type) {
        DateFormat df = null;
        if (type == 1) {
            df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        } else {
            df = new SimpleDateFormat("yyyy/MM/dd HH:mm:ss");
        }
        return df.format(date);
    }
    /**
     * 根据输入的时间和输入的格式获取时间
     *
     * @param time
     * @param
     * @return
     */
    public static String getTimeFormatted(long time) {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd ");
        String dateStr = sdf.format(new Date(time * 1000));
        return dateStr;
    }



    /**
     * 将时间戳转为时间字符串
     * <p>格式为yyyy-MM-dd HH:mm:ss</p>
     *
     * @param millis 毫秒时间戳
     * @return 时间字符串
     */
    public static String millisToString(long millis) {
        SimpleDateFormat format = getSimpleDateFormat("yyyy-MM-dd HH:mm");
        return format.format(new Date(millis));
    }


    /**
     * 获取当前的月的第几天
     * @return
     */
    public static int getDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
            calendar.setTime(date);
            return calendar.get(Calendar.DAY_OF_MONTH);
    }

    public static int getWeekInt(Date date) {
        int week = 0;
        Calendar c = Calendar.getInstance();
        c.setTime(date);
        int weekday = c.get(Calendar.DAY_OF_WEEK);
        if (weekday == 1) {
            week = 7;
        } else if (weekday == 2) {
            week = 1;
        } else if (weekday == 3) {
            week = 2;
        } else if (weekday == 4) {
            week = 3;
        } else if (weekday == 5) {
            week = 4;
        } else if (weekday == 6) {
            week = 5;
        } else if (weekday == 7) {
            week = 6;
        }
        return week;
    }

}
