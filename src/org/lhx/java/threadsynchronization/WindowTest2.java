package org.lhx.java.threadsynchronization;

/**
 * @author lhx
 * @date 2019/8/13 - 16:03
 */
class Window2 extends Thread {
    private static int ticket = 100;

    @Override
    public void run() {
        while (true) {
            synchronized(Window2.class) {
                if (ticket > 0) {
                    System.out.println(getName() + ": 票号为：" + ticket);
                    ticket--;
                } else {
                    break;
                }
            }
        }
    }
}

public class WindowTest2 {

    public static void main(String[] args) {
        Window2 w1 = new Window2();
        Window2 w2 = new Window2();
        Window2 w3 = new Window2();

        w1.setName("窗口一");
        w2.setName("窗口二");
        w3.setName("窗口三");

        w1.start();
        w2.start();
        w3.start();
    }

}
