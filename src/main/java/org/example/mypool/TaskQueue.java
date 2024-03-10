package org.example.mypool;

import java.util.ArrayDeque;
import java.util.Deque;
import java.util.concurrent.locks.Condition;
import java.util.concurrent.locks.ReentrantLock;

public class TaskQueue<T>{
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
                    System.out.println("等待队列加入");
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
            return t;
        }finally {
            queueLock.unlock();
        }
    }

    public void tryPut(RejectPolicy<T> rejectPolicy,T task){
        queueLock.lock();
        try {
            if (queue.size()==maxSize){
                rejectPolicy.reject(this,task);
            }
        }finally {
            queue.addLast(task);
            queueLock.unlock();
        }
    }

}
