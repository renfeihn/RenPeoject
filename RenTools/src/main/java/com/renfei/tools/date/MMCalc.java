package com.renfei.tools.date;

import com.renfei.tools.util.BusiUtil;
import com.renfei.tools.util.DateUtil;

import java.util.Date;

/**
 * class_name: YearMonthCalc
 * 功能说明：30D:按照每月30天(1~30号为满月，少补，多不计)计算天数
 * creat_user: renfei
 * email: renfei_hn@163.com
 * creat_time: 2017/11/7 21:36
 **/
public class MMCalc implements IDateCalc {


    public Long calcDate(Date startDate, Date endDate) {

        int year1 = startDate.getYear();
        int month1 = startDate.getMonth() + 1;

        int year2 = endDate.getYear();
        int month2 = endDate.getMonth() + 1;

        // 开始日期在当月的日
        int startMonthDay = getDayCalc(startDate);
        // 截止日期在当月的日
        int endMonthDay = getDayCalc(endDate);

        int year = year2 - year1;
        int month = month2 - month1 - 1;
        int day = (30 - startMonthDay) + (endMonthDay);

        long days = year * 30 * 12 + month * 30 + day;

        return days;
    }

    /**
     * 获取截止当前日，当月实际已计天数
     *
     * @param date
     * @return
     */
    private static int getDayCalc(Date date) {
        int day = DateUtil.getDayOfMonth(date);
        if (day > 30) {
            day = 30;
        } else if (day < 30) {
            Date last = DateUtil.getLastDayOfMonth(date);
            int lastDay = DateUtil.getDayOfMonth(last);
            if (day == lastDay) {
                day = day + (30 - lastDay);
            }
        }
        return day;
    }
}
