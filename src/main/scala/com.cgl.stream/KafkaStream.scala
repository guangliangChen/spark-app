package com.cgl.stream

import org.apache.spark.streaming.{Seconds, StreamingContext}
import org.apache.spark.{SparkConf, SparkContext}

/**
 * Created by Lenovo on 2015/10/10.
 */
object KafkaStream {
  def main (args: Array[String]) {

    val Array(zkQuorum, group, topics, numThreads) = args

    val sparkConf = new SparkConf().setAppName("KafkaWordCount")
    val scc = new StreamingContext(sparkConf, Seconds(3))
    scc.checkpoint("checkpoint")


  }
}
