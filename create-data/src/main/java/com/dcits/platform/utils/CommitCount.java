package com.dcits.platform.utils;

import java.text.DecimalFormat;
import java.text.NumberFormat;

public class CommitCount {
    private static long totalCount;
    private static long finishCount;
    private static double lastPerCent;
    private static DecimalFormat df = (DecimalFormat)NumberFormat.getInstance();

    public CommitCount() {
    }

    public static void init(long total) {
        totalCount = total;
        finishCount = 0L;
        lastPerCent = 0.0D;
    }

    public static synchronized void commit(long count) {
        finishCount += count;
        double percent = (double)finishCount / (double)totalCount * 100.0D;
        if(percent - lastPerCent > 0.009999999999D) {
            lastPerCent = percent;
            byte total = 50;
            int finish = (int)(percent / 100.0D * (double)total);
            char[] text = new char[total + 2];
            text[0] = 91;

            int sb;
            for(sb = 0; sb < finish; ++sb) {
                text[sb + 1] = 35;
            }

            for(sb = finish; sb < total; ++sb) {
                text[sb + 1] = 32;
            }

            text[total + 1] = 93;
            StringBuilder var8 = new StringBuilder();
            var8.append("\r").append(" 完成数:").append(finishCount).append("  完成百分比：").append(df.format(percent)).append("%").append(text);
            System.out.print(var8);
        }
    }

    static {
        df.applyPattern("00.00");
    }
}
