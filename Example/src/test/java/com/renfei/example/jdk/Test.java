package com.renfei.example.jdk;

import java.lang.reflect.Field;
import java.lang.reflect.ParameterizedType;
import java.lang.reflect.Type;
import java.util.List;
import java.util.Set;

public class Test {
    private Set<User> list;

    public static void main(String[] args) {
        // TODO Auto-generated method stub
        try {
            Field listField = Test.class.getDeclaredField("list");
            System.out.println(listField.getGenericType());
            //获取 list 字段的泛型参数
            ParameterizedType listGenericType = (ParameterizedType) listField.getGenericType();
            Type[] listActualTypeArguments = listGenericType.getActualTypeArguments();
            System.out.println(listActualTypeArguments[listActualTypeArguments.length - 1]);
//            for (int i = 0; i < listActualTypeArguments.length; i++) {
//                System.out.println(listActualTypeArguments[i]);
//            }
        } catch (NoSuchFieldException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        } catch (SecurityException e) {
            // TODO Auto-generated catch block
            e.printStackTrace();
        }
    }

}
