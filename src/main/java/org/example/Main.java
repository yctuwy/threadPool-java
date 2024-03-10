package org.example;

import org.example.util.*;

import java.util.concurrent.TimeUnit;

public class Main {
    public static void main(String[] args) {
        ThreadPool threadPool1=new ThreadPool(2, 1000L, TimeUnit.MICROSECONDS,10);
        for (int i=0;i<15;i++){
            int finalI = i;
            threadPool1.execute(()->{
                System.out.println(finalI);
            });
        }
    }
}