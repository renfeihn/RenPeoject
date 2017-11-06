package com.renfei.tools.test;

import com.renfei.tools.Print;
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

        long day = date2.getTime() / 86400000 - date1.getTime() / 86400000;  //用立即数，减少乘法计算的开销

        Print.line(day);

    }

    /**
     * 对年对月
     * 按照每月30天计算两个时间差
     */
    @Test
    public void test1() {
        Date date1 = DateUtil.getDate("20170215");
        Date date2 = DateUtil.getDate("20170331");

        int year1 = date1.getYear();
        int month1 = date1.getMonth() + 1;
        int day1 = getDayOfMonth(date1);

        int year2 = date2.getYear();
        int month2 = date2.getMonth();
        int day2 = getDayOfMonth(date2);

        int year = year2 - year1;
        int month = month2 - month1 + 1;
        int day = day2 - day1;

        int calcDay = year * 30 * 12 + month * 30 + day;
        Print.line(calcDay);

    }

    @Test
    public void test2() {
        Date date1 = DateUtil.getDate("20170215");
        Date date2 = DateUtil.getDate("20170331");

        int year1 = date1.getYear();
        int month1 = date1.getMonth() + 1;
        int day1 = getDayOfMonth(date1);

        int year2 = date2.getYear();
        int month2 = date2.getMonth();
        int day2 = getDayOfMonth(date2);

        int year = year2 - year1;
        int month = month2 - month1 + 1;
        int day = day2 - day1;

        int calcDay = year * 30 * 12 + month * 30 + day;
        Print.line(calcDay);

    }


    private static int getDayOfMonth(Date date) {
        Calendar ca = Calendar.getInstance();
        ca.setTime(date);

        return ca.get(Calendar.DAY_OF_MONTH);
    }


}
