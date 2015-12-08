package com.cgl.concurrent;

import java.util.concurrent.TimeUnit;

/**
 * Created by guangliang.chen on 2015/12/8.
 */
public class Join {
    public static void main(String[] args) throws Exception {
        Thread previous = Thread.currentThread();
        for(int i=0; i<10; i++) {
            Thread thread = new Thread(new Domino(previous), String.valueOf(i));
            thread.start();
            previous = thread;//thread has the previous thread reference
        }
        TimeUnit.SECONDS.sleep(5);
        System.out.println(Thread.currentThread().getName() + " terminate in main");
    }
    static class Domino implements Runnable {
        private Thread thread;
        public Domino(Thread thread) {
            this.thread = thread;
        }
        @Override
        public void run() {
            try{
                thread.join();
            } catch (InterruptedException e) {
            }
            System.out.println(Thread.currentThread().getName() + " terminate in Domino.");
        }
    }
}
