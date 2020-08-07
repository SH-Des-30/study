package current;

import org.slf4j.Logger;
import org.slf4j.LoggerFactory;

/**
 * @author sishijie@winployee.com
 * @since 2020-08-07
 */
public class SemaphoreDemoTest {

    private static Logger logger = LoggerFactory.getLogger(SemaphoreDemoTest.class);

    public static void main(String[] args) {

        SSJSemaphoreDemo ssjSemaphoreDemo = new SSJSemaphoreDemo(6);

        for (int i = 0; i < 100; i++) {

            new Thread(() -> {

                ssjSemaphoreDemo.acquire(1);
                logger.info("获取到了信号。。。。");

                try {
                    Thread.sleep(2000L);
                } catch (InterruptedException e) {
                    logger.error(e.getMessage(), e);
                }

                System.out.println("执行完了");
                ssjSemaphoreDemo.release(1); //执行完了，就释放信号量

            }).start();
        }


    }



}
