package org.example.cas;

import static org.example.util.cas.Account.demo;

public class TestAccount {
    private static void unsafeTest(){
        Account account=new AccountUnsafe(10000);
        demo(account);
    }
    private static void casTest(){
        Account account=new AccountCas(10000);
        demo(account);
    }
    public static void main(String[] args) {
        //加锁状态
        unsafeTest();
        //cas实现
        casTest();
    }
}
