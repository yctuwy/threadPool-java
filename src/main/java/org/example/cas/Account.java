package org.example.cas;

import java.util.ArrayList;
import java.util.List;

public interface Account {
    //获取余额
    Integer getBalance();
    //取款
    void withdraw(Integer amount);
    static void demo(Account account){
        List<Thread> ts=new ArrayList<>();
        for (int i=0;i<10000;i++){
            ts.add(new Thread(()->{
                account.withdraw(1);
            }));
        }
        long start=System.nanoTime();
        //开启线程
        ts.forEach(Thread::start);
        //线程回收
        ts.forEach(t->{
            try {
                t.join();
            }catch (InterruptedException e){
                e.printStackTrace();
            }
        });
        long end=System.nanoTime();
        System.out.println(account.getBalance()+"cost:"+(end-start)/1000_000+"ms");
    }
}
