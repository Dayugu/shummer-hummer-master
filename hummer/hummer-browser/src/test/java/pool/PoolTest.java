package pool;

import com.hummer.browser.Browser;
import com.hummer.browser.pool.BrowserPool;
import com.hummer.browser.pool.BrowserPoolFactory;
import org.apache.commons.pool2.impl.AbandonedConfig;
import org.apache.commons.pool2.impl.GenericObjectPool;
import org.apache.commons.pool2.impl.GenericObjectPoolConfig;

/**
 * @Discribe
 * @Author gzy
 * @Date 2018/10/23 15:56
 */
public class PoolTest {

    static String url1 = "http://www.163.com/";
    static String url2 = "http://www.ifeng.com/";

    public static void singleBrowser() throws InterruptedException {
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();

        config.setMinIdle(2);
        config.setMaxIdle(3);
        config.setMaxTotal(3);
        config.setLifo(false);
        //AbandonedConfig管理连接池中处于ALLOCATED（被使用）状态的连接
        AbandonedConfig abandonedConfig = new AbandonedConfig();
        abandonedConfig.setRemoveAbandonedOnBorrow(true);//标记从objectPool里"借"出时,是否将一些认为无用的pooledObject给remove掉
        abandonedConfig.setRemoveAbandonedTimeout(120);//当pooledObject处于allocated状态下，但是超过该时长没有用过，则abandon
        BrowserPoolFactory factory = new BrowserPoolFactory();

        BrowserPool pool = new BrowserPool(config,factory,abandonedConfig);

        String url ="https://www.163.com/";
        //启动单个browser
        System.out.println("borrowObject....");
        Browser browser = pool.getBrowser();
        //System.out.println("当前browser： "+browser+" 已创建浏览器个数："+pool.getCreatedCount()+" 剩余浏览器个数："+pool.getBorrowedCount());
        System.out.println("打开目标网页....");
        browser.get(url,null);

        Thread.sleep(100*1000);
        System.out.println(Thread.currentThread().getName()
                +" is over!"+" browser port:"+browser.getContext().getPort());

        //browser.destoryBrowser();
    }

    /**
     * GenericObjectPool测试
     */
    public static void genericObjectPoolConfigTest() throws Exception {
        //配置连接池
        GenericObjectPoolConfig config = new GenericObjectPoolConfig();

        config.setMinIdle(2);
        config.setMaxIdle(3);
        config.setMaxTotal(3);
        config.setLifo(false);

        //AbandonedConfig管理连接池中处于ALLOCATED（被使用）状态的连接
        AbandonedConfig abandonedConfig = new AbandonedConfig();
        abandonedConfig.setRemoveAbandonedOnBorrow(true);//标记从objectPool里"借"出时,是否将一些认为无用的pooledObject给remove掉
        abandonedConfig.setRemoveAbandonedTimeout(120);//当pooledObject处于allocated状态下，但是超过该时长没有用过，则abandon

        //EvictionConfig管理连接池中处于空闲状态的连接

        BrowserPoolFactory factory = new BrowserPoolFactory();

        GenericObjectPool<Browser> pool = new GenericObjectPool<Browser>(factory, config, abandonedConfig);
        pool.setTestOnReturn(true);
        String url ="https://www.163.com/";

        for (int i = 0; i < 3; i++ ){
            System.out.println("borrowObject....");
            Browser browser = pool.borrowObject();
            //System.out.println("当前browser： "+browser+" 已创建浏览器个数："+pool.getCreatedCount()+" 剩余浏览器个数："+pool.getBorrowedCount());
            System.out.println("打开目标网页....");
            browser.get(url,null);

            if (i==1){
                System.out.println("线程开始休眠....");
                Thread.sleep(1000*70);
            }
            System.out.println(Thread.currentThread().getName()
                    +" is over!"+" browser port:"+browser.getContext().getPort());

            //browser.destoryBrowser();
            //System.out.println("returnObject....");
            pool.returnObject(browser);
            System.out.println("-------------------------------");
        }

    }

