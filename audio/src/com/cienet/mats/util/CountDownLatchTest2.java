package com.cienet.mats.util;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.TimeUnit;

public class CountDownLatchTest2 {
    private static CountDownLatch latch = new CountDownLatch(3);

    public static void main(String[] args) throws InterruptedException{

        new Thread(){
            public void run()
            {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("0 " + Thread.currentThread().getName());
                latch.countDown();
            };
        }.start();

        new Thread(){
            public void run()
            {
                try {
                    Thread.sleep(1000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("1 " + Thread.currentThread().getName());

                latch.countDown();
            };
        }.start();

        new Thread(){
            public void run()
            {
                try {
                    Thread.sleep(500);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("2 " + Thread.currentThread().getName());

                latch.countDown();
            };
        }.start();

        latch.await(1800, TimeUnit.MILLISECONDS);
        System.out.println(Thread.currentThread().getName());
    }

}
