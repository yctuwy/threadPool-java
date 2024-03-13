package org.example.mypool;

import org.example.mypool.ThreadPool;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        ThreadPool threadPool1=new ThreadPool(2, 1000L, TimeUnit.MICROSECONDS,10,
                ((queue, task) -> {
                    //1 死等
//                    queue.addTask(task);
                    //2 超时等待后放弃
                    //3 放弃任务
                    //4 抛出异常
//                    throw new RuntimeException("任务执行失败"+task);
                    //调用只自己执行
                    task.run();
                }));
        for (int i=0;i<15;i++){
            int finalI = i;
            threadPool1.execute(()->{
                System.out.println(finalI);
            });
        }
    }
}