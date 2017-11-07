package com.renfei.tools.date;

import java.util.Date;

/**
 * class_name: ACTCalc
 * 功能说明：ACT方式计息天数
 * creat_user: renfei
 * email: renfei_hn@163.com
 * creat_time: 2017/11/8 0:41
 **/
public class ACTCalc implements IDateCalc{


    public Long calcDate(Date startDate, Date endDate) {
        // 86400000 = 24 * 60 * 60 * 1000
        long day = endDate.getTime() / 86400000 - startDate.getTime() / 86400000;
        return day;
    }
}
