package org.lhx.java;

/**
 * @author lhx
 * @date 2019/8/2 - 15:40
 *
 * 测试Thread中的常用方法
 *
 * 1、start()：启动线程。调用当前的run()。
 * 2、run()：通常需要重写Thread类中的此方法，将创建线程要执行的操作声明在此方法中。
 * 3、currentThread():静态方法，返回执行当前代码执行的线程。
 * 4、getName():获取当前线程名字。
 * 5、setName():设置当前线程名字。
 * 6、yield():释放当前cpu执行权。
 * 7、join():在线程a中调用线程b的join()，此时线程a进入阻塞状态，直到线程b完全执行完以后线程a才结束阻塞状态。
 * 8、stop():已过世，强制结束当前线程。
 * 9、sleep(long millitime):让当前线程睡眠指定的millitime毫秒，在指定的millitime毫秒内，当前线程是阻塞状态。
 * 10、isAlive():判断当前线程是否存活。
 *
 * 线程的优先等级：
 * 	MAX_PRIORITY:10
 * 	MIN_PRIORITY:1
 * 	NORM_PRIORITY:5  默认优先级
 * 获取和设置线程的优先级：
 * 	getPriority():返回线程优先级
 * 	setPriority():改变线程的优先级
 * 说明：
 * 	线程创建时继承父线程的优先级。
 * 	低优先级只是获得调度的概率低，并非一定是在高优先级线程之后才被调用。
 */
class TestThreadMethod extends Thread {
    @Override
    public void run() {
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
//                try {
//                    sleep(1000);
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
                System.out.println(Thread.currentThread().getName() + ":" + Thread.currentThread().getPriority() + ":" + i);
            }

            if (i % 20 == 0) {
                yield();
            }
        }
    }

    public TestThreadMethod(String name) {
        super(name);
    }

    public TestThreadMethod() {
    }
}

public class ThreadMethodTest {

    public static void main(String[] args) {
        TestThreadMethod testThreadMethod = new TestThreadMethod();
        testThreadMethod.setName("线程一");
        testThreadMethod.setPriority(Thread.MAX_PRIORITY);
        testThreadMethod.start();

        TestThreadMethod testThreadMethod1 = new TestThreadMethod("线程二");
        testThreadMethod1.start();

        //给主线程命名
        Thread.currentThread().setName("主线程");
        Thread.currentThread().setPriority(Thread.MIN_PRIORITY);
        for (int i = 0; i < 100; i++) {
            if (i % 2 == 0) {
                System.out.println(Thread.currentThread().getName() + ":" + Thread.currentThread().getPriority() + ":" + i);
            }

//            if (i == 20) {
//                try {
//                    testThreadMethod.join();
//                } catch (InterruptedException e) {
//                    e.printStackTrace();
//                }
//            }
        }
    }

}
