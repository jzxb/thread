package org.lhx.java;

/**
 * @author lhx
 * @date 2019/8/13 - 10:47
 *
 * 创建多线程方式二：实现Runnable接口
 * 1、创建一个实现了Runnable接口的类
 * 2、实现类去实现Runnable接口中的抽象方法run()
 * 3、创建实现类的对象
 * 4、将此对象作为参数传递到Thread类的构造器中，创建Thread类的对象
 * 5、通过Thread类的对象调用start()
 */
class MThread implements Runnable {

    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                System.out.println(Thread.currentThread().getName() + ":" + i);
            }
        }
    }
}
public class ThreadTest1 {

    public static void main(String[] args) {
        MThread mThread = new MThread();
        Thread thread = new Thread(mThread);
        thread.start();
        Thread thread1 = new Thread(mThread);
        thread1.start();
    }

}
