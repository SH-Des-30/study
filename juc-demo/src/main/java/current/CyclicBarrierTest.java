package current;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sishijie@winployee.com
 * @since 2020-08-07
 */
public class CyclicBarrierTest {

    private static Logger logger = LoggerFactory.getLogger(CyclicBarrierTest.class);


    public static void main(String[] args) {
        SSJCyclicBarrierDemo ssjCyclicBarrierDemo = new SSJCyclicBarrierDemo(4);

        for (int i = 0; i < 31; i++) {

            new Thread(() -> {

                ssjCyclicBarrierDemo.await();
                System.out.println("任务开始执行");
            }).start();

            try {
                Thread.sleep(500L);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }

        }

    }

}
