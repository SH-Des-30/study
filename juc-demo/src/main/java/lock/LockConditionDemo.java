package lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;


/**
 * @author sishijie@winployee.com
 * @since 2020-08-04
 */
public class LockConditionDemo {

    private static Logger logger = LoggerFactory.getLogger(LockConditionDemo.class);
    private static final Lock lock = new ReentrantLock();

    private static final Condition condition = lock.newCondition();


    /**
     * 测试Condition的基本用法，需要注意，在await方法之前，使用signal，会出现思索的现象。
     *
     * Condition也是对象监听器，和object.wait方法区别：Condition的等待队列支持多个。
     *
     */
    public static void main(String[] args) {


        Thread thread = new Thread(() -> {

            lock.lock();

            try {
                logger.info("当前线程：{},获得锁",Thread.currentThread().getName());
                try {
                    condition.await();
                    logger.info("当前线程：{},开始执行了",Thread.currentThread().getName());
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                }

            }finally {
                lock.unlock();
            }

        });

        thread.start();

        try {
            Thread.sleep(2000L);
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        }

        lock.lock();
        condition.signal();//通知线程需要先获取到锁
        lock.unlock();

    }

}
