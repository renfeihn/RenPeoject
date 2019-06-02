package com.renfei.example.spark;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.sql.Dataset;
import org.apache.spark.sql.Row;
import org.apache.spark.sql.SQLContext;
import org.apache.spark.sql.SparkSession;

import java.io.Serializable;
import java.util.Arrays;

/**
 * Created by renfei on 2019/5/15.
 */
public class SparkJava {


    public static class Person implements Serializable {
        private static final long serialVersionUID = -6259413972682177507L;
        private String name;
        private int age;

        public Person(String name, int age) {
            this.name = name;
            this.age = age;
        }

        public String toString() {
            return name + ": " + age;
        }

        public String getName() {
            return name;
        }

        public void setName(String name) {
            this.name = name;
        }

        public int getAge() {
            return age;
        }

        public void setAge(int age) {
            this.age = age;
        }
    }


    public static void main(String[] args) {
        SparkConf conf = new SparkConf().setAppName("Test").setMaster("local");
        JavaSparkContext sc = new JavaSparkContext(conf);

        JavaRDD<String> input = sc.parallelize(Arrays.asList("abc,1", "test,2"));
        JavaRDD<Person> persons = input.map(s -> s.split(",")).map(s -> new Person(s[0], Integer.parseInt(s[1])));

        SparkSession spark = SparkSession.builder().appName("Test").getOrCreate();

        System.out.println(".." + persons.collect());
        Dataset<Row> df = spark.createDataFrame(persons, Person.class);
        df.show();

        df.printSchema();
        SQLContext sql = new SQLContext(spark);
        sql.registerDataFrameAsTable(df, "person");
        sql.sql("SELECT * FROM person WHERE age > 1").show();
        sc.close();
    }

}