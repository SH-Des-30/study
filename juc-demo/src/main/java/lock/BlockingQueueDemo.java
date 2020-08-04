package lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.sql.Connection;
import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author sishijie@winployee.com
 * @since 2020-08-04
 */
public class BlockingQueueDemo {

    private static Logger logger = LoggerFactory.getLogger(BlockingQueueDemo.class);


    public static void main(String[] args) throws InterruptedException {

        SSJBlockQueue ssjBlockQueue = new SSJBlockQueue(6);

        Thread thread = new Thread(() -> {
            for (int i = 0; i < 20; i++) {
                ssjBlockQueue.put("元素"+i);
            }
        });

        thread.start();

        for (int i = 0; i < 10; i++) {
            ssjBlockQueue.take();
            Thread.sleep(1000L);
        }

    }
}

class SSJBlockQueue{
    private static Logger logger = LoggerFactory.getLogger(SSJBlockQueue.class);

    private List<Object> objects = new ArrayList<>();

    private static Lock lock = new ReentrantLock();

    private Condition putCondition = lock.newCondition();

    private Condition takeCondition = lock.newCondition();

    private int length;

    public SSJBlockQueue(int length) {
        this.length = length;
    }

    public void put(String obj){

        //先去获取锁，没有获取到锁之前，Condition.signal 在Condition.await之前会出现死锁的情况
        lock.lock();

        try {
           while (true){
               if (objects.size() < length){
                   objects.add(obj);
                   logger.info("向队列中添加元素：{}",obj);
                   takeCondition.signal();
                   return;
               }else {
                   putCondition.await();
               }
           }
        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        } finally {
            lock.unlock();
        }
    }

    public Object take(){

        lock.lock();

        try {

            if (objects.size() > 0){
                Object take = objects.remove(0);
                logger.info("移除元素：{}",take);
                putCondition.signal();
                return take;
            }else {
                takeCondition.await();
            }

        } catch (InterruptedException e) {
            logger.error(e.getMessage(), e);
        } finally {
            lock.unlock();
        }
        return null;
    }

}
