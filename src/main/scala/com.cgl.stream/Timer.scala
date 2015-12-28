package com.cgl.stream

/**
 * Created by guangliang.chen on 2015/12/17.
 */
object Timer {
  def oncePerSecond(callback: () => Unit): Unit = {
    while (true) {callback(); Thread sleep 1000 }
  }
  def timeFiles(): Unit = {
    println("Time flies like an arrow...")
  }
  def main (args: Array[String]){
    oncePerSecond(timeFiles)
  }
}
