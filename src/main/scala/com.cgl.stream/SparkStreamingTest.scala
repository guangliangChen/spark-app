package com.cgl.stream

import org.apache.spark.streaming.{Seconds, StreamingContext}

/**
 * Created by chenguangliang on 2015/11/30.
 */
object SparkStreamingTest {
  //node1 nc -lk 9999
  //program arguments "local[2] node1 9999"
  def main (args: Array[String]) {
    //create StreamingContext object
    val ssc = new StreamingContext(args(0), "SparkStreamingTest_NetWorkWordCount", Seconds(1))
//    val ssc = new StreamingContext("local[2]", "SparkStreamingTest_NetWorkWordCount", Seconds(1))

    //create InputDStream
    val lines = ssc.socketTextStream(args(1),args(2).toInt)
//    val lines = ssc.socketTextStream("node1",9999)

    //operate DStream
    val words = lines.flatMap(_.split(" "))
    val wordCounts = words.map(x => (x, 1)).reduceByKey(_ + _)
    wordCounts.print()

    //start Spark Streaming
    ssc.start()
    ssc.awaitTermination()
    ssc.stop()
  }

}
