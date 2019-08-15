package org.lhx.juc;

/**
 * @author lhx
 * @date 2019/8/15 - 15:21
 * 判断打印的“one” or “two”
 *
 * 两个同步方法，两个线程，标准打印 //one two
 * 新增Thread.sleep()给getOne()//one two
 * 新增普通方法getThree()//three one two
 * 两个普通的同步方法，两个Number对象//two one
 * 修改getOne()为静态同步方法//two one
 * 修改两个方法都为静态同步方法，一个Number对象//one two
 * 一个是静态同步方法，一个是非静态同步方法，两个Number对象//two one
 * 两个均为静态同步方法，两个Number对象// one two
 *
 * 非静态方法的锁默认为this，静态方法的锁为对应的Class实例
 * 某一个时刻内，只能有一个线程持有锁，无论有几个方法。
 */
public class TestThreadEightMonitor {
    public static void main(String[] args) {
        Number number = new Number();
        Number number2 = new Number();

        new Thread(new Runnable() {
            @Override
            public void run() {
                number.getOne();
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
//                number.getTwo();
                number2.getTwo();
            }
        }).start();
//        new Thread(new Runnable() {
//            @Override
//            public void run() {
//                number.getThree();
//            }
//        }).start();
    }
}

class Number {
//    public synchronized void getOne() {
    public static synchronized void getOne() {
        try {
            Thread.sleep(3000);
        } catch (InterruptedException e) {
            e.printStackTrace();
        }
        System.out.println("one");
    }
    public static synchronized void getTwo() {
//    public synchronized void getTwo() {

        System.out.println("two");
    }
    public void getThree() {
        System.out.println("three");
    }
}
