package org.lhx.java;

import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.ThreadPoolExecutor;

/**
 * @author lhx
 * @date 2019/8/14 - 10:55
 */
class NumberThread implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i <= 100; i++) {
            if (i % 2 == 0) {
                System.out.println(Thread.currentThread().getName() + ":" + i);
            }
        }
    }

}

class NumberThread1 implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i <= 100; i++) {
            if (i % 2 != 0) {
                System.out.println(Thread.currentThread().getName() + ":" + i);
            }
        }
    }

}

public class ThreadPool {

    public static void main(String[] args) {
        ExecutorService executorService = Executors.newFixedThreadPool(10);
        ThreadPoolExecutor executor = (ThreadPoolExecutor) executorService;
        //设置线程池属性
        executor.setCorePoolSize(15);
//        executor.getKeepAliveTime();
        //适合适用于Runnable
        executorService.execute(new NumberThread());
        executorService.execute(new NumberThread1());
        //适合适用于Callable
//        executorService.submit();
        //关闭连接池
        executorService.shutdown();
    }

}
