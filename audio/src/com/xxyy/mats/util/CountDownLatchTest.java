package com.cienet.mats.util;

import java.util.concurrent.CountDownLatch;

public class CountDownLatchTest {
    public void timeTasks(int nThreads, final Runnable task) throws InterruptedException{
        final CountDownLatch startGate = new CountDownLatch(1);
        final CountDownLatch endGate = new CountDownLatch(nThreads);

        for(int i = 0; i < nThreads; i++){
            Thread t = new Thread(){
                public void run(){
                    try{
                        System.out.println("before start await");

                        startGate.await();

                        System.out.println("after start await");

                        try{
                            System.out.println("before run");
                            task.run();
                            System.out.println("after run");
                        }finally{
                            System.out.println("before  endGate down");

                            endGate.countDown();
                        }
                    }catch(InterruptedException ignored){

                    }

                }
            };
            t.start();
        }

        long start = System.nanoTime();
        System.out.println("打开闭锁");
        startGate.countDown();
        System.out.println("after start down");
        endGate.await();
        long end = System.nanoTime();
        System.out.println("闭锁退出，共耗时" + (end-start));
    }

    public static void main(String[] args) throws InterruptedException{
        CountDownLatchTest test = new CountDownLatchTest();
        test.timeTasks(4, test.new RunnableTask());
    }

    class RunnableTask implements Runnable{

        @Override
        public void run() {
            System.out.println("当前线程为：" + Thread.currentThread().getName());

        }
    }
}
