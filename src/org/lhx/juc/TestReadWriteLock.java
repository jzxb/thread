package org.lhx.juc;

import java.util.concurrent.locks.ReadWriteLock;
import java.util.concurrent.locks.ReentrantReadWriteLock;

/**
 * @author lhx
 * @date 2019/8/15 - 15:05
 */
public class TestReadWriteLock {

    public static void main(String[] args) {
        ReadWriteLockDemo readWriteLockDemo = new ReadWriteLockDemo();
        new Thread(new Runnable() {
            @Override
            public void run() {
                readWriteLockDemo.set((int) (Math.random() * 101));
            }
        }, "Write:").start();

        for (int i = 0; i < 100; i++) {
            new Thread(new Runnable() {
                @Override
                public void run() {
                    readWriteLockDemo.get();
                }
            }).start();
        }
    }

}

class ReadWriteLockDemo {
    private int number = 0;

    private ReadWriteLock lock = new ReentrantReadWriteLock();

    public void get() {
        lock.readLock().lock();
        try {
            System.out.println(Thread.currentThread().getName() + ":" + number);
        } finally {
            lock.readLock().unlock();
        }
    }

    public void set(int number) {
        lock.writeLock().lock();
        try {
            System.out.println(Thread.currentThread().getName());
            this.number = number;
        } finally {
            lock.writeLock().unlock();
        }
    }
}
