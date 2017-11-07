package com.renfei.tools.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
import java.util.Calendar;
import java.util.Date;

/**
 * Created by renfei on 2017/11/6.
 */
public class DateUtil {

    public static final String yyyyMMdd = "yyyyMMdd";

    public static SimpleDateFormat simpleDateFormat = new SimpleDateFormat(yyyyMMdd);

    public static Date getDate(String dateStr) {
        try {
            return simpleDateFormat.parse(dateStr);
        } catch (ParseException e) {
            e.printStackTrace();
        }
        return null;
    }

    public static String getYyyyMMdd(Date date) {
        return simpleDateFormat.format(date);
    }

    /**
     * 获取给定日期的日
     *
     * @param date
     * @return
     */
    public static int getDayOfMonth(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);
        return ca.get(Calendar.DAY_OF_MONTH);
    }

    /**
     * 获取给定日期的最后一天日期
     *
     * @param date
     * @return
     */
    public static Date getLastDayOfMonth(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        calendar.set(Calendar.DATE, calendar.getActualMaximum(Calendar.DAY_OF_MONTH));
        Date lastDayOfMonth = calendar.getTime();
        return lastDayOfMonth;
    }

    /**
     * 将日期转换为日历
     *
     * @param date 日期
     * @return 日历
     */
    private static Calendar convert(Date date) {
        Calendar calendar = Calendar.getInstance();
        calendar.setTime(date);
        return calendar;
    }
}
