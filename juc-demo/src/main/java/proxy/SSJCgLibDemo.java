package proxy;

import net.sf.cglib.proxy.Enhancer;
import net.sf.cglib.proxy.MethodInterceptor;

/**
 * @author sishijie@winployee.com
 * @since 2020-08-09
 */
public class SSJCgLibDemo {

    public void test(){
        System.out.println("测试cglib");
    }

    public void test1(){
        System.out.println("测试cglib test1");
    }

    public static void main(String[] args) {

        Enhancer enhancer = new Enhancer();

        enhancer.setSuperclass(SSJCgLibDemo.class);

        enhancer.setCallback((MethodInterceptor) (o, method, objects, methodProxy) -> {
            System.out.println("before....");
            Object result = methodProxy.invokeSuper(o, objects);
            System.out.println("after....");
            return result;
        });

        SSJCgLibDemo ssjCgLibDemo = (SSJCgLibDemo) enhancer.create();
        ssjCgLibDemo.test1();
    }

}
