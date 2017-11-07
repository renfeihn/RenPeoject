package com.renfei.tools.date;

import com.renfei.tools.util.DateUtil;

import java.util.Date;

/**
 * class_name: YearMonthCalc
 * 功能说明：30D:对年对月计算天数
 * creat_user: renfei
 * email: renfei_hn@163.com
 * creat_time: 2017/11/7 21:36
 **/
public class YMCalc implements IDateCalc {


    // 假设上一结息日是 20170215 M1 15 计算出下一结息日为 20170331     20170215 ~ 20170315 = 30天
    private Date lastCalcDate = DateUtil.getDate("20170215");
    private Date nextCalcDate = DateUtil.getDate("20170315");

    /**
     * 在一个结息周期内按照实际天数进行计提，
     * 如果实际计提天数大于本结息周期内应计天数，则不计天数，
     * 如果到截止日期实际计提天数小于本计息周期内应计天数，直接补全
     *
     * @param startDate
     * @param endDate
     * @return
     */
    public Long calcDate(Date startDate, Date endDate) {
        // 计算该结息周期应计息总天数
        int year1 = lastCalcDate.getYear();
        int month1 = lastCalcDate.getMonth() + 1;   // JDK 月份比实际月份小1 为了好理解 +1
        int day1 = DateUtil.getDayOfMonth(lastCalcDate);

        int year2 = nextCalcDate.getYear();
        int month2 = nextCalcDate.getMonth() + 1;
        int day2 = DateUtil.getDayOfMonth(nextCalcDate);

        int year = year2 - year1;
        int month = month2 - month1;
        int day = day2 - day1;

        long accruedDay = year * 30 * 12 + month * 30 + day;

        // 计算开始日期到上一结息日的实际天数
        IDateCalc dateCalc = new ACTCalc();
        long startDay = dateCalc.calcDate(lastCalcDate, startDate);

        // 计算结束日期到上一结息日的实际天数
        long endDay = dateCalc.calcDate(lastCalcDate, endDate);

        // 如果截止日期为下一结息日，直接赋值为应计天数
        long endDayDiff = dateCalc.calcDate(endDate, nextCalcDate);
        if (endDayDiff == 0) {
            endDay = accruedDay;
        }

        long days = 0;
        if (endDay > accruedDay) {
            days = accruedDay - startDay;
        } else {
            days = endDay - startDay;
        }

        // 有一种可能 (实际计息天数已经大于该结息周期内应计天数，则不计天数) days < 0
        if (days < 0) {
            days = 0;
        }

        return days;
    }
}
