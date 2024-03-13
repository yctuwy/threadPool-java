package org.example.jdkpool;

import lombok.extern.slf4j.Slf4j;

import java.util.List;
import java.util.concurrent.Callable;
import java.util.concurrent.ExecutorService;
import java.util.concurrent.Executors;
import java.util.concurrent.Future;

/*
线程池饥饿模式场景处理
饥饿设计模式
 */
@Slf4j
public class PoolHunger {
    public static void main(String[] args) throws InterruptedException {
        ExecutorService pool=Executors.newFixedThreadPool(2);
        pool.execute(()->{
            log.debug("处理点餐");
            Future<String> f = pool.submit(() -> {
                log.debug("做菜");
                return "宫保鸡丁";
            });
            try {
                f.get();
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        pool.execute(()->{
            log.debug("处理点餐");
            Future<String> f = pool.submit(() -> {
                log.debug("做菜");
                return "辣子鸡";
            });
            try {
                f.get();
            }catch (Exception e){
                e.printStackTrace();
            }
        });
        Thread.sleep(1000);
        List<Runnable> runnables = pool.shutdownNow();
        for (Runnable runnable : runnables) {
            runnable.run();
        }
    }
}
