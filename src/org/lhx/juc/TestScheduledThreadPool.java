package org.lhx.juc;

import java.util.Random;
import java.util.concurrent.*;

/**
 * @author lhx
 * @date 2019/8/15 - 16:24
 */
public class TestScheduledThreadPool {
    public static void main(String[] args) throws ExecutionException, InterruptedException {
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(5);
        for (int i = 0; i < 10; i++) {
            ScheduledFuture<Integer> schedule = pool.schedule(new Callable<Integer>() {
                @Override
                public Integer call() throws Exception {
                    int num = new Random().nextInt(100);
                    System.out.println(Thread.currentThread().getName() + ":" + num);
                    return num;
                }
            }, 1, TimeUnit.SECONDS);
            System.out.println(schedule.get());
        }
        pool.shutdownNow();
    }
}
