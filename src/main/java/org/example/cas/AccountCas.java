package org.example.cas;

import java.util.concurrent.atomic.AtomicInteger;

public class AccountCas implements Account{
    private AtomicInteger balance;
    public AccountCas(int balance){
        this.balance=new AtomicInteger(balance);
    }
    @Override
    public Integer getBalance() {
        return balance.get();
    }

    @Override
    public void withdraw(Integer amount) {
        while (true){
            //获取余额最新值
            int prev=balance.get();
            //要修改的余额
            int next=prev-amount;
            //真正修改
            if (balance.compareAndSet(prev,next)){
                break;
            }
        }
    }
}
