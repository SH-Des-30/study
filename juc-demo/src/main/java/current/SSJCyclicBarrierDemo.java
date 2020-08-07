package current;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.Collection;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author sishijie@winployee.com
 * @since 2020-08-07
 */
public class SSJCyclicBarrierDemo {

    private static Logger logger = LoggerFactory.getLogger(SSJCyclicBarrierDemo.class);

    //使用锁把任务分批

    private final ReentrantLock lock = new ReentrantLock();

    private final Condition condition = lock.newCondition();

    //任务执行的计数器
    private int count;

    private final int size;

    //任务版本
    private Object versionObj = new Object();

    public SSJCyclicBarrierDemo(int size) {
        this.size = size;
    }


    public void await(){

        final ReentrantLock lock = this.lock;
        lock.lock();

        try{

            final Object version = versionObj;

            int currentCount = ++count;

            //说明已经达到任务批次的数量，可以执行了
            if (currentCount == size){
                count = 0;
                versionObj = new Object();//下一个批次的版本
                condition.signalAll();//唤醒等待的任务
                return;
            }


            //防止有为唤醒
            while (true){
                try {
                    condition.await();
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                }

                if (version != versionObj){
                    return;//说明是一个版本阻塞的任务，需要唤醒
                }
            }

        }finally {
            lock.unlock();
        }

    }
}
