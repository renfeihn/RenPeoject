package com.renfei.example.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;

import java.io.File;
import java.util.Arrays;

/**
 * Created by renfei on 2019/5/15.
 */
public class SparkJson {


    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("Test").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);

//        JavaRDD<String> input = sc.parallelize(Arrays.asList("abc,1", "test,2"));
//        JavaRDD<SparkJava.Person> persons = input.map(s -> s.split(",")).map(s -> new SparkJava.Person(s[0], Integer.parseInt(s[1])));

//        SparkSession spark = SparkSession.builder().appName("Test").getOrCreate();

//        System.out.println(".." + persons.collect());
//        Dataset<Row> df = spark.createDataFrame(persons, SparkJava.Person.class);
//        df.show();
//
//        df.printSchema();
        SQLContext sql = new SQLContext(sc);
        File file = new File("src/main/resources/properties/employee.json");
        String path = SparkJson.class.getClass().getResource("/employee.json").toString();

        Dataset<Row> df = sql.read().json(path);

//        -- 显示数据
        df.show();
//                -- 查看数据结构
        df.printSchema();
//                -- 查看某一列
        df.select("name").show();
//                -- 查找年龄大于23（age> 23）的雇员。
        df.filter("age > 23").show();
//                -- 计算同一年龄的员工人数。
        df.groupBy("age").count().show();
    }

}
