package current;

import java.util.concurrent.locks.AbstractQueuedSynchronizer;

/**
 * @author sishijie@winployee.com
 * @since 2020-08-07
 */
public class SSJSemaphoreDemo {

    private aqsSemaphore aqsSemaphore;

    public SSJSemaphoreDemo(int count) {
        this.aqsSemaphore = new aqsSemaphore(count);
    }

    public void acquire(int acquire){
        aqsSemaphore.acquireShared(acquire);
    }

    public void release(int acquire){
        aqsSemaphore.releaseShared(acquire);
    }

    class aqsSemaphore extends AbstractQueuedSynchronizer {

        private int count ;

        public aqsSemaphore(int count) {
            this.count = count;
        }

        @Override
        protected int tryAcquireShared(int arg) {

            int state = getState();

            int nextState = state +arg;

            if (nextState <= count){
                if (compareAndSetState(state,nextState)){
                    return 1;
                }
            }
            return -1;
        }

        @Override
        protected boolean tryReleaseShared(int arg) {

            int state = getState();

            return compareAndSetState(state, state - arg);
        }
    }


}
