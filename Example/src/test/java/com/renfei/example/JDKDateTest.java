package com.renfei.example;

import org.junit.Test;

import java.time.*;
import java.time.format.DateTimeFormatter;
import java.time.temporal.TemporalAdjusters;
import java.util.Arrays;
import java.util.Comparator;
import java.util.Set;
import java.util.TreeSet;

/**
 * @ClassName JDKTest
 * @Description: TODO
 * @Author renfei
 * @Date 2019/9/3
 * @Version V1.0
 **/
public class JDKDateTest {


    public static void main(String[] args) {
        //匿名内部类
        Comparator<Integer> cpt = new Comparator<Integer>() {
            @Override
            public int compare(Integer o1, Integer o2) {
                return Integer.compare(o1, o2);
            }
        };

        TreeSet<Integer> set = new TreeSet<>(cpt);

        System.out.println("=========================");

        //使用lambda表达式
        Comparator<Integer> cpt2 = (x, y) -> Integer.compare(x, y);
        TreeSet<Integer> set2 = new TreeSet<>(cpt2);


        Arrays.asList("a", "b", "d").forEach(e -> System.out.println(e));

    }

    @Test
    public void test() {
        // 从默认时区的系统时钟获取当前的日期时间。不用考虑时区差
        LocalDateTime date = LocalDateTime.now();
        //2018-07-15T14:22:39.759
        System.out.println(date);

        System.out.println(date.getYear());
        System.out.println(date.getMonthValue());
        System.out.println(date.getDayOfMonth());
        System.out.println(date.getHour());
        System.out.println(date.getMinute());
        System.out.println(date.getSecond());
        System.out.println(date.getNano());

        // 手动创建一个LocalDateTime实例
        LocalDateTime date2 = LocalDateTime.of(2017, 12, 17, 9, 31, 31, 31);
        System.out.println(date2);
        // 进行加操作，得到新的日期实例
        LocalDateTime date3 = date2.plusDays(12);
        System.out.println(date3);
        // 进行减操作，得到新的日期实例
        LocalDateTime date4 = date3.minusYears(2);
        System.out.println(date4);
    }

    @Test
    public void test2() {
        // 时间戳  1970年1月1日00：00：00 到某一个时间点的毫秒值
        // 默认获取UTC时区
        Instant ins = Instant.now();
        System.out.println(ins);

        System.out.println(LocalDateTime.now().toInstant(ZoneOffset.of("+8")).toEpochMilli());
        System.out.println(System.currentTimeMillis());

        System.out.println(Instant.now().toEpochMilli());
        System.out.println(Instant.now().atOffset(ZoneOffset.ofHours(8)).toInstant().toEpochMilli());
    }

    @Test
    public void test3() {
        // Duration:计算两个时间之间的间隔
        // Period：计算两个日期之间的间隔

        Instant ins1 = Instant.now();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        Instant ins2 = Instant.now();
        Duration dura = Duration.between(ins1, ins2);
        System.out.println(dura);
        System.out.println(dura.toMillis());

        System.out.println("======================");
        LocalTime localTime = LocalTime.now();
        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        LocalTime localTime2 = LocalTime.now();
        Duration du2 = Duration.between(localTime, localTime2);
        System.out.println(du2);
        System.out.println(du2.toMillis());
    }

    @Test
    public void test4() {
        LocalDate localDate = LocalDate.now();

        try {
            Thread.sleep(1000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }

        LocalDate localDate2 = LocalDate.of(2016, 12, 12);
        Period pe = Period.between(localDate, localDate2);
        System.out.println(pe);
    }

    @Test
    public void test5() {
        // temperalAdjust 时间校验器
        // 例如获取下周日  下一个工作日
        LocalDateTime ldt1 = LocalDateTime.now();
        System.out.println(ldt1);

        // 获取一年中的第一天
        LocalDateTime ldt2 = ldt1.withDayOfYear(1);
        System.out.println(ldt2);
        // 获取一个月中的第一天
        LocalDateTime ldt3 = ldt1.withDayOfMonth(1);
        System.out.println(ldt3);

        LocalDateTime ldt4 = ldt1.with(TemporalAdjusters.next(DayOfWeek.FRIDAY));
        System.out.println(ldt4);

        // 获取下一个工作日
        LocalDateTime ldt5 = ldt1.with((t) -> {
            LocalDateTime ldt6 = (LocalDateTime) t;
            DayOfWeek dayOfWeek = ldt6.getDayOfWeek();
            if (DayOfWeek.FRIDAY.equals(dayOfWeek)) {
                return ldt6.plusDays(3);
            } else if (DayOfWeek.SATURDAY.equals(dayOfWeek)) {
                return ldt6.plusDays(2);
            } else {
                return ldt6.plusDays(1);
            }
        });

        System.out.println(ldt5);
    }

    @Test
    public void test6() {
        // DateTimeFormatter: 格式化时间/日期
        // 自定义格式
        LocalDateTime ldt = LocalDateTime.now();
        DateTimeFormatter formatter = DateTimeFormatter.ofPattern("yyyy年MM月dd日");
        String strDate1 = ldt.format(formatter);
        String strDate = formatter.format(ldt);
        System.out.println(strDate);
        System.out.println(strDate1);

        // 使用api提供的格式
        DateTimeFormatter dtf = DateTimeFormatter.ISO_DATE;
        LocalDateTime ldt2 = LocalDateTime.now();
        String strDate3 = dtf.format(ldt2);
        System.out.println(strDate3);

        // 解析字符串to时间
        DateTimeFormatter df = DateTimeFormatter.ofPattern("yyyy-MM-dd HH:mm:ss");
        LocalDateTime time = LocalDateTime.now();
        String localTime = df.format(time);
        LocalDateTime ldt4 = LocalDateTime.parse("2017-09-28 17:07:05", df);
        System.out.println("LocalDateTime转成String类型的时间：" + localTime);
        System.out.println("String类型的时间转成LocalDateTime：" + ldt4);
    }

    // ZoneTime  ZoneDate       ZoneDateTime
    @Test
    public void test7() {
        LocalDateTime now = LocalDateTime.now(ZoneId.of("Asia/Shanghai"));
        System.out.println(now);

        LocalDateTime now2 = LocalDateTime.now();
        ZonedDateTime zdt = now2.atZone(ZoneId.of("Asia/Shanghai"));
        System.out.println(zdt);

        Set<String> set = ZoneId.getAvailableZoneIds();
        set.stream().forEach(System.out::println);
    }

}
