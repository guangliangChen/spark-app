package com.cgl.stream;

import com.google.common.collect.Lists;
import org.apache.spark.SparkConf;
import org.apache.spark.api.java.function.FlatMapFunction;
import org.apache.spark.api.java.function.Function;
import org.apache.spark.api.java.function.Function2;
import org.apache.spark.api.java.function.PairFunction;
import org.apache.spark.streaming.Durations;
import org.apache.spark.streaming.Seconds;
import org.apache.spark.streaming.api.java.JavaDStream;
import org.apache.spark.streaming.api.java.JavaPairDStream;
import org.apache.spark.streaming.api.java.JavaPairReceiverInputDStream;
import org.apache.spark.streaming.api.java.JavaStreamingContext;
import org.apache.spark.streaming.kafka.KafkaUtils;

import java.util.HashMap;
import java.util.Map;
import java.util.regex.Pattern;

import scala.Tuple2;

/**
 * Created by Lenovo on 2015/10/10.
 */
public class JavaKafkaStream {
    private static final Pattern SPACE = Pattern.compile(" ");

    //args(1.kafkaZookeeper IP:Port 2.groupId for this consumer 3.topic names contacted by"," 4.partition numbers)
    public static void main(String[] args) {
        SparkConf sparkConf = new SparkConf().setAppName("JavaKafkaStream");

        JavaStreamingContext jssc = new JavaStreamingContext(sparkConf, Durations.seconds(3));

        int numThreads = Integer.valueOf(args[3]);
        Map<String, Integer> topicMap = new HashMap<String, Integer>();
        String[] topics = args[2].split(",");
        for(String topic : topics) {
            topicMap.put(topic, numThreads);
        }

//        * @param jssc      JavaStreamingContext object
//                * @param zkQuorum  Zookeeper quorum (hostname:port,hostname:port,..)
//        * @param groupId   The group id for this consumer
//                * @param topics    Map of (topic_name -> numPartitions) to consume. Each partition is consumed
        JavaPairReceiverInputDStream<String, String> messages = KafkaUtils.createStream(jssc, args[0], args[1], topicMap);

        JavaDStream<String> lines = messages.map(new Function<Tuple2<String, String>, String>() {
            public String call(Tuple2<String, String> tuple2) {
                return tuple2._2();
            }
        });

        JavaDStream<String> words = lines.flatMap(new FlatMapFunction<String, String>() {
            public Iterable<String> call(String s) throws Exception {
                return Lists.newArrayList(SPACE.split(s));
            }
        });

        JavaPairDStream<String, Integer> wordCounts = words.mapToPair(new PairFunction<String, String, Integer>() {
            public Tuple2<String, Integer> call(String s) throws Exception {
                return new Tuple2<String, Integer>(s, 1);
            }
        }).reduceByKey(new Function2<Integer, Integer, Integer>() {
            public Integer call(Integer v1, Integer v2) throws Exception {
                return v1 + v2;
            }
        });

        wordCounts.print();

        jssc.start();
        jssc.awaitTermination();
    }

}
