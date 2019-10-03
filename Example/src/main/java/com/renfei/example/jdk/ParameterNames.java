package com.renfei.example.jdk;

import java.lang.reflect.Method;
import java.lang.reflect.Parameter;

/**
 * @ClassName ParameterNames
 * @Description: TODO
 * @Author renfei
 * @Date 2019/9/4
 * @Version V1.0
 **/
public class ParameterNames {


    public static void main(String[] args) throws Exception {
        Method method = ParameterNames.class.getMethod("main", String[].class);
        for (final Parameter parameter : method.getParameters()) {
            System.out.println("Parameter: " + parameter.getName());
        }
    }
}
