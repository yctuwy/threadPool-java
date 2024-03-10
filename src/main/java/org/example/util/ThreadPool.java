package org.example.util;

import java.util.*;
import java.util.concurrent.TimeUnit;

public class ThreadPool {
    private TaskQueue<Runnable> taskQueue;
    private HashSet<ThreadWorker> workers=new HashSet<>();
    //最大线程数
    private Integer coreSize;
    //时间
    private Long timeOut;
    //时间单位
    private TimeUnit timeUnit;
    //初始化
    public ThreadPool(Integer coreSize, Long timeOut, TimeUnit timeUnit, Integer queueMaxSize) {
        this.coreSize = coreSize;
        this.timeOut = timeOut;
        this.timeUnit = timeUnit;
        taskQueue=new TaskQueue<>(queueMaxSize);
    }

    //执行线程
    public void execute(Runnable task){
        synchronized (workers){
            if (workers.size()<coreSize){
                ThreadWorker worker=new ThreadWorker(task);
                workers.add(worker);
                worker.start();
            }else {
                taskQueue.addTask(task);
            }
        }

    }
    class ThreadWorker extends Thread{
        private Runnable task;

        public ThreadWorker( Runnable task) {
            this.task = task;
        }
        @Override
        public void run(){
            while (task!=null||(task=taskQueue.popTask())!=null){
                try{
                    task.run();
                }catch (Exception e){
                    e.printStackTrace();
                }finally {
                    task=null;
                }

            }
        }
    }

}
