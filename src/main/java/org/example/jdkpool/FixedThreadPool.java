package org.example.jdkpool;

import lombok.extern.slf4j.Slf4j;

import java.util.ArrayList;
import java.util.List;
import java.util.concurrent.*;
import java.util.concurrent.atomic.AtomicInteger;

@Slf4j(topic = "fixPool")
public class FixedThreadPool {

    //固定的线程池
    public static void func(){
        //没有救急线程
        ExecutorService pool=Executors.newFixedThreadPool(2);
        pool.execute(()->{
            log.debug("1");
        });
        pool.execute(()->{
            log.debug("2");
        });
        pool.execute(()->{
            log.debug("3");
        });
    }

    //复写工厂模式的固定线程池
    public static void func1(){
        ExecutorService pool=Executors.newFixedThreadPool(2, new ThreadFactory() {
            private AtomicInteger t=new AtomicInteger(1);
            @Override
            public Thread newThread(Runnable r) {
                return new Thread(r,"测试"+t.getAndIncrement());
            }
        });
        pool.execute(()->{
            log.debug("1");
        });
        pool.execute(()->{
            log.debug("2");
        });
        pool.execute(()->{
            log.debug("3");
        });
    }



    //submit方法
    public static void poolSubmit(){
        ExecutorService pool=Executors.newFixedThreadPool(2);
        Future<String> future=pool.submit(new Callable<String>() {
            @Override
            public String call() throws Exception {
                Thread.sleep(1000);
                return "ok";
            }
        });
        try {
            log.debug("{}",future.get());
        }catch (Exception e){
            e.printStackTrace();
        }
    }

    //单线程池
    public static void singlePool(){
        ExecutorService pool=Executors.newSingleThreadExecutor();
        pool.execute(()->{
            log.debug("1");
            int i=1/0;
        });
        pool.execute(()->{
            log.debug("2");
        });
        pool.execute(()->{
            log.debug("3");
        });
    }
    public static void main(String[] args){
//       poolInvokeAll();
//        poolShutdownNow();
        poolShutdownNow();
    }
    /**
     * 关闭线程池 shutdown函数
     * 描述:运行完线程
     * 执行完任务队列所有函数
     */
    private static void poolShutdown(){
        ExecutorService pool=Executors.newFixedThreadPool(2);
        pool.submit(()->{
            log.debug("1");
            Thread.sleep(1000);
            return "1";
        });
        pool.submit(()->{
            log.debug("2");
            Thread.sleep(2000);
            return "2";
        });
        pool.submit(()->{
            log.debug("3");
            Thread.sleep(3000);
            return "3";
        });
        log.debug("线程数关闭");
        pool.shutdown();
    }

    /**
     * 关闭线程池 shutdownNow函数
     * 描述:运行完线程
     * 执行完任务队列所有函数
     */
    private static void poolShutdownNow(){
        ExecutorService pool=Executors.newFixedThreadPool(2);
        pool.submit(()->{
            log.debug("1");
            Thread.sleep(1000);
            return "1";
        });
        pool.submit(()->{
            log.debug("2");
            Thread.sleep(2000);
            return "2";
        });
        pool.submit(()->{
            log.debug("3");
            Thread.sleep(3000);
            return "3";
        });
        pool.execute(()->{
            log.debug("123");
        });
        log.debug("线程数关闭");
        List<Runnable> runnables = pool.shutdownNow();
        System.out.println(runnables.size());
        runnables.forEach(func->{
           func.run();
        });
    }
    private static void poolInvokeAll() {
        ExecutorService pool=Executors.newFixedThreadPool(2);
        List<Callable<String>> list=new ArrayList<>();
        list.add(()->{
            log.debug("begin");
            Thread.sleep(1000);
            return "1";
        });
        list.add(()->{
            Thread.sleep(1000);
            log.debug("begin");
            return "2";
        });
        Callable callable=new Callable<String>(){
            @Override
            public String call() throws Exception {
                Thread.sleep(1000);
                return "3";
            }
        };
        list.add(callable);
        List<Future<String>> futures= null;
        try {
            futures = pool.invokeAll(list);
        } catch (InterruptedException e) {
            throw new RuntimeException(e);
        }

        futures.forEach(future->{
            try {
                log.debug("{}", future.get());
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            } catch (ExecutionException e) {
                throw new RuntimeException(e);
            }
        });
    }
}
