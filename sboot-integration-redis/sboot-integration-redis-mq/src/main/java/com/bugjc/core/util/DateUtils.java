package com.bugjc.core.util;

import com.xiaoleilu.hutool.date.DateUtil;
import com.xiaoleilu.hutool.util.StrUtil;

import java.text.DateFormat;
import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * <p>日期工具类
 * <p>@author 作者 yangqing
 * <p>@version 创建时间：2016年12月20日
 */
public class DateUtils {

    public final static String format_yyyy_mm_dd = "yyyy-MM-dd";
    public final static String format_yyyymmdd = "yyyyMMdd";
    public final static String format_yyyymmddhhmmss = "yyyyMMddHHmmss";
    public final static String format_yyyymmddhhmmsssss = "yyyyMMddHHmmssSSS";


    public final static String format_yyyy_mm_dd_hh_mm_ss = "yyyy-MM-dd HH:mm:ss";
    public final static String format_yyyy__mmddhhmmss = "yyyy/MM/dd HH:mm:ss";


    /**
     * 计算两个日期之间相差的天数
     *
     * @param smdate 较小的时间
     * @param bdate  较大的时间
     * @return 相差天数
     * @throws ParseException
     */
    public static int daysBetween(Date smdate, Date bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        smdate = sdf.parse(sdf.format(smdate));
        bdate = sdf.parse(sdf.format(bdate));
        Calendar cal = Calendar.getInstance();
        cal.setTime(smdate);
        long time1 = cal.getTimeInMillis();
        cal.setTime(bdate);
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * @param date1 <String>
     * @param date2 <String>
     * @return int
     * @throws ParseException
     */
    public static int getMonthSpace(String date1, String date2)
            throws ParseException {

        int result = 0;

        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");

        Calendar c1 = Calendar.getInstance();
        Calendar c2 = Calendar.getInstance();

        c1.setTime(sdf.parse(date1));
        c2.setTime(sdf.parse(date2));
        if(c2.get(Calendar.YEAR) == c1.get(Calendar.YEAR)){
        	result = c2.get(Calendar.MONTH) - c1.get(Calendar.MONTH);
        }else if(c2.get(Calendar.YEAR) > c1.get(Calendar.YEAR)){
        	result = c2.get(Calendar.MONTH)+12 - c1.get(Calendar.MONTH);
        }

        return result == 0 ? 0 : Math.abs(result);

    }

    /**
     * 比较时间是否一致
     */
    public static int compareDate(Date date1, Date date2) {
        if (date1.getTime() > date2.getTime()) {
            return 1;
        } else if (date1.getTime() < date2.getTime()) {
            return -1;
        } else {//相等
            return 0;
        }
    }

    /**
     * 字符串的日期格式的计算
     *
     * @param smdate
     * @param bdate
     * @return
     * @throws ParseException
     */
    public static int daysBetween(String smdate, String bdate) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Calendar cal = Calendar.getInstance();
        cal.setTime(sdf.parse(smdate));
        long time1 = cal.getTimeInMillis();
        cal.setTime(sdf.parse(bdate));
        long time2 = cal.getTimeInMillis();
        long between_days = (time2 - time1) / (1000 * 3600 * 24);

        return Integer.parseInt(String.valueOf(between_days));
    }

    /**
     * 日期减
     *
     * @param date
     * @param n
     * @return
     */
    public static String dateReduce(Date date, long n) {
        SimpleDateFormat df = new SimpleDateFormat(DateUtils.format_yyyy_mm_dd_hh_mm_ss);
        String dateStr = df.format((date.getTime() - n));
        return dateStr;
    }

    /**
     * 日期加
     *
     * @param date
     * @param n
     * @return
     */
    public static String dateIncrease(Date date, long n) {
        SimpleDateFormat df = new SimpleDateFormat(DateUtils.format_yyyy_mm_dd_hh_mm_ss);
        String dateStr = df.format((date.getTime() + n));
        return dateStr;
    }


    /**
     * 获取当前月份
     *
     * @return
     */
    public static int currentMonth() {
        Calendar cal = Calendar.getInstance();
        int month = cal.get(Calendar.MONTH + 1);
        return month;
    }

    /**
     * 获取年月
     *
     * @return
     */
    public static String findYearMonth() {
        int year;
        int month;
        String date;
        Calendar calendar = Calendar.getInstance();
        year = calendar.get(Calendar.YEAR);
        month = calendar.get(Calendar.MONTH) + 1;
        date = year + "-" + (month < 10 ? "0" + month : month);
        return date;
    }


