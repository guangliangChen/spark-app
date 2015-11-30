package com.cgl.stream;

import org.apache.spark.SparkConf;
import org.apache.spark.api.java.JavaRDD;
import org.apache.spark.api.java.JavaSparkContext;
import org.apache.spark.storage.StorageLevel;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.api.java.JavaStreamingContext;

/**
 * Created by Lenovo on 2015/9/29.
 */
public class JavaFileSource {
    public static void main(String[] args) {

        // Create a local StreamingContext with two working thread and batch interval of 1 second
        SparkConf conf = new SparkConf().setMaster("local[2]").setAppName("JavaNetworkWordCount");
//        JavaStreamingContext jssc = new JavaStreamingContext(conf, Durations.seconds(1));
        JavaSparkContext jsc = new JavaSparkContext(conf);

        JavaRDD<String> lines = jsc.textFile("data.txt");
        JavaRDD<Integer> lineLengths = lines.map(s -> s.length());
        int totalLength = lineLengths.reduce((a, b) -> a + b);

        lineLengths.persist(StorageLevel.MEMORY_ONLY());

        System.out.println("The whole file length is:" + totalLength);

    }
}
