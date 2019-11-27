package com.renfei.example.jdk;

import org.apache.commons.lang3.ArrayUtils;
import org.junit.Test;

import javax.script.ScriptEngine;
import javax.script.ScriptEngineManager;
import javax.script.ScriptException;
import java.lang.reflect.Field;
import java.time.Clock;
import java.time.LocalDate;
import java.time.LocalTime;
import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.concurrent.ThreadLocalRandom;

/**
 * @ClassName Jdk8NewTest
 * @Description: TODO
 * @Author renfei
 * @Date 2019/9/4
 * @Version V1.0
 **/
public class Jdk8NewTest {


    /**
     * @MethodName: test
     * @Description: Optional 检查类例子
     * @Param: []
     * @Return: void
     * @Author: renfei
     * @Date: 2019/9/4
     **/
    @Test
    public void test() {

        Optional<String> fullName = Optional.ofNullable(null);
        System.out.println("Full Name is set? " + fullName.isPresent());
        System.out.println("Full Name: " + fullName.orElseGet(() -> "[none]"));
        System.out.println(fullName.map(s -> "Hey " + s + "!").orElse("Hey Stranger!"));

        Optional<String> firstName = Optional.of("Tom");
        System.out.println("First Name is set? " + firstName.isPresent());
        System.out.println("First Name: " + firstName.orElseGet(() -> "[none]"));
        System.out.println(firstName.map(s -> "Hey " + s + "!").orElse("Hey Stranger!"));
        System.out.println();
    }

    @Test
    public void test1() {

        final Clock clock = Clock.systemUTC();
//        System.out.println(clock.instant());
//        System.out.println(clock.millis());
//        try {
//            Thread.sleep(10);
//        } catch (InterruptedException e) {
//            e.printStackTrace();
//        }
//        System.out.println(clock.millis());


        // Get the local date and local time
        final LocalDate date = LocalDate.now();
        final LocalDate dateFromClock = LocalDate.now(clock);

        System.out.println(date);
        System.out.println(dateFromClock);

        // Get the local date and local time
        final LocalTime time = LocalTime.now();
        final LocalTime timeFromClock = LocalTime.now(clock);

        System.out.println(time);
        System.out.println(timeFromClock);
    }

    @Test
    public void test2() throws ScriptException {
        ScriptEngineManager manager = new ScriptEngineManager();
        ScriptEngine engine = manager.getEngineByName("JavaScript");

        System.out.println(engine.getClass().getName());
        System.out.println("Result:" + engine.eval("function f() { return 1; }; f() + 1;"));
    }

    @Test
    public void test3() {
        long[] arrayOfLong = new long[20000];

        Arrays.parallelSetAll(arrayOfLong,
                index -> ThreadLocalRandom.current().nextInt(1000000));

        Arrays.stream(arrayOfLong).limit(10).forEach(
                i -> System.out.print(i + " "));
        System.out.println();

        Arrays.parallelSort(arrayOfLong);
        Arrays.stream(arrayOfLong).limit(10).forEach(
                i -> System.out.print(i + " "));
        System.out.println();
    }

    @Test
    public void test4() throws Exception {
        User u = new User();
        String[] dataTypes = {"int", "long", "", "", "", "java.lang.String"};
        for (Field field : getAllFields(u)) {
            if (ArrayUtils.toString(dataTypes).contains(field.getType().getName())) {
//                System.out.println(field.getName());

//                System.out.println(field.getType().getName());
            } else {
                System.out.println(field.getName());
                for (Field field1 : getAllFields(field.getType().newInstance())) {
//                    System.out.println(field1.getName());
                }

            }


        }
    }

    public static Field[] getAllFields(Object object) {
        Class<?> clazz = object.getClass();
        List<Field> fieldList = new ArrayList<>();
        while (clazz != null) {
            fieldList.addAll(new ArrayList<>(Arrays.asList(clazz.getDeclaredFields())));
            clazz = clazz.getSuperclass();
        }
        Field[] fields = new Field[fieldList.size()];
        fieldList.toArray(fields);
        return fields;
    }
}
