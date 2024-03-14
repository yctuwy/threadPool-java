package org.example.jdkpool;

import lombok.extern.slf4j.Slf4j;

import java.util.Calendar;
import java.util.Date;
import java.util.Timer;
import java.util.TimerTask;

//timer 定时任务测试
public abstract class TimerTest {
    public static void main(String[] args) throws InterruptedException {
        Timer timer = new Timer("心跳检测",true);
        Calendar currentDateTime = Calendar.getInstance();
        Date time=currentDateTime.getTime();
        Long result = 1000L;
        //每天定时任务每隔result时间执行一次
        timer.schedule(new TimerTaskTest(), time, result);
        timer.schedule(new TimerTaskTest1(), time, result);
        Thread.sleep(3000);
    }

    public abstract void run();
}
@Slf4j(topic = "心跳检测")
class TimerTaskTest extends TimerTask {
    @Override
    public void run(){
        int k=1/0;
        log.debug("123");
    }
}

@Slf4j(topic = "心跳检测")
class TimerTaskTest1 extends TimerTask {
    @Override
    public void run(){
        log.debug("123");
    }
}