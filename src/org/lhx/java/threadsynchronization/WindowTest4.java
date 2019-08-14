package org.lhx.java.threadsynchronization;

/**
 * @author lhx
 * @date 2019/8/13 - 16:54
 * <p>
 * 使用同步方法解决继承Thread类的线程安全问题。
 */
class Window4 extends Thread {
    private static int ticket = 100;

    @Override
    public void run() {
        while (true) {
            show();
        }
    }

//    private synchronized void show() {//同步监视器 t1 t2 t3
    private static synchronized void show() {//同步监视器 当前类
        if (ticket > 0) {
            System.out.println(Thread.currentThread().getName() + ": 票号为：" + ticket);
            ticket--;
        }
    }
}

public class WindowTest4 {

    public static void main(String[] args) {
        Window4 w1 = new Window4();
        Window4 w2 = new Window4();
        Window4 w3 = new Window4();

        w1.setName("窗口一");
        w2.setName("窗口二");
        w3.setName("窗口三");

        w1.start();
        w2.start();
        w3.start();
    }

}
