package com.cgl.stream;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import scala.Console;

/**
 * Created by Lenovo on 2015/9/29.
 */
public class JavaFileSourceByFunc {

    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setMaster("local[2]").setAppName("JavaFileSourceByFunc");
        JavaSparkContext jsc = new JavaSparkContext(sparkConf);

        JavaRDD<String> lines = jsc.textFile("data.txt");
        JavaRDD<Integer> lineLegths = lines.map(new Function<String, Integer>() {
            @Override
            public Integer call(String line) throws Exception {
                return line.length();
            }
        });
        int totalLength = lineLegths.reduce(new Function2<Integer, Integer, Integer>() {
            @Override
            public Integer call(Integer length1, Integer length2) throws Exception {
                return length1 + length2;
            }
        });

        Console.println("The total length is:" + totalLength);

    }
}
