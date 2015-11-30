package com.cgl.stream

import org.apache.spark.{SparkContext, SparkConf}

/**
 * Created by Lenovo on 2015/9/29.
 */
class FileSourceByFunc {
//  def main(args : Array[String]): Unit = {
//    val sparkConf = new SparkConf().setMaster("local[2]").setAppName("FileSourceByFunc")
//    val sc = new SparkContext(sparkConf)
//
//    val lines = sc.textFile("data.txt")
////    val lineLength = lines.map(line : String) {
//////      MyFunctions.func1(line)
////    }
//    val totalLength = lineLength.reduce(f :(Integer, Integer) => Integer)
//    Console.println("The total file length calculate by FileSourceByFunc is: " + totalLength)
//  }
}

object MyFunctions {
//  def func1(s : String): Integer = {
//    return s.length;
//  }
}