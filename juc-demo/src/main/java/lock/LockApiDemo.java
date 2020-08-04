package lock;

import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;
import java.util.logging.Logger;

/**
 * Lock 接口中api的简单运用
 *
 * void lock() 获取锁，获取不到就等待
 *
 * void lockInterruptibly() throws InterruptedException; 终止锁，直到线程中断才会停止，不然会一直等待下去
 *
 * boolean tryLock(); 尝试获取锁，获取不到直接返回false
 *
 * void unlock() 解锁
 *
 * Condition newCondition() 锁的条件，丰富锁的实现场景
 *
 * @author sishijie@winployee.com
 * @since 2020-08-04
 */
public class LockApiDemo {


    private static Lock lock = new ReentrantLock();

    /**
     * 测试获取锁的场景
     * 当线程掉用了interrupt方法之后，线程不是马上终止的，并且是可以获取到锁的
     * interrupt是会标记一个线程的状态为终止状态，在线程掉用 wait、join、sleep方法会抛出InterruptException
     * 掉用者在try-catch中去终止线程，这样终止线程更加的优雅。
     */
    private void testGetLock(){

        lock.lock();

        Thread thread = new Thread(new Runnable() {
            public void run() {
                System.out.println("准备获取锁");
                lock.lock();//线程中断了，无需解锁了
                System.out.println("已经获取到锁");
            }
        });

        thread.start();

        try {
            Thread.sleep(2000L);
            thread.interrupt();
            System.out.println("线程中断了。。。");
            Thread.sleep(3000L);
            lock.unlock();
            System.out.println("已经解锁");
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }

    /**
     *lock.lockInterruptibly() 会一直等待下去，直到线程中断
     */
    private void testLockInterruptibly(){

        lock.lock();

        Thread thread = new Thread(new Runnable() {
            public void run() {
                System.out.println("准备获取锁");
                try {
                    lock.lockInterruptibly();
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                System.out.println("锁中断了");

                lock.lock();//线程中断了，无需解锁了

                System.out.println("已经获取到锁");
            }
        });

        thread.start();

        try {
            Thread.sleep(2000L);
            thread.interrupt();
            System.out.println("线程中断");
            Thread.sleep(3000L);
            lock.unlock();
            System.out.println("已经解锁");
        } catch (InterruptedException e) {
            System.out.println(e.getMessage());
        }
    }


    public static void main(String[] args) {
        LockApiDemo lockApiDemo = new LockApiDemo();
//        testGetLock();
        lockApiDemo.testLockInterruptibly();
    }


}
