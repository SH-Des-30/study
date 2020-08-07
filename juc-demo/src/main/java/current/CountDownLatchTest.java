package current;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sishijie@winployee.com
 * @since 2020-08-07
 */
public class CountDownLatchTest {

    private static Logger logger = LoggerFactory.getLogger(CountDownLatchTest.class);

    static SSJCountDownLatchDemo ssjCountDownLatchDemo = new SSJCountDownLatchDemo(6);

    public static void main(String[] args) {

        for (int i = 0; i < 6; i++) {
            new Thread(() -> {
                logger.info("开始准备....");
                ssjCountDownLatchDemo.countDown();
            }).start();
            try {
                Thread.sleep(1000L);
            } catch (InterruptedException e) {
                logger.error(e.getMessage(), e);
            }
        }

        ssjCountDownLatchDemo.await();

        logger.info("执行完毕。。。。");
    }


}
