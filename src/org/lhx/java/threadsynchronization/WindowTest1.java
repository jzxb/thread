package org.lhx.java.threadsynchronization;

/**
 * @author lhx
 * @date 2019/8/13 - 15:46
 *
 * 在Java中，通过同步机制，来解决线程的安全问题。
 * 方式一：同步代码块
 *  synchronized(同步监视器) {
 *      //需要被同步的代码
 *  }
 *
 *  说明：
 *      1、操作共享数据的代码，即为需要同步的代码。
 *      2、共享数据：多个线程共同操作的变量。
 *      3、同步监视器：俗称，锁。任何一个类的对象都可以充当锁。
 *          要求：多个线程必须共用同一把锁。
 *      4、在实现Runnable接口创建多线程的方式中，可以考虑使用this充当同步监视器
 *      5、在继承Thread类创建多线程的方式中，慎用this充当同步监视器，可以考虑使用当前类来充当同步监视器。。
 * 方式二：同步方法
 *
 *
 * 同步的方式解决了线程的安全问题。
 * 操作同步代码的同时，只能有一个线程参与，其他线程等待，相当于是y一个单线程的过程。
 */
class Window1 implements Runnable {

    private int ticket = 100;

    Object obj = new Object();

    @Override
    public void run() {
        while (true) {
//            synchronized (obj) {
            synchronized (this) {
                if (ticket > 0) {
                    System.out.println(Thread.currentThread().getName() + ": 票号为：" + ticket);
                    ticket--;
                } else {
                    break;
                }
            }
        }
    }

}

public class WindowTest1 {

    public static void main(String[] args) {
        Window1 w = new Window1();

        Thread t1 = new Thread(w);
        Thread t2 = new Thread(w);
        Thread t3 = new Thread(w);

        t1.setName("窗口一");
        t2.setName("窗口二");
        t3.setName("窗口三");

        t1.start();
        t2.start();
        t3.start();
    }

}