    /**
     * BrowserPool封装测试
     */
    public static void browserPoolTest(){

        GenericObjectPoolConfig config = new GenericObjectPoolConfig();
        config.setMinIdle(3);
        config.setMaxTotal(3);
        config.setMaxIdle(3);
        //config.setMaxWaitMillis(1000);
        config.setLifo(true);
//AbandonedConfig管理连接池中处于ALLOCATED（被使用）状态的连接
        AbandonedConfig abandonedConfig = new AbandonedConfig();
        abandonedConfig.setRemoveAbandonedOnBorrow(true);//标记从objectPool里"借"出时,是否将一些认为无用的pooledObject给remove掉
        abandonedConfig.setRemoveAbandonedTimeout(120);//当pooledObject处于allocated状态下，但是超过该时长没有用过，则abandon
        BrowserPoolFactory factory = new BrowserPoolFactory();

        BrowserPool pool = new BrowserPool(config,factory,abandonedConfig);

        /*Set<DefaultPooledObjectInfo> defaultPooledObjectInfos = pool.internalPool.listAllObjects();
        for (DefaultPooledObjectInfo info : defaultPooledObjectInfos){

            System.out.println(info.toString());

        }*/

        forTheadTest(pool);



    }

    public static void forTheadTest(BrowserPool pool){

        for (int i = 0;i <= 5; i++ ){

            if (i == 1){
                final int a = i;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("--------------------------");
                        Browser browser = pool.getBrowser();
                        Thread.currentThread().setName("Thread"+a);
                        try {
                            Thread.sleep(1000* 100);
                        } catch (InterruptedException e) {
                            e.printStackTrace();
                        }
                        browser.get(url1,null);
                        pool.returnBrowser(browser);

                        System.out.println(Thread.currentThread().getName()
                                +" is over!"+" browser:"+browser.toString());
                    }
                }).start();
            }else{
                final int a = i;
                new Thread(new Runnable() {
                    @Override
                    public void run() {
                        System.out.println("--------------------------");
                        Browser browser = pool.getBrowser();
                        Thread.currentThread().setName("Thread"+a);

                        browser.get(url1,null);
                        pool.returnBrowser(browser);

                        System.out.println(Thread.currentThread().getName()
                                +" is over!"+" browser:"+browser.toString());
                    }
                }).start();
            }
        }
    }


    public static void singleThreadTest(BrowserPool pool){
        new Thread(new Runnable() {
            @Override
            public void run() {
                Browser browser = pool.getBrowser();
                Thread.currentThread().setName("firstThread");
                browser.get(url1,null);
                pool.returnBrowser(browser);

                System.out.println(Thread.currentThread().getName()
                        +" is over!"+" browser port:"+browser.getContext().getPort());
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Browser browser = pool.getBrowser();
                Thread.currentThread().setName("secondThread");
                browser.get(url2, null);
                try {
                    Thread.sleep(5000);
                } catch (InterruptedException e) {
                    e.printStackTrace();
                }
                browser.get(url1,null);
                //pool.returnBrowser(browser);
                System.out.println(Thread.currentThread().getName()
                        +" is over!"+" browser port:"+browser.getContext().getPort());
            }
        }).start();

        new Thread(new Runnable() {
            @Override
            public void run() {
                Browser browser = pool.getBrowser();
                Thread.currentThread().setName("thridThread");
                browser.get(url2,null);

                pool.returnBrowser(browser);

                System.out.println(Thread.currentThread().getName()
                        +" is over!"+" browser port:"+browser.getContext().getPort());
            }
        }).start();

    }



    /**
     * 测试
     * @param args
     */
    public static void main(String[] args) throws Exception{
        //PhantomJSDriver driver = new PhantomJSDriver();

        singleBrowser();
        //browserPoolTest();
        //genericObjectPoolConfigTest();
    }
}
