package com.renfei.tools.date;

import com.renfei.tools.util.BusiUtil;

import java.util.Date;

/**
 * Created by renfei on 2017/11/7.
 */
public class BaseDateCalc {

    /**
     * 功能说明：给定日期区间，计算日期区间的天数
     * param: fromDate   开始日期
     * param: toDate     结束日期
     * param: monthType  月基准 ACT/30[YM：对年对月/MM：每月标准30天]
     * return: 日期之间的天数
     * creat_date: 2017/11/7 21:42
     **/
    public Long calcDate(Date fromDate, Date endDate, String monthType) {
        IDateCalc dateCalc;
        if (BusiUtil.isNull(monthType)) {
            throw new RuntimeException("monthType must not null");
        }
        dateCalc = (IDateCalc) getClass(monthType);

        long days = dateCalc.calcDate(fromDate, endDate);

        BusiUtil.printLine("计算的天数" + days);

        return days;
    }

    private static Object getClass(String type) {
        String fullClassName = "com.renfei.tools.date." + type + "Calc";
        Class<?> obj = null;
        try {
            obj = Class.forName(fullClassName);
            return obj.newInstance();
        } catch (ClassNotFoundException e) {
            e.printStackTrace();
        } catch (InstantiationException e) {
            e.printStackTrace();
        } catch (IllegalAccessException e) {
            e.printStackTrace();
        }

        return null;
    }

}
