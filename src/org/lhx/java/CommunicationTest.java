package org.lhx.java;

/**
 * @author lhx
 * @date 2019/8/13 - 19:11
 *
 * 线程通信例子：使用两个线程交替打印1-100。
 *
 * wait():执行此方法，当前线程就进入阻塞状态，并释放同步监视器。
 * notify():执行此方法，会唤醒被wati()的方法，如果有多个线程被wait，就唤醒优先级高的线程。
 * notifyAll():执行此方法，会唤醒所有被wati()的方法。
 *
 * 说明：
 * 1、三个方法必须使用在同步代码块或同步方法中
 * 2、三个方法的调用者必须是同步代码块或同步方法中的同步监视器。否则会出现java.lang.IllegalMonitorStateException异常。
 * 3、三个方法都是定义在Objcect类中。
 */
class Number implements Runnable {

    private int number = 1;

    private Object object = new Object();

    @Override
    public void run() {

        while (true) {
            synchronized (object) {
                object.notify();
                if (number <= 100) {
                    System.out.println(Thread.currentThread().getName() + ":" + number);
                    number++;
                    try {
                        //使得调用wait()的线程进入阻塞状态
                        object.wait();
                    } catch (InterruptedException e) {
                        e.printStackTrace();
                    }
                } else {
                    break;
                }
            }
        }

    }

}
public class CommunicationTest {

    public static void main(String[] args) {
        Number number = new Number();
        Thread t1 = new Thread(number);
        Thread t2 = new Thread(number);
        t1.setName("线程一");
        t2.setName("线程二");
        t1.start();
        t2.start();
    }

}
