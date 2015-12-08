package com.cgl.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * Created by guangliang.chen on 2015/12/8.
 */
public class Profiler {
    private static final ThreadLocal<Long> TIME_THREADLOCAL = new ThreadLocal<Long>() {
        protected Long initialValue() {//be executed before get method be called && method set has not be executed
            System.out.println("initialValue() start...");
            return System.currentTimeMillis();
        }
    };
    public static final void begin() {
        try {
            System.out.println("begin() start...");
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        TIME_THREADLOCAL.set(System.currentTimeMillis());//console output log will change
    }
    public static final long end() {
        System.out.println("end() start...");
        return System.currentTimeMillis() - TIME_THREADLOCAL.get();
    }
    public static void main(String[] args) {
        begin();
        try {
            Thread.sleep(1000);
            TimeUnit.SECONDS.sleep(1);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("Cost: " + end() + " ms.");
    }
}
