package com.renfei.tools.test;

import com.renfei.tools.Print;
import com.renfei.tools.date.BaseDateCalc;
import com.renfei.tools.util.BusiUtil;
import com.renfei.tools.util.DateUtil;
import org.junit.Test;

import java.util.Calendar;
import java.util.Date;

/**
 * Created by renfei on 2017/11/6.
 */
public class DateTest {

    @Test
    public void testDate() {

        Date date1 = DateUtil.getDate("20170101");
        Date date2 = DateUtil.getDate("20170301");

        long days = date2.getTime() / 86400000 - date1.getTime() / 86400000;  //用立即数，减少乘法计算的开销

        BusiUtil.printLine(days);

    }

    /**
     * 每月30天
     * 按照每月30天(1~30号为满月，少补，多不计)计算两个时间差
     */
    @Test
    public void test1() {
        Date date1 = DateUtil.getDate("20170215");
        Date date2 = DateUtil.getDate("20170331");

        int year1 = date1.getYear();
        int month1 = date1.getMonth() + 1;
        int day1 = DateUtil.getDayOfMonth(date1);

        int year2 = date2.getYear();
        int month2 = date2.getMonth();
        int day2 = DateUtil.getDayOfMonth(date2);

        int year = year2 - year1;
        int month = month2 - month1 + 1;
        int day = day2 - day1;

        int days = year * 30 * 12 + month * 30 + day;
        BusiUtil.printLine(days);

    }


    @Test
    public void test2() {

        Date fromDate = DateUtil.getDate("20170131");
        Date endDate = DateUtil.getDate("20170201");

        BaseDateCalc baseDateCalc = new BaseDateCalc();
        long days = baseDateCalc.calcDate(fromDate, endDate, "YM");


        Print.printLine(days);

    }

    @Test
    public void test3(){
        Date endDate = DateUtil.getDate("20200223");
        Date date = DateUtil.getLastDayOfMonth(endDate);

        Print.printLine(DateUtil.getYyyyMMdd(date));

    }


}
