package org.lhx.juc;

import java.util.concurrent.atomic.AtomicInteger;

/**
 * @author lhx
 * @date 2019/8/14 - 20:09
 *
 * i++的原子性问题：i++的操作分为“读，改，写”
 *         int i = 10;
 *         i = i++;//10
 */
public class TestAtomicDemo {

    public static void main(String[] args) {
        AtomicDemo ad = new AtomicDemo();
        for (int i = 0; i < 10; i++) {
            new Thread(ad).start();
        }
    }

}

class AtomicDemo implements Runnable {

    private AtomicInteger serialNumber = new AtomicInteger();

    @Override
    public void run() {
        try {
            Thread.sleep(200);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println(Thread.currentThread().getName() + ":" + getSerialNumber());
    }

    public int getSerialNumber() {
        return serialNumber.getAndIncrement();
    }

}
