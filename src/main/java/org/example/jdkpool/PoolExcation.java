package org.example.jdkpool;

import lombok.extern.slf4j.Slf4j;

import java.util.concurrent.*;

@Slf4j(topic = "PoolExcation")
public class PoolExcation {
    public static void test(){
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        pool.schedule(() -> {
//            //不会抛出异常
////            int i = 1 / 0;
            /*
             * 会抛出异常
             * */
            func();
        },1L, TimeUnit.SECONDS);
    }
    public static void test2() throws ExecutionException, InterruptedException {
        ExecutorService pool=Executors.newFixedThreadPool(1);
        Future<Boolean> f=pool.submit(()->{
            log.debug("task1");
            int i=1/0;
           return true;
        });
        //不调用f.get();不会报异常
        f.get();
    }
    public static void main(String[] args) {
        try{
//            test2();
//            test();
//            test3();

        }catch (Exception e){
            e.printStackTrace();
        }

    }

    public static void test3(){
        func3();
    }
    public static void func3(){
        ScheduledExecutorService pool = Executors.newScheduledThreadPool(1);
        pool.scheduleWithFixedDelay(()->{
            log.debug("running");
            try {
                Thread.sleep(2);
            } catch (InterruptedException e) {
                throw new RuntimeException(e);
            }
        },5,1,TimeUnit.SECONDS);
    }
    public static void func() {
        try {
            log.debug("task1");
        } catch (Exception e) {
            log.debug("error", e);
        }
    }
}
