package com.cgl.stream

/**
 * Created by guangliang.chen on 2015/12/17.
 */
object TimerAnonymous {
  def oncePerSecond(callback: () => Unit): Unit = {
    while (true) {callback(); Thread sleep 1000}
  }
  def main (args: Array[String]) {
    oncePerSecond(() => println("Time flies like an arrow....."))
  }
}