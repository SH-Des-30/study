package lock;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

import java.util.ArrayList;
import java.util.Arrays;
import java.util.List;
import java.util.concurrent.atomic.AtomicInteger;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.Lock;
import java.util.concurrent.locks.ReentrantLock;

/**
 * @author sishijie@winployee.com
 * @since 2020-08-23
 */
public class ThreadPrintStrDemo {

    private static Logger logger = LoggerFactory.getLogger(ThreadPrintStrDemo.class);

    private static final int THREAD_COUNT = 4;
    private static String[] strArray = new String[]{};

    public static void main(String[] args) throws InterruptedException {

        groupPrintArray(4);

        Thread.sleep(3000);
        System.out.println(Arrays.toString(strArray));

    }

    private static void groupPrintArray(int count){

        int size = THREAD_COUNT * count;

        ThreadBlock threadBlock = new ThreadBlock(new AtomicInteger(size));

        for (int i = 0; i < size; i++) {

            int finalI = i;
            new Thread(()->{
                threadBlock.blockThread(finalI);
            }).start();
        }

    }

    static class ThreadBlock {

        private final Lock lock = new ReentrantLock();

        private final List<Condition> conditions = new ArrayList<>(4);
        private final String[] printArray = new String[]{"A","B","C","D"};
        private volatile int state = 0;
        private final AtomicInteger size;
        private final AtomicInteger count = new AtomicInteger();

        public ThreadBlock(AtomicInteger size) {
            this.size = size;
            for (int i = 0; i < THREAD_COUNT; i++) {
                conditions.add(lock.newCondition());
            }
        }

        public void blockThread(int i){

            int index = i % THREAD_COUNT;

            while (true){
                lock.lock();
                try {
                    if (size.get() == count.get())
                        return;
                    if (index == state){
                        //转移数组
                        transformArray(printArray[index]);
                        //设置状态
                        setState(index);
                        //设置数量
                        countCompareAndSet();

                        Condition condition = getCondition(state);
                        condition.signal();
                    }else {
                        Condition condition = getCondition(index);
                        condition.await();
                    }
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                } finally {
                    lock.unlock();
                }

            }

        }

        private Condition getCondition(int state) {
            Condition condition = conditions.get(state);
            if (condition == null)
                throw new IllegalThreadStateException("线程状态不对");
            else
                return condition;
        }

        private void setState(int index) {
            int nextIndex = index+1;
            state =  nextIndex == THREAD_COUNT ? 0 : nextIndex;
        }

        private void countCompareAndSet() {
            int current = count.get();
            int next = current +1;
            count.compareAndSet(current,next);

        }

        private void transformArray(String str) {

            List<String> arrayList = new ArrayList<>(Arrays.asList(strArray));
            arrayList.add(str);

            String[] array = new String[arrayList.size()];
            strArray = arrayList.toArray(array);

        }
    }
}
