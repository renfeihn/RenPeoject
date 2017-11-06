package com.renfei.tools.util;

import java.text.ParseException;
import java.text.SimpleDateFormat;
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

}
