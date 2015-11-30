package com.cgl.stream

import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by Lenovo on 2015/9/29.
 */
object FileSource {
  def main (args: Array[String] ) {
    val conf = new SparkConf().setMaster("local[2]").setAppName("NetworkWordCount")
    val sc = new SparkContext(conf)

    val lines = sc.textFile("data.txt")
    val lineLength = lines.map(s => s.length)
    val totalLength = lineLength.reduce((a, b) => a + b)

    Console.println("The total file length is " + totalLength)

    lineLength.persist()
  }
}
