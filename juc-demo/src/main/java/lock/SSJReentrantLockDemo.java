package lock;

import java.util.concurrent.LinkedBlockingQueue;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.atomic.AtomicReference;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.LockSupport;
import java.util.concurrent.locks.ReentrantLock;

/**
 *
 * 可重入锁组成：
 *      锁的拥有者（线程）、锁的计数器、排队的队列
 * 原理分析：
 * 1、tryLock（）方法：
 *    判断锁的技术器是不是为0
 *       是：说明当前锁没有被其他人所拥有，使用cas方法抢占锁，抢占抢占成功，设定锁的拥有者为当前线程，return true。
 *       否：当前线程是和锁的拥有者是不是统一线程
 *          是：可以获取锁，只需将锁的计数器加一，return true。
 *          否：return false。
 * 2、lock（）方法：
 *    如果tryLock（）方法返回的是false，加入到等待队列，阻塞线程，此时可以使用LockSport.park()方法，唤醒线程的时候只需从队列中获取线程，掉用lockSport.unPark()方法。
 *
 * 3、tryUnLock()方法：
 *    3.1、校验当前线程是不是线程的拥有者，不是需要抛出IllegalMonitorStateException异常。
 *    3.2、将线程计数器减一得到最新的计数器。
 *    3.3、线程计数器为0，使用cas把锁的拥有者设置为null
 * 4、unLock()方法：
 *    tryUnLock()返回true，说明解锁成功，需要唤醒等待队列中的等待枷锁线程
 *
 *
 *
 *
 * @author sishijie@winployee.com
 * @since 2020-08-04
 */
public class SSJReentrantLockDemo implements Lock {


    //锁的拥有者
    private AtomicReference<Thread> owner = new AtomicReference<>();
    //锁计数器
    private AtomicInteger count = new AtomicInteger();

    //排队的队列
    private LinkedBlockingQueue<Thread> waiters = new LinkedBlockingQueue<>();

    @Override
    public void lock() {

        if (!tryLock()){
            //没有获取到锁，进入等待队列
            waiters.offer(Thread.currentThread());

            while (true){
                //头部元素需要单独的判断，
                Thread heard = waiters.peek();
                if (Thread.currentThread() == heard){
                    //我们只是队列里的头部元素去强锁，公平锁
                    if (!tryLock()){
                        LockSupport.park();
                    }else {
                        waiters.poll();
                        return;
                    }
                }else {
                    LockSupport.park();
                }
            }
        }

    }

    @Override
    public boolean tryLock() {

        //1、锁计数器是否为0
        int currentCount = count.get();
        if (currentCount != 0){
            //2、当前线程是否为锁的拥有者
            if (Thread.currentThread() == owner.get()){
                count.set(currentCount+1);
                return true;
            }

        }else if (count.compareAndSet(currentCount,currentCount+1)){
            //使用cas抢占锁
            owner.set(Thread.currentThread());

            return true;
        }

        return false;
    }


    @Override
    public void unlock() {

        //解锁成功需要唤醒等待队列，重新抢占锁
        if (tryUnLock()){

            Thread queueThread = waiters.poll();
            if (queueThread != null){
                LockSupport.unpark(queueThread);
            }
        }

    }

    public boolean tryUnLock(){

        //如果当前锁的拥有者不是当前线程需要抛出异常
        if (owner.get() != Thread.currentThread())
            throw new IllegalMonitorStateException();

        //判断锁计数器是否为0
        int ct = count.get();
        int nextCount = ct -1;
        //设置程序计数器
        count.set(nextCount);
        if (nextCount == 0){
            return owner.compareAndSet(Thread.currentThread(), null);
        }else {
            return false;
        }
    }

    @Override
    public void lockInterruptibly() throws InterruptedException {

    }


    @Override
    public boolean tryLock(long time, TimeUnit unit) throws InterruptedException {
        return false;
    }


    @Override
    public Condition newCondition() {
        return null;
    }
}
