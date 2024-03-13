package org.example.cas.volatileTest;

public class Test {
    public static volatile boolean  runT=true;
    public void demo(){
        while (runT){
        }
    }
}
