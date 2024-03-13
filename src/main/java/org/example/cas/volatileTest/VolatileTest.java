package org.example.cas.volatileTest;

import lombok.extern.slf4j.Slf4j;

@Slf4j(topic = "volatile测试")
public class VolatileTest {
    public static void main(String[] args) throws InterruptedException {
        Thread thread=new Thread(()->{
            new Test().demo();
        },"测试线程");
        thread.start();
        Thread.sleep(1000);
        log.debug("runT复制为false");
        Test.runT=false;
    }
}
