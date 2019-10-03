package com.renfei.example.jdk;

import java.util.function.Supplier;

/**
 * @ClassName Car
 * @Description: TODO
 * @Author renfei
 * @Date 2019/9/4
 * @Version V1.0
 **/
public class Car {
    public static Car create(final Supplier<Car> supplier) {
        return supplier.get();
    }

    public static void collide(final Car car) {
        System.out.println("Collided " + car.toString());
    }

    public void follow(final Car another) {
        System.out.println("Following the " + another.toString());
    }

    public void repair() {
        System.out.println("Repaired " + this.toString());
    }
}