    /**
     * 获取今日开始时间
     *
     * @return
     */
    public static Date currentDayBegin() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        return c.getTime();
    }

    /**
     * 获取今日结束时间
     *
     * @return
     */
    public static Date currentDayEnd() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MINUTE, 59);
        return c.getTime();
    }

    /**
     * 获取指定日开始时间 默认当前日的开始时间
     *
     * @return
     */
    public static Date currentDayBegin(String date) {
        if (!StrUtil.isEmpty(date)) {
            Calendar c = Calendar.getInstance();
            c.setTime(DateUtil.parseDate(date));
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MINUTE, 0);
            return c.getTime();
        }
        return DateUtils.currentDayBegin();

    }

    /**
     * 获取指定日结束时间 默认当前日的结束时间
     *
     * @return
     */
    public static Date currentDayEnd(String date) {
        if (!StrUtil.isEmpty(date)) {
            Calendar c = Calendar.getInstance();
            c.setTime(DateUtil.parseDate(date));
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.SECOND, 59);
            c.set(Calendar.MINUTE, 59);
            return c.getTime();
        }
        return DateUtils.currentDayEnd();
    }


    /**
     * 获取当前月开始日期 例如：2016-1-1 00:00:00
     *
     * @return
     */
    public static Date currentMonthBegin() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, 1);
        c.set(Calendar.HOUR_OF_DAY, 0);
        c.set(Calendar.SECOND, 0);
        c.set(Calendar.MINUTE, 0);
        return c.getTime();
    }

    /**
     * 获取当前月末日期 例如：2016-1-31 23:59:59
     *
     * @return
     */
    public static Date currentMonthEnd() {
        Calendar c = Calendar.getInstance();
        c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
        c.set(Calendar.HOUR_OF_DAY, 23);
        c.set(Calendar.SECOND, 59);
        c.set(Calendar.MINUTE, 59);
        return c.getTime();
    }

    /**
     * 获取指定月开始日期 例如：2016-1-1 00:00:00 默认当前日期的月初
     *
     * @return
     */
    public static Date currentMonthBegin(String date) {
        if (!StrUtil.isEmpty(date)) {
            Calendar c = Calendar.getInstance();
            c.setTime(DateUtil.parseDate(date));
            c.set(Calendar.DAY_OF_MONTH, 1);
            c.set(Calendar.HOUR_OF_DAY, 0);
            c.set(Calendar.SECOND, 0);
            c.set(Calendar.MINUTE, 0);
            return c.getTime();
        }
        return DateUtils.currentMonthBegin();
    }

    /**
     * 获取当前月末日期 例如：2016-1-31 23:59:59 默认当前日期的月末
     *
     * @return
     */
    public static Date currentMonthEnd(String date) {
        if (!StrUtil.isEmpty(date)) {
            Calendar c = Calendar.getInstance();
            c.setTime(DateUtil.parseDate(date));
            c.set(Calendar.DAY_OF_MONTH, c.getActualMaximum(Calendar.DAY_OF_MONTH));
            c.set(Calendar.HOUR_OF_DAY, 23);
            c.set(Calendar.SECOND, 59);
            c.set(Calendar.MINUTE, 59);
            return c.getTime();
        }
        return DateUtils.currentMonthEnd();
    }


    /**
     * 判断日期大小
     *
     * @param DATE1
     * @param DATE2
     * @return
     */
    public static int compareDate(String DATE1, String DATE2) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd hh:mm");
        try {
            Date dt1 = df.parse(DATE1);
            Date dt2 = df.parse(DATE2);
            if (dt1.getTime() > dt2.getTime()) {
                return 1;
            } else if (dt1.getTime() < dt2.getTime()) {
                return -1;
            } else {
                return 0;
            }
        } catch (Exception exception) {
            exception.printStackTrace();
        }
        return 0;
    }

    /**
     * 计算 second 天后的时间
     *
     * @param date
     * @param second 正数：second天前，second天后
     * @return
     */
    public static Date getNextDay(Date date, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, second);
        date = calendar.getTime();
        return date;
    }

    /**
     * 计算 second 天后的时间
     *
     * @param dateTime
     * @param second   正数：second天前，second天后
     * @return
     * @throws ParseException
     */
    public static String getNextDay(String dateTime, int second) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM-dd");
        Date date = sdf.parse(dateTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.DAY_OF_MONTH, second);
        date = calendar.getTime();
        String addDate = sdf.format(date);
        return addDate;
    }

    public static String addMonth(String dateTime, int month) throws ParseException {
        SimpleDateFormat sdf = new SimpleDateFormat("yyyy-MM");
        Date date = sdf.parse(dateTime);
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(calendar.MONTH, month);
        date = calendar.getTime();
        String addMonth = sdf.format(date);
        return addMonth;
    }


    /**
     * 计算 second 秒后的时间
     *
     * @param date
     * @param second
     * @return
     */
    public static Date addSecond(Date date, int second) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.SECOND, second);
        return calendar.getTime();
    }

    /**
     * 计算 minute 分钟后的时间
     *
     * @param date
     * @param minute
     * @return
     */
    public static Date addMinute(Date date, int minute) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.MINUTE, minute);
        return calendar.getTime();
    }

    /**
     * 计算 hour 小时后的时间
     *
     * @param date
     * @param hour
     * @return
     */
    public static Date addHour(Date date, int hour) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.add(Calendar.HOUR, hour);
        return calendar.getTime();
    }

    /**
     * 获取月初
     *
     * @param date
     * @return
     */
    public static String getStartTime(String date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(DateUtil.parseDate(date));
        ca.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        String last = new SimpleDateFormat("yyyy-MM-dd").format(ca.getTime());
        return last;
    }

    /**
     * 获取上几个月或下几个月月初
     *
     * @param date
     * @param month 正：下几个月		负：上几个月
     * @return
     */
    public static String getStartTime(String date, int month) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(DateUtil.parseDate(date));
        ca.add(Calendar.MONTH, month);
        ca.set(Calendar.DAY_OF_MONTH, 1);//设置为1号,当前日期既为本月第一天
        String last = new SimpleDateFormat("yyyy-MM-dd").format(ca.getTime());
        return last;
    }

    /**
     * 获取月末
     *
     * @param date
     * @return
     */
    public static String getEndTime(String date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(DateUtil.parseDate(date));
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));//设置为月末,当前日期既为本月最后一天
        String last = new SimpleDateFormat("yyyy-MM-dd").format(ca.getTime());
        return last;
    }

    /**
     * 获取上几个月或下几个月月末
     *
     * @param date
     * @return 正：上几个月		负：下几个月
     */
    public static String getEndTime(String date, int month) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(DateUtil.parseDate(date));
        ca.add(Calendar.MONTH, month);
        ca.set(Calendar.DAY_OF_MONTH, ca.getActualMaximum(Calendar.DAY_OF_MONTH));//设置为月末,当前日期既为本月最后一天
        String last = new SimpleDateFormat("yyyy-MM-dd").format(ca.getTime());
        return last;
    }


    /**
     * 获取7天前或7天后
     *
     * @param date
     * @return 正：下几个周		负：上几个周
     */
    public static String getFirstDate(String date, int WEEK_OF_YEAR) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(DateUtil.parseDate(date));
        ca.add(Calendar.WEEK_OF_YEAR, WEEK_OF_YEAR);
        String last = new SimpleDateFormat("yyyy-MM-dd").format(ca.getTime());
        return last;
    }

    /**
     * 获取7天前或7天后,当前时间
     *
     * @param i
     * @return 正：下几个周		负：上几个周
     */
    public static String getdate(int i) // //获取前后日期 i为正数 向后推迟i天，负数时向前提前i天
    {
        Date dat = null;
        Calendar cd = Calendar.getInstance();
        cd.add(Calendar.DATE, i * 7);
        dat = cd.getTime();
        SimpleDateFormat dformat = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        return dformat.format(dat);
    }

    /**
     * 获取小时
     *
     * @param dateString
     * @return
     */
    public static int getHours(String dateString) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(DateUtil.parseDateTime(dateString));
        return ca.getTime().getHours();
    }


    /**
     * 获取分钟
     *
     * @param dateString
     * @return
     */
    public static int getMinutes(String dateString) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(DateUtil.parseDateTime(dateString));
        return ca.getTime().getMinutes();
    }


    /**
     * 计算两日期分钟差
     * @param startTime
     * @param endTIme
     * @return 正负分钟数
     */
    public static long timesMinBetween(Date startTime, Date endTIme) {

        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long min = 0;
        long diff = 0;

        try {
            startTime = df.parse(df.format(startTime));
            endTIme = df.parse(df.format(endTIme));
            long startDateTime = startTime.getTime();
            long endDateTime = endTIme.getTime();
            if (startDateTime > endDateTime) {
                diff = startDateTime - endDateTime;
                min = diff / (60 * 1000);
            } else {
                diff = endDateTime - startDateTime;
                min = -diff / (60 * 1000);
            }

        } catch (ParseException e) {
            e.printStackTrace();

        }
        return min;

    }


    /**
     * 计算两日期分钟差
     * @param startTime
     * @param endTime
     * @return 正负分钟数
     */
    public static long timesMinBetween(String startTime, String endTime) {
        DateFormat df = new SimpleDateFormat("yyyy-MM-dd HH:mm:ss");
        long min = 0;
        long diff = 0;
        try {
            Date startDate = df.parse(startTime);
            Date bindDate = df.parse(endTime);
            long startDateTime = startDate.getTime();
            long endDateTime = bindDate.getTime();
            if (startDateTime > endDateTime) {
                diff = startDateTime - endDateTime;
                min = diff / (60 * 1000);
            } else {
                diff = endDateTime - startDateTime;
                min = -diff / (60 * 1000);
            }

        } catch (ParseException e) {
            e.printStackTrace();
        }
        return min;

    }


    public static void main(String[] args) throws ParseException {
        long min = DateUtils.timesMinBetween("2017-08-06 13:00:00","2017-08-06 13:25:00");
        System.out.println(min);
    }


}


