package org.lhx.java;

/**
 * @author lhx
 * @date 2019/8/2 - 14:19
 * <p>
 * 多线程的创建
 * 方式一：继承于Thread类
 * 1、创建一个继承于Thread类的子类
 * 2、重写Thread类的run() --> 将此线程执行的操作声明在run()中
 * 3、创建Thread类的子类的对象
 * 4、通过此对象调用start()  ①启动当前线程 ②调用当前线程的run()
 * <p>
 * 遍历100以内的所有偶数
 */

class MyThread extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                System.out.println(Thread.currentThread().getName() + ":" + i);
            }
        }
    }
}

public class ThreadTest {

    public static void main(String[] args) {
        MyThread t1 = new MyThread();
        t1.start();
//        t1.run();
        //需要多个线程需要new多个对象，不能一个对象使用调用多个start()
        MyThread t2 = new MyThread();
        t2.start();
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                System.out.println(Thread.currentThread().getName() + ":" + i + "********main()*********");
            }
        }
        //创建Thread类的匿名子类方式
        new Thread() {
            @Override
            public void run() {
                for (int i = 0; i < 100; i++) {
                    if (i % 2 != 0) {
                        System.out.println(Thread.currentThread().getName() + ":" + i);
                    }
                }
            }
        }.start();
    }

}
