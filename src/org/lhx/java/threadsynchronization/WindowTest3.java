package org.lhx.java.threadsynchronization;

/**
 * @author lhx
 * @date 2019/8/13 - 16:49
 *
 * 使用同步方法解决实现Runnable接口的线程安全问题。
 *
 */
class Window3 implements Runnable {

    private int ticket = 100;

    @Override
    public void run() {
        while (true) {
            show();
        }
    }

    private synchronized void show() {//同步监视器：this
        if (ticket > 0) {
            System.out.println(Thread.currentThread().getName() + ": 票号为：" + ticket);
            ticket--;
        }
    }

}

public class WindowTest3 {

    public static void main(String[] args) {
        Window3 w = new Window3();

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
