package org.example.util;

import javafx.concurrent.Worker;

import java.util.*;
import java.util.concurrent.TimeUnit;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class ThreadPool {
    private TaskQueue<Runnable> taskQueue;
    private HashSet<Worker> workers=new HashSet<>();
    //最大线程数
    private Integer threadMaxSize;
    //最小线程数
    private Integer threadMinSize;
    //时间
    private Long timeOut;
    //时间单位
    private TimeUnit timeUnit;

    public ThreadPool(TaskQueue<Runnable> taskQueue, Integer threadMaxSize,
                      Integer threadMinSize, Long timeOut, TimeUnit timeUnit,Integer queueMaxsize) {
        this.taskQueue = taskQueue;
        this.threadMaxSize = threadMaxSize;
        this.threadMinSize = threadMinSize;
        this.timeOut = timeOut;
        this.timeUnit = timeUnit;
        taskQueue=new TaskQueue<>(queueMaxsize);
    }
    //执行线程


}
class TaskQueue<T>{
    public TaskQueue(Integer maxSize) {
        this.maxSize = maxSize;
    }

    //任务队列最大长度
    private Integer maxSize;
    //锁
    private ReentrantLock queueLock=new ReentrantLock();
    private Condition fullWaitSet=queueLock.newCondition();
    private Condition emptyWaitSet=queueLock.newCondition();
    //消息队列容器
    private Deque<T> queue=new ArrayDeque<>();

    public void addTask(T runnable){
        queueLock.lock();
        try{
           while (queue.size()==maxSize){
               try{
                   fullWaitSet.await();
               }catch (Exception e){
                   e.printStackTrace();
               }
           }
           queue.addLast(runnable);
           emptyWaitSet.signal();
        }finally {
            queueLock.unlock();
        }
    }

    public T popTask(){
        queueLock.lock();
        try {
            while (queue.isEmpty()){
                try{
                    emptyWaitSet.await();
                }catch (Exception e){
                    e.printStackTrace();
                }
            }
            T t=queue.removeFirst();
            fullWaitSet.signal();
            System.out.println("拿到");
            return t;
        }finally {
            queueLock.unlock();
        }
    }

}
class worker extends Thread{

}
