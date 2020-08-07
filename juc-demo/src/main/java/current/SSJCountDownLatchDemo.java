package current;

import java.util.concurrent.CountDownLatch;
import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author sishijie@winployee.com
 * @since 2020-08-07
 */
public class SSJCountDownLatchDemo {

    private aqsCountDownLatch aqsCountDownLatch;

    public SSJCountDownLatchDemo(int size) {
        this.aqsCountDownLatch = new aqsCountDownLatch(size);
    }

    public void countDown(){
        aqsCountDownLatch.releaseShared(1);
    }

    public void await(){
        aqsCountDownLatch.acquireShared(1);
    }

    class aqsCountDownLatch extends AbstractQueuedSynchronizer {

        private int size;

        public aqsCountDownLatch(int size) {
            this.size = size;
        }

        @Override
        protected int tryAcquireShared(int acquire) {

            return getState() == 0 ? 1 : -1;

        }

        @Override
        protected boolean tryReleaseShared(int acquire) {

            while (true){

                int state = getState();

                if (state == 0)
                    return true;

                int next = state-acquire;

                if (compareAndSetState(state,next)){
                    return next==0;
                }
                return false;
            }

        }
    }

}
