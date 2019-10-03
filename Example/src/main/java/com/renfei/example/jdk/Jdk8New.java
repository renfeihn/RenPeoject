package com.renfei.example.jdk;

import java.util.Arrays;
import java.util.List;
import java.util.Optional;
import java.util.function.Supplier;

/**
 * @ClassName Jdk8New
 * @Description: TODO
 * @Author renfei
 * @Date 2019/9/4
 * @Version V1.0
 **/
public class Jdk8New {


    private interface Defaulable {
        // Interfaces now allow default methods, the implementer may or
        // may not implement (override) them.
        default String notRequired() {
            return "Default implementation";
        }
    }


    private static class DefaultableImpl implements Defaulable {
    }

    private static class OverridableImpl implements Defaulable {
        @Override
        public String notRequired() {
            return "Overridden implementation";
        }
    }

    private interface DefaulableFactory {
        // Interfaces now allow static methods
        static Defaulable create(Supplier<Defaulable> supplier) {
            return supplier.get();
        }
    }

    private class DefaulableFactoryImp implements DefaulableFactory {

    }

    public static void main(String[] args) {
//        Defaulable defaulable = DefaulableFactory.create(DefaultableImpl::new);
//        System.out.println(defaulable.notRequired());
//
//        defaulable = DefaulableFactory.create(OverridableImpl::new);
//        System.out.println(defaulable.notRequired());


//        final Car car = Car.create(Car::new);
//        final List<Car> cars = Arrays.asList(car);
//        final Car police = Car.create(Car::new);
//        cars.forEach(police::follow);


    }
}